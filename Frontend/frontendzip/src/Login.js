import React, { useState } from "react";
import axios from 'axios';

const Login = ()=>{
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const loginHandler = async ()=>{
        try{
            await axios.post('http://localhost:8080/signin', {
                username,
                password
            }, {
                auth:{
                    username,
                    password
                }
            });
            alert('Login successful');
        }catch(error){
            console.log('Error while Login into the application : ', error);
            alert('Login failed');
        }
    };

    return (
        <div className="Login">
            <input type = "text" value={username} onChange = {(e)=>setUsername(e.target.value)} placeholder="Enter Username here"/>
            <input type = "password" value = {password} onChange={(e)=>setPassword(e.target.value)} placeholder = "Enter your password here"/>
            <button onClick={loginHandler}>Sign in</button>
        </div>
    )
}

export default Login;