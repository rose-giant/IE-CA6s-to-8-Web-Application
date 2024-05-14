import { useNavigate } from "react-router-dom"

export default function AccessDeniedPage() {
    const navigate = useNavigate()
    return (
        <>
            <section >
                <div>
                    <img className="err-image" src="/errors/403.png" />
                    <p className="err-msg">Access Forbidden</p>
                </div>
                <button className="center-btn" onClick={() => navigate("/")}>
                    Return
                </button>
            </section>

        </>
    )
}


