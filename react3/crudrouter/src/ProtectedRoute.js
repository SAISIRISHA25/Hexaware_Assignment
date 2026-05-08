import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }) {
  const loggedInUser = sessionStorage.getItem("loggedInUser");

  if (!loggedInUser) {
    alert("Please sign in first");
    return <Navigate to="/signin" />;
  }

  return children;
}

export default ProtectedRoute;