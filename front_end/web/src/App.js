import React from "react";
import { HashRouter, Route} from "react-router-dom";
import Home from "./screen/home/Home";
import Matching from "./screen/matching/Matching";
import Login from "./screen/login/Login";
function App(){
  
  return (
    <HashRouter>
      <Route path="/" exact={true} component={Home}/>
      <Route path="/matching" exact={true} component={Matching}/>
      <Route path="/login" exact={true} component={Login}/>
    </HashRouter>
    );
}

export default App;

