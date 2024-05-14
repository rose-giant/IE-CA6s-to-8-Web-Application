import React, { useContext, useState, useEffect } from "react"
import { useLocation } from "react-router-dom"
import "./manage.css"
import { Context } from "../../App"
import { useParams } from 'react-router-dom';
import Footer from "../Footer/footer"
import axios from "axios"
import TableList from "./TableList";
import ReservationList from "./ReservationList";
export const GlobalTable = React.createContext()


export default function ManageRestaurant() {

  const { restaurantName } = useParams();
  const [restaurant, setRestaurant] = useState(null)
  const [table, setTable] = useState(null)
  

  useEffect(() => {
    axios.get("http://localhost:8080/restaurants")
      .then(response => {
        setRestaurant(response.data.filter(rest => rest.name == restaurantName)[0]);
      })
      .catch(error => {
        console.error("Error fetching restaurants:", error);
      });
  }, [restaurant])

  // console.log(restaurantName)
  // console.log(restaurant)
  // console.log(table)

  return (
    <>
    <GlobalTable.Provider value={[table, setTable]}>
    {restaurant && (
        <div>
          <div className="sub-nav">
            <p className="normal-font">{restaurant.name}</p>
            <p className="normal-font">Address: {restaurant.address.street}, {restaurant.address.city}, {restaurant.address.country}</p>
          </div>
          <div className="grid-container">
            <ReservationList restName={restaurant.name}/>
            <TableList restName={restaurant.name} />
          </div>
        </div>
      )}
    </GlobalTable.Provider>
    </>
  )
}


