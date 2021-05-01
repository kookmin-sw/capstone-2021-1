import React from "react";
import "../../assets/css/Enroll/Enrollment.css";
import "../../assets/css/Home/Home.css";
import "../../assets/css/Crew/Crew.css";
import Swiper from 'react-id-swiper';
import Header from "../../components/common/header"
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"

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

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            uid:'',password:'',name:'',nickname:'',phoneNumber:'',provider:'default',address:'seoul',complete:false}
    }

    render() {
        console.log(this.props);
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
              matchingDatas.content.map((data)=>(
                
                <div className="default_background_chess">
                  <div className="matching_text">
                    <div className="matching_text_locate">{data.description}</div>
                    <div className="matching_text_title">{data.title}</div>
                  </div>
                  <div className="person_for_matching">/{data.maxCount}</div>
                </div>)
              )
            }
            </div>
          </div>
          <div className="home_content_crew">
            <div className="content_text">
              크루
            </div>
            <Swiper {...params}>
            {
              crewDatas.content.map((data)=>(
                <div className="crew_text">
                  <div className="crew_area">{data.activityArea}</div>
                  <div className="crea_name">{data.name}</div>
                </div>
                )
              )
            }
            
           </Swiper>
          </div>
        </div>
      </section>
    );
  }
}


export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Home);