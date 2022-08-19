import http from "../http-common";

class ExerciseDataService {
    getAll() {
        return http.get("/exercises");
    }
}

export default new ExerciseDataService();
