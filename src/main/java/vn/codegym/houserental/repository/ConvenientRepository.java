package vn.codegym.houserental.repository;

import vn.codegym.houserental.model.Convenient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenientRepository extends JpaRepository<Convenient, Long> {
    Iterable<Convenient> findAllByDeleteFlag(boolean deleteFlag);
}
