import React from "react";
import { HashRouter, Route} from "react-router-dom";
import Home from "./screen/home/Home";
import Matching from "./screen/matching/Matching";
import Login from "./screen/login/Login";
import Enroll from "./screen/Enrollment/Enrollment";
import Crew from "./screen/Crew/Crew";
import Manage from "./screen/manage/Manage";
import Mypage from "./screen/Mypage/Mypage";
import Auth from "./redux/hoc/auth";
function App(){
  return (
    <HashRouter>
     
      <Route path="/matching" exact={true} component={Auth(Matching,null)}/>
      <Route path="/login" exact={true} component={Auth(Login,false)}/>
      <Route path="/enroll" exact={true} component={Auth(Enroll,false)}/>
      <Route path="/crew" exact={true} component={Auth(Crew,true)}/>
      <Route path="/mypage" exact={true} component={Auth(Mypage,true)}/>
      <Route path="/manage" exact={true} component={Auth(Manage,true)}/>
      <Route path="/" exact={true} component={Home}/>
    </HashRouter>
    );
}

export default App;

