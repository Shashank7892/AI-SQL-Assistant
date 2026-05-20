import { createContext,useEffect,useState } from "react";

// context container
export const Authcontext=createContext();

// provider component

export const Authprovider=({children})=>{
    const [token,settoken]=useState(localStorage.getItem("token") ||"");

    useEffect(()=>{
        if(token){
            localStorage.setItem("token",token);
        }
        else{
            localStorage.removeItem("token");
        }
    },[token]);

return(
<Authcontext.Provider value={{token,settoken}}>
    {children}
</Authcontext.Provider>)
}
