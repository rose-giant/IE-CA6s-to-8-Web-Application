import React, { useState } from "react"

export default function Login() {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    const handleLogin = async (e) => {
        e.preventDefault()
        const params = { username: username, password: password }
        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                mode: "cors",
                headers: {
                    "Accept": 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });
    
            if (!response.ok) {
                const message = await response.text()
                console.error('Login failed:', message)
                return
            }
    
            const token = await response.text()
            localStorage.setItem('jwtToken', token)
            console.log('Token stored:', token)
        } catch (error) {
            console.error('Error during login:', error)
        }
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