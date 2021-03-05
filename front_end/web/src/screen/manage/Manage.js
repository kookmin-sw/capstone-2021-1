import React from "react";
//import axios from "axios";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Manage/Manage.css"
import picture from "../../assets/images/common/logo.jpg"
class Manage extends React.Component {
  state = {matching_data:[{
    "title":"체스 초보방",
    "description":"초보방 홍대 히어로 보드게임 카페",
    "latitude":  37.55214,
    "longitude": 126.92184,
    "maxCount": 5,
    "category":"BOARD GAME",
    "startTime":"2021-03-20T12:00:00"
},{
    "title":"체스 초보방2",
    "description":"초보방 홍대 히어로 보드게임 카페",
    "latitude":  37.55214,
    "longitude": 126.92184,
    "maxCount": 5,
    "category":"BOARD GAME",
    "startTime":"2021-03-20T12:00:00"
},{
    "title":"체스 초보방3",
    "description":"초보방 홍대 히어로 보드게임 카페",
    "latitude":  37.55214,
    "longitude": 126.92184,
    "maxCount": 5,
    "category":"BOARD GAME",
    "startTime":"2021-03-20T12:00:00"
}],league_data:[{
    "title": "바둑 리그",
    "description":"아마추어 바둑 리그",
    "activityArea":"서울",
    "maxCount":10,
    "leagueType":"LEAGUE",
    "participantType":"INDIVIDUAL",
    "startTime":"2021-11-20T12:00:00",
    "category":"BOARD GAME"
}]
}
  componentDidMount() {

  }

  render() {
    const {matching_data,league_data}=this.state;
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="manage_container">
            <div className="manage_receive_sending_request">
                <div className="manage_sending_request">
                    {
                        matching_data.map((matching) => (
                            <div className="matching_league_data_container">
                                <div className="matching_image" >
                                </div>
                                <div className="matching_info">
                                    <div className="matching_info_title">
                                        {matching.title}
                                    </div>
                                    <div className="matching_info_description">
                                        {matching.description}
                                    </div>    
                                </div>
                            </div>
                        ))
                    }
                </div>
                <div className="manage_sending_request float_right">
                    {
                        matching_data.map((matching) => (
                            <div className="matching_league_data_container">
                                <div className="matching_image" >
                                </div>
                                <div className="matching_info">
                                    <div className="matching_info_title">
                                        {matching.title}
                                    </div>
                                    <div className="matching_info_description">
                                        {matching.description}
                                    </div>    
                                </div>
                            </div>
                        ))
                    }
                </div>
            </div>
            <div className="manage_receive_sending_request">
            <div className="manage_sending_request">
                    {
                        matching_data.map((matching) => (
                            <div className="matching_league_data_container">
                                <div className="matching_image" >
                                </div>
                                <div className="matching_info">
                                    <div className="matching_info_title">
                                        {matching.title}
                                    </div>
                                    <div className="matching_info_description">
                                        {matching.description}
                                    </div>    
                                </div>
                            </div>
                        ))
                    }
                </div>
                <div className="manage_sending_request float_right">
                    {
                        matching_data.map((matching) => (
                            <div className="matching_league_data_container">
                                <div className="matching_image" >
                                </div>
                                <div className="matching_info">
                                    <div className="matching_info_title">
                                        {matching.title}
                                    </div>
                                    <div className="matching_info_description">
                                        {matching.description}
                                    </div>    
                                </div>
                            </div>
                        ))
                    }
                </div>
            </div>
        </div>
      </section>
    );
  }
}


export default Manage;