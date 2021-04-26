/*global kakao*/
import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Matching/Matching.css"
import axios from "axios";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"

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
      <section className="container">
        <Header/>
        <div id='myMap' style={{width:"50%", height:"600px", float:"left", marginLeft:"25%"}}/>
        {
          is_Click ? 
          <div className="map_info">
            <div className="map_title">{CLICK_MARKER.Fb}</div>
            <div className="marker_info">{this.state.clicked_marker}</div>
            <div className="propose_matching_btn" onClick={this.handlePropose}>신청하기</div>
          </div> : null
        }
      </section>
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Matching);
