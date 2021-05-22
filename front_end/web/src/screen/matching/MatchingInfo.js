import React from "react";
import axios from "axios";
import "../../assets/css/Matching/MatchingInfo.css";
import BACK_ICON from "../../assets/images/common/backBtn_lmk.png"

var MATCHING_DATA_COMPLETE=false;
var MATCHING_DATA;
var COUNT=0;
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
    COUNT = 1;
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
        
        getMatchingDetail(MATCHING_DATA.id);
    }

    
    componentDidMount(){
      this.interval = setInterval(this.dataCheck, 500)
    }
    dataCheck = () =>{
      if (COUNT == 1){      
        this.setState({dataCheck:true});
        clearInterval(this.interval);
      }
    }
    goBackBtn = () =>{
      this.props.history.go(-1);
  }
    

    render(){
        console.log(MATCHING_DATA);
        var MEMBERS = MATCHING_DATA.participants;
        if(MEMBERS == null){
          MEMBERS = [];
        }
        console.log(MEMBERS)
        var participantsCount = MATCHING_DATA.participantsCount+1;
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
                  <div className="matchingInfo_block1_info_participation">{participantsCount}/{MATCHING_DATA.maxCount}</div>
                </div>
                <div className="matchingInfo_block1_joinBtn">매칭 참여하기</div>
              </div>
              <div className="matchingInfo_block2">
                <div className="matchingInfo_block2_title">매칭 참가자 살펴보기</div>
                <div className="matchingInfo_block2_row">
                  <div className="matchingInfo_block2_row_nickname">닉네임</div>
                  <div className="matchingInfo_block2_row_info">기본정보</div>
                </div>
                {
                  MEMBERS.map((member) =>{
                    return(
                      <div className="matchingInfo_block2_list">
                        <div className="matchingInfo_block2_list_image" style={{backgroundImage: `url("${member.imageList[0]}")`}}></div>
                        <div className="matchingInfo_block2_list_nickname">{member.nickname}</div>
                        <div className="matchingInfo_block2_list_info">27세/남</div>
                      </div>
                    )
                  })
                }
                
                
                <div className="matchingInfo_block2_participants"></div>
              </div>
            </div>
            )
    }
}


export default MatchingInfo;