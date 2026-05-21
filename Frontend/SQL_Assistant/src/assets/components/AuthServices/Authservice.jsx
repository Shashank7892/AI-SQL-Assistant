import axios from "axios";

const API_URL="http://localhost:8080/";


export const loginss=async(data)=>{
    try{
        const response=await axios.post(API_URL+"auth/login",data);
        return response;
    }
    catch(error){
        throw error;
    }
}


export const querydata=async(data,token)=>{
    try{
        console.log(token);
        console.log(data);
        const response=await axios.post(API_URL+"user/generatesql",data,{headers:{"Authorization":`Bearer ${token}`}});
        return response;
    }
    catch(error){
        throw error;
    }
}