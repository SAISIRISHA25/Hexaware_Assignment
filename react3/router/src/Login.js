// //import { useState } from "react";
// import "./App.css";
// import { useNavigate } from "react-router-dom";
 
// const Login = () => {
//   let nav=useNavigate()  
//   // let [username, setUsername] = useState();
//   // let [password, setPassword] = useState();
 
//   // function handleuserName(e) {
//   //   setUsername(e.target.value);
//   // }
//   // function handlePassword(e) {
//   //   setPassword(e.target.value);
//   // }



//   function handleLogin() {
//     const user = document.getElementById("uName").value;
//     const pass = document.getElementById("uPass").value;
//     if (username === "admin" && password === "admin") {
//       sessionStorage.setItem("user", user);
//       sessionStorage.setItem("isLoggedIn", "true");
//     nav("/welcome")
//       //alert("Login successful");
//     } else {
//       alert("Login failed");
//     }
//   }
 
//   return (
//     <div className="d1">
//       <h1>Login</h1>
//       <input type="text" placeholder="Enter username" onChange={handleuserName} /> <br />
//       <input type="password" placeholder="Enter password" onChange={handlePassword} />
//       <br />
//       <button onClick={handleLogin}>Login</button>
//       <br />
//     </div>
//   );
// }
 
// export default Login;


import "./App.css";
import { useNavigate } from "react-router-dom";

const Login = () => {
  let nav = useNavigate();

  function handleLogin() {
    const userValue = document.getElementById("uName").value;
    const passValue = document.getElementById("uPass").value;

    if (userValue === "admin" && passValue === "admin") {
      sessionStorage.setItem("user", userValue);
      sessionStorage.setItem("isLoggedIn", "true");

      nav("/welcome");
    } else {
      alert("Login failed");
    }
  }

  return (
    <div className="d1">
      <h1>Login</h1>
      {}
      <input type="text" id="uName" placeholder="Enter username" /> 
      <br />
      <input type="password" id="uPass" placeholder="Enter password" />
      <br />
      <button onClick={handleLogin}>Login</button>
      <br />
    </div>
  );
}

export default Login;
 
 