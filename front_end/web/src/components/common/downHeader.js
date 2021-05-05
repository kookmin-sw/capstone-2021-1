import React from "react";
import {Link} from "react-router-dom"
import "../../assets/css/Common/common.css"
import "../../assets/css/Common/header.css";
import HOME_ICON from "../../assets/images/common/home_icon.png";
import MEDAL_ICON from "../../assets/images/common/medal_icon.png";
import SETTING_ICON from "../../assets/images/common/setting_icon.png";
import CREW_ICON from "../../assets/images/common/crew_icon.png";


function DownHeader() {
  return (
    <div className="down_header">
      <Link to="/home" className="common_link">
        <div className="down_header_content">
          <div className="down_header_content_img"><img src={HOME_ICON}/></div>
          <div className="down_header_content_text">홈</div>
        </div>  
      </Link>
      <Link to="/matching" className="common_link">
        <div className="down_header_content">
          <div className="down_header_content_img"><img src={MEDAL_ICON}/></div>
          <div className="down_header_content_text">매칭 및 대회</div>
        </div>  
      </Link>
      <Link to="/setting" className="common_link">
        <div className="down_header_content_right down_header_content">
          <div className="down_header_content_img"><img src={SETTING_ICON}/></div>
          <div className="down_header_content_text">환경설정</div>
        </div>  
      </Link>
      <Link to="/crew" className="common_link">
        <div className="down_header_content down_header_content_right">
          <div className="down_header_content_img"><img src={CREW_ICON}/></div>
          <div className="down_header_content_text">크루</div>
        </div>  
      </Link>
    </div>
  );
}

export default DownHeader;