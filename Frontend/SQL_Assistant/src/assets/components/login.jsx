import React, { useContext, useState } from "react";
import { loginss } from "./AuthServices/Authservice";
import { useNavigate } from "react-router-dom";
import { Authcontext } from "./Authcontext";

const Login = ()=> {
    const navigate=useNavigate();
    const {settoken}=useContext(Authcontext);
    const [data,setData]=useState({
            email:"",
            password:""
        })

    const onChangeHandler=(event)=>{
        const name=event.target.name;
        const value=event.target.value;
        setData(data=>({...data,[name]:value}))
    }

    const onSubmitHandler=async (event)=>{
        event.preventDefault();

        try{
            const response=await loginss(data);
            if(response.status===200){
                localStorage.setItem("token",response.data.token)
                settoken(response.data.token);
                
                console.log(response.data.token);
                navigate("/SqlAssistant");
            }
            else{
                localStorage.removeItem("token");
                console.log("unable to login");
            }
        }
        catch(error){
            localStorage.removeItem("token");
            throw error;
        }
    }
    // const handleLogout=()=>{
    //     localStorage.removeItem("token");
    //     settoken("");
    //     setData({email:"",password:""});
    //     navigate("/");
    //     console.log("logged out successfully");
    // }
    return(
        
    <div className="login-container">
       
        <form className="login-form" onSubmit={onSubmitHandler}>
        <div className="login-box">
            <h2>Login</h2>
            <input type="email" name="email" placeholder="sample@gmail.com" value={data.email} onChange={onChangeHandler} required />
            <input type="password" name="password" placeholder="12346" value={data.password} onChange={onChangeHandler} required />
            <button type="submit">Sign in</button>
            <button type="reset" onClick={()=> setData({email:"",password:""})}> Reset</button>
            <div>

            </div>
        </div>
        </form>  
    </div>
    
)    
}
export default Login;