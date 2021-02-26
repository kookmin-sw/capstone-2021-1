import { MARKERCLICK , REGISTER} from '../actions/index';
import { bindActionCreators, combineReducers } from 'redux';

const counterInitialState = { isLogin: false, marker_clicked: null };

function allFunction(state = counterInitialState, action){
    switch (action.type) {
        case MARKERCLICK:
            return state = {
                ...state,
                marker_clicked : action.marker_data
            }
        case REGISTER:
            return {
                ...state,
                register: action.payload
            }
        default: 
            return state;
    }
}



export const reducer = combineReducers({ allFunction });

