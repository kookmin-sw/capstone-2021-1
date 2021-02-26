import React from 'react';
import "../../assets/css/Enroll/Enrollment.css";

class EnrollmentButton extends React.Component {
    
    render() {
        return (
               <div className="EnrollBtn" onClick="location.href ='http://localhost:3000/enroll">회원가입</div>
        )
    }
}

export default EnrollmentButton;