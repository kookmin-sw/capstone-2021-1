import React, { Component } from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import KakaoSignUp from "../../components/login/KaKaoSignUp";
import GoogleSignUp from "../../components/login/GoogleSignUp";
import EnrollmentButton from "../../components/login/EnrollmentButton";
import LoginText from "../../components/login/login_text";
import CommonInput from "../../components/enrollment/enrollInput";
import "../../assets/css/Login/Login.css"
import LoginSubmitBtn from "../../components/login/loginSubmitBtn";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
class Login extends React.Component {
 
  handleLogin=data=>{
    const{loginUser} = this.props;
    loginUser(data);
  }
  
  render() {
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="login_contents">
          <div className="login_input">
            <div className="login_input_id"><LoginText text="email"/><CommonInput doubleChecked={false} info="email"/></div>
            <div className="login_input_pw"><LoginText text="password"/><CommonInput doubleChecked={false} info="pw"/></div>
          </div>
          <div className="login_submit">
          <div className="login_submit_btn" onClick={()=>this.handleLogin({
    "uid":"dlwlsrn94@naver.com",
    "password":"1234"
})}>로그인</div>
            <EnrollmentButton/>          
          </div>
          <div className="sns_login">
            <div className="sns_kakao_login"><KakaoSignUp></KakaoSignUp></div>
            <div className="sns_google_login"><GoogleSignUp></GoogleSignUp></div>
          </div>
        </div>
      </section>
    );
  }
}



export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Login);