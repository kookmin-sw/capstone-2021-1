
import { bindActionCreators, combineReducers } from 'redux';
import { LOGIN_USER , REGISTER_USER } from '../types';

const counterInitialState = { isLogin: false, marker_clicked: null };

function allFunction(state = counterInitialState, action){
    switch (action.type) {
        case REGISTER_USER:
            return state = {
                ...state,

            }
        case LOGIN_USER:
            return {
                ...state,
                user_info: action.payload,
                isLogin: true
            }
        default: 
            return state;
    }
}



export const reducer = combineReducers({ allFunction });

