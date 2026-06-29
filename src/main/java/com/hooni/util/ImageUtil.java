package com.hooni.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class ImageUtil
{
    public static void encode(BufferedImage img, OutputStream out, ImageType type)
        throws IOException
    {
        // For now write using ImageIO; advanced metadata handling omitted
        String fmt = type == ImageType.PNG ? "png" : "jpg";
        ImageIO.write(img, fmt, out);
    }

    public static void encode2Jpeg(BufferedImage img, OutputStream out, ImageMetadata metadata)
        throws IOException
    {
        // metadata handling omitted; simply encode as JPEG
        encode(img,out,ImageType.JPEG);
    }

    public static void renderText(BufferedImage img, String text, int x, int y, Font font, Color color)
    {
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawString(text, x, y);
        g2d.dispose();
    }

    public static BufferedImage makePlanarImage(final byte[] content, ImageType imageType)
        throws IOException
    {
        // Read into a BufferedImage; drop transparency later if needed
        return removeTransparency(ImageIO.read(new ByteArrayInputStream(content)));
    }

    public static Color rgbArrayToColor(byte[] rgb)
    {
        return new Color((0x000000ff & rgb[0]),
                         (0x000000ff & rgb[1]),
                         (0x000000ff & rgb[2]));
    }

    public static Color rgbStringToColor(String rgb)
    {
        int rgbInt = Integer.parseInt(rgb, 16);
        return new Color(rgbInt);
    }

    public static byte[] colorToRgbArray(Color color)
    {
        if (color == null) return null;

        byte[] rgb = new byte[3];
        rgb[0] = (byte)color.getRed();
        rgb[1] = (byte)color.getGreen();
        rgb[2] = (byte)color.getBlue();

        return rgb;
    }

    public static BufferedImage makePlanarImage(int width, int height, Color rgb)
    {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(rgb == null ? Color.WHITE : rgb);
        g.fillRect(0,0,width,height);
        g.dispose();
        return img;
    }

    public static BufferedImage makePlanarImage(int width, int height, byte[] band)
    {
        Color c = rgbArrayToColor(band);
        return makePlanarImage(width,height,c);
    }

    public static BufferedImage resize(BufferedImage img, int width, int height)
    {
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        return out;
    }

    public static BufferedImage scaleToWidth(BufferedImage img, final int width)
    {
        return scale(img, width, Integer.MAX_VALUE);
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

    public static BufferedImage scale(BufferedImage img, final double factor)
    {
        int newW = Math.max(1, (int)Math.round(img.getWidth() * factor));
        int newH = Math.max(1, (int)Math.round(img.getHeight() * factor));
        return resize(img, newW, newH);
    }

    public static BufferedImage crop(BufferedImage img, int x, int y, int width, int height)
    {
        return img.getSubimage(x, y, width, height);
    }

    public static BufferedImage lazyScaleDown(BufferedImage image, int maxWidth, int maxHeight)
    {
        if (image.getWidth() > maxWidth || image.getHeight() > maxHeight)
            image = scale(image, maxWidth, maxHeight);

        return image;
    }

    public static BufferedImage lazyScaleDownAndCrop(BufferedImage img, int maxWidth, int maxHeight, int leftCropPercent, int topCropPercent)
    {
        int relativeWidth = img.getWidth()*maxHeight;
        int relativeHeight = img.getHeight()*maxWidth;
        if (relativeWidth > relativeHeight)
        {
            if (img.getHeight() > maxHeight)
                img = scaleToHeight(img, maxHeight);
            if (img.getWidth() > maxWidth)
                img = crop(img, (img.getWidth() - maxWidth) * leftCropPercent / 100, 0, maxWidth, img.getHeight());
        }
        else if (relativeWidth < relativeHeight)
        {
            if (img.getWidth() > maxWidth)
                img = scaleToWidth(img, maxWidth);
            if (img.getHeight() > maxHeight)
                img = crop(img, 0, (img.getHeight() - maxHeight) * topCropPercent / 100, img.getWidth(), maxHeight);
        }
        else
            img = lazyScaleDown(img, maxWidth, maxHeight);

        return img;
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

    static BufferedImage removeTransparency(BufferedImage img)
    {
        if (img == null) return null;
        if (img.getColorModel().getTransparency() == Transparency.OPAQUE)
            return img;
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImg.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,newImg.getWidth(), newImg.getHeight());
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return newImg;
    }

    private final static int[] BAND_SELECT_DROP_TRANS = { 0, 1, 2 };
}
