import { combineReducers, createStore, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import { routerReducer, routerMiddleware } from "react-router-redux";
import reducer from "./reducers/index";
import { createBrowserHistory } from 'history';
import  { composeWithDevTools } from "redux-devtools-extension";

const env = process.env.NODE_ENV;

const history = createBrowserHistory()

const middlewares = [thunk, routerMiddleware(history)];

if(env === 'development') {
  const { logger } = require("redux-logger");
  middlewares.push(logger);
}

const reducers = combineReducers({
  reducer,
  routing: routerReducer,
});

let store;

if (env === "development") {
  store = () =>
    createStore(reducers,
    composeWithDevTools(applyMiddleware(...middlewares)));
} else {
  store = () => createStore(reducers, applyMiddleware(...middlewares));
}


export {history};
export default store();