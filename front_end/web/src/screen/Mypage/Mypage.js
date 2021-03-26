import React from "react";
import Header from "../../components/common/header";
import "../../assets/css/Mypage/Mypage.css";
import SideContentsContainer from "../../components/common/side_contents_container";
import default_profile from "../../assets/images/common/default_profile.png";
import RadarChart from 'react-svg-radar-chart';

class Mypage extends React.Component {

  state={user_data:[{"id":1,"uid":"dlwlsrn94@naver.com","password":"{bcrypt}$2a$10$xf5HGxFwKlqTZRwx/4HPEOw3bpR8SubTM9inm4FHNAF7e6A4iBdfq","nickname":"닉네임9","name":"이진구구","address":"서울특별시 서대문구","description":"자기소개","phoneNumber":"010-8784-3827","provider":"default","status":"ACTIVE","role":"USER"},{"id":8,"uid":"dlwlsrn8","password":"{bcrypt}$2a$10$SseHs69gKw7XH8cDmGRpxujMGUsjyYDFXSpSNcwFTk1rnAm39Bi3q","nickname":"이진팔","name":"이진팔","address":"대구광역시 동구","description":"자기 소개","phoneNumber":"010-8784-3827","provider":"default","status":"ACTIVE","role":"USER"},{"id":12,"uid":"dlwlsrn7","password":"{bcrypt}$2a$10$8JttqrnQDpNy6ZW67j2sSu0ic8GnHhVf9mONUevfZu/8elM2W3sqS","nickname":"이진칠","name":"이진칠","address":" 부산","description":"자기 소개","phoneNumber":"010-8784-3827","provider":"default","status":"ACTIVE","role":"USER"}]}
  componentDidMount() {
    
  }

  render() {
    const user =this.state.user_data[0];
    
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
                    {user.nickname}
                    <div className="my_info_descript">
                        {user.description}
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
                <div className="mycrew">
                    <div className="mycrew_img"></div>
                    <div className="mycrew_info">crew1</div>
                </div>
            </div>
            </div>
        </div>
      </section>
    );
  }
}


export default Mypage;