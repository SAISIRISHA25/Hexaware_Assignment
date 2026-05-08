import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();
  const loggedInUser = sessionStorage.getItem("loggedInUser");

  const logout = () => {
    sessionStorage.removeItem("loggedInUser");
    navigate("/signin");
  };

  return (
    <nav className="navbar">
      <h2>Car Routing App</h2>

      <div>
        {!loggedInUser ? (
          <>
            <Link to="/signin">Sign In</Link>
            <Link to="/signup">Sign Up</Link>
          </>
        ) : (
          <>
            <Link to="/cars">Cars</Link>
            <Link to="/add-car">Add Car</Link>
            <Link to="/search-car">Search</Link>
            <button onClick={logout}>Logout</button>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;