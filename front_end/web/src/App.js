import React from "react";
import { BrowserRouter, Route, withRouter} from "react-router-dom";
import { connect } from 'react-redux';
import {actionCreators} from "./redux/reducers/index"
import { bindActionCreators } from 'redux';
import FirstPage from "./screen/home/FristPage";
import Matching from "./screen/matching/Matching";
import Login from "./screen/login/Login";
import Enroll from "./screen/Enrollment/Enrollment";
import Crew from "./screen/Crew/Crew";
import Manage from "./screen/manage/Manage";
import Mypage from "./screen/Mypage/Mypage";
import CrewMake from "./screen/Crew/CrewMake";
import Home from "./screen/home/Home";
import CrewDetail from "./screen/Crew/CrewDetail";
import MatchingMake from "./screen/matching/MatchingMake";
import MatchingInfo from "./screen/matching/MatchingInfo";
import MemberInfo from "./screen/manage/MemberInfo";
import MatchingCollect from "./screen/matching/MatchingCollect";
import Mymatching from "./screen/Mypage/Mymatching";
import MyCrew from "./screen/Mypage/MyCrew";
import sendMatchingRequest from "./screen/Mypage/sendMatchingRequest";
import sendCrewRequest from "./screen/Mypage/receiveCrewRequest";
import addr from "./screen/matching/addr";
class App extends React.Component{
 
  render(){
    
  return (
    <BrowserRouter>
      <Route path="/matching" exact={true} component={Matching}/>
      <Route path="/login" exact={true} component={Login}/>
      <Route path="/enroll" exact={true} component={Enroll}/>
      <Route path="/crew" exact={true} component={Crew}/>
      <Route path="/crewMake" exact={true} component={CrewMake}/>
      <Route path="/mypage" exact={true} component={Mypage}/>
      <Route path="/manage" exact={true} component={Manage}/>
      <Route path="/" exact={true} component={FirstPage}/>
      <Route path="/home" exact={true} component={Home}/>
      <Route path="/crew/:id" component={CrewDetail}/>
      <Route path="/matching/make" component={MatchingMake}/>
      <Route path="/matching/detail/:id" component={MatchingInfo}/>
      <Route path="/member/:id" component={MemberInfo}/>
      <Route path="/AllMatching" component={MatchingCollect}/>
      <Route path="/MyMatching" component={Mymatching}/>
      <Route path="/MyCrew" component={MyCrew}/>
      <Route path="/mypage/sendMatchingRequest" component={sendMatchingRequest}/>
      <Route path="/mypage/sendCrewRequest" component={sendCrewRequest}/>
      <Route path="/findaddr" component={addr}/>
    </BrowserRouter>
    );
  }
}



export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(App);