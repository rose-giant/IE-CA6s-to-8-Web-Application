import React, { useContext, useState, useEffect } from "react"
import { GlobalTable } from "./ManageRestaurant"
import axios from "axios"
import "./manage.css"

export default function ReservationList({ restName }) {

    const [reservations, setReservations] = useState([])
    const [restReservations, setRestReservations] = useState([])
    const [table, setTable] = useContext(GlobalTable)
    // console.log("http://localhost:8080/reservations?restName=" + restName)

    useEffect(() => {
        axios.get("http://localhost:8080/reservations?restName=" + restName)
            .then(response => {
                setRestReservations(response.data);
                table != null ? setReservations(restReservations.filter(resrv => resrv.tableNumber == table.tableNumber)):setReservations(response.data)
            })
            .catch(error => {
                console.error("Error fetching reservations:", error);
            });
    }, [restReservations, table])

   
    // console.log(reservations)
    // console.log(table)



    return (

        <div className="grid-item">
            <section className="reservations">

                {reservations && (
                    <div className="wide">
                        <p className="bold">Reservation List</p>
                        {
                            table == null ? <p className="blur">Select a table to see its reservations</p> :
                                reservations.length == 0 ? <p className="red-blur">No Reservations.</p> :
                                    <p className="red-blur">Reservations for Table-{table && table.tableNumber}</p>
                        }
                    </div>
                    )
                }
                <div className="reservations">
                    <table className="table center">
                        <tbody className="left-align">
                            {
                                table == null ?
                                    <div >
                                        {restReservations && restReservations.map((reservation, index) => (
                                            <tr className="reserve-row inline-cell" key={index}>
                                                <td className="ruler">{reservation.datetime}</td>
                                                <td>By {reservation.username}</td>
                                                <td>Table-{reservation.tableNumber}</td>
                                            </tr>
                                        ))}
                                    </div>
                                    :
                                    <div>
                                        {
                                            reservations && reservations.map((reservation, index) => (
                                                <tr className="reserve-row inline-cell" key={index}>
                                                    <td className="ruler">{reservation.datetime}</td>
                                                    <td>By {reservation.username}</td>
                                                    <td>Table-{reservation.tableNumber}</td>
                                                </tr>
                                            ))}
                                    </div>
                            }
                        </tbody>
                    </table>
                </div>
            </section>

        </div>

    )
}