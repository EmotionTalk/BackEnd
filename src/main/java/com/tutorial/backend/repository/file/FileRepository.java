package com.tutorial.backend.repository.file;

import com.tutorial.backend.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>, FileQueryDSL {
    Optional<File> findByFileOriginName(String fileName);
}
