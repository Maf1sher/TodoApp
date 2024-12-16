import React from 'react';
import {Link, useNavigate} from "react-router-dom";
import {isUserLoggedIn, logout} from "../api/api.js";
import logo from "../assets/logo.png";

export function NavBar() {

    const loggedIn = isUserLoggedIn();
    const navigate = useNavigate();

    function handleLogout() {
        logout()
        navigate("/login")
    }

    return (
        <nav className="w-full bg-gray-800 h-16 flex justify-between">
            <img src={logo} alt="logo" className="h-full p-2" />
            <ul className="flex justify-end items-center h-full mr-5 text-white w-20">
                <li className="nav-item">
                    {loggedIn ?
                        <button onClick={handleLogout}>logout</button>:
                        <Link to="/login">logout</Link>
                    }
                </li>
            </ul>
        </nav>
    );
}

