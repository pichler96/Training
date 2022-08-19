package com.training.backend.repository;

import com.training.backend.model.Exercise;
import com.training.backend.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository  extends JpaRepository<Exercise, Long> {

    List<Exercise> findByNameContaining(String name);
}
