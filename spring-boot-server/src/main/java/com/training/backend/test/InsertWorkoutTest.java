package com.training.backend.test;


import com.training.backend.model.Exercise;
import com.training.backend.model.Workout;
import com.training.backend.model.WorkoutExercise;
import com.training.backend.repository.ExerciseRepository;
import com.training.backend.repository.WorkoutExerciseRepository;
import com.training.backend.repository.WorkoutRepository;
//import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class InsertWorkoutTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Test
    public void insertWorkoutTest(){
        Workout workout = new Workout("Chest Workout");
        Workout savedWorkout = workoutRepository.save(workout);
        Assert.assertEquals(savedWorkout.getTitle(), workout.getTitle());
    }


    //embeddedID funktioniert nicht
    @Test
    public void insertWorkoutwithExercisesTest(){
        Workout workout = new Workout("Chest Workout");
        Workout savedWorkout = workoutRepository.save(workout);
        Exercise exercise = new Exercise("Bench Press", "Maschine");
        Exercise savedExercise = exerciseRepository.save(exercise);
        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        WorkoutExercise benchPress = new WorkoutExercise(3,2,savedWorkout,savedExercise);
        WorkoutExercise savedWorkoutExercise = workoutExerciseRepository.save(benchPress);
    }

    @Test
    public void getWorkoutExercises(){
        Workout workout = new Workout("Chest Workout");
        Workout savedWorkout = workoutRepository.save(workout);
        Exercise exercise = new Exercise("Bench Press", "Maschine");
        Exercise savedExercise = exerciseRepository.save(exercise);
        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        WorkoutExercise benchPress = new WorkoutExercise(3,2,savedWorkout,savedExercise);
        WorkoutExercise savedWorkoutExercise = workoutExerciseRepository.save(benchPress);
        List<WorkoutExercise> test = workoutExerciseRepository.findByWorkoutId(savedWorkout.getId());

    }

    @Test
    public void getWorkoutExercise(){
        List<WorkoutExercise> test = workoutExerciseRepository.findByWorkoutId(15);
    }



}
