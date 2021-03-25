import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Enroll/Enrollment.css";
import EnrollText from "../../components/enrollment/enroll_text";
import DoubleCheckBtn from "../../components/enrollment/doubleCheckBtn";
import EnrollInput from "../../components/enrollment/enrollInput";
class Enrollment extends React.Component {
    constructor(props) {
        super(props);
        this.state={data: {
            uid:'',password:'',name:'',nickname:'',phoneNumber:''}}
    }
    
    uidChange = (e) => {
        this.setState({data:{
            uid:e.target.value,
        }})
    }

    pwChange = (e) => {
        this.setState({data:{
            password:e.target.value,
        }})
    }
    nameChange = (e) => {
        this.setState({data:{
            name:e.target.value,
        }})
    }
    nicknameChange = (e) => {
        this.setState({data:{
            nickname:e.target.value,
        }})
    }
    phoneNumChange = (e) => {
        this.setState({data:{
            phoneNumber:e.target.value,
        }})
    }
        
    render() {
        
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div id="Enroll_Container" className="main_contents_container">
                    <div className="enroll_id">
                        <EnrollText text="id"/>
                        <EnrollInput doubleChecked={false} info="id"/> 
                        <DoubleCheckBtn/>
                    </div>
                    <div className="enroll_pw">
                        <EnrollText text="pw"/>
                        <input name="enroll_pw" className="input_non_double_check"  onChange={this.pwChange} placeholder="pw를 입력해주세요."></input>
                    </div>
                    <div className="enroll_nickname">
                        <EnrollText text="nickname"/>
                        <input name="enroll_nickname" className="input_double_check" onChange={this.nicknameChange} placeholder="nickname을 입력해주세요."></input>
                        <DoubleCheckBtn/>
                    </div>
                    <div className="enroll_name">
                        <EnrollText text="name"/>
                        <input name="enroll_name" className="input_non_double_check" onChange={this.nameChange} placeholder="이름을 입력해주세요."></input>
                    </div>
                    <div className="enroll_phoneNumber">
                        <EnrollText text="phoneNumber"/>
                        <input name="enroll_phoneNum" className="input_non_double_check" onChange={this.phoneNumChange} placeholder="휴대폰 번호를 입력해주세요."></input>
                    </div>
                    <div className="submit_btn">제출하기</div>
        </div>
                
      </section>
    );
  }
}

export default Enrollment;