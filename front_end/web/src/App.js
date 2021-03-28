import React from "react";
import { BrowserRouter, Route, withRouter} from "react-router-dom";
import { connect } from 'react-redux';
import {actionCreators} from "./redux/reducers/index"
import { bindActionCreators } from 'redux';
import Home from "./screen/home/Home";
import Matching from "./screen/matching/Matching";
import Login from "./screen/login/Login";
import Enroll from "./screen/Enrollment/Enrollment";
import Crew from "./screen/Crew/Crew";
import Manage from "./screen/manage/Manage";
import Mypage from "./screen/Mypage/Mypage";


class App extends React.Component{
 
  render(){
    console.log(this.props);
  return (
    <BrowserRouter>
      <Route path="/matching" exact={true} component={Matching}/>
      <Route path="/login" exact={true} component={Login}/>
      <Route path="/enroll" exact={true} component={Enroll}/>
      <Route path="/crew" exact={true} component={Crew}/>
      <Route path="/mypage" exact={true} component={Mypage}/>
      <Route path="/manage" exact={true} component={Manage}/>
      <Route path="/" exact={true} component={Home}/>
    </BrowserRouter>
    );
  }
}



export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(App);