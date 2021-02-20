import React, { Component } from 'react';
import KakaoSignUp from "./KaKaoSignUp";
import GoogleSignUp from "./GoogleSignUp";

class LoginContainer extends Component {
    

    render() {
        return (
            <>
                <div>
                    <KakaoSignUp></KakaoSignUp>
                    <GoogleSignUp></GoogleSignUp>
                </div>

            </>
        );
    }
}


export default LoginContainer;