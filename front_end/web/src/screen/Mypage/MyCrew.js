import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import hamburger_icon from '../../assets/images/common/Hamburger_icon.png';
import notice_icon from '../../assets/images/common/noticeBell_icon.png';
import { Link } from "react-router-dom";
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
var COUNT=0;

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
    COUNT = COUNT+1;
    return data;
  }
  
  async function receiveCrewRequest(token){
    var data = await axios({
        method:'get',
        url: "http://54.180.98.138:8080"+"/member/crew/participate/request",
        headers: {'X-AUTH-TOKEN': token}
      }).then(function(response){
        return response.data
      }).catch(function(error){
        alert(error.message);
      })
      MY_CREW_RECEIVE_REQUEST = data;
      COUNT = COUNT+1;
      return data;
}

async function sendCrewRequest(token){
    var data = await axios({
        method:'get',
        url: "http://54.180.98.138:8080"+"/member/crew/participate",
        headers: {'X-AUTH-TOKEN': token}
      }).then(function(response){
        return response.data
      }).catch(function(error){
        alert(error.message);
      })
      MY_CREW_SEND_REQUEST = data;
      COUNT = COUNT+1;
      return data;
}

class MyCrew extends React.Component {

    constructor(props){
        super(props);
        getMyCrew(this.props.store.state.request_header.accessToken);
        sendCrewRequest(this.props.store.state.request_header.accessToken);
        receiveCrewRequest(this.props.store.state.request_header.accessToken);
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
              <div className="navi_tab_div active">크루</div>
              <Link to="/MyMatching">
                <div className="navi_tab_div unactive">매칭</div>
              </Link>
        </div>
        <div className="pad_bot">
            <div className="crew_info_container">
                <div className="matching_make_text">내 크루</div>
                <SLIDERDATA data={MY_CREW} type="MY_CREW"></SLIDERDATA>
            </div>
            <div className="crew_info_container">
                <div className="matching_make_text">보낸 크루요청</div>
                <SLIDERDATA data={MY_CREW_SEND_REQUEST}></SLIDERDATA>
            </div>
            <div className="crew_info_container">
                <div className="matching_make_text">받은 크루요청</div>
                <Link to={{ pathname:'/mypage/sendCrewRequest', state:{MY_CREW_RECEIVE_REQUEST}}}>
                <div className="go_request_page">크루 요청 관리하러 가기</div>
                </Link>
            </div>
            </div>
            <DownHeader/>
        </div>
    );
  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(MyCrew);