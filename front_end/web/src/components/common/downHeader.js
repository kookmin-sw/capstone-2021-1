import React from "react";
import {Link} from "react-router-dom"
import "../../assets/css/Common/common.css"
import "../../assets/css/Common/header.css";


function DownHeader() {
  return (
    <div className="down_header">
      <Link to="/home" className="common_link">
        <div className="down_header_content">
          <div className="down_header_content_img"></div>
          <div className="down_header_content_text">홈</div>
        </div>  
      </Link>
      <Link to="/matching" className="common_link">
        <div className="down_header_content">
          <div className="down_header_content_img"></div>
          <div className="down_header_content_text">매칭 및 대회</div>
        </div>  
      </Link>
      
      <Link to="/setting" className="common_link">
        <div className="down_header_content_right down_header_content">
          <div className="down_header_content_img"></div>
          <div className="down_header_content_text">환경설정</div>
        </div>  
      </Link>
      <Link to="/crew" className="common_link">
        <div className="down_header_content down_header_content_right">
          <div className="down_header_content_img"></div>
          <div className="down_header_content_text">크루</div>
        </div>  
      </Link>
    </div>
  );
}

export default DownHeader;