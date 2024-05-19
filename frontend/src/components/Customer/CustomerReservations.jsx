import React, { useContext, useState, useEffect } from "react"
import { Context } from "../../App"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./customer.css"

export default function CustomerReservations({ customerName }) {
    const [signedIn, setSignedIn] = useContext(Context)
    const [reservations, setReservations] = useState([])

    useEffect(() => {
        const params = { username: signedIn }
        axios.get("http://localhost:8080/reservations", params)
            .then(response => {
                // setReservations(response.data.filter(rest => rest.username == customerName));
                setReservations(response.data)
            })
            .catch(error => {
                console.error("Error fetching reservations:", error);
            })
    }, reservations)

    console.log(customerName);
    console.log(reservations)

    const navigate = useNavigate()

    return (
        <div className="reservations">
            <table className="table center">
                <thead>
                    <tr className="header">
                        <th colspan="5">My Reservations</th>
                    </tr>
                </thead>
                <tbody>
                    {reservations && reservations.map((reservation, index) => (
                        <tr key={index}>
                            <td className="gray">{reservation.datetime}</td>
                            <td>
                            <a className="red" onClick={NaN}>{reservation.restaurantName}</a>
                            </td>
                            <td className="gray">Table-{reservation.tableNumber}</td>
                            <td className="gray">4 Seats</td>
                            <td>
                                <a className="red" onClick={NaN}>Add Comment</a>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}