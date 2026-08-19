package com.hooni.service;

import com.hooni.component.HooniFileSystem;
import com.hooni.component.UploadFileFormSystem;
import com.hooni.web.util.ImagePath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HooniImageService {

    @Value("${IMAGES_PATH:/images/}")
    String IMAGE_BASE_PATH;

    final HooniFileSystem hooniFileSystem;
    final UploadFileFormSystem uploadFileFormSystem;

    public HooniImageService(HooniFileSystem hooniFileSystem, UploadFileFormSystem uploadFileFormSystem) {
        this.hooniFileSystem = hooniFileSystem;
        this.uploadFileFormSystem = uploadFileFormSystem;
    }
    public void setImageBasePath(String imageBasePath) {
        this.hooniFileSystem.setBaseDir(imageBasePath);
    }
    public void setImagePath(ImagePath imagePath) {
        this.hooniFileSystem.setBaseDir(IMAGE_BASE_PATH + imagePath.name().toLowerCase());
    }

}
