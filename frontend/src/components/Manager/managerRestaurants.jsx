import React, { useContext, useState, useEffect } from "react"
import { Context } from "../../App"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./manager.css"
import Modal from "../Helpers/Modal/Modal"

export default function ManagerRestaurants({managerName}) {

    const [restaurants, setRestaurants] = useState([])
    const [name, setName] = useState("")
    const [type, setType] = useState("")
    const [image, setImage] = useState("")
    const [city, setCity] = useState("")
    const [country, setCountry] = useState("")
    const [street, setStreet] = useState("")
    const [description, setDesc] = useState("")
    const [startTime, setStart] = useState("")
    const [endTime, setEnd] = useState("")
    const [modalOpen, setModalOpen] = useState(false)
    
    const openModal = () => {
        setModalOpen(true);
    };

    const closeModal = () => {
        setModalOpen(false);
    };

    useEffect(() => {
        axios.get("http://localhost:8080/restaurants/" + managerName, {
            headers: {
                'Authorization': localStorage.getItem('authToken'),
                'Content-Type': 'application/json'
            }
            })
            .then(response => {
                setRestaurants(response.data);
            })
            .catch(error => {
                console.error("Error fetching restaurants:", error);
            });
    }, [])

    const addRestaurantHandler = (e) => {
        closeModal()
        e.preventDefault()
        const params = {name: name,
                        managerUsername: managerName,
                        type: type,
                        image:image ,
                        city: city, 
                        country: country , 
                        street: street, 
                        description: description, 
                        startTime: startTime,
                        endTime:  endTime}
        axios({
            method: 'post',
            url: "http://localhost:8080/restaurants",
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

    const navigate = useNavigate()

    return (
        <div className="restaurants">
            <div className="table center second-table">
                <ul>
                    <li>
                        <div className="inline-cell">
                            <p className="bold">My Restaurants </p>
                            <a className="red-btn btn" onClick={openModal}>Add</a>
                        </div>
                    </li>
                </ul>
                <Modal isOpen={modalOpen} onClose={closeModal}>
                    <form className="form-cotrol" action="" onSubmit={addRestaurantHandler}>
                    
                    <label htmlFor="seats">Name</label>
                    <input required className="input-group" onChange={(e) => setName(e.target.value)} type="text" />
                    <label htmlFor="seats">Type</label>
                    <input required className="input-group" onChange={(e) => setType(e.target.value)} type="text" />
                    <label htmlFor="seats">City</label>
                    <input required className="input-group" onChange={(e) => setCity(e.target.value)} type="text" />
                    <label htmlFor="seats">Country</label>
                    <input required className="input-group" onChange={(e) => setCountry(e.target.value)} type="text" />
                    <label htmlFor="seats">Street</label>
                    <input required className="input-group" onChange={(e) => setStreet(e.target.value)} type="text" />
                    <label htmlFor="seats">Description</label>
                    <input required className="input-group" onChange={(e) => setDesc(e.target.value)} type="text" />
                    <label htmlFor="seats">Image</label>
                    <input required className="input-group" onChange={(e) => setImage(e.target.value)} type="text" />
                    <label htmlFor="seats">Start Time</label>
                    <input required className="input-group" onChange={(e) => setStart(e.target.value)} type="time" />
                    <label htmlFor="seats">End Time</label>
                    <input required className="input-group" onChange={(e) => setEnd(e.target.value)} type="time" />
                    <button type="submit">Add</button>

                    </form>
                </Modal>
                <ul>
                    {restaurants && restaurants.map((restaurant, index) => (
                        <li key={index}>
                            <div className="inline-cell">
                                <p className="bold">{restaurant.name} </p>
                                <p className="bold">{restaurant.address.city}, {restaurant.address.country} </p>
                                <button className="red-btn btn" onClick={()=>navigate(`/manage/${restaurant.name}`)}>Manage</button>
                            </div>
                        </li>
                    ))}
                </ul>

            </div>
        </div>


    )
}