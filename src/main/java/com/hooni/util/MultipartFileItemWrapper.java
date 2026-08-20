package com.hooni.util;

import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileItemHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * Minimal FileItem wrapper for MultipartFile to work with HooniImage.
 * Only implements methods that HooniImage needs.
 */
public class MultipartFileItemWrapper implements FileItem {
    
    private final MultipartFile multipartFile;
    
    public MultipartFileItemWrapper(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return multipartFile.getInputStream();
    }
    
    @Override
    public String getContentType() {
        return multipartFile.getContentType();
    }
    
    @Override
    public String getName() {
        return multipartFile.getOriginalFilename();
    }
    
    @Override
    public String getFieldName() {
        return multipartFile.getName();
    }
    
    @Override
    public boolean isFormField() {
        return false;
    }
    
    @Override
    public boolean isInMemory() {
        return multipartFile.getSize() < 1048576;
    }
    
    @Override
    public long getSize() {
        return multipartFile.getSize();
    }
    
    @Override
    public byte[] get() {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public String getString(Charset charset) {
        try {
            return new String(multipartFile.getBytes(), charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public String getString() {
        try {
            return new String(multipartFile.getBytes(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileItem setFormField(boolean state) {
        return this;
    }

    @Override
    public FileItem setFieldName(String name) {
        return this;
    }

    public FileItem setContentType(String contentType) {
        return this;
    }

    @Override
    public FileItem write(Path file) {
        try {
            multipartFile.transferTo(file.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public FileItem delete() {
        return this;
    }

    public String getCharSet() {
        return "UTF-8";
    }

    public FileItem setCharSet(String charset) {
        return this;
    }
    
    @Override
    public OutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public FileItemHeaders getHeaders() {
        return null;
    }
    
    @Override
    public FileItem setHeaders(FileItemHeaders headers) {
        return this;
    }
}
