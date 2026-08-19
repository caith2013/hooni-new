package com.hooni.web.util;

import org.apache.commons.fileupload2.core.FileItem;

import java.io.IOException;
import java.util.HashMap;


public interface HooniImageFileInterface 
{
	boolean writeToFileSystem(HashMap<String, FileItem> images, long id, ImagePath path) throws IOException;
}
