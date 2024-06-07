import React, { useContext, useState, useEffect } from "react"
import { GlobalTable } from "./ManageRestaurant"
import axios from "axios"
import { Context } from "../../App"
import "./manage.css"
import Modal from "../Helpers/Modal/Modal"

export default function TableList({ restName }) {

    const [tables, setTables] = useState([])
    const [table, setTable] = useContext(GlobalTable)
    const [modalOpen, setModalOpen] = useState(false)
    const [signedIn] = useContext(Context)
    const [seatNum, setSeatNum] = useState("")

    const openModal = () => {
        setModalOpen(true);
    };

    const closeModal = () => {
        setModalOpen(false);
    };
    

    useEffect(() => {
        axios.get("http://localhost:8080/tables/"+ restName, {
            headers: {
                'Authorization': localStorage.getItem('authToken'),
                'Content-Type': 'application/json'
            }
            })
            .then(response => {
                setTables(response.data)
            })
            .catch(error => {
                console.error("Error fetching restaurants:", error);
            });
    }, [restName])

    const addTableHandler = (e) => {
        closeModal()
        e.preventDefault()
        const params = { restaurantName: restName, seatsNumber: seatNum , managerUsername: signedIn, tableNumber: tables.length + 1}
        axios({
            method: 'post',
            url: "http://localhost:8080/tables",
            headers: {
                'Authorization': localStorage.getItem('authToken'),
                'Content-Type': 'application/json'
            },
            data: params
          })
            .catch(error => {
                console.error("Error adding table: ", error);
        })
    }

    return (
        <div>
            <div class="grid-item pink-back">
                <a className="red inline-cell" onClick={openModal}>+ Add Table</a>
                <Modal isOpen={modalOpen} onClose={closeModal}>
                    <form className="form-cotrol" action="" onSubmit={addTableHandler}>
                    
                    <label htmlFor="seats">Number of seats</label>
                    <input required className="input-group" onChange={(e) => setSeatNum(e.target.value)} type="text" />

                    <button type="submit">Add</button>
                    </form>
                </Modal>
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