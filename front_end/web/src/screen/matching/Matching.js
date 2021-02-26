/*global kakao*/
import React from "react";
import { markerdata } from "../../components/map/markerData"
import Header from "../../components/common/header";
import MarkerInfo from "../../components/map/MarkerInfo";

class Matching extends React.Component {
  
map;
markers = markerdata;

componentDidMount(){
    var container = document.getElementById('myMap'); //지도를 담을 영역의 DOM 레퍼런스
    var options = { //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(37.62197524055062, 127.16017523675508), //지도의 중심좌표.
        level: 4 //지도의 레벨(확대, 축소 정도)
    };
  this.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
  markerdata.forEach((el) => {
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      //마커가 표시 될 지도
      map: this.map,
      //마커가 표시 될 위치
      position: new kakao.maps.LatLng(el.lat, el.lng),
      //마커에 hover시 나타날 title
      title: el.title,
    });

    kakao.maps.event.addListener(marker, 'click', function() {
      this.setState({clicked_marker: marker})
      console.log(marker.Fb)
    });
  });    
}

  render() {
    return (
      <section className="container">
        <Header/>
        <div id='myMap' style={{width:"50%", height:"600px", float:"left", marginLeft:"25%"}}/>
        <MarkerInfo marker={this.state.clicked_marker}/>
      </section>
    );
  }
}

export default Matching;