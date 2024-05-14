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
        e.preventDefault();
        const params = { username: username, password: password };
        axios.post("http://localhost:8080/login", params)
            .then(response => {
                if (response.status && response.status === 200) {
                    setSignedIn(username)
                    response.data.role && setRole(response.data.role)
                    console.log(signedIn)
                    console.log(role)
                    navigate("/home")
                }                   
                else{
                    navigate("/403")
                }
            })
            .catch(error => {
                navigate("/403")
                console.error("Error fetching users:", error);
            });
        

        // e.preventDefault()
        // axios.get("http://localhost:8080/users")
        // .then(response => {
        //   findTargetUser(response.data)
        // })
        // .catch(error => {
        //   console.error("Error fetching reviews:", error)
        // })
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