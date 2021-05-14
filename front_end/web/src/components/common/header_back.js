import React from "react";
import { Link } from "react-router-dom";
import "../../assets/css/Common/common.css"
import "../../assets/css/Common/header.css";
import BackBtn_icon from '../../assets/images/common/backbtn.png';
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"

function BackHeader(props) {
  return (
    <div className="header">
      <Link to={props.history}>
      <div className="back_header">
        <img src={BackBtn_icon}/>
      </div>
      </Link>
    </div>
  );
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(BackHeader);
