package com.hooni.web.util;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.fileupload.FileItem;

public interface HooniImageFileInterface 
{
	boolean writeToFileSystem(HashMap<String,FileItem> images, long id, ImagePath path) throws IOException;
}
