import React from "react";
//import axios from "axios";
import Header from "../../components/common/header";
import MainContentsContainer from "../../components/common/main_contents_container";
import SideContentsContainer from "../../components/common/side_contents_container";

import "./Home.css";

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
        <MainContentsContainer>
        </MainContentsContainer>
      </section>
    );
  }
}


export default Home;