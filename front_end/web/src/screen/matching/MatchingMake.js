/*global kakao*/
import React from "react";
import "../../assets/css/Matching/Matching.css"
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import BackHeader from "../../components/common/header_back";
import SEARCH_ICON from "../../assets/images/common/search_icon.png";
import DateInput from "../../components/common/DateInput";
import {Link} from "react-router-dom";



class MatchingMake extends React.Component {

    state = {start_date:new Date()}

    handleBtnClick = () =>{
        console.log(this.state)
        const {request_header, makeLocation} = this.props.store.state;
        const {postMatching} = this.props;

        postMatching({
            title : this.state.title,
            description: this.state.description,
            latitude: makeLocation.lat,
            longitude: makeLocation.lng,
            maxCount: 5,
            category: "BOARD_GAME",
            startTime: "2021-05-20T12:00:00"
        },request_header.accessToken)
        
        this.props.history.push("/matching");
    }

    changeDate = e => {
        this.setState({ 
            start_date: e 
        });
    };

    titleChange = (e) => {
        this.setState({
            title:e.target.value,
        })
    }
    descChange = (e) => {
        this.setState({
            description:e.target.value,
        })
    }
    maxCChange = (e) => {
        this.setState({
            maxCount:e.target.value,
        })
    }
    
  render() {

    return (
        <div className="matching_make_container">
            <BackHeader history="/matching"></BackHeader>
            <div className="make_main_container">
                <div className="game_room_picture"></div>
                <div className="matching_name">
                    <div className="matching_make_text">매칭 이름</div>
                    <input className="matching_make_input" onChange={this.titleChange} placeholder="제목을 입력하세요."></input>
                </div>
                <div className="matching_location">
                    <div className="matching_make_text">위치</div>
                    <Link to="/findaddr">
                        <div className="matching_make_input">
                            <text>주소를 검색하세요.</text>
                            
                            <img src={SEARCH_ICON}/>
                        </div>
                    </Link>
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
                        <input className="desc_input" onChange={this.descChange} placeholder="매칭과 관련된 내용을 입력하세요."/>
                    </div>
                </div>
                <div className="make_btn" onClick={this.handleBtnClick}>매칭 생성하기</div>
            </div>
        </div>  
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(MatchingMake);

const postCodeStyle = {
    display: "none",
    position: "absolute",
    top: "50%",
    width: "400px",
    height: "500px",
    padding: "7px",
  };