import { REGISTER_USER, LOGIN_USER, AUTH_USER } from "../types/index";
import axios from 'axios';

const USER_URL = "http://54.180.98.138:8080";

export function registerUser(dataToSubmit) {
  const data = axios.request("post", USER_URL + "/signup", dataToSubmit);
  return {
    type: REGISTER_USER,
    payload: data,
  };
}

export function loginUser(dataToSubmit) {
  const data = axios.request("post", USER_URL + "/signin", dataToSubmit);
  return {
    type: LOGIN_USER,
    payload: data,
  };
}


// export function logoutUser() {
//   const data = axios.request("post", USER_URL + "/logout");
//   return {
//     type: LOGOUT_USER,
//     payload: data,
//   };
// }

 export function authUser() {
   const data = axios.request("post", USER_URL + "/auth");
   return {
     type: AUTH_USER,
     payload: data,
   };
 }