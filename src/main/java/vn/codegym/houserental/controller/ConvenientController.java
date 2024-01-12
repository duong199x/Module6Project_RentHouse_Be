package vn.codegym.houserental.controller;

import vn.codegym.houserental.model.Convenient;
import vn.codegym.houserental.service.impl.ConvenientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convenient")
@CrossOrigin("*")
public class ConvenientController {
    @Autowired
    private ConvenientServiceImpl convenientService;

    @GetMapping("")
    public ResponseEntity<List<Convenient>> showAllImage() {
        List<Convenient> convenientList = (List<Convenient>) convenientService.findAllByDeleteFlag(false);
        if (convenientList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(convenientList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Convenient> create(@RequestBody Convenient convenient) {
        return new ResponseEntity<>(convenientService.save(convenient), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Convenient> delete(@PathVariable Long id) {
        convenientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
