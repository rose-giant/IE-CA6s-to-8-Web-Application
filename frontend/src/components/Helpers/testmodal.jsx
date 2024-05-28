import React, { useContext, useEffect, useState } from "react"
import { Context } from "../../App"
import "./reviewmodal.css"
import axios from "axios"

const ReviewModal = ({ isOpen, onClose, restaurantName }) => {
    const [singedIn, setSignedIn] = useContext(Context)
    const [review, setReview] = useState("")

    // const setFakes = (listie) => {
    //   listie.forEach(item => {
    //       if(String(item.restaurantName).valueOf().trim() == String(restaurantName).valueOf()) {
    //           console.log("1 , ", String(item.username).valueOf().trim())
    //           console.log("2 , ", String(singedIn).valueOf())
    //           if(String(item.username).valueOf().trim() == String(singedIn).valueOf().trim()) {
    //             setReview(item)
    //             console.log("matched item = ", item)
    //           }
    //       }
    //   })
    // }

    const submitReview = (e) => {
      e.preventDefault()
      const params = { username: singedIn, restaurantName: restaurantName, comment: review.comment, ambianceRate: review.ambianceRate, overallRate: review.overallRate, serviceRate: review.serviceRate, foodRate: review.foodRate}
          axios({
            method: 'post',
            url: "http://localhost:8080/reviews",
            headers: {}, 
            data: params
          })
          .catch(error => {
            console.error("Error fetching reviews:", error);
      })
    }

    useEffect(() => {
      axios.get("http://localhost:8080/reviews")
          .then(response => {
            // setFakes(response.data)
            setReview(response.data)
          })
          .catch(error => {
            console.error("Error fetching reviews:", error);
          })
        
    }, [review, singedIn])

    if (!isOpen) return null;
    return (
        <div style={{
          position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.5)',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center', }}>
        <div className="modall" style={{ backgroundColor: 'white', padding: '20px', borderRadius: '8px', maxWidth: '80%', }}>
          <button className="close-btn" onClick={onClose} style={{ float: 'right' }}>X</button>
          
            <div className="modal-head">
              <p>
              Add Review for <span>{review.restaurantName}  </span> 
              </p> 
            </div>
          
            <hr />

            <div className="modal-body">
              <p className="sub">
              Note: Reviews can only be made by diners who have eaten at this restaurant 
              </p>

              <ul className="review-rates">
                <li>
                  <p>Overall: &nbsp; {review.overallRate}</p>
                  <p>Food: &nbsp; {review.foodRate}</p>
                  <p>Ambiance: &nbsp; {review.ambianceRate}</p>
                  <p>Service: &nbsp; {review.serviceRate}</p>
                </li>
              </ul>

                <textarea name="review" id="">
                  {review.comment}
                </textarea>
             
              <div className="submission">
                <button className="submit" type="submit" onClick={submitReview}>Submit Review</button>
                <button className="cancel">Cancel</button>
              </div>
            </div>
        </div>
      </div>
    )
  }

export default ReviewModal