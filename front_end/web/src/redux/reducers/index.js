import { MARKERCLICK } from '../actions/index';
import { bindActionCreators, combineReducers } from 'redux';

const counterInitialState = { isLogin: false, marker_clicked: null };

const markerClick = (state = counterInitialState, action) => {
    switch (action.type) {
        case MARKERCLICK:
            return state = {
                ...state,
                marker_clicked : action.marker_data
            }
        default: return state;
    }
}


export const reducer = combineReducers({ markerClick });

