import React, { useContext, useEffect, useState } from "react"
import { Context } from "../../App"
import "./reviewmodal.css"
import axios from "axios"
import { useNavigate } from "react-router-dom"

const ReserveModal = ({ isOpen, onClose, time, city, country, street, restaurantName, username, tableNumber}) => {
    const [singedIn, setSignedIn] = useContext(Context)
    const navigate = useNavigate()
    const submitReservation = (e) => {
      const params = { userName: username, restName: restaurantName, dateTime: time, tableNum:  tableNumber}
      axios({
        method: 'post',
        url: "http://localhost:8080/reservations",
        headers: {}, 
        data: params
      })
      .then(navigate("../../customer"))
      .catch(error => {
          navigate("/403")
          console.error("Error fetching users:", error);
      })
    }
    
    if (!isOpen) return null;
    return (
        <div style={{
          position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.5)',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center', }}>
        <div className="modall" style={{ backgroundColor: 'white', padding: '20px', borderRadius: '8px', maxWidth: '80%', }}>
          <button className="close-btn" onClick={onClose} style={{ float: 'right' }}>X</button>
          
            <div className="modal-head">
             <p>
             Reservation Detail
             </p>
            </div>
          
            <hr />

            <div className="modal-body">
              <p className="sub">
              Note:  Please Arrive at Least 15 Minutes Early.
              </p>

              <ul className="review-rates">
                <li>
                  <p>Table Number: 12</p>
                  <p>Time: {time}</p>
                  <p>Address: {country}, {city}, {street}</p>
                  
                </li>
              </ul>

              <div className="submission">
                <button className="cancel">Cancel</button>
                <button className="cancel" onClick={submitReservation}>Submit</button>
              </div>
            </div>
        </div>
      </div>
    )
  }

export default ReserveModal