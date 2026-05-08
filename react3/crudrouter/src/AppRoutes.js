import { Routes, Route, Navigate } from "react-router-dom";

import SignUp from "./SignUp";
import SignIn from "./SignIn";
import CarList from "./CarList";
import AddCar from "./AddCar";
import UpdateCar from "./UpdateCar";
import CarDetails from "./CarDetails";
import SearchCar from "./SearchCar";
import ProtectedRoute from "./ProtectedRoute";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/cars" />} />

      <Route path="/signup" element={<SignUp />} />

      <Route path="/signin" element={<SignIn />} />

      <Route path="/cars" element={<CarList />} />

      <Route path="/car-details/:id" element={<CarDetails />} />

      <Route path="/search-car" element={<SearchCar />} />

      <Route
        path="/add-car"
        element={
          <ProtectedRoute>
            <AddCar />
          </ProtectedRoute>
        }
      />

      <Route
        path="/update-car/:id"
        element={
          <ProtectedRoute>
            <UpdateCar />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}

export default AppRoutes;