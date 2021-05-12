/*global kakao*/
import React from "react";
import "../../assets/css/Matching/Matching.css"
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import BackHeader from "../../components/common/header_back";
import SEARCH_ICON from "../../assets/images/common/search_icon.png";
import { Link } from "react-router-dom";
import DateInput from "../../components/common/DateInput";

class MatchingMake extends React.Component {

    state = {start_date:new Date()}
    
    handleBtnClick = e =>{
        console.log(this.props)
        const {request_header} = this.props.store.state;
        const {postMatching} = this.props;
        postMatching({
            request_header : request_header,
            title : this.state.title,
            description: this.state.description,
            latitude: this.state.latitude,
            longitude: this.state.longitude,
            maxCount: this.state.maxCount,
            category: this.state.category,
            startTime: this.state.start_date
        })
    }
    changeDate = e => {
        this.setState({ start_date: e });
      };
  render() {
      const {postMatching} = this.props;

    return (
        <div className="matching_make_container">
            <BackHeader history="/matching"></BackHeader>
            <div className="make_main_container">
                <div className="game_room_picture"></div>
                <div className="matching_name">
                    <div className="matching_make_text">매칭 이름</div>
                    <input className="matching_make_input" placeholder="제목을 입력하세요."></input>
                </div>
                <div className="matching_location">
                    <div className="matching_make_text">위치</div>
                    <div className="matching_make_input">
                        <text>주소를 검색하세요.</text>
                        <img src={SEARCH_ICON}/>
                    </div>
                </div>
                <div className="matching_time">
                    <div className="matching_make_text">시간
                        <DateInput changeDate={this.changeDate} value={this.state.start_date}/>
                    </div>
                    
                </div>
                <div className="matching_numOfPeople">
                    <div className="matching_make_text">인원</div>
                    
                </div>
                <div className="matching_category">
                    <div className="matching_make_text">카테고리</div>
                    <div className="checkbox_container">
                    <div className="category_checkbox">
                        <input type="checkbox"></input><label>방탈출</label>
                    </div>
                    <div className="category_checkbox">
                    <input type="checkbox"></input><label>보드게임</label>
                    </div>
                    <div className="category_checkbox">
                    <input type="checkbox"></input><label>추리게임</label>
                    </div>
                    </div>
                </div>
                <div className="matching_desc">
                    <div className="matching_make_text">매칭내용</div>
                    <div className="matching_desc_input">
                        <input className="desc_input" placeholder="매칭과 관련된 내용을 입력하세요."/>
                    </div>
                </div>
                <div className="make_btn" onClick={this.handleBtnClick}>매칭 생성하기</div>
            </div>
        </div>  
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(MatchingMake);
