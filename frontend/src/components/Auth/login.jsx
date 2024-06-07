import React, { useState } from "react"
import { useContext, useEffect } from "react"
import { Context } from "../../App"
import { useNavigate } from "react-router-dom"
import axios from "axios"

export default function Login() {
    const [user, setUser] = useState()
    const [users, setUsers] = useState()
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [signedIn, setSignedIn, role, setRole] = useContext(Context)
    const navigate = useNavigate()

    const handleLogin = (e) => {
        e.preventDefault()
        const params = { username: username, password: password }
        axios({
            method: 'post',
            url: "http://localhost:8080/login",
            headers: {}, 
            data: params
          })
            .then(response => {
                if (response.status && response.status === 200) {
                    const token = response.headers['authorization'];
                    localStorage.setItem('authToken', token);
                    setSignedIn(username)
                    response.data.role && setRole(response.data.role)
                    console.log(signedIn)
                    console.log(role)
                    console.log(response.headers)
                    console.log(token)
                    console.log(localStorage.getItem('authToken'))
                    navigate("/home")
                }                   
                else{
                    navigate("/403")
                }
            })
            .catch(error => {
                navigate("/403")
                console.error("Error fetching users:", error);
            })
            
    }

    return(
        <>
            <form className="form-cotrol" action="" onSubmit={handleLogin}>
                <label htmlFor="username">Username</label>
                <input required onChange={(e) => setUsername(e.target.value)} className="input-group" type="text" />

                <label htmlFor="password">Password</label>
                <input required className="input-group" onChange={(e) => setPassword(e.target.value)} type="password" />

                <button type="submit">Login</button>
            </form>
        </>
    )
}