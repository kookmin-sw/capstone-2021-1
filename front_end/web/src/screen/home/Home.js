import React from "react";
//import axios from "axios";
import Header from "../../components/common/header";
import "../../assets/css/Common/common.css"
import SideContentsContainer from "../../components/common/side_contents_container";
import LinkForHomeBtn from "../../components/Home/link_for_home_btn";
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import "../../assets/css/Home/Home.css"
import axios from "axios";

var CREW_DATAS;
async function getCrewData(){
  const data = await axios.get('http://54.180.98.138:8080/crew/search').then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    CREW_DATAS = data
  return data;
}


class Home extends React.Component {
  constructor(props){
    super(props);
    getCrewData();
  }
  componentWillUnmount(){
     const {setCrewData} = this.props;
     setCrewData(CREW_DATAS);
  }

  render() {
    console.log(this.props);
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="home_main">
          <LinkForHomeBtn link="crew" toLink="크루"></LinkForHomeBtn>
          <LinkForHomeBtn link="matching" toLink="매칭"></LinkForHomeBtn>
          <LinkForHomeBtn link="mypage" toLink="마이페이지"></LinkForHomeBtn>
          <LinkForHomeBtn link="manage" toLink="관리"></LinkForHomeBtn>
        </div>
      </section>
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Home);