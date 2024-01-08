package org.example.projectmodule6renthousebe.repository;

import org.example.projectmodule6renthousebe.model.Category;
import org.example.projectmodule6renthousebe.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Iterable<Category> findAllByDeleteFlag(boolean deleteFlag);
}
