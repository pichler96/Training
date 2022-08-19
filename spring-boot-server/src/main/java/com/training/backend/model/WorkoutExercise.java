package com.training.backend.model;

import javax.persistence.*;

@Entity
public class WorkoutExercise {

    @EmbeddedId
    WorkoutExerciseCompositeId id = new WorkoutExerciseCompositeId();

    @Column(name = "repetitions")
    private int repetitions;

    @Column(name = "sets")
    private int sets;

    @ManyToOne
    @MapsId("workoutId")
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @ManyToOne
    @MapsId("exerciseId")
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    public WorkoutExercise() {
    }

    public WorkoutExercise(int repetitions, int sets, int workoutId, int exerciseId) {
        this.repetitions = repetitions;
        this.sets = sets;
        this.id = new WorkoutExerciseCompositeId(workoutId, exerciseId);
        this.workout = new Workout(workoutId);
        this.exercise = new Exercise(exerciseId);
    }

    public WorkoutExercise(int repetitions, int sets, Workout workout, Exercise exercise){
        this.repetitions = repetitions;
        this.sets = sets;
        this.workout = workout;
        this.exercise = exercise;
    }


    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

}
