import React from "react"
import { useContext } from "react"
import "./../Home/home.css"
import { useNavigate } from "react-router-dom"
import { Context } from "../../App"

export default function Email({email}) {

    const navigate = useNavigate()
    const [signedIn, setSignedIn, role, setRole] = useContext(Context)

    const handleLogout = () =>{
        setRole("")
        setSignedIn("")
        navigate("/")
    }
    return (
        <div className="inline-cell notif">
            <div className="content">
                <p className="text">Your reservations are also emailed to   </p>
                <a className="red" href="mailto:Tom_holland@ut.ac.ir"> {email} </a>
            </div>
            &nbsp;
            <div>
                <button className="red-btn btn" onClick={handleLogout}>Logout</button>
            </div>
        </div>
    )
}