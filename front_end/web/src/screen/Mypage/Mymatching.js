import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import hamburger_icon from '../../assets/images/common/Hamburger_icon.png';
import notice_icon from '../../assets/images/common/noticeBell_icon.png';
import SLIDERDATA from "../../components/common/SliderData";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import axios from "axios";
import DownHeader from "../../components/common/downHeader";


const params = {
    pagination: {
      el: '.swiper-pagination.customized-swiper-pagination',
    }, // Add your class name for pagination container
    navigation: {
      nextEl: '.swiper-button-next.customized-swiper-button-next', // Add your class name for next button
      prevEl: '.swiper-button-prev.customized-swiper-button-prev' // Add your class name for prev button
    },
    containerClass: 'customized-swiper-container' // Replace swiper-container with customized-swiper-container
  }

var MY_CREW = [];
var MY_CREW_RECEIVE_REQUEST = [];
var MY_CREW_SEND_REQUEST = [];

var MY_MATCHING = [];
var MY_MATCHING_RECEIVE_REQUEST = [];
var MY_MATCHING_SEND_REQUEST = [];

async function getMyCrew(token){
    var data = await axios({
      method:'get',
      url: "http://54.180.98.138:8080"+"/member/crew",
      headers: {'X-AUTH-TOKEN': token}
    }).then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    MY_CREW = data;
    return data;
  }

class Mymatching extends React.Component {
    state = {}
    
    constructor(props){
        super(props);
        console.log(this.props);
        getMyCrew(this.props.store.state.request_header.accessToken);
    }
    
    render() {
    return (
      <div className="crew_main_container">
        <Header/>
        <div className="region_filter">
            <div className="hamburger_filter">
                <img src={hamburger_icon}/>
            </div>
            <div className="crew_notice">
                <img src={notice_icon}/>
            </div>
        </div>
            <div className="crew_info_container">
                <div className="matching_make_text">내 크루</div>
                <SLIDERDATA data={MY_CREW}></SLIDERDATA>
            </div>
            <div className="crew_info_container">
                <div className="matching_make_text">보낸 크루요청</div>
            </div>
            <div className="crew_info_container">
                <div className="matching_make_text">받은 크루요청</div>
            </div>
            <DownHeader/>
        </div>
    );
  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Mymatching);