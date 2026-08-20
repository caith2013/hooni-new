package com.hooni.component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private static final long FILE_SIZE_THRESHOLD = 1048576; // 1MB - files larger than this go to disk

	private final HttpServletRequest request;

	public UploadFileFormSystem(HttpServletRequest request) {
		this.request = request;

		try {
			// Create repository directory for temporary files
			Path tempDir = Files.createTempDirectory("hooni-upload-");
			File repository = tempDir.toFile();
			repository.deleteOnExit();

			// Configure DiskFileItemFactory using builder for commons-fileupload2
			DiskFileItemFactory factory = new DiskFileItemFactory.Builder()
					.setFile(repository)
					.get();

			servletFileUpload = new JakartaServletFileUpload(factory);
			servletFileUpload.setMaxFileSize(UPLOAD_SIZE_LIMIT);
			
			logger.debug("UploadFileFormSystem initialized with temp directory: {}", repository.getAbsolutePath());
		} catch (Exception e) {
			logger.error("Failed to initialize UploadFileFormSystem", e);
			throw new RuntimeException("Failed to initialize file upload system", e);
		}

		this.formFields = new TreeMap<>(new KeyComparator());
		this.uploadFiles = new TreeMap<>(new KeyComparator());
	}
	
	public JakartaServletFileUpload getServletFileUpload() {
		return servletFileUpload;
	}
	
	public List<FileItem> getFormItems(HttpServletRequest req) {
		try {
			logger.debug("Getting form items from request");
			List<FileItem> items = servletFileUpload.parseRequest(req);
			logger.debug("Retrieved {} items from request", items.size());
			return items;
		} catch (FileUploadException e) {
			logger.error("FileUploadException while parsing form items", e);
		} catch (Exception e) {
			logger.error("Unexpected error while parsing form items", e);
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
			String contentType = request.getContentType();
			logger.debug("Processing form with Content-Type: {}", contentType);
			
			if (contentType == null || !contentType.startsWith("multipart/form-data")) {
				logger.warn("Request is not multipart/form-data. Content-Type: {}", contentType);
				return;
			}

			List<FileItem> fileItemsList = servletFileUpload.parseRequest(request);
			logger.debug("Parsed {} file items from request", fileItemsList.size());
			
			if (fileItemsList.isEmpty()) {
				logger.warn("No file items parsed from request");
			}
			
			Collections.sort(fileItemsList, new FileItemComparator());
			
			for (FileItem fileItem : fileItemsList) {
				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName();
					String fieldValue = fileItem.getString(StandardCharsets.UTF_8);
					formFields.put(fieldName, fieldValue);
					logger.debug("Added form field: {} = {}", fieldName, fieldValue);
				} else if (fileItem.getSize() > 0) {
					String fieldName = fileItem.getFieldName();
					uploadFiles.put(fieldName, fileItem);
					logger.debug("Added upload file: {} (size: {} bytes)", fieldName, fileItem.getSize());
				}
			}
			
			logger.info("Form processing complete. Fields: {}, Files: {}", 
				formFields.size(), uploadFiles.size());
		} catch (FileUploadException e) {
			logger.error("FileUploadException during form processing", e);
		} catch (Exception ex) {
			logger.error("Unexpected error processing form", ex);
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
