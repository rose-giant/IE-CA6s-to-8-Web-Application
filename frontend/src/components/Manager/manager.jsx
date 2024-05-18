import React, { useContext, useState, useEffect } from "react"
import "./manager.css"
import { Context } from "../../App"
import Footer from "../Footer/footer"
import axios from "axios"
import Email from "../Helpers/Email"
import ManagerRestaurants from "./managerRestaurants"



export default function Manager() {
    const [signedIn, setSignedIn, role, setRole] = useContext(Context)

    const [manager, setManager] = useState(null)

    useEffect(() => {
        axios.get("http://localhost:8080/users/" + signedIn)
            .then(response => {
                // setManager(response.data.filter(user => user.username == signedIn)[0]);
                setManager(response.data)
            })
            .catch(error => {
                console.error("Error fetching users:", error);
            });
    }, [manager])

    // console.log(manager)
    // console.log(manager.username )

    return (
        <>
        {manager && (
            <div >
                <section className="container">
                    <Email email={manager.email}/>
                    <ManagerRestaurants managerName = {manager.username}/>
                </section>
                <Footer />
            </div>
        )}            
        </>
    )
}