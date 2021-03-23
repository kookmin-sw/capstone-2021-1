import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import KakaoSignUp from "../../components/login/KaKaoSignUp";
import GoogleSignUp from "../../components/login/GoogleSignUp";
import EnrollmentButton from "../../components/login/EnrollmentButton";
import "../../assets/css/Login/Login.css"

class Login extends React.Component {

  render() {
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="login_contents">
          <div className="login_input">
            <div className="login_input_id"><h1>email : </h1><input id="login_id" placeholder="email을 입력해주세요."></input></div>
            <div className="login_input_pw"><h1>password : </h1><input id="login_pw" placeholder="pw를 입력해주세요."></input></div>
          </div>
          <div className="login_submit">
            <div className="login_submit_btn">로그인</div>
            <EnrollmentButton></EnrollmentButton>
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


export default Login;