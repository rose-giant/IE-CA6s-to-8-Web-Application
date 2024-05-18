import React, { useEffect, useState } from "react"
import axios from "axios"
import RestaurantCard from "./restaurantcard"
import RestaurantDetail from "../Restaurant/detail"

export default function RestaurantList() {
    const [restaurants, setRestaurants] = useState([])

    useEffect(() => {
        const params = {}
        axios.get("http://localhost:8080/restaurants", params)
          .then(response => {
            setRestaurants(response.data)
          })
          .catch(error => {
            console.error("Error fetching restaurants:", error);
          })
      }, restaurants)

    return(
        <div>
          <p className="list-titles">Top Restaurants in Mizdooni</p>
          <ul>
            {restaurants.slice(0, 6).map((restaurant, index) => (
              <li key={index}>
                <RestaurantCard restaurant={restaurant} />
              </li> 
            ))}
          </ul>
         
          <p className="list-titles">You Might Also Like</p>
          <ul>
              {restaurants.slice(6, 12).map((restaurant, index) => (
                <li key={index}>
                  <RestaurantCard restaurant={restaurant} />
                </li> 
              ))}
          </ul>
        </div>
    )
}