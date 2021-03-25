import React from 'react';
import "../../assets/css/Enroll/Enrollment.css";

function EnrollInput({info , doubleChecked}) {
    var Cn = "";
    if (doubleChecked) {Cn="input_double_check"} else Cn="input_non_double_check";
    var Name = "enroll_"+info;
    var ph = info+"를 입력해주세요.";
  
  return (
    <input name={Name} className={Cn} placeholder={ph}></input>
  )
}

export default EnrollInput;