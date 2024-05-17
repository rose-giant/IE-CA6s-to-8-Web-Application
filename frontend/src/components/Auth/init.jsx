import React, { useContext, useEffect, useState } from "react"
import Login from "./login"
import Register from "./register"
import "./init.css"
import axios from "axios"
import { Context } from "../../App"

export default function InitUser() {
    const [userState, setUserState] = useState()
    const [activeButton, setActiveButton] = useState('login')
    const [users, setUsers] = useState([])
    const [signedIn, setSignedIn] = useContext(Context)

    const handleLoginClick = () => {
        setUserState("login")
        setActiveButton("login")
    }

    const handleRegisterClick = () => {
        setUserState("register")
        setActiveButton("register")
    }

    useEffect(() => {
        axios.get("http://localhost:8080/users")
          .then(response => {
            setUsers(response.data);
          })
          .catch(error => {
            console.error("Error fetching restaurants:", error);
          });
      }, [])

    return(
        <>
        <div className="activators">
            <button
                className={activeButton === 'login' ? 'activeLogin' : 'inactiveLogin'}
                onClick={handleLoginClick} >
                Login
            </button>

            <button
                className={activeButton === 'register' ? 'activeRegister' : 'inactiveRegister'}
                onClick={handleRegisterClick}>
                Register
            </button>
        </div>

        {userState === "login" ? <Login /> : <Register />}  
        </>
    )
}