
package com.hooni.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;


public class ImageMetadata
{

    public ImageMetadata(byte[] jpeg)
        throws IOException
    {
        this(new java.io.ByteArrayInputStream(jpeg));
    }

    /**
     * This contructor closes the argument input stream via the image
     * input stream it creates.
     */
    ImageMetadata(java.io.InputStream in)
        throws IOException
    {

        ImageInputStream iin = ImageIO.createImageInputStream(in);
        try 
        {
            for (Iterator iterator = ImageIO.getImageReaders(iin); iterator.hasNext();)
            {
                ImageReader reader = ((ImageReader) iterator.next());
                if (reader != null && (!_jpegExifIptcDysfunctional.contains(reader.getOriginatingProvider().getPluginClassName())) && 
                    reader.getOriginatingProvider().canDecodeInput(iin))
                {
                    reader.setInput(iin);

                    IIOMetadata md = reader.getImageMetadata(0);

                    _format = md.getNativeMetadataFormatName();

                    IIOMetadataNode node = (IIOMetadataNode)md.getAsTree(_format);

                    _exif = findJpegExif(node);

                    _iptc = findJpegIptc(node);

                    return;
                }
            }
            throw new IllegalStateException("Metadata not available.");
        }
        finally
        {
            iin.close();
        }
    }


    public String getFormat() { return _format;}

    public byte[] getExif() { return _exif;}
    public boolean hasExif() { return (null != _exif);}

    public byte[] getIptc() { return _iptc;}
    public boolean hasIptc() { return (null != _iptc);}



    private static byte[] findJpegExif(IIOMetadataNode node)
    {
        return find(node,0xE1);
    }

    private static byte[] findJpegIptc(IIOMetadataNode node)
    {
        return find(node,0xED);
    }

    private static byte[] find(IIOMetadataNode node, int type)
    {
        if (node.getLocalName().equals("unknown"))
        {
            String value = node.getAttribute("MarkerTag");
            if (null != value)
            {
                try 
                {
                    Integer test = new Integer(value);
                    if (type == test.intValue())
                        return (byte[])node.getUserObject();
                }
                catch (NumberFormatException ignore)
                {
                }
            }
        }
        if (node.hasChildNodes())
        {
            org.w3c.dom.NodeList list = node.getChildNodes();
            for (int cc = 0, count = list.getLength(); cc < count; cc++)
            {
                byte[] test = find( (IIOMetadataNode)list.item(cc), type);
                if (null != test)
                    return test;
            }
        }
        return null;
    }

    private final static Set<String> _jpegExifIptcDysfunctional = new HashSet<String>();
    static
    {
        _jpegExifIptcDysfunctional.add("com.sun.media.imageioimpl.plugins.jpeg.CLibJPEGImageReader");
        _jpegExifIptcDysfunctional.add("com.sun.media.imageioimpl.plugins.jpeg.CLibJPEGImageWriter");
    }


    private final String _format;
    private final byte[] _exif;
    private final byte[] _iptc;

}
