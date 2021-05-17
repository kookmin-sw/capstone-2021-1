import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import "../../assets/css/Matching/Matching.css";
import "../../assets/css/Common/common.css";
import DownHeader from "../../components/common/downHeader";
import NONEDATA from "../../assets/images/common/noneData.png";
import BackHeader from "../../components/common/header_back";


var datas=[];

class sendCrewRequest extends React.Component {
    
    constructor(props){
        super(props);
        this.state={dsa : 0}
        datas = this.props.location.state.MY_CREW_RECEIVE_REQUEST;
    }

    onClickConfirm = (data,index,indexcol,request) => {
        const {crewConfirm} = this.props;
        const {request_header} = this.props.store.state;
        crewConfirm(request_header.accessToken, data);
        var requestData = request;
        requestData.request.splice(index,1);
        datas[indexcol] = requestData;
        this.setState({dsa : this.state.dsa + 1})
    }

    onClickRefuse = (data,index,indexcol,request) => {
        console.log(this.props)
        const {crewRefuse} = this.props;
        const {request_header} = this.props.store.state;
        crewRefuse(request_header.accessToken, data);
        var requestData = request;
        requestData.request.splice(index,1);
        datas[indexcol] = requestData;
        this.setState({dsa : this.state.dsa + 1})
    }

    
    render() {
    const datas = this.props.location.state.MY_CREW_SEND_REQUEST;
    if (datas.length==0){
        return (
            <div className="crew_main_container">
              <BackHeader history="/MyCrew"/>
              <div className="request_box">
                  받은 크루 요청
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
                  받은 크루 요청
              </div>
              {
                  datas.map((data,indexcol)=>{
                      return(
                      <div className="dataOftitle">
                          {data.crew.name}
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

export default sendCrewRequest;