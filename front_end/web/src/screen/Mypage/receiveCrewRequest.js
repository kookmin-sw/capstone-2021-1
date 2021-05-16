import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import "../../assets/css/Matching/Matching.css";
import "../../assets/css/Common/common.css";
import DownHeader from "../../components/common/downHeader";
import NONEDATA from "../../assets/images/common/noneData.png";
import BackHeader from "../../components/common/header_back";



class sendCrewRequest extends React.Component {
    
    
    constructor(props){
        super(props);
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
        return (
            <div className="crew_main_container">
              <BackHeader history="/MyCrew"/>
              <div className="request_box">
                  받은 크루 요청
              </div>
              {
                  datas.map((data)=>{
                      return(
                      <div className="dataOftitle">
                          {data.crew.name}
                          <div className="dataOfNumber">
                              총 {data.request.length}명
                          </div>
                      </div>
                      )
                  })
              }
              <DownHeader/>
              </div>
        )
}
   
        

  }
}

export default sendCrewRequest;