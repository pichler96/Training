import React, { Component } from "react";
import WorkoutDataService from "../services/workout.service";


export default class Workout extends Component {
  constructor(props) {
    super(props);
    this.getWorkout = this.getWorkout.bind(this);
    this.deleteWorkout = this.deleteWorkout.bind(this);

    this.initWorkoutExercises = this.initWorkoutExercises.bind(this);

    this.state = {
      currentWorkout: {
        id: null,
        title: "",
        description: ""
      },

      exercises: []
    };
  }

  initWorkoutExercises(){
    WorkoutDataService.getWorkoutExercises(this.props.match.params.id).then(response => {
      const workoutExercises = response.data;
      let exercises = [];
      for(const workoutExercise of workoutExercises){
        exercises.push(workoutExercise);
      }
      this.setState({
          exercises: exercises,
          currentWorkout: this.state.currentWorkout
      })
    })
  }

  componentDidMount() {
    this.getWorkout(this.props.match.params.id);
    this.initWorkoutExercises();
  }


  getWorkout(id) {
    WorkoutDataService.get(id)
      .then(response => {
        this.setState({
          currentWorkout: response.data.workout
        });
      })
      .catch(e => {
        console.log(e);
      });
  }


  deleteWorkout() {
    WorkoutDataService.delete(this.state.currentWorkout.id)
      .then(response => {
        console.log(response.data);
        this.props.history.push('/workouts')
      })
      .catch(e => {
        console.log(e);
      });
  }


  render() {
    console.log(this.state.exercises);

    return (
      <div>
        {this.state.currentWorkout ? (
          <div className="m-3 edit-form">
            <h4>{this.state.currentWorkout.title} Workout</h4>

            <ol className="list-group list-group-numbered">
            {this.state.exercises && this.state.exercises.map((exercise) => (
                <li className="list-group-item d-flex justify-content-between align-items-start">
                  <div className="ms-2 me-auto">
                    <h6>{exercise.exercise.name}</h6>
                    <button onClick={() => window.open(exercise.exercise.description, '_blank')} type="button" className="btn btn-dark btn-sm rounded-pill">Description</button>
                  </div>
                  <span className="badge bg-primary rounded-pill">{exercise.sets} Sets</span>
                  <span className="badge bg-danger rounded-pill">{exercise.repetitions} Reps</span>
                </li>
            ))}

            </ol>

            <button
              className="mt-3 btn btn-sm btn-danger"
              onClick={this.deleteWorkout}
            >
              Delete
            </button>

            <p>{this.state.message}</p>
          </div>
        ) : (
          <div>
            <br />
            <p>Please click on a Workout...</p>
          </div>
        )}
      </div>
    );
  }
}
