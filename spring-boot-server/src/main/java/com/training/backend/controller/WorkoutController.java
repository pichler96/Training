package com.training.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.training.backend.model.*;
import com.training.backend.repository.WorkoutExerciseRepository;
import com.training.backend.repository.WorkoutRepository;
import com.training.backend.responses.WorkoutDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class WorkoutController {

	@Autowired
	WorkoutRepository workoutRepository;


	@Autowired
	WorkoutExerciseRepository workoutExerciseRepository;

	@GetMapping("/workouts")
	public ResponseEntity<List<Workout>> getAllWorkouts(@RequestParam(required = false) String title) {
		try {
			List<Workout> workouts = new ArrayList<Workout>();

			if (title == null)
				workoutRepository.findAll().forEach(workouts::add);
			else
				workoutRepository.findByTitleContaining(title).forEach(workouts::add);

			if (workouts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(workouts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/workouts/{id}")
	public ResponseEntity<WorkoutDetailResponse> getWorkoutById(@PathVariable("id") long id) {
		Optional<Workout> workout = workoutRepository.findById(id);

		if (!workout.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<WorkoutExercise> workoutExercises = workoutExerciseRepository.findByWorkoutId(workout.get().getId());

		return new ResponseEntity<>(new WorkoutDetailResponse(workout.get(), workoutExercises), HttpStatus.OK);
	}

	@PostMapping("/workouts")
	public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
		try {
			Workout _workout = workoutRepository
					.save(new Workout(workout.getTitle()));//hier
			return new ResponseEntity<>(_workout, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/workouts/{id}")
	public ResponseEntity<Workout> updateWorkout(@PathVariable("id") long id, @RequestBody Workout workout) {
		Optional<Workout> tutorialData = workoutRepository.findById(id);

		if (tutorialData.isPresent()) {
			Workout _workout = tutorialData.get();
			_workout.setTitle(workout.getTitle());

			//_workout.setDescription(workout.getDescription());
			return new ResponseEntity<>(workoutRepository.save(_workout), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/workouts/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deleteWorkout(@PathVariable("id") long id) {
		try {
			workoutExerciseRepository.deleteByWorkoutId(id);
			workoutRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/workouts")
	@Transactional
	public ResponseEntity<HttpStatus> deleteAllWorkouts() {
		try {
			workoutExerciseRepository.deleteAll();
			workoutRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/workoutExercise")
	public ResponseEntity<WorkoutExercise> createWorkoutExercise(@RequestBody CreateWorkoutExerciseRequest workoutExerciseRequest) {
		try {
			WorkoutExercise _workoutExercise = workoutExerciseRepository
					.save(new WorkoutExercise(
							workoutExerciseRequest.repetitions,
							workoutExerciseRequest.sets,
							workoutExerciseRequest.workoutId,
							workoutExerciseRequest.exerciseId));
			return new ResponseEntity<>(_workoutExercise, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/workoutExercise/{workoutId}")
	public ResponseEntity<List<ExerciseResponse>> getWorkoutExercisesByWorkout(@PathVariable("workoutId") long workoutId){
		List<WorkoutExercise> workoutExercises = workoutExerciseRepository.findByWorkoutId(workoutId);
		List<ExerciseResponse> response = new ArrayList<>();
		workoutExercises.forEach(workoutExercise -> {
			response.add(new ExerciseResponse(workoutExercise.getRepetitions(), workoutExercise.getSets(), workoutExercise.getExercise()));
		});
		if (!workoutExercises.isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
