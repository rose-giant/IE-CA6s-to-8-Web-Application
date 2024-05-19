import React from "react"
import { useLocation } from "react-router-dom"
import "./search.css"
import axios from "axios"
import { useEffect, useState } from "react"
import RestaurantCard from "../Home/restaurantcard"
import Nav from "../Nav/nav"
import { useContext } from "react"
import { Context } from "../../App"
import Footer from "../Footer/footer"

export default function Search() {
    const [signedIn, setSignedIn] = useContext(Context)
    const location = useLocation()
    const searchParams = new URLSearchParams(location.search)
    const locationParam = searchParams.get('address')
    const restaurantParam = searchParams.get('type')
    const searchParam = searchParams.get('name')
    const [currentPage, setCurrentPage] = useState(1)
    const itemsPerPage = 6
    const [restaurants, setRestaurants] = useState([])

    useEffect(() => {
      console.log(locationParam, restaurantParam, searchParam)
      let url = "http://localhost:8080/restaurants/search?" 
        if(locationParam) url += ("address="+locationParam+"&")
        if(restaurantParam) url += ("type="+restaurantParam+"&")
        if(searchParam) url += ("name="+searchParam)
      axios.get(url)
            .then(response => {
              setRestaurants(response.data)
            })
      
    }, [])

    const totalPages = Math.ceil(restaurants.length / itemsPerPage)
    const indexOfLastItem = currentPage * itemsPerPage
    const indexOfFirstItem = indexOfLastItem - itemsPerPage
    const currentItems = restaurants.slice(indexOfFirstItem, indexOfLastItem)

    return(
      <>
        <div className="homepage"> 
        </div>
        <div className="search-container">
          <ul>
            {currentItems.map((restaurant, index) => (
              <li key={index}>
                <RestaurantCard restaurant={restaurant} />
              </li> 
            ))}
          </ul>
        
        </div>

        <div className="pages">
            <button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1}>Previous</button>
            <span>{currentPage} of {totalPages}</span>
            <button onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage === totalPages}>Next</button>
            </div>
        <Footer />
      </>
    )
}