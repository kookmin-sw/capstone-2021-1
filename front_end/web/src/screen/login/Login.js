import React from "react";
import Header from "../../components/common/header";
import LoginContentsContainer from "../../components/login/loginContainer";
import "./Login.css";

class Login extends React.Component {

  render() {
    return (
      <section className="container">
        <Header/>
        <LoginContentsContainer>
            
        </LoginContentsContainer>
      </section>
    );
  }
}


export default Login;