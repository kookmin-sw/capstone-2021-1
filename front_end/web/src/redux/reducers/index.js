
import { combineReducers } from 'redux';
import { LOGIN_USER , REGISTER_USER } from '../types';

const InitialState = { isLogin: false, user_nickname: null, access_token: null};

function allFunction(state = InitialState, action){
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



export const reducer = combineReducers({ allFunction });

