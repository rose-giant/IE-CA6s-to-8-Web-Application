import React, { useContext, useState, useEffect } from "react"
import { Context } from "../../App"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./manager.css"

export default function ManagerRestaurants({managerName}) {

    const [restaurants, setRestaurants] = useState([])

    useEffect(() => {
        axios.get("http://localhost:8080/restaurants")
            .then(response => {
                setRestaurants(response.data.filter(rest => rest.managerUsername == managerName));
            })
            .catch(error => {
                console.error("Error fetching restaurants:", error);
            });
    }, [])

    // console.log(managerName);
    // console.log(restaurants)

    const navigate = useNavigate()

    return (
        <div className="restaurants">
            <div className="table center second-table">
                <ul>
                    <li>
                        <div className="inline-cell">
                            <p className="bold">My Restaurants </p>
                            <a className="red-btn btn" href="ManagerSecondPage.html">Add</a>
                        </div>
                    </li>
                </ul>
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