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
        console.log(this.props)
        if (location.state!=undefined){
          MATCHING_DATA = location.state.data;
        }
        MATCHING_DATA = {id : 19, imageList:["https://play-maker.s3.ap-northeast-2.amazonaws.com/1bd02b15-b228-4626-9d67-cefb69cbfd89.jpg"]}
        getMatchingDetail(MATCHING_DATA.id);
    }

    goBackBtn = () =>{
      this.props.history.goBack();
  }
    componentDidMount(){
        
    }

    render(){
      console.log(MATCHING_DATA);
        var repeat = setInterval(function(){
            if (MATCHING_DATA_COMPLETE){
                clearInterval(repeat);
            }
        },500)

        // 전역변수 MATCHING_DATA에 필요한 데이터가 전부 들어있습니다.
        return (
            <div className="matchingInfo">
              <div className="matchingInfo_block1">
                <div className="matchingInfo_block1_backBtn" onClick={this.goBackBtn}>
                  <img src={BACK_ICON}/>
                </div>
                <div className="matchingInfo_block1_image" style={{backgroundImage: `url("${MATCHING_DATA.imageList[0]}")`}}>
                </div>
                <div className="matchingInfo_block1_info">
                  <div className="matchingInfo_block1_info_title">{MATCHING_DATA.title}</div>
                  <div className="matchingInfo_block1_info_description">{MATCHING_DATA.description}</div>
                  <div className="matchingInfo_block1_info_participation">{MATCHING_DATA.participantsCount}/{MATCHING_DATA.maxCount}</div>
                </div>
                <div className="matchingInfo_block1_joinBtn">리그 참여하기</div>
              </div>
              <div className="matchingInfo_block2">
                <div className="matchingInfo_block2_title">매칭 참가자 살펴보기</div>
                <div className="matchingInfo_block2_row">
                  <div className="matchingInfo_block2_row_nickname">닉네임</div>
                  <div className="matchingInfo_block2_row_info">기본정보</div>
                </div>
                
                <div className="matchingInfo_block2_list">
                  <div className="matchingInfo_block2_list_image">이미지</div>
                  <div className="matchingInfo_block2_list_nickname">이름</div>
                  <div className="matchingInfo_block2_list_info">27세/남</div>
                </div>
                
                <div className="matchingInfo_block2_participants"></div>
              </div>
            </div>
            )
    }
}


export default MatchingInfo;