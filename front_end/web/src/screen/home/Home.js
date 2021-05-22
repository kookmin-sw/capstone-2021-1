import React from "react";
import "../../assets/css/Enroll/Enrollment.css";
import "../../assets/css/Home/Home.css";
import "../../assets/css/Crew/Crew.css";
import { Swiper, SwiperSlide } from 'swiper/react';
import DownHeader from "../../components/common/downHeader";
import Header from "../../components/common/header"
import {Link} from "react-router-dom"
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import axios from "axios"

const params = {
  pagination: {
    el: '.swiper-pagination.customized-swiper-pagination',
  }, // Add your class name for pagination container
  navigation: {
    nextEl: '.swiper-button-next.customized-swiper-button-next', // Add your class name for next button
    prevEl: '.swiper-button-prev.customized-swiper-button-prev' // Add your class name for prev button
  },
  containerClass: 'customized-swiper-container' // Replace swiper-container with customized-swiper-container
}

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

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            uid:'',password:'',name:'',nickname:'',phoneNumber:'',provider:'default',address:'seoul',complete:false}
    }

    componentDidMount(){
      getMatchingData();
      getCrewData();
    }
    
    componentWillUnmount(){
      const {setCrewData, setMatchingData} = this.props;
      setCrewData(CREW_DATAS);
      setMatchingData(MATCHING_DATAS);
   }

    render() {
        const {matchingDatas, crewDatas} = this.props.store.state;
    return (
      
      <section className="container">
        <Header/>
        
        <div className="home_content">
          <div className="home_content_matching">
            <div className="content_text">
              매칭
            </div>
            <div>
            {
              matchingDatas.content.splice(0,2).map((data)=>
                  (
                  <Link to={{ pathname:'/matching/detail/${data.id}', state:{data}}}>
                  <div className="matching_box " style={{backgroundImage: `url("${data.imageList[0]}")`}}>
                    <div className="matching_text" >
                      <div className="matching_text_locate"><text>{data.description}</text></div>
                      <div className="matching_text_title"><text>{data.title}</text></div>
                    </div>
                    <div className="person_for_matching">{data.participantsCount+1}/{data.maxCount}</div>
                  </div>
                </Link>)
              )
            }
            </div>
            <Link to={{ pathname:'/AllMatching'}}>
            <div className="view_all_btn">
              View all
              <div className="view_all_img">
                {'>'}
              </div>
            </div>
            </Link>
            
          </div>
          <div className="home_content_crew">
            <div className="content_text">
              크루
            </div>
            <Swiper {...params} spaceBetween={50}>
            {
              crewDatas.content.map((data)=>(
                <SwiperSlide >
                  <Link to={{ pathname:'/crew/${data.id}', state:{data}}}>
                    <div className="crew_text" style={{backgroundImage: `url("${data.imageList[0]}")`}}>
                      <div className="crew_area">{data.activityArea}</div>
                      <div className="crea_name">{data.name}</div>
                    </div>
                  </Link>
                </SwiperSlide>
                )
              )
            }
           </Swiper>
          </div>
          
        </div>
        <DownHeader />
      </section>
    );
  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Home);