package vn.codegym.houserental.service.impl;

import jakarta.persistence.EntityNotFoundException;
import vn.codegym.houserental.model.Convenient;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.Image;
import vn.codegym.houserental.repository.ConvenientRepository;
import vn.codegym.houserental.repository.HouseRepository;
import vn.codegym.houserental.requests.CreateHouseRequest;
import vn.codegym.houserental.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private ConvenientRepository convenientRepository;
    @Autowired
    private ImageServiceImpl imageService;

    @Override
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public Iterable<House> findAllByDeleteFlag(boolean deleteFlag) {
        return houseRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<House> findOneById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public House save(House house) {
        return null;
    }

    public House saveOrUpdateHouse(CreateHouseRequest request) {
        if (request.getId() == null) {
            return saveNewHouse(request);
        } else {
            return updateExistingHouse(request);
        }
    }

    private House saveNewHouse(CreateHouseRequest request) {
        House house = new House();
        setCommonHouseProperties(house,request);
        return houseRepository.save(house);
    }
    private House updateExistingHouse(CreateHouseRequest request) {
        Long houseId = request.getId();
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException("House with id " + houseId + " not found."));
        // Update properties for an existing house
        setCommonHouseProperties(house,request);

        return houseRepository.save(house);
    }
    private Set<Convenient> getConvenientsFromRequest(List<Long> convenientIds) {
        Set<Convenient> convenients = new HashSet<>();
        if (convenientIds != null && !convenientIds.isEmpty()) {
            for (Long convenientId : convenientIds) {
                Convenient convenient = convenientRepository.findById(convenientId)
                        .orElseThrow(() -> new EntityNotFoundException("Convenient with id " + convenientId + " not found."));
                convenients.add(convenient);
            }
        }
        return convenients;
    }

    private void setCommonHouseProperties(House house, CreateHouseRequest request) {
        // Set properties common to both new and existing houses
        house.setName(request.getName());
        house.setCategory(request.getCategory());
        house.setBedRoom(request.getBedRoom());
        house.setDescription(request.getDescription());
        house.setKitchen(request.getKitchen());
        house.setPricePerNight(request.getPrice());
        house.setUser(request.getUser());
        house.setLocation(request.getLocation());
        house.setBathRoom(request.getBathRoom());
        house.setLivingRoom(request.getLivingRoom());

        // Set convenients
        house.setConvenients(getConvenientsFromRequest(request.getConvenientIds()));
    }



    //    public House save(CreateHouseRequest request) {
//
//        House house = new House();
//        house.setName(request.getName());
//        house.setCategory(request.getCategory());
//        house.setBedRoom(request.getBedRoom());
//        house.setDescription(request.getDescription());
//        house.setKitchen(request.getKitchen());
//        house.setPrice(request.getPrice());
//        house.setUser(request.getUser());
//        house.setLocation(request.getLocation());
//        house.setBathRoom(request.getBathRoom());
//        house.setLivingRoom(request.getLivingRoom());
//        Set<Convenient> newConvenients = new HashSet<>();
//        if (request.getConvenientIds() != null && !request.getConvenientIds().isEmpty()) {
//            for (Long convenientId : request.getConvenientIds()) {
//                Convenient convenient = convenientRepository.findById(convenientId)
//                        .orElseThrow(() -> new EntityNotFoundException("Convenient with id " + convenientId + " not found."));
//                newConvenients.add(convenient);
//            }
//        }
//        house.setConvenients(newConvenients);
//        return houseRepository.save(house);
//    }
    @Async
    public CompletableFuture<Void> saveImageListAsync(House house, List<String> imageList) {
        imageList.forEach(image -> {
            Image imageEntity = new Image();
            imageEntity.setHouse(house);
            imageEntity.setImage(image);
            imageService.save(imageEntity);
        });

        return CompletableFuture.completedFuture(null);
    }
    @Override
    public void delete(Long id) {
        Optional<House> house = houseRepository.findById(id);
        if (house.isPresent()) {
            house.get().setDeleteFlag(true);
            houseRepository.save(house.get());
        }
    }

    @Override
    public Page<House> findAll(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }

    @Override
    public Page<House> getAllHousesSortedByPriceUp(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("price").ascending());
        return houseRepository.findAll(pageRequest);
    }

    @Override
    public Page<House> findAllByCategoryId(Pageable pageable, Long categoriesId) {
        return houseRepository.findAllByCategoryId(pageable, categoriesId);
    }

    @Override
    public Iterable<House> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag) {
        return houseRepository.findAllByUserIdAndDeleteFlag(userId,deleteFlag);
    }

    @Override
    public Iterable<House> findByNameContainsIgnoreCaseAndDeleteFlag(String name, boolean deleteFlag) {
        return houseRepository.findByNameContainsIgnoreCaseAndDeleteFlag(name,deleteFlag);
    }

//    @Transactional
//    public void addConvenientsToHouse(Long houseId, List<Long> convenientIds) {
//        Optional<House> houseOptional = houseRepository.findById(houseId);
//        if (houseOptional.isPresent()) {
//            House house = houseOptional.get();
//            Set<Convenient> convenients = house.getConvenients();
//            convenients.addAll(convenient.findAllById(convenientIds));
//
//            houseRepository.save(house);
//        } else {
//            throw new RuntimeException("House not found");
//        }
//    }
}
