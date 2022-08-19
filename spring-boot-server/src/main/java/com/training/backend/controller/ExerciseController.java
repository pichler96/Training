package com.training.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.training.backend.model.Exercise;
import com.training.backend.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ExerciseController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @GetMapping("/exercises")
    public ResponseEntity<List<Exercise>> getAllExercises(@RequestParam(required = false) String name) {
        try {
            List<Exercise> exercises = new ArrayList<Exercise>();

            if (name == null)
                exerciseRepository.findAll().forEach(exercises::add);
            else
                exerciseRepository.findByNameContaining(name).forEach(exercises::add);

            if (exercises.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exercises/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable("id") long id) {
        Optional<Exercise> exerciseData = exerciseRepository.findById(id);
        return exerciseData.map(exercise -> new ResponseEntity<>(exercise, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
