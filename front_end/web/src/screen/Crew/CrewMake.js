import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/CrewMake.css"
import "../../assets/css/Crew/Crew.css";
import search_icon from '../../assets/images/common/search.jpg';
import reset_icon from '../../assets/images/common/reset.png';
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import EnrollText from "../../components/enrollment/enroll_text";
import "../../assets/css/Enroll/Enrollment.css";


class CrewMake extends React.Component {
    handleSubmit = () =>  {
        const { registerCrew } = this.props;
        const { request_header } = this.props.store.state;
        registerCrew(this.state, request_header.accessToken);
        this.props.history.push("/crew");
    }
    state = {
        "name":"크루1",
        "description":"잉여 인간 모임",
        "maxCount":10,
        "activityArea":"서울",
        "category":"BOARD GAME"
    }
    
    constructor(props){
        super(props);
    }
    
    handleDoubleckecked = () => {
        const { doubleChecked_ID } = this.props;
        doubleChecked_ID(this.state.name);
    }
    
    uidChange = (e) => {
        this.setState({
            name:e.target.value,
        })
    }

    pwChange = (e) => {
        this.setState({
            description:e.target.value,
        })
    }

    nameChange = (e) => {
        this.setState({
            activityArea:e.target.value,
        })
    }

    nicknameChange = (e) => {
        this.setState({
            maxCount:e.target.value,
        })
    }
    
    phoneNumChange = (e) => {
        this.setState({
            category:e.target.value,
        })
    }


    render() {
        
    return (
      <section className="container">
        <Header/>
        
        <div>
        <div className="enroll_id">
                        <EnrollText text="크루 이름"/>
                        <input className="doubleckecked_input" onChange={this.uidChange} placeholder="크루명을 입력해주세요."></input>
                        <div className="id_double_check" onClick={this.handleDoubleckecked}>중복확인</div>
                    </div>
                    <div className="enroll_pw">
                        <EnrollText text="크루 설명"/>
                        <input className="nondoubleckecked_input" onChange={this.pwChange} placeholder="크루 설명을 입력해주세요."></input>
                    </div>
                    <div className="enroll_nickname">
                        <EnrollText text="최대 인원"/>
                        <input className="nondoubleckecked_input" onChange={this.nicknameChange} placeholder="최대인원을 입력해주세요."></input>
                    </div>
                    <div className="enroll_name">
                        <EnrollText text="지역"/>
                        <input className="nondoubleckecked_input" onChange={this.nameChange} placeholder="지역을 입력해주세요."></input>
                    </div>
                    <div className="enroll_phoneNumber">
                        <EnrollText text="카테고리"/>
                        <input className="nondoubleckecked_input" onChange={this.phoneNumChange} placeholder="카테고리를 입력해주세요."></input>
                    </div>
                    <div className="submit_btn" onClick={this.handleSubmit}>제출하기</div>
        </div>

      </section>
    );
  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(CrewMake);