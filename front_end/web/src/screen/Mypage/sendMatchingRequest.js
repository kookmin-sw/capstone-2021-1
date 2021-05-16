import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import "../../assets/css/Matching/Matching.css";
import "../../assets/css/Common/common.css";
import DownHeader from "../../components/common/downHeader";
import NONEDATA from "../../assets/images/common/noneData.png";
import BackHeader from "../../components/common/header_back";

const memberDatas=[
    {
        "id": 1,
        "uid": "dlwlsrn94@naver.com",
        "nickname": "LJG070",
        "name": "이진구",
        "address": null,
        "phoneNumber": "010-8784-3827",
        "description": "소개글",
        "memberStats": {
            "manner": 0.0,
            "affinity": 0.0,
            "physical": 0.0,
            "intellect": 0.0,
            "comprehension": 0.0
        },
        "imagePath": "https://play-maker.s3.ap-northeast-2.amazonaws.com/1bd02b15-b228-4626-9d67-cefb69cbfd89.jpg"
    },
    {
        "id": 1,
        "uid": "dlwlsrn94@naver.com",
        "nickname": "LJG070",
        "name": "이진구",
        "address": null,
        "phoneNumber": "010-8784-3827",
        "description": "소개글",
        "memberStats": {
            "manner": 0.0,
            "affinity": 0.0,
            "physical": 0.0,
            "intellect": 0.0,
            "comprehension": 0.0
        },
        "imagePath": "https://play-maker.s3.ap-northeast-2.amazonaws.com/1bd02b15-b228-4626-9d67-cefb69cbfd89.jpg"
    }
    
]

class sendMatchingRequest extends React.Component {
    
    
    constructor(props){
        super(props);
    }

    
    render() {
    const datas = this.props.location.state.MY_MATCHING_RECEIVE_REQUEST;
    if (datas.length==0){
        return (
            <div className="crew_main_container">
              <BackHeader history="/MyCrew"/>
              <div className="request_box">
                  받은 크루 요청
              </div>
              <div className="none_data">
                <img src={NONEDATA}></img>
                <div>
                    No results
                </div>
                </div>
              <DownHeader/>
              </div>
              
          );
    }else{
        console.log(datas)
        return (
            <div className="crew_main_container">
              <BackHeader history="/MyCrew"/>
              <div className="request_box">
                  받은 매칭 요청
              </div>
              {
                  datas.map((data)=>{
                      return(
                      <div className="dataOftitle">
                          {data.matching.title}
                          <div className="dataOfNumber">
                              총 {memberDatas.length}명
                          </div>
                          {
                              memberDatas.map((member)=>{
                                  return(
                                        <div className="request_member">
                                            <div className="member_profile"  style={{backgroundImage: `url("${member.imagePath}")`}}/>
                                            <div className="member_nickname">{member.nickname}</div>
                                            <div className="member_info">여/20살</div>
                                            <div className="response_btn">
                                                <div className="refuse_btn">거절</div>
                                                <div className="confirm_btn">수락</div>
                                            </div>
                                        </div>
                                  )
                              })
                          }
                      </div>
                      );
                  })
              }
              <DownHeader/>
              </div>
        )
}
   
        

  }
}

export default sendMatchingRequest;