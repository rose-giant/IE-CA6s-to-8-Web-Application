import React, { useContext, useState, useEffect } from "react"
import { Context } from "../../App"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./customer.css"
import Modal from "../Helpers/Modal/Modal"

export default function CustomerReservations({ customerName }) {
    const [signedIn, setSignedIn] = useContext(Context)
    const [reservations, setReservations] = useState([])
    const navigate = useNavigate()
    const [comment, setcomment] = useState("")
    const [foodRate, setfoodRate] = useState("")
    const [overallRate, setoverallRate] = useState("")
    const [restaurantName, setrestaurantName] = useState("")
    const [serviceRate, setserviceRate] = useState("")
    const [ambianceRate, setambianceRate] = useState("")
    const [modalOpen, setModalOpen] = useState(false)
    
    const openModal = () => {
        setModalOpen(true);
    };

    const closeModal = () => {
        setModalOpen(false);
    };


    useEffect(() => {
        axios.get("http://localhost:8080/reservations?userName="+ customerName)
            .then(response => {
                setReservations(response.data)
            })
            .catch(error => {
                console.error("Error fetching reservations:", error);
            })
    }, [])

    const addReviewHandler = (e) => {
        closeModal()
        e.preventDefault()
        const params = {
            ambianceRate: ambianceRate,
            comment: comment,
            foodRate: foodRate,
            overallRate:overallRate ,
            restaurantName: restaurantName, 
            serviceRate: serviceRate,
            username: customerName }
        axios({
            method: 'post',
            url: "http://localhost:8080/reviews",
            headers: {}, 
            data: params
          })
            .catch(error => {
                console.error("Error adding table: ", error);
        })
    }

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
                            <a className="red" onClick={()=>navigate("../restaurant/"+reservation.restaurantName)}>{reservation.restaurantName}</a>
                            </td>
                            <td className="gray">Table-{reservation.tableNumber}</td>
                            <td className="gray">4 Seats</td>
                            <td>
                                <a className="red" onClick={openModal}>Add Comment</a>
                            </td>
                            <Modal isOpen={modalOpen} onClose={closeModal}>
                                <form className="form-cotrol" action="" onSubmit={addReviewHandler}>
                                    <label htmlFor="seats">Ambiance Rate</label>
                                    <input required className="input-group" onChange={(e) => {
                                        setambianceRate(e.target.value)
                                        setrestaurantName(reservation.restaurantName)
                                    }} type="text" />
                                    <label htmlFor="seats">Food Rate</label>
                                    <input required className="input-group" onChange={(e) => setfoodRate(e.target.value)} type="number" />
                                    <label htmlFor="seats">Service Rate</label>
                                    <input required className="input-group" onChange={(e) => setserviceRate(e.target.value)} type="number" />
                                    <label htmlFor="seats">Overall Rate</label>
                                    <input required className="input-group" onChange={(e) => setoverallRate(e.target.value)} type="number" />
                                    <label htmlFor="seats">Comment</label>
                                    <input required className="input-group" onChange={(e) => setcomment(e.target.value)} type="text" />
                                    
                                    <button type="submit" >Add</button>
                                </form>
                            </Modal>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}