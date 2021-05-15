/*global kakao*/
import React from "react";
import "../../assets/css/Matching/Matching.css"
import { Swiper, SwiperSlide } from 'swiper/react';
import {Link} from "react-router-dom"

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
      console.log(this.props);
      const DATAS = this.props.data;
    return(
        <Swiper {...params}>
        {
          DATAS.map((data)=>(
            <SwiperSlide>
              
            </SwiperSlide>
            )
          )
        }
       </Swiper>
    )
  }
}


export default SliderData;
