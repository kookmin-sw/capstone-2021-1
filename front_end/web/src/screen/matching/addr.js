/*global kakao*/
import React from 'react';
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
var loc={};

class addr extends React.Component {
   constructor(props) {
        super(props);	
    }
    map;
    markers = []
    infowindows = []
    componentDidMount(){
        var container = document.getElementById('myMap'); //지도를 담을 영역의 DOM 레퍼런스
        var options = { //지도를 생성할 때 필요한 기본 옵션
            center: new kakao.maps.LatLng(37.55214, 126.92184), //지도의 중심좌표.
            level: 4 //지도의 레벨(확대, 축소 정도)
        };
	    this.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
      var marker = new kakao.maps.Marker({ 
        // 지도 중심좌표에 마커를 생성합니다 
        position: this.map.getCenter() 
    }); 
    // 지도에 마커를 표시합니다
    marker.setMap(this.map);
      kakao.maps.event.addListener(this.map, 'click', function(mouseEvent) {        
        var latlng = mouseEvent.latLng; 
        marker.setPosition(latlng);
        loc = {lat:latlng.getLat(),lng: latlng.getLng()}
        
    });
        }
    getLoc=()=>{
      const {setLoc} = this.props;
      setLoc(loc);
      this.props.history.push("/matching/make");
    }

    render() {
        return (
            <div className='popup'>
               <div id='myMap' style={{width:"400px", height:"800px"}}/>
               <div className="map_confirm_btn" onClick={this.getLoc}>여기요!</div>
            </div>
        )
    }
}
export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(addr);