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
var MATCHING_DATAS;
async function getCrewData(){
  const data = await axios.get('http://54.180.98.138:8080/crew/search').then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    CREW_DATAS = data
  return data;
}

async function getMatchingData(){
  const data = await axios({
    method:'get',
    url: "http://54.180.98.138:8080"+"/matching/search",
  }).then(function(response){
    return response.data
  }).catch(function(error){
    alert(error.message);
  })
  MATCHING_DATAS = data
  return data;
}


class FirstPage extends React.Component {
  constructor(props){
    super(props);
    getCrewData();
    getMatchingData();
  }
  componentWillUnmount(){
     const {setCrewData, setMatchingData} = this.props;
     setCrewData(CREW_DATAS);
     setMatchingData(MATCHING_DATAS);
  }

  render() {
    console.log(this.props);
    return (
      <section className="container">
        
        <div className="first_page" >
          <div className="linkdiv">
          <div className="link_to_login"><LinkForHomeBtn link="login" toLink="SIGN IN"></LinkForHomeBtn></div>
          <div className="link_to_enroll"><LinkForHomeBtn link="enroll" toLink="CREATE ACCOUNT"></LinkForHomeBtn></div>
          </div>
        </div>
      </section>
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(FirstPage);