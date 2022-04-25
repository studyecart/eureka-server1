package com.example.demo.controller;

import com.example.demo.repo.DemoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.repo.*;
import com.example.demo.model.*;
@CrossOrigin(origins = "http://localhost:7676")
@RestController
@RequestMapping("/api")

public class DemoController {


    @Autowired
    DemoRepository DemoRepository;
        @GetMapping("/tutorials")
        public ResponseEntity<List<DemoModel>> getAllTutorials(@RequestParam(required = false) String title) {
            try {
                List<DemoModel> tutorials = new ArrayList<DemoModel>();
                if (title == null)
                    DemoRepository.findAll().forEach(tutorials::add);
                else
                    DemoRepository.findByTitleContaining(title).forEach(tutorials::add);
                if (tutorials.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(tutorials, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @GetMapping("/tutorials/{id}")
        public ResponseEntity<DemoModel> getTutorialById(@PathVariable("id") long id) {
            Optional<DemoModel> tutorialData = DemoRepository.findById(id);
            if (tutorialData.isPresent()) {
                return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        @PostMapping("/tutorials")
        public ResponseEntity<DemoModel> createTutorial(@RequestBody DemoModel tutorial) {
            try {
                DemoModel _tutorial = DemoRepository
                        .save(new DemoModel(tutorial.getTitle(), tutorial.getDescription(), false));
                return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @PutMapping("/tutorials/{id}")
        public ResponseEntity<DemoModel> updateTutorial(@PathVariable("id") long id, @RequestBody DemoModel tutorial) {
            Optional<DemoModel> tutorialData = DemoRepository.findById(id);
            if (tutorialData.isPresent()) {
                DemoModel _tutorial = tutorialData.get();
                _tutorial.setTitle(tutorial.getTitle());
                _tutorial.setDescription(tutorial.getDescription());
                _tutorial.setPublished(tutorial.isPublished());
                return new ResponseEntity<>(DemoRepository.save(_tutorial), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        @DeleteMapping("/tutorials/{id}")
        public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
            try {
                DemoRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @DeleteMapping("/tutorials")
        public ResponseEntity<HttpStatus> deleteAllTutorials() {
            try {
                DemoRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @GetMapping("/tutorials/published")
        public ResponseEntity<List<DemoModel>> findByPublished() {
            try {
                List<DemoModel> tutorials = DemoRepository.findByPublished(true);
                if (tutorials.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(tutorials, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
