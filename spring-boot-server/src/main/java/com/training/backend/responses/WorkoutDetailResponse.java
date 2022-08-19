package com.training.backend.responses;

import com.training.backend.model.Workout;
import com.training.backend.model.WorkoutExercise;

import java.util.List;

public class WorkoutDetailResponse {
    public Workout workout;
    public List<WorkoutExercise> workoutExercises;

    public WorkoutDetailResponse(Workout workout, List<WorkoutExercise> workoutExercises){
        this.workout = workout;
        this.workoutExercises = workoutExercises;
    }
}
