package com.training.backend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "workout")
    private List<WorkoutExercise> workoutExercises;

    public Workout() {
    }

    public Workout(long id) {
        this.id = id;
    }

    public Workout(String title) {
        this.title = title;
    }

    public Workout(String title, List<WorkoutExercise> workoutExercises){
        this.title = title;
        this.workoutExercises = workoutExercises;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Tutorial [id= " + id;
    }

}
