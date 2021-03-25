import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Enroll/Enrollment.css";
import EnrollText from "../../components/enrollment/enroll_text";
import DoubleCheckBtn from "../../components/enrollment/doubleCheckBtn";
import EnrollInput from "../../components/enrollment/enrollInput";
import SubmitBtn from "../../components/enrollment/submitBtn";
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
                        <EnrollInput doubleChecked={true} info="id"/> 
                        <DoubleCheckBtn/>
                    </div>
                    <div className="enroll_pw">
                        <EnrollText text="pw"/>
                        <EnrollInput doubleChecked={false} info="pw"/> 
                    </div>
                    <div className="enroll_nickname">
                        <EnrollText text="nickname"/>
                        <EnrollInput doubleChecked={true} info="nickname"/> 
                        <DoubleCheckBtn/>
                    </div>
                    <div className="enroll_name">
                        <EnrollText text="name"/>
                        <EnrollInput doubleChecked={false} info="name"/> 
                    </div>
                    <div className="enroll_phoneNumber">
                        <EnrollText text="phoneNumber"/>
                        <EnrollInput doubleChecked={false} info="phoneNumber"/> 
                    </div>
                    <SubmitBtn/>
        </div>
                
      </section>
    );
  }
}

export default Enrollment;