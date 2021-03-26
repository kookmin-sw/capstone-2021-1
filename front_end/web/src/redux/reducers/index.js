
import { combineReducers } from 'redux';
import { REGISTER_USER, LOGIN_USER } from "../types/index";
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

const InitialState = { isLogin: false };

function reducer(state = InitialState, action){
    switch (action.type) {
        case REGISTER_USER:
            return state = {
                ...state,
            }
        case LOGIN_USER:
            return {
                ...state,
                user_nickname: action.payload.nickname,
                access_token: action.payload.access_token,
                isLogin: true
            }
        default: 
            return state;
    }
}
const actionCreators = {
    loginUser,
    registerUser
  };

export { actionCreators };

export default reducer;



