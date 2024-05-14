import React from "react"
import { useContext } from "react"
import "./../Home/home.css"
import { Context } from "../../App"
import { useNavigate } from "react-router-dom"

export default function Nav() {
    const [signedIn, setSignedIn, role] = useContext(Context)
    
    const navigate = useNavigate()
    const handleNavigation = () => {
        navigate("/home")
    }

    return (
        <nav className="navbar px-5 py-2">
            <div className="nav-left">
                <a className="navbar-brand" onClick={handleNavigation}>
                    <img className="logo" src="./logo/logo.png" alt="" />

                    <p className="nav-title">
                        Reserve Table From Anywhere!
                    </p>
                </a>
            </div>

            <div className="nav-right">
            {
                signedIn === "" ? "" :
                <p>Welcome {signedIn}!</p> 
            }
            {
                signedIn === "" ? <button className="btn nav-btn">Reserve Now!</button> :
                role === "manager"? <button className="btn nav-btn"onClick={()=>navigate("/manager")}>My Restaurants</button>
                :<button className="btn nav-btn"onClick={()=>navigate("/customer")}>My Reservations</button>
            }
            </div>
        </nav>
    )
}