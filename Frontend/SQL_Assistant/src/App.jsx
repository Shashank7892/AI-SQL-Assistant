import { useState } from 'react'
import './App.css'
import {BrowserRouter,Routes,Route} from "react-router-dom"
import Login from './assets/components/login'


function App() {
  return (
    <>
    <BrowserRouter>
    <Routes>
      {/* Login Route */}
      <Route path="/" element={<Login/>} />
    </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
