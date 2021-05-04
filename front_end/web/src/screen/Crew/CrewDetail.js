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
                        <div className="crewDetail_crewInfo_info_title">크루이름</div>
                        <div className="crewDetail_crewInfo_info_info">크루세부정보</div>
                        <div className="crewDetail_crewInfo_info_intro">크루소개</div>
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