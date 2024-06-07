import Email from "../Helpers/Email"
import React, { useContext, useState, useEffect } from "react"
import "./customer.css"
import { Context } from "../../App"
import Footer from "../Footer/footer"
import axios from "axios"
import CustomerReservations from "./CustomerReservations"

export default function Customer() {
    const [signedIn, setSignedIn] = useContext(Context)

    const [customer, setcustomer] = useState(null)

    useEffect(() => {
        axios.get("http://localhost:8080/users/" + signedIn, {
            headers: {
                'Authorization': localStorage.getItem('authToken'),
                'Content-Type': 'application/json'
            }
            })
            .then(response => {
                console.log(response.data)
                setcustomer(response.data)
            })
            .catch(error => {
                console.error("Error fetching users:", error);
            })
    }, [])

    // console.log(customer)
    // console.log(customer.username )

    return (
        <>
        {customer && (
            <div >
                <section className="container">
                    <Email email={customer.email}/>
                    <CustomerReservations customerName = {customer.username}/>
                </section>
                <Footer />
            </div>
        )}            
        </>
    )
}