import { useState } from "react";
import { useNavigate } from "react-router-dom";

function SignIn() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const signin = (e) => {
    e.preventDefault();

    const storedUser = JSON.parse(sessionStorage.getItem("registeredUser"));

    if (!storedUser) {
      alert("No user found. Please sign up first.");
      navigate("/signup");
      return;
    }

    if (username === storedUser.username && password === storedUser.password) {
      sessionStorage.setItem("loggedInUser", username);
      alert("Login successful");
      navigate("/cars");
    } else {
      alert("Invalid username or password");
    }
  };

  return (
    <div className="container">
      <h2>Sign In</h2>

      <form onSubmit={signin}>
        <input
          type="text"
          placeholder="Enter username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="Enter password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button type="submit">Sign In</button>
      </form>
    </div>
  );
}

export default SignIn;