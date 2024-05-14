import React from "react"
import "./reviews.css"

export default function ReviewItem({review}) {
    return(
        <>
            <div className="left">
                <div className="profile">
                    {review.username[0]}
                </div>

                <div className="comment">
                    <p className="username">
                        {review.username}
                    </p>

                    <ul className="review-detail">
                        <li>Overall &nbsp;<span>{review.overallRate}</span>.</li>
                        <li>Food &nbsp;<span>{review.foodRate}</span>.</li>
                        <li>Service &nbsp;<span>{review.serviceRate}</span>.</li>
                        <li>Ambiance &nbsp;<span>{review.ambianceRate}</span></li>
                    </ul>

                    <p className="comment-body">
                        {review.comment}
                    </p>
                </div>
            </div>

            <div className="right">
                <div className="stars">
                    <img className="staricon" src="./../icons/svg/pinkstar.svg" alt=""/>
                    <img className="staricon" src="./../icons/svg/pinkstar.svg" alt=""/>
                    <img className="staricon" src="./../icons/svg/pinkstar.svg" alt=""/>
                    <img className="staricon" src="./../icons/svg/pinkstar.svg" alt=""/>
                    <img className="staricon" src="./../icons/svg/star-rate-rating-outline-svgrepo-com.svg" alt=""/>
                </div>

                <p className="date">
                    Dined on February 17, 2024
                </p>
            </div>
        </>
    )
}