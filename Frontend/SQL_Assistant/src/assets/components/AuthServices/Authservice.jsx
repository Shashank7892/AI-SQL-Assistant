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