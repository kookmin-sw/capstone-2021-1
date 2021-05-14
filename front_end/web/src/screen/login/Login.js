import React, { Component } from "react";
import KakaoSignUp from "../../components/login/KaKaoSignUp";
import GoogleSignUp from "../../components/login/GoogleSignUp";
import EnrollmentButton from "../../components/login/EnrollmentButton";
import LoginText from "../../components/login/login_text";
import "../../assets/css/Login/Login.css"
import { bindActionCreators } from 'redux';
import main_home_img from "../../assets/images/common/main_screen.jpg"
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import axios from "axios";
import login_background from '../../assets/images/common/Hamburger_icon.png';

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
    'accessToken': data.accessToken,
    }
    setHeader(header);
  }

  
  render() {
    setInterval(() => {
      if(LOGIN_DATA != null){
        this.setAccessToken(LOGIN_DATA);
        this.props.history.push("/home");
        LOGIN_DATA = null;
      }
    }, 500);
    
    return (
      <section className="container">
          <div className="login_header">
            <div className="login_background">
              
            </div>
            <div className="exit_btn">
              X
            </div>
          </div>
      
          <div className="login_input">
            <div className="login_input_id"><input onChange={this.uidChange} placeholder="EMAIL"></input></div>
            <div className="login_input_pw"><input  onChange={this.pwChange} placeholder="PASSWORD"></input></div>
          </div>
          <div className="login_submit">
          <div className="login_submit_btn" onClick={()=>handleLogin({
            "uid": this.state.uid,
            "password": this.state.password,
            })}>로그인</div>
            
          </div>
          <div className="sns_login">
            <div className="sns_kakao_login"><KakaoSignUp></KakaoSignUp></div>
            <div className="sns_google_login"><GoogleSignUp></GoogleSignUp></div>
          </div>
        
      </section>
    );
  }
}



export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Login);