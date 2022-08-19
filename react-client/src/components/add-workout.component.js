import React, { Component } from "react";
import WorkoutDataService from "../services/workout.service";
import ExerciseDataService from "../services/exercise.service";

export default class AddWorkout extends Component {
  constructor(props) {
    super(props);
    this.onChangeTitle = this.onChangeTitle.bind(this);
    this.onChangeSets = this.onChangeSets.bind(this);
    this.onChangeRepetitions = this.onChangeRepetitions.bind(this);
    this.onChangeSelectedExercise = this.onChangeSelectedExercise.bind(this);
    this.saveWorkout = this.saveWorkout.bind(this);
    this.newWorkout = this.newWorkout.bind(this);
    this.addExercise = this.addExercise.bind(this);
    this.initExercises();

    this.state = {
      id: null,
      title: "",
      selectedExercises: [],
      published: false,
      submitted: false,
      exercises: [],
      repetitions: [0],
      sets: [0]

    };
  }

  initExercises(){
    ExerciseDataService.getAll().then(exercises => {
      this.state.selectedExercises.push(exercises.data[0].id)
      this.setState({
        exercises: exercises.data
      })
      console.log(this.state.selectedExercises);
    }).catch(error => {
      console.error(error);
    });
  }

  onChangeTitle(e) {
    this.setState({
      title: e.target.value
    });
  }

  onChangeSets(e, selector) {
    if(e.target.value) {
      this.state.sets[selector] = e.target.value;
      this.setState({
        sets: this.state.sets
      });
    }
  }

  onChangeRepetitions(e,selector) {
    if(e.target.value) {
      this.state.repetitions[selector] = e.target.value;
      this.setState({
        repetitions: this.state.repetitions
      });
    }
  }

  onChangeSelectedExercise(e, selector) {
    let changed = this.state.selectedExercises;
    changed[selector] = e.target.value;
    this.setState({
      selectedExercises: changed
    });
  }

  saveWorkout() {
    let data = {
      title: this.state.title
    };

    WorkoutDataService.create(data)
        .then(response => {
          this.setState({
            id: response.data.id,
            title: response.data.title,
            published: response.data.published,
            submitted: true
          });
          let index = 0;
          for(const selectedExercise of this.state.selectedExercises) {
            let workoutExercise = {
              repetitions: this.state.repetitions[index],
              sets: this.state.sets[index],
              workoutId: this.state.id,
              exerciseId: this.state.selectedExercises[index]
            };
            WorkoutDataService.createWorkoutExercise(workoutExercise).then(response => {
              console.log(response.data);
            }).catch(error => {
              console.error(error);
            });
            index++;
          }
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
  }

  newWorkout() {
    this.setState({
      id: null,
      title: "",
      selectedExercises: [],
      published: false,
      submitted: false,
      repetitions: [0],
      sets: [0],
      exercises: []
    });
  }

  addExercise(){
    this.state.selectedExercises.push(this.state.exercises[0].id);
    this.state.repetitions.push(0);
    this.state.sets.push(0);
    this.setState({
      selectedExercises: this.state.selectedExercises
    })
  }

  getExerciseStyle(options, selector){
    return (<div><select className="btn btn-secondary dropdown-toggle" onChange={(e) => this.onChangeSelectedExercise(e, selector)} >
      {options}
    </select>
    <div>
      <div className="form-group">
        <label htmlFor="repetitions">Repetitions</label>
        <input
            type="number"
            className="form-control"
            id="repetitions"
            required
            value={this.state.repetitions[selector]}
            onChange={(e) => this.onChangeRepetitions(e,selector)}
            name="repetitions"
        />
      </div>
    </div>
    <div>
      <div className="form-group">
        <label htmlFor="sets">Sets</label>
        <input
            type="number"
            className="form-control"
            id="sets"
            required
            value={this.state.sets[selector]}
            onChange={(e) => this.onChangeSets(e, selector)}
            name="sets"
        />
      </div>
    </div></div>);
  }

  render() {
    let options = [];
    for(const exercise of this.state.exercises){
      options.push(<option className="dropdown-item" value={exercise.id} key={exercise.name}>{exercise.name}</option>)
    }

    let exercises = [];

    let index = 0;
    for(const id of this.state.selectedExercises){
      exercises.push(this.getExerciseStyle(options, index));
      index++;
    }

    return (
      <div className="m-3 submit-form">
        {this.state.submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={this.newWorkout}>
              Add
            </button>
          </div>
        ) : (
          <div>
            <div className="form-group">
              <label htmlFor="title">Title</label>
              <input
                type="text"
                className="form-control"
                id="title"
                required
                value={this.state.title}
                onChange={this.onChangeTitle}
                name="title"
              />
            </div>
            {exercises}

            <button onClick={this.saveWorkout} className="btn btn-sm btn-success">
              Submit
            </button>
            <br/>
            <button onClick={this.addExercise} className="btn btn-sm btn-success">
              Add Exercise
            </button>
          </div>
        )}
      </div>
    );
  }
}
