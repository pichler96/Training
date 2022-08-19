package com.training.backend.repository;

import com.training.backend.model.WorkoutExercise;
import com.training.backend.model.WorkoutExerciseCompositeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseCompositeId> {
    List<WorkoutExercise> findByWorkoutId(long workoutId);
    int deleteByWorkoutId(long workoutId);
}
