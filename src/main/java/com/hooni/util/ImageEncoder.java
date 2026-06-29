// $Id: ImageEncoder.java 12380 2008-06-11 22:43:11Z rodrigo $

package com.hooni.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;


/**
 * This class encodes JPEG images with optional metadata support.
 * Uses Java's standard ImageIO for JPEG encoding.
 *
 * Used by {@link ImageUtil} for encoding JPEG with {@link
 * ImageMetadata}.
 */
public class ImageEncoder
{

    public ImageEncoder(BufferedImage img, ImageMetadata metadata)
    {
        if (null != img){
            _img = img;
            _metadata = metadata;
        }
        else
            throw new IllegalArgumentException();
    }

    public void encode(OutputStream out)
        throws IOException 
    {
        // Note: EXIF metadata handling would require additional library support
        // For now, we use ImageIO which handles basic JPEG encoding
        ImageIO.write(_img, "jpg", out);
    }


    private final BufferedImage _img;
    private final ImageMetadata _metadata;
}
