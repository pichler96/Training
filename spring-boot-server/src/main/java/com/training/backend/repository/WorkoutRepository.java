package com.training.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.backend.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
	//List<Workout> findByPublished(String published);
	List<Workout> findByTitleContaining(String title);
}
