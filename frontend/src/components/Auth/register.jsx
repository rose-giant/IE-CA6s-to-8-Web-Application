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

    const registerSubmit = async (e) => {
        e.preventDefault();

        const params = {
            username: name,
            password: password,
            email: email,
            role: role,
            city: city,
            country: country
        };
    
        try {
            const response = await fetch('http://localhost:8080/signup', {
                method: 'POST',
                mode: 'cors',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(params)
            });
    
            if (!response.ok) {
                console.error('Signup failed:', await response.text());
                navigate('/403');
                return;
            }
            
            console.log(1)
            const authHeader = response.headers.get('Authorization');
            if (authHeader && authHeader.startsWith('Bearer ')) {
                const token = authHeader.substring(7)
                localStorage.setItem('jwtToken', token);
                console.log('Token stored:', token);
            }
            console.log(2)
    
            const data = await response.json();
            setSignedIn(name);
            setRole(role);
            navigate('/home');
        } catch (error) {
            console.error('Error during signup:', error);
            navigate('/403');
        }
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
                        onChange={(e) => setRole("client")}/>

                        <label htmlFor="Manager">Manager</label>
                        <input className="input-group" type="radio" id="m"
                        onChange={(e) => setRole("manager")}/>
                </div>
                <button type="submit">Register</button>
            </form>
        </>
    )
}