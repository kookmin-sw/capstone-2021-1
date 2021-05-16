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

var COUNT = 0;

var MY_MATCHING = [];
var MY_MATCHING_RECEIVE_REQUEST = [];
var MY_MATCHING_SEND_REQUEST = [];

async function getMyMatching(token){
  var data = await axios({
    method:'get',
    url: "http://54.180.98.138:8080"+"/member/matching",
    headers: {'X-AUTH-TOKEN': token}
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  MY_MATCHING = data;
  COUNT = COUNT+1;
  console.log(MY_MATCHING)
  return data;
}
async function receiveMatchingRequest(token){
  var data = await axios({
      method:'get',
      url: "http://54.180.98.138:8080"+"/member/matching/participate/request",
      headers: {'X-AUTH-TOKEN': token}
    }).then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    MY_MATCHING_RECEIVE_REQUEST = data;
    COUNT = COUNT+1;
    return data;
}

async function sendMatchingRequest(token){
  var data = await axios({
      method:'get',
      url: "http://54.180.98.138:8080"+"/member/matching/participate",
      headers: {'X-AUTH-TOKEN': token}
    }).then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    MY_MATCHING_SEND_REQUEST = data;
    COUNT = COUNT+1;
    return data;
}


class Mymatching extends React.Component {
    
    
    constructor(props){
        super(props);
        
        getMyMatching(this.props.store.state.request_header.accessToken);
        receiveMatchingRequest(this.props.store.state.request_header.accessToken);
        sendMatchingRequest(this.props.store.state.request_header.accessToken);
    }

    componentDidMount(){
      this.interval = setInterval(this.dataCheck, 500)
    }
    
    dataCheck = () =>{
      if (COUNT == 3){
        this.setState({dataCheck:true});
        clearInterval(this.interval);
      }
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
        <div className="navi_tab">
            <Link to="/MyCrew">
              <div className="navi_tab_div unactive">크루</div>
              </Link>
              <div className="navi_tab_div active">매칭</div>
        </div>
        <div className="pad_bot">
            <div className="crew_info_container">
                <div className="matching_make_text">내 매칭</div>
                <SLIDERDATA data={MY_MATCHING} type="MY_MATCHING"></SLIDERDATA>
            </div>
            <div className="crew_info_container">
                <div className="matching_make_text">보낸 매칭요청</div>
                <SLIDERDATA data={MY_MATCHING_SEND_REQUEST} ></SLIDERDATA>
            </div>
            <div className="crew_info_container last_content">
                <div className="matching_make_text">받은 매칭요청</div>
                <Link to={{ pathname:'/mypage/sendMatchingRequest', state:{MY_MATCHING_RECEIVE_REQUEST}}}>
                  <div className="go_request_page">받은 매칭 요청 보러가기</div>
                </Link>
            </div>
            </div>
            <DownHeader/>
        </div>
        
    );
  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Mymatching);