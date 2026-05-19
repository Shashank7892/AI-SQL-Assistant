import React, { useState } from "react";

const Login = ()=> {
    const [data,setData]=useState({
            email:"",
            password:""
        })

    const onChangeHandler=(event)=>{
        const name=event.target.name;
        const value=event.target.value;
        setData(data=>({...data,[name]:value}))
    }
    return(
        
    <div className="login-container">
        <form className="login-form" >
        <div className="login-box">
            <h2>Login</h2>

            <input type="email" name="email" placeholder="sample@gmail.com" value={data.email} onChange={onChangeHandler} required />
            <input type="password" name="password" placeholder="12346" value={data.password} onChange={onChangeHandler} required />
            <button type="submit">Sign in</button>
            <button type="reset"> Reset</button>
            <div>

            </div>
        </div>
        </form>
    </div>
    
)    
}
export default Login;