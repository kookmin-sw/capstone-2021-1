import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Enroll/Enrollment.css";
import EnrollText from "../../components/enrollment/enroll_text";
import DoubleCheckBtn from "../../components/enrollment/doubleCheckBtn";
import CommonInput from "../../components/enrollment/enrollInput";
import SubmitBtn from "../../components/enrollment/submitBtn";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import { Redirect } from "react-router";
class Enrollment extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            uid:'',password:'',name:'',nickname:'',phoneNumber:'',provider:'default',address:'seoul',complete:false}
    }

    handleDoubleckecked = () => {
        const { doubleChecked_ID } = this.props;
        doubleChecked_ID(this.state.uid);
    }

    handleSubmit = () =>  {
        const { registerUser } = this.props;
        registerUser(this.state);
        this.props.history.push("/");
    }
    
    uidChange = (e) => {
        this.setState({
            uid:e.target.value,
        })
    }

    pwChange = (e) => {
        this.setState({
            password:e.target.value,
        })
    }
    nameChange = (e) => {
        this.setState({
            name:e.target.value,
        })
    }
    nicknameChange = (e) => {
        this.setState({
            nickname:e.target.value,
        })
    }
    phoneNumChange = (e) => {
        this.setState({
            phoneNumber:e.target.value,
        })
    }
        
    render() {
        
    return (
        
      <section className="container">
        <div id="Enroll_Container">
                    <div className="enroll_id">
                        <EnrollText text="id"/>
                        <input className="doubleckecked_input" onChange={this.uidChange} placeholder="id를 입력해주세요."></input>
                        <div className="id_double_check" onClick={this.handleDoubleckecked}>중복확인</div>
                    </div>
                    <div className="enroll_pw">
                        <EnrollText text="pw"/>
                        <input className="nondoubleckecked_input" onChange={this.pwChange} placeholder="pw를 입력해주세요."></input>
                    </div>
                    <div className="enroll_nickname">
                        <EnrollText text="nickname"/>
                        <input className="nondoubleckecked_input" onChange={this.nicknameChange} placeholder="닉네임을 입력해주세요."></input>
                    </div>
                    <div className="enroll_name">
                        <EnrollText text="name"/>
                        <input className="nondoubleckecked_input" onChange={this.nameChange} placeholder="이름을 입력해주세요."></input>
                    </div>
                    <div className="enroll_phoneNumber">
                        <EnrollText text="phoneNumber"/>
                        <input className="nondoubleckecked_input" onChange={this.phoneNumChange} placeholder="휴대폰 번호를 입력해주세요."></input>
                    </div>
                    <div className="submit_btn" onClick={this.handleSubmit}>제출하기</div>
        </div>
                
      </section>
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Enrollment);