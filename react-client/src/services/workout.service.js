import http from "../http-common";

class WorkoutDataService {
  getAll() {
    return http.get("/workouts");
  }

  get(id) {
    return http.get(`/workouts/${id}`);
  }

  create(data) {
    return http.post("/workouts", data);
  }

  update(id, data) {
    return http.put(`/workouts/${id}`, data);
  }

  delete(id) {
    return http.delete(`/workouts/${id}`);
  }

  deleteAll() {
    return http.delete(`/workouts`);
  }

  findByTitle(title) {
    return http.get(`/workouts?title=${title}`);
  }

  createWorkoutExercise(data){
    console.log(data);
    return http.post('/workoutExercise', data);
  }

  getWorkoutExercises(id){
    return http.get('/workoutExercise/' + id);
  }
}

export default new WorkoutDataService();
