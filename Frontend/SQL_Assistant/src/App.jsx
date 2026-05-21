import { useContext, useState } from 'react'
import './App.css'
import {BrowserRouter,Routes,Route} from "react-router-dom"
import Login from './assets/components/login'
import Sample from './assets/components/sample'
import { Authcontext } from './assets/components/Authcontext'
import SqlAssistant from './assets/components/SqlAssistant/SqlAssistant'


function App() {
  const {token}=useContext(Authcontext);
  return (
    <>
    <BrowserRouter>
    <Routes>
      {/* Login Route */}
      <Route path="/" element={<Login/>} />
      <Route path="/SqlAssistant" element={token?<SqlAssistant/>:<Login/>}/>
      <Route path="/sample" element={token?<Sample/>:<Login/>}/>
      {/* <Route path="/logout" element={token?<Logout/>:<Login/>}/> */}
    </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
