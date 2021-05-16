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


class sendCrewRequest extends React.Component {
    
    
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

export default sendCrewRequest;