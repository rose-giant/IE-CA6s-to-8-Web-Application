import React, { useState } from "react"
import { useContext } from "react"
import { Context } from "../../App"
import { useNavigate } from "react-router-dom"
import axios from "axios"

export default function Register() {
    const [signedIn, setSignedIn, role, setRole] = useContext(Context)
    const [name, setName] = useState("")
    const [password, setPassword] = useState("")
    const [email, setEmail] = useState("")
    const [city, setCity] = useState("")
    const [country, setCountry] = useState("")
    const navigate = useNavigate()

    const registerSubmit = (e) => {
        e.preventDefault();
        const params = {
            username: name,
            password: password,
            email: email,
            role: role,
            city: city,
            country: country
          };
          axios({
            method: 'post',
            url: "http://localhost:8080/signup",
            headers: {}, 
            data: params
          })
            .then(response => {
                const token = response.headers['authorization'];
                localStorage.setItem('authToken', token);
                console.log(response)
                if (response.status && response.status === 201) {
                    setSignedIn(name)
                    response.data.role && setRole(role)
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
            })
            
        e.preventDefault()
        console.log(name)
        setSignedIn(name)
        navigate("/home")
    }

    return(
        <>
             <form className="form-cotrol" onSubmit={registerSubmit} action="">
                <label htmlFor="username" >Username</label>
                <input required className="input-group" type="text"
                    onChange={(e) => setName(e.target.value)}/>

                <label htmlFor="email">Email</label>
                <input required className="input-group" type="email"
                    onChange={(e) => setEmail(e.target.value)}/>

                <label htmlFor="country">Country</label>
                <input required className="input-group" type="text"
                    onChange={(e) => setCountry(e.target.value)}/>

                <label htmlFor="city">City</label>
                <input required className="input-group" type="text" 
                    onChange={(e) => setCity(e.target.value)}/>

                <label htmlFor="password">Password</label>
                <input required className="input-group" type="password" 
                    onChange={(e) => setPassword(e.target.value)}/>

                <div className="radios">
                        <label htmlFor="I'm a new Customer">Customer</label>
                        <input className="input-group" type="radio" id="c"
                        onChange={(e) => setRole("customer")}/>

                        <label htmlFor="Manager">Manager</label>
                        <input className="input-group" type="radio" id="m"
                        onChange={(e) => setRole("manager")}/>
                </div>
                <button type="submit">Register</button>
            </form>
        </>
    )
}