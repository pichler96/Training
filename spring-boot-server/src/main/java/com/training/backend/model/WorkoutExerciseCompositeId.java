package com.training.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WorkoutExerciseCompositeId implements Serializable {
    @Column(name = "workout_id")
    private long workoutId;
    @Column(name = "exercise_id")
    private long exerciseId;

    public WorkoutExerciseCompositeId(int workoutId, int exerciseId){
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
    }

    public WorkoutExerciseCompositeId() {

    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutExerciseCompositeId that = (WorkoutExerciseCompositeId) o;
        return workoutId == that.workoutId && exerciseId == that.exerciseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutId, exerciseId);
    }
}
