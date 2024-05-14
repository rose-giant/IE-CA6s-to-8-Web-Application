import React from "react"
import Nav from './../components/Nav/nav'
import Hero from './../components/Home/hero'
import "./../components/Home/home.css"
import RestaurantList from "./../components/Home/restaurantlist"
import About from "../components/Home/about"
import Footer from "../components/Footer/footer"

export default function HomePage() {
    return (
        <>
        <div className="homepage">
            <Hero />
            <RestaurantList />
            <About />
        </div>
        <Footer />
        </>
    )
}