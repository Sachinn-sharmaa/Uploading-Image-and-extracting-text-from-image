package com.backendUpload.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backendUpload.Entity.UploadImageFile;

public interface UploadImageRepository extends JpaRepository<UploadImageFile, Long>{

}
