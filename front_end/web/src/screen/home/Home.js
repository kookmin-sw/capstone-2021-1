import React from "react";
//import axios from "axios";
import Header from "../../components/common/header";
import {Link} from 'react-router-dom';
import "../../assets/css/Common/common.css"
import SideContentsContainer from "../../components/common/side_contents_container";

import "../../assets/css/Home/Home.css"
class Home extends React.Component {
  
  componentDidMount() {
    // 전체 데이터 받아오기
    // 1. 매칭데이터
    // 2. 크루데이터
    // 3. 대회데이터
  }

  render() {
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="home_main">
        <Link to="crew" className="common_link"><div className="home_to_league">크루</div></Link>
        <Link to="matching" className="common_link"><div claasName="home_to_matching">매칭</div></Link>
        <Link to="mypage" className="common_link"><div className="home_to_mypage">마이페이지</div></Link>
        <Link to="manage" className="common_link"><div className="home_to_setting">관리</div></Link>
        </div>
      </section>
    );
  }
}


export default Home;