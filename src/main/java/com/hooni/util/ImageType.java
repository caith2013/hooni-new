
package com.hooni.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public enum ImageType
{
    JPEG(MimeType.IMAGE_JPEG, ".jpg"),
    GIF(MimeType.IMAGE_GIF, ".gif"),
    PNG(MimeType.IMAGE_PNG, ".png");

    public static final ImageType[] VALUES = ImageType.values();
    
    public static ImageType getByMimeType(final String mimeType)
    {
        ImageType t = _mimeType2ImageType.get(mimeType.toLowerCase());
        if (t != null) return t;
        throw new IllegalArgumentException("image type not supported: " + mimeType);
    }
    
    public static ImageType getByFileName(final String fileName)
    {
        final String extension = '.'+FilenameUtils.getExtension(fileName);
        for (ImageType type : VALUES)
            if (type._extension.equals(extension))
                return type;
        throw new IllegalArgumentException("image type not supported: " + extension);
    }
    
    
    public MimeType getMimeType() { return _mimeType; }
    public String getJaiType() { return name(); }
    public String getExtension() { return _extension; }
    
    
    private ImageType(MimeType mimeType, String extension)
    {
        _mimeType = mimeType;
        _extension = extension;
    }
    
    
    private MimeType _mimeType;
    private String _extension;
    
    private static Map<String, ImageType> _mimeType2ImageType;
    
    static
    {
        _mimeType2ImageType = new HashMap<String,ImageType>();
        
        for (ImageType type : VALUES)
            _mimeType2ImageType.put(type.getMimeType().toString().toLowerCase(), type);

        _mimeType2ImageType.put("image/jpg", JPEG);
        _mimeType2ImageType.put("image/pjpg", JPEG);
        _mimeType2ImageType.put("image/pjpeg", JPEG);
        _mimeType2ImageType.put("image/x-jg", JPEG);
        
        _mimeType2ImageType.put("image/x-png", PNG);
    }
    
}
