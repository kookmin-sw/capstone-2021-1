import React from "react";
//import axios from "axios";
import Header from "../../components/common/header";
import {Link} from 'react-router-dom';

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
          
          <div className="home_to_league"><Link to="league">리그</Link></div>
          <div claasName="home_to_matching"><Link to="matching">매칭</Link></div>
          <div className="home_to_mypage"><Link to="mypage">마이페이지</Link></div>
          <div className="home_to_setting"><Link to="setting">세팅</Link></div>
        </div>
      </section>
    );
  }
}


export default Home;