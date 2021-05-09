import React from "react";
import "../../assets/css/Crew/CrewDetail.css";
import BACK_ICON from "../../assets/images/common/backBtn_lmk.png"
import PLUS_ICON from "../../assets/images/common/plus_lmk.png"

class CrewDetail extends React.Component{

    

    componentDidMount(){
       
    }

    render(){
        const {location} = this.props;
        console.log(location);
        return (
            <div className="crewDetail">
                <div className="crewDetail_crewInfo">
                    <div className="crewDetail_crewInfo_image">
                        <div className="crewDetail_crewInfo_image_backBtn">
                            <img src={BACK_ICON}/>
                        </div>
                        <p>이미지</p>
                    </div>
                    <div className="crewDetail_crewInfo_info">
                        <div className="crewDetail_crewInfo_info_block1">
                            <div className="crewDetail_crewInfo_info_title">{location.state.data.name}</div>
                            <div className="crewDetail_crewInfo_info_plusBtn">
                                <img src={PLUS_ICON}/>
                            </div>
                            <div className="crewDetail_crewInfo_info_adminBtn">크루관리</div>
                        </div>
                        <div className="crewDetail_crewInfo_info_block2">
                            <div className="crewDetail_crewInfo_info_activityArea">지역: {location.state.data.activityArea} 총모집인원: {location.state.data.participantsCount}</div>
                            <div className="crewDetail_crewInfo_info_category">카테고리: {location.state.data.category}</div>
                        </div>
                        <div className="crewDetail_crewInfo_info_description">{location.state.data.description}</div>
                        <div className="crewDetail_crewInfo_info_joinBtn">가입하기</div>
                    </div>
                </div>
                <div className="crewDetail_crewMember">
                    <div className="crewDetail_crewMember_title">모든 크루원</div>
                    <div className="crewDetail_crewMember_list">
                        <div className="crewDetail_crewMember_list_image">
                            <p>얼굴</p>
                        </div>
                        <div className="crewDetail_crewMember_list_name">
                            <p>크루원 이름</p>
                        </div>
                        <div className="crewDetail_crewMember_list_intro">
                            <p>크루원 소개</p>
                        </div>
                        <hr size="1" color="#bcbcbc"></hr>
                    </div>
                </div>
            </div>
            )
    }
}

export default CrewDetail;