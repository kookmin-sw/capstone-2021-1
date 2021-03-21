import React from "react";
import "../../assets/css/Common/header.css";
import Hamburger_icon from '../../assets/images/common/Hamburger_icon.png';

function Header() {
  return (
    <div className="header">
      <div className="header_header">
        <img src={Hamburger_icon} alt="" className="hamburger_bar"/>
        <div className="no_right_border to_enroll">회원 가입</div>
        <div className="to_login">로그인</div>
        <div className="to_home">홈</div>
      </div>
      <div className="header_logo">
      <h1 className="logo_text">Play Maker</h1>
      </div>
    </div>
  );
}

export default Header;