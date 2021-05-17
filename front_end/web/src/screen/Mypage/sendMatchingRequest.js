import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import "../../assets/css/Matching/Matching.css";
import "../../assets/css/Common/common.css";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import DownHeader from "../../components/common/downHeader";
import NONEDATA from "../../assets/images/common/noneData.png";
import BackHeader from "../../components/common/header_back";


class sendMatchingRequest extends React.Component {
    
    
    constructor(props){
        super(props);
        this.state={dsa : 0}
    }


    onClickConfirm = (data) => {
        const {matchingConfirm} = this.props;
        const {request_header} = this.props.store.state;
        matchingConfirm(request_header.accessToken, data);
        this.setState({dsa : this.state.dsa + 1})
    }
    onClickRefuse = (data) => {
        console.log(this.props)
        const {matchingRefuse} = this.props;
        const {request_header} = this.props.store.state;
        matchingRefuse(request_header.accessToken, data);
    }
    
    render() {
    const datas = this.props.location.state.MY_MATCHING_RECEIVE_REQUEST;
    if (datas.length==0){
        return (
            <div className="crew_main_container">
              <BackHeader history="/MyCrew"/>
              <div className="request_box">
                  받은 매칭 요청
              </div>
              <div className="none_data">
                <img src={NONEDATA}></img>
                <div>
                    No results
                </div>
                </div>
              <DownHeader/>
              </div>
              
          );
    }else{
        console.log(datas)
        return (
            <div className="crew_main_container">
              <BackHeader history="/MyCrew"/>
              <div className="request_box">
                  받은 매칭 요청
              </div>
              {
                  datas.map((data)=>{
                      return(
                      <div className="dataOftitle">
                          {data.matching.title}
                          <div className="dataOfNumber">
                              총 {data.request.length}명
                          </div>
                          {
                              data.request.map((member)=>{
                                  return(
                                        <div className="request_member">
                                            <div className="member_profile"  style={{backgroundImage: `url("${member.member.imageList[0]}")`}}/>
                                            <div className="member_nickname">{member.member.nickname}</div>
                                            <div className="member_info">여/20살</div>
                                            <div className="response_btn">
                                                <div className="refuse_btn" onClick={ () => { this.onClickRefuse(member.id); } }>거절</div>
                                                <div className="confirm_btn" onClick={()=>{this.onClickConfirm(member.id)}}>수락</div>
                                            </div>
                                        </div>
                                  )
                              })
                          }
                      </div>
                      );
                  })
              }
              <DownHeader/>
              </div>
        )
}
   
        

  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(sendMatchingRequest);