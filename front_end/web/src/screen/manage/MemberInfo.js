import React from "react";
import "../../assets/css/Manage/MemberInfo.css";
import BACK_ICON from "../../assets/images/common/backBtn_lmk.png"
import PLUS_ICON from "../../assets/images/common/plus_lmk.png"
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import Stat from "../../components/Mypage/stat"



class MemberInfo extends React.Component{
    
    outOnClick = () =>{
        console.log(this.props.location)
        const token = this.props.store.state.request_header.accessToken;
        const {deportCrewMember} = this.props;
        deportCrewMember(token,this.props.location.state.member.id,this.props.location.state.CREW_ID);
        this.goBackBtn();
    }

    goBackBtn = () =>{
        this.props.history.go(-1);
    }
    render(){
        const MEMBER_DATA = this.props.location.state.member;
        console.log(MEMBER_DATA);
        console.log(this.props)
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
                        <div className="memberInfo_block2_info_image" style={{backgroundImage: `url("${MEMBER_DATA.imageList[0]}")`}}>
                            
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
                    <div className="memberInfo_block2_stat">
                        
                    </div>
                </div>
                <div className="memberInfo_block3">
                    <div className="memberInfo_block3_detail">
                        {MEMBER_DATA.description}
                    </div>
                    <div className="memberInfo_block3_graph">
                        <Stat data={MEMBER_DATA.memberStats}/>
                    </div>
                    <div className="memberInfo_block3_release" onClick={this.outOnClick}>방출하기</div>
                    
                </div>
            </div>
            )
    }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(MemberInfo);