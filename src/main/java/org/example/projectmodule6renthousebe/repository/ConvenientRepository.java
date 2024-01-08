package org.example.projectmodule6renthousebe.repository;

import org.example.projectmodule6renthousebe.model.Convenient;
import org.example.projectmodule6renthousebe.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenientRepository extends JpaRepository<Convenient, Long> {
    Iterable<Convenient> findAllByDeleteFlag(boolean deleteFlag);
}
