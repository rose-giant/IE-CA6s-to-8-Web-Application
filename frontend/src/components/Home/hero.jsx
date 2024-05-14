import React from "react"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

export default function Hero() {
    const [location, setLocation] = useState('')
    const [restaurant, setRestaurant] = useState('')
    const [search, setSearch] = useState('')
    const navigate = useNavigate()
    const handleSearch = (e) => {
        e.preventDefault()   
        const queryString = `?location=${location}&restaurant=${restaurant}&search=${search}`;
        navigate(`/search/${queryString}`)
    }

    return(
        <div className="container">
            <div className="hero">
                    <img className="cover" src="./covers/pizzaaa.png" alt=""/>
                    <div className="inner-main">
                        <img className="logo" src="./logo/logo.png" alt=""/>

                        <div className="bottom-nav">
                            <form action="./Search/Search.html" method="" onSubmit={handleSearch}>
                                <select name="Location" id="location" value={location} onChange={(e) => setLocation(e.target.value)}>
                                    <option>America</option>
                                    <option>Europe</option>
                                    <option>Asia</option>
                                    <option>Africa</option>
                                </select>
                                <select name="Restaurant" id="restaurant" value={restaurant} onChange={(e) => setRestaurant(e.target.value)}>
                                    <option>Asian</option>
                                    <option>American</option>
                                    <option>European</option>
                                    <option>African</option>
                                </select>
                                <input type="text" placeholder="type restaurant..." value={search} onChange={(e) => setSearch(e.target.value)} />
                                <button type="submit" className="search-btn" >
                                    Search
                                </button>
                            </form>
                        </div>
                    </div>
            </div>
        </div>
    )
}