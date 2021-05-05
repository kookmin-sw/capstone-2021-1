import React from "react";
import "../../assets/css/Common/common.css"
import "../../assets/css/Common/header.css";
import BackBtn_icon from '../../assets/images/common/backbtn.png';

function BackHeader() {
  return (
    <div className="header">
      <div className="back_header">
        <img src={BackBtn_icon}/>
      </div>
    </div>
  );
}

export default BackHeader;