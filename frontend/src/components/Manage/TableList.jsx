import React, { useContext, useState, useEffect } from "react"
import { GlobalTable } from "./ManageRestaurant"
import axios from "axios"
import "./manage.css"

export default function TableList({ restName }) {

    const [tables, setTables] = useState([])
    const [table, setTable] = useContext(GlobalTable)

    useEffect(() => {
        const params = { restaurantName: restName }
        axios.get("http://localhost:8080/tables", params)
            .then(response => {
                // setTables(response.data.filter(table => table.restaurantName == restName));
                setTables(response.data)
            })
            .catch(error => {
                console.error("Error fetching restaurants:", error);
            });
    }, tables)

    const addTableHandler = (e) => {
        e.preventDefault()
        const params = { restaurantName: restName, seats: table.seatsNumber }
        axios.post("http://localhost:8080/table", params)
            .then(response => {
                // setFakes(response.data)
                // setReview(response.data)
            })
            .catch(error => {
                console.error("Error fetching reviews:", error);
        })
    }

    return (
        <div>
            <div class="grid-item pink-back">
                <a className="red inline-cell" onClick={addTableHandler}>+ Add Table</a>
                {
                    tables.length == 0
                    ?
                    <div>
                        <p className="bold center">No Tables have been added.</p>
                    </div>
                    :
                    <div>
                        <ul className="tables-list">
                            {tables && tables.map((table, index) => (
                                <li key={index}>
                                    <button className="table-card" onClick={() => setTable(table)}>
                                        <div className="card-part">
                                            <img className="seat" src="/table_cards/hashtag.svg" alt="" />
                                        </div>
                                        <div className="card-part">{table.tableNumber}</div>
                                        <div className="card-part">
                                            <img className="seat" src="/table_cards/seat.svg" alt="" />
                                        </div>
                                        <div className="card-part">{table.seatsNumber}</div>
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </div>
                }
            </div>
        </div>
    )
}