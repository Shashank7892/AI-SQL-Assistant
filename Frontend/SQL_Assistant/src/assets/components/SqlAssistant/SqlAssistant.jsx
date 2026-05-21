import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Authcontext } from "../Authcontext";
import { querydata } from "../AuthServices/Authservice";
const SqlAssistant=()=>{

    const navigate=useNavigate();

    const {token,settoken}=useContext(Authcontext);

    const [result,setresult]=useState("");

    const [data,setdata]=useState({
        prompt:""
    })

    const onchangeHandler=(event)=>{
        const name=event.target.name;
        const value=event.target.value;

        setdata(data=>({...data,[name]:value}))
    }

    const onSendprompt=async ()=>{
        try{
            const response=await querydata(data,token);
            if(response.status===200){
                setresult(response.data);
                console.log(response.data);
            }
            else{
                console.log("error");
            }
        }
        catch(error){
            console.log(error);
        }
    }

    const handleLogout=()=>{
        localStorage.removeItem("token");
        settoken("");
        navigate("/");
        console.log("logogged out succesfully");
    }

    const onHandleSubmit=()=>{
        onSendprompt();
        setdata({prompt:""});
    }

    return(
        <div>
            <button onClick={handleLogout}>signout</button>
            <input type="text" name="prompt" value={data.prompt} onChange={onchangeHandler} placeholder="Enter Prompt"/>
            <button onClick={onHandleSubmit}>Enter</button>
            <h2>{result.generatedSql}</h2>
        </div>
    )
}

export default SqlAssistant