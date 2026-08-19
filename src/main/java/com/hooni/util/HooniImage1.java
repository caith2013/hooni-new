package com.hooni.util;

import com.hooni.web.util.ImagePath;
import org.apache.commons.fileupload2.core.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class HooniImage1 {

	public HooniImage1(FileItem imageItem, String imageName, ImagePath imagePath) throws IOException
	{
		try
		{
			_type = ImageType.getByMimeType(imageItem.getContentType());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		_basePath = imagePath.getPath();
		_imgName = imageName;
		_original = imageItem.get();
		
		_fullSize = new File(_basePath + File.separatorChar + imageName + "_full" + ImageType.JPEG);
		_thumbNail = new File(_basePath + File.separatorChar + imageName + "_thumb" + ImageType.JPEG);
		_miniNail = new File(_basePath + File.separatorChar + imageName + "_mini" + ImageType.JPEG);
		/*
		_fullSize = scaleImage(imageItem, FULLSIZE_MAX_WIDTH, FULLSIZE_MAX_HEIGHT);
		_thumbNail = scaleImage(imageItem, THUMBNAIL_MAX_WIDTH, THUMBNAIL_MAX_HEIGHT, THUMBS_LEFT_CROP, THUMBS_TOP_CROP);
		_miniNail = scaleImage(imageItem, MINI_MAX_WIDTH, MINI_MAX_HEIGHT, THUMBS_LEFT_CROP, THUMBS_TOP_CROP);
		*/
	}

	private byte[] scaleImage(FileItem fileImg, int maxWidth, int maxHeight) throws IOException
	{
		BufferedImage img = null;
		BufferedImage oimg = null;

		try {
			img = makePlanarImage(fileImg.get(), _type);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			_log.info("!!! Fail to create the HooniImage with {}", fileImg.getName());
		}

		if (img.getWidth() <= maxWidth && img.getHeight() <= maxHeight)
			return fileImg.get();
		else
		{
			oimg = scale(img, maxWidth, maxHeight);
			return encodeToByte(oimg);
		}
	}
	private byte[] encodeToByte(BufferedImage img) throws IOException
	{
		ImageMetadata md = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		if (_type != ImageType.JPEG)
		{
			md = new ImageMetadata(_original);
			encode2Jpeg(img, os, md);
		}
		else
		{
			encode(img, os, ImageType.JPEG);
		}
		return os.toByteArray();
	}
	
	private byte[] scaleImage(FileItem fileImg, int maxWidth, int maxHeight, int leftCropPercent, int topCropPercent) throws IOException
	{
		BufferedImage img = null;
		try {
			img = makePlanarImage(fileImg.get(), _type);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			_log.info("!!! Fail to create the HooniImage with {}", fileImg.getName());
		}

		img = lazyScaleDownAndCrop(img, maxWidth, maxHeight, leftCropPercent, topCropPercent);

		return encodeToByte(img);
	}
	
	public static BufferedImage lazyScaleDownAndCrop(BufferedImage img, int maxWidth, int maxHeight, int leftCropPercent, int topCropPercent)
    {
        int relativeWidth = img.getWidth()*maxHeight;
        int relativeHeight = img.getHeight()*maxWidth;
        if (relativeWidth > relativeHeight)
        {
            // original image is wider than thumbnail
            if (img.getHeight() > maxHeight)
                // scale vertically
                img = scaleToHeight(img, maxHeight);
            if (img.getWidth() > maxWidth)
                // crop horizontally
                img = crop(img, (img.getWidth() - maxWidth) * leftCropPercent / 100, 0, maxWidth, img.getHeight());
        }
        else if (relativeWidth < relativeHeight)
        {
            // original image is taller than thumbnail
            if (img.getWidth() > maxWidth)
                // scale horizontally
                img = scaleToWidth(img, maxWidth);
            if (img.getHeight() > maxHeight)
                // crop vertically
                img = crop(img, 0, (img.getHeight() - maxHeight) * topCropPercent / 100, img.getWidth(), maxHeight);
        }
        else
            // original image has the same ratio
            img = lazyScaleDown(img, maxWidth, maxHeight);
        
        return img;
    }
	
	
	public static BufferedImage scaleToWidth(BufferedImage img, final int width)
	{
		return scale(img, width, Integer.MAX_VALUE);
	}

	public static BufferedImage lazyScaleDown(BufferedImage image, int maxWidth, int maxHeight)
	{
		if (image.getWidth() > maxWidth || image.getHeight() > maxHeight)
			image = scale(image, maxWidth, maxHeight);

		return image;
	}
	 
	public static BufferedImage crop(BufferedImage img, int x, int y, int width, int height)
	{
		return ImageUtil.crop(img, x, y, width, height);
	}

	public static BufferedImage scaleToHeight(BufferedImage img, final int height)
	{
		return scale(img, Integer.MAX_VALUE, height);
	}

	 public static BufferedImage scale(BufferedImage img, final int maxWidth, final int maxHeight)
	 {
		 double  factor = getScaleFactor(img, maxWidth, maxHeight);
		 return scale(img, factor);
	 }
	 
	 private static double getScaleFactor(BufferedImage img, int width, int height)
	 {
		 double  imageWidth = img.getWidth();
		 double imageHeight = img.getHeight();
		 double whRatio = imageWidth/imageHeight;

		 double y = width/whRatio; // x=width

		 if ((int)y <= height)
			 return width/imageWidth;

		 return height/imageHeight;
	 }

	 public static BufferedImage scale(BufferedImage img, final double factor)
	 {
		 return ImageUtil.scale(img, factor);
	 }
	
	public static void encode(BufferedImage img, OutputStream out, ImageType type)
	throws IOException
	{
		ImageUtil.encode(img, out, type);
	}

	public static void encode2Jpeg(BufferedImage img, OutputStream out, ImageMetadata metadata)
	throws IOException
	{
		if (null != metadata)
		{
			ImageEncoder im = new ImageEncoder(img,metadata);
			im.encode(out);
		}
		else
			encode(img,out,ImageType.JPEG);
	}
		
	public static BufferedImage makePlanarImage(final byte[] content, ImageType imageType)
	throws IOException
	{
	  return ImageUtil.makePlanarImage(content, imageType);
	}

	
	
	
	static BufferedImage removeTransparency(BufferedImage img)
	{
		return ImageUtil.removeTransparency(img);
	}

	public String getBasePath()
	{
		return _basePath;
	}
	
	public String getImageName()
	{
		return _imgName;
	}
	
	public ImageType getImageType()
	{
		return _type;
	}
	
	public File getFullSizeImage()
	{
		return _fullSize;
	}
	
	public File getThumbNail()
	{
		return _thumbNail;
	}
	
	
	public File getMiniNail()
	{
		return _miniNail;
	}
	
	public byte[] getBytes()
	{
		return _original;
	}
	
	private String _basePath;
	private String _imgName;
	private ImageType _type;
	private File _fullSize = null;
	private File _thumbNail = null;
	private File _miniNail = null;
	private byte[] _original;
	
	
	private static final int FULLSIZE_MAX_WIDTH = 500;
    private static final int FULLSIZE_MAX_HEIGHT = 500;
    
	private static final int THUMBNAIL_MAX_WIDTH = 100;
    private static final int THUMBNAIL_MAX_HEIGHT = 75;

    private static final int MINI_MAX_WIDTH = 25;
    private static final int MINI_MAX_HEIGHT = 25;
    
    private static final int THUMBS_TOP_CROP = 5;  // top-aligned at 5%
    private static final int THUMBS_LEFT_CROP = 50;
	
	private final static int[] BAND_SELECT_DROP_TRANS = { 0, 1, 2 };
	
	
	private static final Logger _log = LoggerFactory.getLogger(HooniImage1.class);
}
