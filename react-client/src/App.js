import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AddWorkout from "./components/add-workout.component";
import Workout from "./components/workout.component";
import WorkoutList from "./components/workout-list.component";

class App extends Component {
  render() {
    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/workouts"} className="navbar-brand">
            Training Progessive-Web-Application
          </Link>
        </nav>

        <div className="container mt-3">
          <Switch>
            <Route exact path={["/", "/workouts"]} component={WorkoutList} />
            <Route exact path="/add" component={AddWorkout} />
            <Route path="/workouts/:id" component={Workout} />
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
