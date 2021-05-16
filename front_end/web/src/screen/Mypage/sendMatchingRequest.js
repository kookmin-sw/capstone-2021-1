import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import hamburger_icon from '../../assets/images/common/Hamburger_icon.png';
import notice_icon from '../../assets/images/common/noticeBell_icon.png';
import SLIDERDATA from "../../components/common/SliderData";
import { Link } from "react-router-dom";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import axios from "axios";
import DownHeader from "../../components/common/downHeader";
import { isThisSecond } from "date-fns";
import Mymatching from "./Mymatching";

const datas = 
{
    "0": [
        {
            "id": 23,
            "status": "PENDING_ACCEPTANCE",
            "createdAt": "2021-02-12T08:59:01",
            "member": {
                "id": 12,
                "uid": "dlwlsrn7",
                "nickname": "이진칠",
                "name": null,
                "address": null,
                "phoneNumber": "010-8784-3827",
                "description": null,
                "memberStats": null,
                "imagePath": null
            },
            "matching": {
                "id": 19,
                "title": "체스 고수방",
                "description": "고수방 홍대 히어로 보드게임 카페",
                "startTime": "2021-03-20T12:00:00",
                "endTime": null,
                "latitude": 37.55214,
                "longitude": 126.92184,
                "distance": null,
                "status": "SCHEDULED",
                "category": "BOARD GAME",
                "maxCount": 5,
                "participantsCount": null,
                "host": null,
                "participants": null
            }
        }
    ],
    "matching": [
        "체스 고수방"
    ]
}


class sendMatchingRequest extends React.Component {
    
    
    constructor(props){
        super(props);
        console.log(this.props)
    }

    
    render() {
    
    return (
      <div className="crew_main_container">
        <Header/>
        <div>
            
        </div>
        <DownHeader/>
        </div>
        
    );
  }
}

export default sendMatchingRequest;