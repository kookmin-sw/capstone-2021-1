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

var CLICK_MARKER;
var isClick=false;

async function getMatchingData(map){
  const data = await axios({
    method:'get',
    url: "http://54.180.98.138:8080"+"/matching/search",
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  data.content.forEach((el) => {
    // 마커를 생성합니다
    console.log(el);
    var marker = new kakao.maps.Marker({
      //마커가 표시 될 지도
      map: map,
      //마커가 표시 될 위치
      position: new kakao.maps.LatLng(el.latitude, el.longitude),
      //마커에 hover시 나타날 title
      title: el.title,
      id:el.id,
    });
    kakao.maps.event.addListener(marker, 'click', function() {
      CLICK_MARKER=marker;
      isClick=true;
    }.bind(map));
  });    
  return map;
}
class Matching extends React.Component {

componentDidUpdate(){

}

componentDidMount(){
  var container = document.getElementById('myMap'); //지도를 담을 영역의 DOM 레퍼런스
  var options = { //지도를 생성할 때 필요한 기본 옵션
      center: new kakao.maps.LatLng(37.55214, 126.92184), //지도의 중심좌표.
      level: 4 //지도의 레벨(확대, 축소 정도)
  };
  this.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
  this.map = getMatchingData(this.map)
}

  render() { 
    const is_Click = isClick;
    return (
      
        <div className="matching_container">
          <div className="search_box">
            <div className="search_input">
              <input placeholder="매칭 찾아보기"/>
              
            </div>
            <div className="search_filter">
              <div className="search_input_img">
                <img src={FILTER_ICON}/>
              </div>  </div>
          </div>
          <div id='myMap' style={{width:"400px", height:"800px"}}/>
          {
            is_Click ? 
            <div className="map_info">
              <div className="map_title">{CLICK_MARKER.Fb}</div>
              <div className="marker_info">{this.state.clicked_marker}</div>
              <div className="propose_matching_btn" onClick={this.handlePropose}>신청하기</div>
            </div> : null
          }
          <Link to="/matching/make"  className="common_link">
          <div className="matching_add_btn">
            +
          </div>
          </Link>
          <div className="refresh_btn">
            <img src={REFRESH_ICON}/>
          </div>
          <div className="location_confirm_btn">
            <img src={CURRENT_LOCATE_ICON}/>
          </div>
          <DownHeader/>
        </div>

      
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Matching);
