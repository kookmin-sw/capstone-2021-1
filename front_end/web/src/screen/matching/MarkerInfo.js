import React from "react";
import "../../assets/css/Crew/CrewDetail.css";
import Slider from "react-slick";
class MarkerInfo extends React.Component{

    

    componentDidMount(){
       
    }

    render(){
        const {data} = this.props;
        console.log(data);
        return (
            <Slider >
                {data}
            </Slider>
            )
    }
}

export default MarkerInfo;