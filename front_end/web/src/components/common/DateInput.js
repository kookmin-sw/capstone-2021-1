/*global kakao*/
import React from "react";
import "../../assets/css/Matching/Matching.css"
import DatePicker from "react-datepicker";
import ko from "date-fns/locale/ko";
import "react-datepicker/dist/react-datepicker.css"
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"


class DateInput extends React.Component {
  render() { 
      console.log(this.props)
    return(
        <form className="inputDeliveryDate">
            <DatePicker id="datePicker"
                placeholderText="게임 시작일을 선택해주세요" 
                locale={ko}
                name="day1"
                selected={this.props.startDate}
                closeOnScroll={true}
                minDate={new Date()}
                popperModifiers={{preventOverflow:{enabled:true}}}
                onChange={(date, event)=>{
                    this.props.changeDate(date, event);
                }}
                value={this.props.value}
                dateFormat = "yyyy.MM.dd(eee)"/>
                </form>
    )
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(DateInput);
