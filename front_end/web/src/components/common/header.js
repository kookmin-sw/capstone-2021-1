import React from "react";
import "./header.css";
import Hamburger_icon from '../../images/common/Hamburger_icon.png';

function Header() {
  return (
    <div className="header">
      <div className="header_header">
          홈,로그인,회원가입
          <img src={Hamburger_icon} alt="" className="hamburger_bar"/>
      </div>
      <div className="header_logo">
      <h1 className="logo_text">Play Maker</h1>
      </div>
    </div>
  );
}

export default Header;