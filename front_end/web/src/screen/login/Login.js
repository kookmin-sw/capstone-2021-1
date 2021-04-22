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
import axios from "axios";

var LOGIN_DATA ;

async function handleLogin(data){
  var data = await axios({
    method:'post',
    url: "http://54.180.98.138:8080"+"/signin",
    data: {
      uid: data.uid,
      password : data.password,
    },
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  LOGIN_DATA = data
  return data;
}

class Login extends React.Component {

  uidChange = (e) => {
    this.setState({
        uid:e.target.value,
    })
  }
  

  pwChange = (e) => {
      this.setState({
          password:e.target.value,
      })
  }

  state = {

  }
  
  setAccessToken = (data) =>{
    const { loginUser,setHeader } = this.props;
    loginUser(data);
    alert(data.nickname + "님 환영합니다.");
    const header = {
    'Content-type': 'application/x-www-form-urlencoded; charset=UTF-8',
    'Accept': '*/*',
    'X-AUTH-TOKEN': data.accessToken,
    }
    setHeader(header);
  }

  
  render() {
    setInterval(() => {
      if(LOGIN_DATA != null){
        this.setAccessToken(LOGIN_DATA);
        this.props.history.go(-1);
        LOGIN_DATA = null;
      }
    }, 500);
    
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="login_contents">
          <div className="login_input">
            <div className="login_input_id"><LoginText text="email"/><input onChange={this.uidChange} placeholder="email을 입력해 주세요."></input></div>
            <div className="login_input_pw"><LoginText text="password"/><input  onChange={this.pwChange} placeholder="비밀번호를 입력해 주세요."></input></div>
          </div>
          <div className="login_submit">
          <div className="login_submit_btn" onClick={()=>handleLogin({
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