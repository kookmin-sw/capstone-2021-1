import React, { Component } from 'react';
import KakaoSignUp from "./KaKaoSignUp";
import GoogleSignUp from "./GoogleSignUp";
import EnrollmentButton from "./EnrollmentButton";

class LoginContainer extends Component {
    

    render() {
        return (
            <>
                <div>
                    <KakaoSignUp></KakaoSignUp>
                    <GoogleSignUp></GoogleSignUp>
                    <EnrollmentButton></EnrollmentButton>
                </div>

            </>
        );
    }
}


export default LoginContainer;