import { useState } from "react";
import "./App.css";

const Login = () =>
{
    let [username, setUsername] = useState("");
    let [password, setPassword] = useState("");
    let [result, setResult] = useState("");

    const checkLogin = () =>
    {
        if(username === "admin" && password === "1234")
        {
            setResult("Welcome");
        }
        else
        {
            setResult("Invalid Login");
        }
    }

    return(
        <div className="d1">

            <h1>Login Form</h1>

            <input 
                type="text"
                placeholder="Enter Username"
                onChange={(e) => setUsername(e.target.value)}
            />

            <br/><br/>

            <input 
                type="password"
                placeholder="Enter Password"
                onChange={(e) => setPassword(e.target.value)}
            />

            <br/><br/>

            <button onClick={checkLogin}>Login</button>

            <h2>{result}</h2>

        </div>
    )
}

export default Login;