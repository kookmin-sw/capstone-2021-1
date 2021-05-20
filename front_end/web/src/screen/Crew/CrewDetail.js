import React from "react";
import "../../assets/css/Crew/CrewDetail.css";
import { Link } from "react-router-dom";
import BACK_ICON from "../../assets/images/common/backBtn_lmk.png"
import PLUS_ICON from "../../assets/images/common/plus_lmk.png"
import axios from "axios";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"

var CREW_DATA_COMPLETE=false;
var CREW_DATA;
var COUNT =0;
async function getCrewDetail(id){
    const data = await axios({
      method:'get',
      url: "http://54.180.98.138:8080"+"/crew/detail/"+id,
    }).then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    CREW_DATA = data;
    COUNT = 1
    CREW_DATA_COMPLETE = true;
}



class CrewDetail extends React.Component{
    constructor(props){
        super(props);
        console.log(props)
        const {location} = props;
        CREW_DATA = location.state.data;
        console.log(CREW_DATA)
        getCrewDetail(CREW_DATA.id);

    }
    componentDidMount(){
        this.interval = setInterval(this.dataCheck, 500)
      }
      dataCheck = () =>{
        if (COUNT == 1){      
          this.setState({dataCheck:true});
          clearInterval(this.interval);
        }
      }

    requestOnClick = () =>{
        const token = this.props.store.state.request_header.accessToken;
        const {requestCrew} = this.props;
        console.log(this.props.location.state.data.id, token)
        requestCrew(token,this.props.location.state.data.id);
    }
    goBackBtn = () =>{
        this.props.history.go(-1);
    }

    render(){
        
        var MEMBERS = CREW_DATA.participants;
        if (MEMBERS == null){
            MEMBERS= [];
        }
        return (
            <div className="crewDetail">
                <div className="crewDetail_crewInfo">
                    
                    <div className="crewDetail_crewInfo_image" style={{backgroundImage: `url("${CREW_DATA.imageList[0]}")`}}>
                        <div className="crewDetail_crewInfo_image_backBtn" onClick={this.goBackBtn}>
                            <img src={BACK_ICON}/>
                        </div>
                    </div>
                    <div className="crewDetail_crewInfo_info">
                        <div className="crewDetail_crewInfo_info_block1">
                            <div className="crewDetail_crewInfo_info_title">{CREW_DATA.name}</div>
                            <div className="crewDetail_crewInfo_info_plusBtn">
                                <img src={PLUS_ICON}/>
                            </div>
                            <div className="crewDetail_crewInfo_info_adminBtn">크루관리</div>
                        </div>
                        <div className="crewDetail_crewInfo_info_block2">
                            <div className="crewDetail_crewInfo_info_activityArea">지역: {CREW_DATA.activityArea} 총모집인원: {CREW_DATA.participantsCount}</div>
                            <div className="crewDetail_crewInfo_info_category">카테고리: {CREW_DATA.category}</div>
                        </div>
                        <div className="crewDetail_crewInfo_info_description">{CREW_DATA.description}</div>
                        <div className="crewDetail_crewInfo_info_joinBtn" onClick={this.requestOnClick}>가입하기</div>
                    </div>
                </div>
                <div className="crewDetail_crewMember">
                    <div className="crewDetail_crewMember_title">모든 크루원</div>
                    {
                        MEMBERS.map((member)=>{
                            return(
                    <Link to={{ pathname:'/member/'+ member.id, state : {
                        member
                    }}}>
                        <div className="crewDetail_crewMember_list">
                            <div className="crewDetail_crewMember_list_image" style={{backgroundImage: `url("${member.imageList[0]}")`}}>
                                
                            </div>
                            <div className="crewDetail_crewMember_list_name">
                                <p>{member.nickname}</p>
                            </div>
                            <div className="crewDetail_crewMember_list_intro">
                                <p>{member.description}</p>
                            </div>
                            <hr size="1" color="#bcbcbc"></hr>
                        </div>
                    </Link>
                            )
                        })
                    }
                    
                </div>
            </div>
            )
    }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(CrewDetail);