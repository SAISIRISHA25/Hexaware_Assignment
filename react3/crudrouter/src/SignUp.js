import { useState } from "react";
import { useNavigate } from "react-router-dom";

function SignUp() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const signup = (e) => {
    e.preventDefault();

    if (username === "" || password === "") {
      alert("Please fill all fields");
      return;
    }

    const user = {
      username: username,
      password: password,
    };

    sessionStorage.setItem("registeredUser", JSON.stringify(user));

    alert("Sign up successful");
    navigate("/signin");
  };

  return (
    <div className="container">
      <h2>Sign Up</h2>

      <form onSubmit={signup}>
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

        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
}

export default SignUp;