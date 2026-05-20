import { useContext } from "react";
import { Authcontext } from "./Authcontext";
import {useNavigate} from "react-router-dom";

const Sample=()=>{
    const {settoken}=useContext(Authcontext);
    const navigate=useNavigate();
    const handleLogout=()=>{
        localStorage.removeItem("token");
        settoken("");
        navigate("/");
        console.log("logged out sucessfully");
    }
    return(
        <>
        <div>
            <div className="login-box text-center">
                <h2>Welcome Back!</h2>
                <p>You are currently signed in.</p>
                <button onClick={handleLogout} className="logoutbtn"> logout </button>
            </div>
            <h2>logged in successfully</h2>
        </div>
        </>
    )
}
export default Sample;