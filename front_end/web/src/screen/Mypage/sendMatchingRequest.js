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

var datas=[];

class sendMatchingRequest extends React.Component {
    
    
    constructor(props){
        super(props);
        this.state={dsa : 0}
        datas = this.props.location.state.MY_MATCHING_RECEIVE_REQUEST;
    }


    onClickConfirm = (data,index,indexcol,request) => {
        const {matchingConfirm} = this.props;
        const {request_header} = this.props.store.state;
        matchingConfirm(request_header.accessToken, data);
        var requestData = request;
        requestData.request.splice(index,1);
        datas[indexcol] = requestData;
        this.setState({dsa : this.state.dsa + 1})
    }

    onClickRefuse = (data,index,indexcol,request) => {
        console.log(this.props)
        const {matchingRefuse} = this.props;
        const {request_header} = this.props.store.state;
        matchingRefuse(request_header.accessToken, data);
        var requestData = request;
        requestData.request.splice(index,1);
        datas[indexcol] = requestData;
        this.setState({dsa : this.state.dsa + 1})
    }
    
    delMyMatching = (id,indexcol) =>{
        console.log(datas);
        console.log(id)
        const {delMatching} = this.props;
        const {request_header} = this.props.store.state;
        delMatching(request_header.accessToken, id);
        datas.splice(indexcol,1);
        this.setState({dsa : this.state.dsa + 1})
    }

    render() {
    
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
                  datas.map((data,indexcol)=>{
                      return(
                      <div className="dataOftitle">
                          <text>
                          {data.matching.title}
                          </text>
                          <div className="del_matching" onClick={ () => { this.delMyMatching(data.matching.id,indexcol); } }>
                            삭제
                            </div>
                          <div className="dataOfNumber">
                              총 {data.request.length}명
                          </div>
                          {
                              data.request.map((member,index)=>{
                                  return(
                                        <div className="request_member">
                                            <div className="member_profile"  style={{backgroundImage: `url("${member.member.imageList[0]}")`}}/>
                                            <div className="member_nickname">{member.member.nickname}</div>
                                            <div className="member_info">여/20살</div>
                                            <div className="response_btn">
                                                <div className="refuse_btn" onClick={ () => { this.onClickRefuse(member.id,index,indexcol,data); } }>거절</div>
                                                <div className="confirm_btn" onClick={ ()=>{this.onClickConfirm(member.id,index,indexcol,data);}}>수락</div>
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