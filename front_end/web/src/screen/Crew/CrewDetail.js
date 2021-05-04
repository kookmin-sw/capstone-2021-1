import React from "react";
import "../../assets/css/Crew/CrewDetail.css";

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
                        <div className="crewDetail_crewInfo_image_backBtn">뒤로가기버튼</div>
                        <div className="crewDetail_crewInfo_image_image">이미지</div>
                    </div>
                    <div className="crewDetail_crewInfo_info">
                        <div className="crewDetail_crewInfo_info_block1">
                            <div className="crewDetail_crewInfo_info_title">{location.state.data.name}</div>
                            <div className="crewDetail_crewInfo_info_plusBtn">더하기</div>
                            <div className="crewDetail_crewInfo_info_adminBtn">크루관리</div>
                        </div>
                        <div className="crewDetail_crewInfo_info_block2">
                            <div className="crewDetail_crewInfo_info_activityArea">지역: {location.state.data.activityArea}</div>
                            <div className="crewDetail_crewInfo_info_participantsCount">총모집인원: {location.state.data.participantsCount}</div>
                            <div className="crewDetail_crewInfo_info_category">카테고리: {location.state.data.category}</div>
                        </div>
                        <div className="crewDetail_crewInfo_info_description">{location.state.data.description}</div>
                        <div className="crewDetail_crewInfo_info_joinBtn">가입하기</div>
                    </div>
                </div>
                <div className="crewDetail_crewMember">
                    <div className="crewDetail_crewMember_title">모든 크루원</div>
                    <div className="crewDetail_crewMember_list">크루원 리스트</div>
                </div>
            </div>
            )
    }
}

export default CrewDetail;