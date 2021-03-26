import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import { Provider } from "react-redux";
import  store, {history }from './redux/configureStore';

import { ConnectedRouter } from 'react-router-redux';

ReactDOM.render(
  <Provider store={store}>
    <ConnectedRouter history={ history }>
      <App />
    </ConnectedRouter>
  </Provider>
  ,
  document.getElementById("root")
);