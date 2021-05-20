/*global kakao*/
import React from "react";
import DownHeader from "../../components/common/downHeader";
import "../../assets/css/Matching/Matching.css"
import REFRESH_ICON from "../../assets/images/matching/refresh_icon.png";
import CURRENT_LOCATE_ICON from "../../assets/images/matching/current_locate_icon.png";
import FILTER_ICON from "../../assets/images/matching/filter_icon.png";
import axios from "axios";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import { Link } from "react-router-dom";
import DownHeaderPosition from "../../components/common/downHeaderPosition";

var COUNT = 0;
var marker_info_css="marker_info";
var CLICK_MARKER;
var isClick = false;
var MARKER_DETAIL_LOC="";
async function getMatchingData(){
  const data = await axios({
    method:'get',
    url: "http://54.180.98.138:8080"+"/matching/search",
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  return data;
}

async function searchData(markertitle){
  const data = await axios({
    method:'get',
    url: "http://54.180.98.138:8080"+"/matching/search",
    data:{
      title: markertitle
    }
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  return data;
}


var geocoder = new kakao.maps.services.Geocoder();
function searchDetailAddrFromCoords(coords, callback) {
  geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

class Matching extends React.Component {
constructor(props){
  super(props);
  this.SlideUP= this.SlideUP.bind(this)
  this.RequestBtnClick = this.RequestBtnClick.bind(this)
  this.onSearchBtn = this.onSearchBtn.bind(this)
}
state={isClick:false, curlat:37.55214, curlng:126.92184}

componentDidMount(){
  
}

RequestBtnClick(){
  console.log(this.props)
  const {request_header} = this.props.store.state;
  const {userRequestMatching} = this.props;
  userRequestMatching(CLICK_MARKER.id,request_header.accessToken);
}

SlideUP(){
  if (marker_info_css=="marker_info"){
    marker_info_css="marker_info_click";
  }else{
    marker_info_css = "marker_info";
  }
  this.setState({marker_info_css})
}


onSearchBtn=()=>{
  const {title} = this.state;
  var search_data = searchData(title).then(res => res.content[0]).then((data)=>{
    this.setState({curlat:data.latitude, curlng:data.longitude});
  })
  this.setState({search_data});
}

onCLickRefresh=()=>{
  this.setState({dsa : 1})
  console.log("dsadsa")
}
titleChange = (e) => {
  this.setState({
      title:e.target.value,
  })
}
  render() { 
    const {curlat,curlng} = this.state;
    getMatchingData().then(function(response){
      var container = document.getElementById('myMap'); //지도를 담을 영역의 DOM 레퍼런스
      var options = { //지도를 생성할 때 필요한 기본 옵션
          center: new kakao.maps.LatLng(curlat,curlng), //지도의 중심좌표.
          level: 4 //지도의 레벨(확대, 축소 정도)
      };
      var map = new kakao.maps.Map(container, options);
      response.content.forEach((el) => {
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(el.latitude, el.longitude),
          title: el
        });
        kakao.maps.event.addListener(marker, 'click', function() {
          CLICK_MARKER=el;
          isClick = !isClick;
          var Marker_latlng = new kakao.maps.LatLng(el.latitude, el.longitude)
          searchDetailAddrFromCoords(Marker_latlng, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
              MARKER_DETAIL_LOC = !!result[0].road_address ? result[0].road_address.address_name : '';
              MARKER_DETAIL_LOC += result[0].address.address_name;
            }
          })
        }.bind(map));
        marker.setMap(map);
      });    
    })

    

    setInterval(() => {
      if(this.state.isClick!=isClick){
        this.setState({isClick : !this.state.isClick})
      }
    }, 500);

    return (
      <div className="matching_container">
        <div className="search_box">
          <div className="search_input">
            <input placeholder="매칭 찾아보기" onChange={this.titleChange}/>
          </div>
          <div className="search_filter" onClick={this.onSearchBtn}>
            <div className="search_input_img">
              <img src={FILTER_ICON}/>
            </div>  
          </div>
        </div>
        <div id='myMap' style={{width:"400px", height:"800px"}}/>
        
        
            {!isClick ? <div>
              <Link to="/matching/make"  className="common_link">
            <div className="matching_add_btn">
                +
              </div>
              </Link>
              <div className="refresh_btn" onClick={this.onCLickRefresh}>
                <img src={REFRESH_ICON}/>
              </div>
              <div className="location_confirm_btn">
                <img src={CURRENT_LOCATE_ICON}/>
              </div>
              <DownHeaderPosition/>
              </div>
            : <div className={marker_info_css} onClick={this.SlideUP}>
              <div className="marker_info_header">
                <div className="marker_title">
                  {CLICK_MARKER.title}
                  <div className="marker_category">{CLICK_MARKER.category}</div>
                </div>
                <div className="marker_distance">200m</div>
              </div>
              <div className="marker_content">
                <div className="marker_loc">{MARKER_DETAIL_LOC}</div>
                <div className="marker_time">시간: {CLICK_MARKER.startTime + ' ~ ' + CLICK_MARKER.endTime}</div>
                <div className="marker_maxPeople">모집인원: {CLICK_MARKER.maxCount}명</div>
                <div className="marker_description">매칭설명<div className="marker_description_detail">{CLICK_MARKER.description}</div></div>
                <div className="marker_picture"></div>
                <div className="marker_request_btn" onClick={this.RequestBtnClick} >매칭 요청하기</div>
              </div>
              
            </div> }
        
      </div>
      )
    
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Matching);
