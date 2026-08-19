package com.hooni.component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.hooni.controller.FoodController;
import com.hooni.util.HooniImage;
import com.hooni.util.ImageMetadata;
import com.hooni.util.ImageType;
import com.hooni.web.util.ImagePath;
import org.apache.commons.fileupload2.core.FileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HooniFileSystem {
	
	private ArrayList<HooniImage> _addedImages = new ArrayList<HooniImage>();
	private File _base_dir;
	
	public HooniFileSystem(@Value("${hooni.image.base-path:}") String basePath) {
		if (basePath != null && !basePath.isBlank()) {
			setBaseDir(basePath);
		}
	}
	public HooniFileSystem(ImagePath path) {
		setBaseDir(path.getPath());
	}

	public HooniFileSystem() {
		// Default constructor
	}

	public void setBaseDir(ImagePath path) {
		setBaseDir(path.getPath());
	}

	public void setBaseDir(String basePath) {
		if (basePath == null || basePath.isBlank()) {
			throw new IllegalArgumentException("Base directory path cannot be null or empty");
		}
		_base_dir = new File(basePath);
		if (!_base_dir.exists()) {
			if (!_base_dir.mkdir()) {
				throw new IllegalStateException("Failed to create base directory: " + basePath);
			}
		}
	}
	public boolean saveFile(String fileName, FileItem fileItem) {
		if (_base_dir == null) {
			throw new IllegalStateException("Base directory not initialized. Call setBaseDir() first.");
		}
		File saveTo = new File(_base_dir, fileName + UploadFileFormSystem.FileExt(fileItem));
		try {
			fileItem.write(saveTo.toPath());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void rollBack(HooniImage hooniImage)
	{
		if (hooniImage.getOriginal().exists()) hooniImage.getOriginal().deleteOnExit();
		if (hooniImage.getFullSizeImage().exists()) hooniImage.getFullSizeImage().deleteOnExit();
		if (hooniImage.getThumbNail().exists()) hooniImage.getThumbNail().deleteOnExit();
		if (hooniImage.getMiniNail().exists()) hooniImage.getMiniNail().deleteOnExit();
	}
	
	public static void rollBack(List<HooniImage> images)
	{
		for (HooniImage image : images)
		{
			rollBack(image);
		}
	}
	
	public boolean writeToFileSystem(HooniImage hooniImage)
	{
		boolean original_ok, full_ok, thumb_ok, mini_ok;
	
		byte[] imageBytes = hooniImage.getBytes();
		
		original_ok = write(imageBytes, hooniImage.getOriginal());

		BufferedImage originalPlanarImage = hooniImage.getPlanarImage();
		BufferedImage pimg = HooniImage.lazyScaleDown(originalPlanarImage, HooniImage.FULLSIZE_MAX_WIDTH, HooniImage.FULLSIZE_MAX_HEIGHT);
		full_ok = write(imageBytes, pimg, hooniImage.getFullSizeImage(), hooniImage.getImageType());
		
		pimg = HooniImage.lazyScaleDownAndCrop(originalPlanarImage, HooniImage.THUMBNAIL_MAX_WIDTH, HooniImage.THUMBNAIL_MAX_HEIGHT, HooniImage.THUMBS_LEFT_CROP, HooniImage.THUMBS_TOP_CROP);
		thumb_ok = write(imageBytes, pimg, hooniImage.getThumbNail(), hooniImage.getImageType());
		
		pimg = HooniImage.lazyScaleDownAndCrop(originalPlanarImage, HooniImage.MINI_MAX_WIDTH, HooniImage.MINI_MAX_HEIGHT, HooniImage.THUMBS_LEFT_CROP, HooniImage.THUMBS_TOP_CROP);
		mini_ok = write(imageBytes, pimg, hooniImage.getMiniNail(), hooniImage.getImageType());
		
		if (original_ok && full_ok && thumb_ok && mini_ok) return true;
		else return false;
	}
	
	private boolean write(byte[] originalBytes, BufferedImage pimg, File file, ImageType type)
	{
		FileOutputStream os = null;
		try
		{
			if (!file.exists())
			{
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			
			if (type != ImageType.JPEG)
			{
				ImageMetadata md = new ImageMetadata(originalBytes);
				HooniImage.encode2Jpeg(pimg, os, md);
			}
			else
			{
				HooniImage.encode(pimg, os, type);
			}
			os.close();
			return true;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	private boolean write(byte[] img, File file)
	{
		FileOutputStream os = null;
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
				os = new FileOutputStream(file);
				os.write(img);
				os.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public boolean saveFile(File file, FileItem fileItem)
	{
		try 
		{
			fileItem.write(file.toPath());
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	
	}
	public boolean deleteProduct(long pid)
	{
		String ext = ImageType.JPEG.getExtension();
		File fullSize = new File(_base_dir, pid + "_full" +  ext);
		File thumbnail = new File(_base_dir, pid + "_thumb" +  ext);
		File miniNail = new File(_base_dir, pid + "_mini" + ext);
		
		if (!fullSize.exists() && !thumbnail.exists() &&  !miniNail.exists())
		{
			return true;
		}
		
		if (fullSize.delete() && thumbnail.delete() && miniNail.delete())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean deleteFile(File file)
	{
		return file.delete();
	}
	public boolean deleteFile(String fileName)
	{
		return new File(_base_dir, fileName).delete();
	}
	
	public File getFile(String fileName)
	{
		File file = new File(_base_dir, fileName);
		if (file.canRead()) return file;
		
		return null;
	}
	
	public File getBaseDir() {
		if (_base_dir == null) {
			throw new IllegalStateException("Base directory not initialized. Call setBaseDir() first.");
		}
		return _base_dir;
	}
	
	public boolean writeToFileSystem(TreeMap<String,FileItem> images, long id, ImagePath path) throws IOException
	{
		HooniImage hi;
		int i = 1;
		for (String key : images.keySet())
		{
			if (images.get(key).getContentType() != null)
			{
				if (path.equals(ImagePath.NEWS))
				{
					hi = new HooniImage(images.get(key), id+"_"+i, path);
					
				}
				else if (path.equals(ImagePath.FOOD))
				{
					if (key.equals(FoodController.SNAP_SHOT))
					{
						hi = new HooniImage(images.get(key), id+"", path);
					}
					else
					{
						hi = new HooniImage(images.get(key), id+"_"+FoodController.SNAP_STEP+"_"+i, path);
					}
					
				}
				else return false;
				
				_addedImages.add(hi);
				if (!writeToFileSystem(hi)) return false;
				i++;
			}
			
		}
		return true;
	}
	
	
	public ArrayList<HooniImage> getAddedImages() {
		return _addedImages;
	}
}
