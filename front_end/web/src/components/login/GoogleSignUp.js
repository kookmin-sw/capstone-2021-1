import React from 'react';
import GoogleLogin from 'react-google-login';
import "../../assets/css/Login/Login.css"

const clientId = "267810043777-e4a9h4smd6nvsk137in0mcmbctrv0ujc.apps.googleusercontent.com";

export default function GoogleSignUp({ onSocial }){
    const onSuccess = async(response) => {
    	console.log(response);
    
        const { googleId, profileObj : { email, name } } = response;
        
        await onSocial({
            socialId : googleId,
            socialType : 'google',
            email,
            nickname : name
        });
    }

    const onFailure = (error) => {
        console.log(error);
    }

    return(
        <div>
            <GoogleLogin className="google_login_btn"
                clientId={clientId}
                responseType={"id_token"}
                onSuccess={onSuccess}
                onFailure={onFailure}/>
        </div>
    )
}