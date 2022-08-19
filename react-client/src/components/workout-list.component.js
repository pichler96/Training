import React, { Component } from "react";
import WorkoutDataService from "../services/workout.service";
import { Link } from "react-router-dom";

export default class WorkoutList extends Component {
  constructor(props) {
    super(props);
    this.retrieveWorkouts = this.retrieveWorkouts.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.removeAllWorkouts = this.removeAllWorkouts.bind(this);

    this.state = {
      workouts: []
    };
  }

  componentDidMount() {
    this.retrieveWorkouts();
  }

  retrieveWorkouts() {
    WorkoutDataService.getAll()
      .then(response => {
        this.setState({
          workouts: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  refreshList() {
    this.retrieveWorkouts();
    this.setState({
      currentTutorial: null,
      currentIndex: -1
    });
  }


  removeAllWorkouts() {
    WorkoutDataService.deleteAll()
      .then(response => {
        console.log(response.data);
        this.refreshList();
      })
      .catch(e => {
        console.log(e);
      });
  }


  render() {
    const { workouts } = this.state;

    return (
      <div className="list row">
        <div className="col-md-6">
          <h4>Workout Types</h4>

          <ul className="list-group">
            <li className="list-group-item">
              <Link to={"/add"}>Create new workout</Link>
            </li>

            {workouts && workouts.map((tutorial) => (
              <li className={"list-group-item"} >
                <Link to={"/workouts/" + tutorial.id}>{tutorial.title}</Link>
              </li>
            ))}
          </ul>
          <button
            className="mt-3 btn btn-sm btn-danger"
            onClick={this.removeAllWorkouts}
          >
            Remove All
          </button>
        </div>
      </div >
    );
  }
}
