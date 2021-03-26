import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import KakaoSignUp from "../../components/login/KaKaoSignUp";
import GoogleSignUp from "../../components/login/GoogleSignUp";
import EnrollmentButton from "../../components/login/EnrollmentButton";
import LoginText from "../../components/login/login_text";
import CommonInput from "../../components/enrollment/enrollInput";
import "../../assets/css/Login/Login.css"
import LoginSubmitBtn from "../../components/login/loginSubmitBtn";
class Login extends React.Component {

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
            <LoginSubmitBtn/>
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


export default Login;