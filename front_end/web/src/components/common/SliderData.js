/*global kakao*/
import React from "react";
import "../../assets/css/Matching/Matching.css";
import "../../assets/css/Common/common.css";
import { Swiper, SwiperSlide } from 'swiper/react';
import {Link} from "react-router-dom"
import NONEDATA from "../../assets/images/common/noneData.png";
import { isCompositeComponentWithType } from "react-dom/test-utils";
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

class SliderData extends React.Component {
  render() { 
      const DATAS = this.props.data;
      if (DATAS.length==0){
        return(
          <div className="none_data">
            <img src={NONEDATA}></img>
            <div>
              No results
            </div>
          </div>
        )
      }
      else if(this.props.type=='MY_MATCHING'){
        console.log(DATAS)
        return(
          <Swiper {...params} slidesPerView={1.2}>
          {
            DATAS.map((data) => (
              <SwiperSlide  style={{width:"240px"}}>
                  <div className="slider_my_info" style={{backgroundImage: `url("${data.imageList[0]}")`}}>
                    {data.title}
                  </div>
              </SwiperSlide>
            ))
          }
         </Swiper>
        )
      }else{
        console.log(DATAS)
        return(
          <Swiper {...params}>
          {
            DATAS.map((data) => (
              <SwiperSlide slidesPerView={3.2}>
                <div>
                  <div className="round_slider" style={{backgroundImage: `url("https://play-maker.s3.ap-northeast-2.amazonaws.com/1bd02b15-b228-4626-9d67-cefb69cbfd89.jpg")`}}></div>
                  <div className="round_slider_text">{data.matching.title}</div>
                  </div>
              </SwiperSlide>
            ))
          }
         </Swiper>
        )
      }
    
  }
}


export default SliderData;
