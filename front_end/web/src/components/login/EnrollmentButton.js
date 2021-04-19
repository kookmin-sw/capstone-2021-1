import React from 'react';
import { Link } from 'react-router-dom';
import "../../assets/css/Enroll/Enrollment.css";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"

class EnrollmentButton extends React.Component {

    handleRegister = () => {
        const { registerUser, register_data } = this.props;
        registerUser(register_data);
    }
    
    render() {
        return (
                <Link to="/enroll" className="common_link">
                   <div className="EnrollBtn" onClick={()=>this.handleLogin()}>회원가입</div>
                </Link>
        )
    }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(EnrollmentButton);