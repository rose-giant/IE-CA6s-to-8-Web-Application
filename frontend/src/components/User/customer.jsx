import React from "react"
import "./customer.css"

export default function Customer({user}) {
    return(
        <>
            <section className="container">
                <div className="inline-cell notif">
                    <div className="content">
                        <div className="left">
                            <p className="text">Your reservations are also emailed to </p>
                            &nbsp;
                            <a className="red" href={"mailto:"+user.email}> {user.email} </a>
                        </div>
                        
                        <div className="right">
                            <p className="address">
                            Address: {user.address.city},&nbsp;{user.address.country}
                            </p>
                            <button>Logout</button>
                        </div>
                    </div>
                </div>

                <div className="reservations">
                    <table className="table center">
                        <thead>
                            <tr className="header">
                            <th colspan="5">My Reservations</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                            <td className="green">2024-06-22 16:00</td>
                            <td>
                                <a className="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                            </td>
                            <td className="green">Table-12</td>
                            <td className="green">4 Seats</td>
                            <td>
                                <a className="red" href="customerSecondPage.html">Cancel</a>
                            </td>
                            </tr>
                            <tr>
                                <td className="gray">2024-02-22 16:00</td>
                                <td>
                                    <a className="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                                </td>
                                <td className="gray">Table-12</td>
                                <td className="gray">4 Seats</td>
                                <td>
                                    <a className="red" href="customerSecondPage.html">Add Comment</a>
                                </td>
                            </tr>
                            <tr>
                                <td className="gray">2024-02-22 16:00</td>
                                <td>
                                    <a className="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                                </td>
                                <td className="gray">Table-12</td>
                                <td className="gray">4 Seats</td>
                                <td>
                                    <a className="red" href="customerSecondPage.html">Add Comment</a>
                                </td>
                            </tr>
                            <tr>
                                <td className="gray">2024-02-22 16:00</td>
                                <td>
                                    <a className="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                                </td>
                                <td className="gray">Table-12</td>
                                <td className="gray">4 Seats</td>
                                <td>
                                    <a className="red" href="customerSecondPage.html">Add Comment</a>
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </section>
        </>
    )
}