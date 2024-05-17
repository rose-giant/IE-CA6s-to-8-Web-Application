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
    // const searchParams = new URLSearchParams(location.search)
    // const locationParam = searchParams.get('location')
    // const restaurantParam = searchParams.get('restaurant')
    // const searchParam = searchParams.get('search')
    const location = useLocation()
    const [currentPage, setCurrentPage] = useState(1)
    const itemsPerPage = 6
    const [restaurants, setRestaurants] = useState([])

    useEffect(() => {
      const data = location.state?.data
      setRestaurants(JSON.stringify(data))
    }, [])

    // const filteredRestaurants = restaurants.filter(restaurant => {
    //     const matchesLocation = !locationParam || restaurant.address.city === locationParam
    //     const matchesRestaurantType = !restaurantParam || restaurant.type === restaurantParam
    //     const matchesSearchQuery = !searchParam || restaurant.name.toLowerCase().includes(searchParam.toLowerCase())
    //     return matchesLocation && matchesRestaurantType && matchesSearchQuery
    // })

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