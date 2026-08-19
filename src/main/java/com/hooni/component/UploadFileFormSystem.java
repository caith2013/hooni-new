package com.hooni.component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileUploadException;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UploadFileFormSystem {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadFileFormSystem.class);
	
	private JakartaServletFileUpload servletFileUpload;
	private TreeMap<String, String> formFields;
	private TreeMap<String, FileItem> uploadFiles;

	private static final long UPLOAD_SIZE_LIMIT = 8500000; // about 8M

	private final HttpServletRequest request;

	public UploadFileFormSystem(HttpServletRequest request) {
		this.request = request;

		DiskFileItemFactory factory = DiskFileItemFactory.builder().get();
		servletFileUpload = new JakartaServletFileUpload(factory);
		servletFileUpload.setMaxFileSize(UPLOAD_SIZE_LIMIT);

		
		this.formFields = new TreeMap<>(new KeyComparator());
		this.uploadFiles = new TreeMap<>(new KeyComparator());
		
		//processForm();
	}
	
	public JakartaServletFileUpload getServletFileUpload() {
		return servletFileUpload;
	}
	
	public List<FileItem> getFormItems(HttpServletRequest req) {
		try {
			return servletFileUpload.parseRequest(req);
		} catch (FileUploadException e) {
			logger.error("Error parsing form items", e);
		}
		return Collections.emptyList();
	}
	

	public TreeMap<String, String> getFormFields() {
		return formFields;
	}
	
	public TreeMap<String, FileItem> getUploadFiles() {
		return uploadFiles;
	}
	
	public static String FileExt(FileItem fileItem) {
		String fileName = fileItem.getName();
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0) {
			return fileName.substring(dotIndex);
		}
		return "";
	}
	
	public void processForm() {
		try {
			List<FileItem> fileItemsList = servletFileUpload.parseRequest(request);
			Collections.sort(fileItemsList, new FileItemComparator());
			
			for (FileItem fileItem : fileItemsList) {
				if (fileItem.isFormField()) {
					// The file item contains a simple name-value pair of a form field
					formFields.put(fileItem.getFieldName(), fileItem.getString(StandardCharsets.UTF_8));
				} else if (fileItem.getSize() > 0) {
					// The file item contains an uploaded file
					uploadFiles.put(fileItem.getFieldName(), fileItem);
				}
			}
		} catch (Exception ex) {
			logger.error("Error processing form", ex);
		}
	}
	
	public static class FileItemComparator implements Comparator<FileItem> {
		@Override
		public int compare(FileItem o1, FileItem o2) {
			return o1.getFieldName().compareTo(o2.getFieldName());
		}
	}
	
	public static class KeyComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	}
}
