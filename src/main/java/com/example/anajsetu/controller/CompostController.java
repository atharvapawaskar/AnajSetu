package com.example.anajsetu.controller;

import com.example.anajsetu.model.Compost;
import com.example.anajsetu.service.CompostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compost")
@CrossOrigin(origins = "http://localhost:3000")
public class CompostController {

    @Autowired
    private CompostService compostService;

    // CREATE COMPOST REDIRECTION
    @PostMapping
    public ResponseEntity<Compost> createCompost(@RequestBody Compost compost) {
        Compost savedCompost = compostService.createCompost(compost);
        return new ResponseEntity<>(savedCompost, HttpStatus.CREATED);
    }

    // GET ALL COMPOST
    @GetMapping
    public ResponseEntity<List<Compost>> getAllCompost() {
        List<Compost> compostList = compostService.getAllCompost();
        return new ResponseEntity<>(compostList, HttpStatus.OK);
    }

    // GET COMPOST BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompostById(@PathVariable int id) {
        Optional<Compost> compost = compostService.getCompostById(id);
        if (compost.isPresent()) {
            return new ResponseEntity<>(compost.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Compost record not found", HttpStatus.NOT_FOUND);
    }

    // GET COMPOST BY CENTER
    @GetMapping("/center/{centerId}")
    public ResponseEntity<List<Compost>> getCompostByCenter(@PathVariable int centerId) {
        List<Compost> compostList = compostService.getCompostByCenter(centerId);
        return new ResponseEntity<>(compostList, HttpStatus.OK);
    }

    // GET COMPOST BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Compost>> getCompostByStatus(@PathVariable String status) {
        List<Compost> compostList = compostService.getCompostByStatus(status);
        return new ResponseEntity<>(compostList, HttpStatus.OK);
    }

    // MARK AS COLLECTED
    @PutMapping("/{id}/collected")
    public ResponseEntity<?> markAsCollected(@PathVariable int id) {
        Compost compost = compostService.markAsCollected(id);
        if (compost != null) {
            return new ResponseEntity<>(compost, HttpStatus.OK);
        }
        return new ResponseEntity<>("Compost record not found", HttpStatus.NOT_FOUND);
    }

    // MARK AS PROCESSED
    @PutMapping("/{id}/processed/{biogasOutputKg}")
    public ResponseEntity<?> markAsProcessed(
            @PathVariable int id,
            @PathVariable int biogasOutputKg) {
        Compost compost = compostService.markAsProcessed(id, biogasOutputKg);
        if (compost != null) {
            return new ResponseEntity<>(compost, HttpStatus.OK);
        }
        return new ResponseEntity<>("Compost record not found", HttpStatus.NOT_FOUND);
    }

    // DELETE COMPOST
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompost(@PathVariable int id) {
        compostService.deleteCompost(id);
        return new ResponseEntity<>("Compost record deleted successfully", HttpStatus.OK);
    }

}