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
        axios.get("http://localhost:8080/users")
            .then(response => {
                setcustomer(response.data.filter(user => user.username == signedIn)[0]);
            })
            .catch(error => {
                console.error("Error fetching users:", error);
            });
    }, [customer])

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

