import React from "react";
import "../../assets/css/Manage/MemberInfo.css";
import BACK_ICON from "../../assets/images/common/backBtn_lmk.png"
import PLUS_ICON from "../../assets/images/common/plus_lmk.png"

class MemberInfo extends React.Component{
    
    goBackBtn = () =>{
        this.props.history.goBack();
    }
    render(){
        const MEMBER_DATA = this.props.location.state;
        console.log(MEMBER_DATA);
        // 전역변수 MEMBER_DATA에 필요한 데이터가 전부 들어있습니다.
        return (
            <div className="memberInfo">
                <div className="memberInfo_block1">
                    <div className="memberInfo_block1_BackBtn" onClick={this.goBackBtn}>
                        <img src={BACK_ICON}/>
                    </div>
                    <div className="memberInfo_block1_PlusBtn">
                        <img src={PLUS_ICON}/>
                    </div>
                    <div className="memberInfo_block1_AdminBtn">
                        크루관리
                    </div>
                </div>
                <div className="memberInfo_block2">
                    <div className="memberInfo_block2_info">
                        <div className="memberInfo_block2_info_image">
                            이미지
                        </div>
                        <div className="memberInfo_block2_info_name">
                            {MEMBER_DATA.name}
                        </div>
                        <div className="memberInfo_block2_info_ageAndGender">
                            <p>나이/성별</p>
                        </div>
                    </div>
                    <div className="memberInfo_block2_description">
                        {MEMBER_DATA.description}
                    </div>
                </div>
                <div className="memberInfo_block3">
                    <div className="memberInfo_block3_detail">
                        상세정보
                    </div>
                    <div className="memberInfo_block3_graph"></div>
                    <div className="memberInfo_block3_release">방출하기</div>
                    
                </div>
            </div>
            )
    }
}


export default MemberInfo;