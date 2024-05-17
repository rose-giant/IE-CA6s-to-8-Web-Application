import React from "react"
import ReviewItem from "./reviewItem"
import axios from "axios"
import { useState, useEffect } from "react"
import "./reviews.css"
import ReviewModal from "./../Helpers/testmodal"
import { useContext } from "react"
import { Context } from "../../App"

export default function ReviewList({ restaurantName }) {
    const [singedIn, setSignedIn] = useContext(Context)
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [reviews, setreviews] = useState([])
    let aveOverall = 0
    let aveAmb = 0
    let aveServ = 0
    let aveFood = 0
    let sumAmb = 0
    let sumFood = 0
    let sumServ = 0
    let sumOverall = 0
    let tempList = []

    const findTargetRest = (allReviews) => {
        allReviews.forEach(item => {
            if (String(item.restaurantName).valueOf().trim() == String(restaurantName).valueOf().trim()) {
                tempList.push(item)
            }
        })

        setreviews(tempList)
        reviews.forEach(item => {    
            sumAmb = sumAmb + item.ambianceRate
            sumFood += item.foodRate
            sumServ += item.serviceRate
            sumOverall += item.overallRate
        })

        const len = reviews.length 
        aveOverall = sumOverall / len
        aveAmb = sumAmb / len
        aveFood = sumFood / len
        aveServ = sumServ / len
    }

    useEffect(() => {
        const params = { restaurantName: restaurantName }
        axios.get("http://localhost:8080/reviews", params)
          .then(response => {
            // findTargetRest(response.data)
            setreviews(response.data)
          })
          .catch(error => {
            console.error("Error fetching reviews:", error)
          })
      }, [reviews])

    return(
        <>
            <div className="review-sec">
                <div className="overview">
                    <div className="left">
                        <p className="title">What {reviews.length} people are saying</p>
                        <div className="rating">
                            <div className="stars">
                            <svg fill="#ED3545" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M16 4.588l2.833 8.719H28l-7.416 5.387 2.832 8.719L16 22.023l-7.417 5.389 2.833-8.719L4 13.307h9.167L16 4.588z"></path></g></svg>
                            <svg fill="#ED3545" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M16 4.588l2.833 8.719H28l-7.416 5.387 2.832 8.719L16 22.023l-7.417 5.389 2.833-8.719L4 13.307h9.167L16 4.588z"></path></g></svg>
                            <svg fill="#ED3545" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M16 4.588l2.833 8.719H28l-7.416 5.387 2.832 8.719L16 22.023l-7.417 5.389 2.833-8.719L4 13.307h9.167L16 4.588z"></path></g></svg>
                            <svg fill="#ED3545" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M16 4.588l2.833 8.719H28l-7.416 5.387 2.832 8.719L16 22.023l-7.417 5.389 2.833-8.719L4 13.307h9.167L16 4.588z"></path></g></svg>
                            <svg fill="#ED3545" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M16 4.588l2.833 8.719H28l-7.416 5.387 2.832 8.719L16 22.023l-7.417 5.389 2.833-8.719L4 13.307h9.167L16 4.588z"></path></g></svg>
                            </div>
                        </div>
                        {
                            singedIn != "" ? 
                                (
                                    <>
                                    <button className="add-rev-btn" onClick={() => setIsModalOpen(true)}>Add Review</button>
                                    <ReviewModal restaurantName={restaurantName} isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
                                    </>
                                ) :
                                (
                                    <button className="add-rev-btn" onClick={() => setIsModalOpen(true)} style={{backgroundColor: "var(--light-grey)", cursor: "default"}}>Add Review</button>
                                )
                        }
                        
                    </div>
                    <div className="right">
                    <ul>
                        <li>
                            <p className="category">
                                Food
                            </p>
                            <p className="rating">
                                {aveFood}
                            </p>
                        </li>
                        <li>
                            <p className="category">
                                Service
                            </p>
                            <p className="rating">
                                {aveServ}
                            </p>
                        </li>
                        <li>
                            <p className="category">
                                Ambiance
                            </p>
                            <p className="rating">
                                {aveAmb}
                            </p>
                        </li>
                        <li>
                            <p className="category">
                                Overall
                            </p>
                            <p className="rating">
                                {aveOverall}
                            </p>
                        </li>   
                    </ul>
                </div>
                </div>
                <div className="reviews">
                <ul>
                    {reviews.map((review, index) => (
                        <li key={index}>
                            <ReviewItem review={review}/>
                        </li>
                    ))}
                </ul>
                </div>
            </div>            
        </>
    )
}