import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Mypage/Mypage.css";
import SideContentsContainer from "../../components/common/side_contents_container";
import default_profile from "../../assets/images/common/default_profile.png";
import RadarChart from 'react-svg-radar-chart';
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import axios from "axios";

var user_detail;
var user_crew = [];
async function getCrewData(data){
  var data = await axios({
    method:'get',
    url: "http://54.180.98.138:8080"+"/member/crew",
    headers: {'X-AUTH-TOKEN': data.accessToken}
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  console.log(data);
  user_crew = data;
  return data;
}

async function getUserData(data){
  var data = await axios({
    method:'get',
    url: "http://54.180.98.138:8080"+"/member/detail/" + data.id,
    headers: {'X-AUTH-TOKEN': data.accessToken}
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  user_detail = data;
  return data;
}

class Mypage extends React.Component {

  state={}
  componentDidMount() {
      const { login_data } = this.props.store.state;
      getUserData(login_data);
      getCrewData(login_data);
  }

  componentWillUnmount(){
    const {setUserDetail, setUserCrew} = this.props;
    setUserDetail(user_detail);
    setUserCrew(user_crew);
  }

  render() {
    
    const { login_data } = this.props.store.state;
    if (login_data == undefined){
      this.props.history.push("/login");
    }else{
      
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="mypage_container">
            <div className="my_info">
                <div className="my_img">
                    <img src={default_profile}></img>
                </div>
                <div className="my_info_info">
                    {login_data.nickname}
                    <div className="my_info_descript">
                        
                    </div>
                </div>
            </div>
            <div className="my_info_detail">
                <div className="my_favorite_type">
                    <div className="my_fovarite_type_text">선호타입</div>
                    <div className="my_favorite_type_box">
                    
                    </div>
                </div>
                <div className="my_stat">
                    <div className="my_stat_text">평점</div>
                    <div className="my_stat_src">
                    <RadarChart 
            captions={{
              manner: '매너지수',
              physical: '피지컬',
              intellect: '능지',
              comprehension: '이해력',
              affinity: '친화도',
              dsa:'참여도'
            }}
            data={[
              {
                data: {
                  dsa: 0.6,
                  manner: 1,
                  affinity: 1,
                  physical: 1,
                  intellect: 1,
                  comprehension: 1
                },
                meta: { color: '#dcdcdc' }
              },
            ]}
            size={200}
          />
                    </div>
                </div>
            <div className="info_mycrew">
                내 크루
                {
                        user_crew.map((crew) => (
                          <div className="mycrew">
                            <div className="mycrew_img"></div>
                            <div className="mycrew_info">{crew.name}     {crew.description}</div>
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
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Mypage);