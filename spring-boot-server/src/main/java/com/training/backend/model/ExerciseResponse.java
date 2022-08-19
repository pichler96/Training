package com.training.backend.model;

public class ExerciseResponse {
    public int repetitions;
    public int sets;
    public Exercise exercise;

    public ExerciseResponse(int repetitions, int sets, Exercise exercise){
        this.repetitions = repetitions;
        this.sets = sets;
        this.exercise = exercise;
    }
}
