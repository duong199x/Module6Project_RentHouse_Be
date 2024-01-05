package org.example.projectmodule6renthousebe.repository;

import org.example.projectmodule6renthousebe.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}