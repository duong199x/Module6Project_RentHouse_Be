package vn.codegym.houserental.service.impl;

import vn.codegym.houserental.model.Convenient;
import vn.codegym.houserental.repository.ConvenientRepository;
import vn.codegym.houserental.service.ConvenientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConvenientServiceImpl implements ConvenientService {
    @Autowired
    private ConvenientRepository convenientRepository;
    @Override
    public Iterable<Convenient> findAll() {

        return convenientRepository.findAll();
    }

    @Override
    public Iterable<Convenient> findAllByDeleteFlag(boolean deleteFlag) {
        return convenientRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<Convenient> findOneById(Long id) {
        return convenientRepository.findById(id);
    }

    @Override
    public Convenient save(Convenient convenient) {
        return convenientRepository.save(convenient);
    }

    @Override
    public void delete(Long id) {
        Optional<Convenient> convenient = convenientRepository.findById(id);
        if (convenient.isPresent()) {
            convenient.get().setDeleteFlag(true);
            convenientRepository.save(convenient.get());
        }
    }
}
