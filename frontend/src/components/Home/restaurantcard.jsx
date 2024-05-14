import "./restaurantcard.css"
import { IsOpen } from "../Helpers/isOpen"
import { useNavigate } from "react-router-dom"

export default function RestaurantCard({ restaurant }) {
    const navigate = useNavigate()
    const hello = () => {
        navigate(`restaurant/${restaurant.name}/`)
    }
        
    return(
        <>
            <div className="card">
                    <div className="body">
                        {/* <div className="pancreas">
                            <img id="star" src="./icons/svg/pinkstar.svg" alt=""/>
                            <img id="star" src="./icons/svg/pinkstar.svg" alt=""/>
                            <img id="star" src="./icons/svg/pinkstar.svg" alt=""/>
                            <img id="star" src="./icons/svg/pinkstar.svg" alt=""/>
                            <img id="star" src="./icons/svg/pinkstar.svg" alt=""/>
                        </div> */}
                        <img src={restaurant.image} alt=""/>
                    </div>

                    <div className="card-footer">
                        <p onClick={hello}>
                        <p className="title">
                            {restaurant.name}
                        </p>
                        </p>
                        <p className="reviews">
                            2096 &nbsp; reviews
                        </p>
                        <p classNameName="type">
                            {restaurant.type}
                        </p>
                        <p className="place">                        
                            <p className="location">
                                <img className="icon location-icon" src="./icons/location.png" alt=""/>
                                <p>{restaurant.address.city}</p>
                            </p>
                        </p>

                        <p className="open">
                            <span className="open-span">
                                {IsOpen(restaurant.startTime, restaurant.endTime) ? 
                                    `Open. Closes at ${restaurant.endTime}` :
                                    `Closed. Opens at ${restaurant.startTime}`
                                } &nbsp;&nbsp;&nbsp;
                            </span>
                        </p>

                    </div>
                </div>
        </>
    )
}