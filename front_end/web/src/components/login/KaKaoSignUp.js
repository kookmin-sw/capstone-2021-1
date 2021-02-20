import React, { Component } from 'react';
import styled from 'styled-components';
import KaKaoLogin from 'react-kakao-login';


class KakaoSignUp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: 'kakao'
        }
    }

    responseKaKao = (res) => {
        this.setState({
            data: res
        })
        alert(JSON.stringify(this.state.data))
    }

    responseFail = (err) => {
        alert(err);
    }

    render() {
        return (
            <>
                <div>
                    <br></br>
                    <KaKaoBtn
                        buttonText="KaKao"
                        onSuccess={this.responseKaKao}
                        onFailure={this.responseFail}
                        getProfile={true}
                    />
                </div>

            </>
        );
    }
}

const KaKaoBtn = styled(KaKaoLogin)`
    padding: 0;
    width: 190px;
    height: 44px;
    line-height: 44px;
    color: #783c00;
    background-color: #FFEB00;
    border: 1px solid transparent;
    border-radius: 3px;
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
    &:hover{
        box-shadow: 0 0px 15px 0 rgba(0, 0, 0, 0.2)
    }
`

export default KakaoSignUp;