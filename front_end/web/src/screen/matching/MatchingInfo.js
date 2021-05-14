import React from "react";
import axios from "axios";
import "../../assets/css/Matching/MatchingInfo.css";
import BACK_ICON from "../../assets/images/common/backBtn_lmk.png"

var MATCHING_DATA_COMPLETE=false;
var MATCHING_DATA;
async function getMatchingDetail(id){
    const data = await axios({
      method:'get',
      url: "http://54.180.98.138:8080"+"/matching/detail/"+id,
    }).then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    MATCHING_DATA = data;
    MATCHING_DATA_COMPLETE = true;
}


class MatchingInfo extends React.Component{
    constructor(props){
        super(props);
        const {location} = props;
        MATCHING_DATA = location.state.data;
        getMatchingDetail(MATCHING_DATA.id);
    }
    componentDidMount(){
        
    }

    render(){
        var repeat = setInterval(function(){
            if (MATCHING_DATA_COMPLETE){
                clearInterval(repeat);
            }
        },500)

        // 전역변수 MATCHING_DATA에 필요한 데이터가 전부 들어있습니다.
        return (
            <div className="matchingInfo">
              <div className="matchingInfo_block1">
                <div className="matchingInfo_block1_backBtn">
                  <img src={BACK_ICON}/>
                </div>
                <div className="matchingInfo_block1_image">
                  <img src="https://sports-phinf.pstatic.net/player/kbo/default/64805.png?type=h202"/>
                </div>
                <div className="matchingInfo_block1_info">
                  <div className="matchingInfo_block1_info_title">제목</div>
                  <div className="matchingInfo_block1_info_description">설명</div>
                  <div className="matchingInfo_block1_info_participation">7/10</div>
                </div>
                <div className="matchingInfo_block1_joinBtn">리그 참여하기</div>
              </div>
              <div className="matchingInfo_block2">
                <div className="matchingInfo_block2_title">매칭 참가자 살펴보기</div>
                <div className="matchingInfo_block2_participants"></div>
              </div>
            </div>
            )
    }
}


export default MatchingInfo;