import React from "react";
import "../../assets/css/Crew/CrewDetail.css";

class CrewDetail extends React.Component{

    componentDidMount(){
       
    }

    render(){
        const {location} = this.props;
        return (
            <div className="crew_detail_container">
                <div className="crew_detail_crew_info">
                    <div className="crew_image">
                        <div className="backbtn"></div>
                    </div>
                    <div className="crew_info">
                        <div className="crew_title">
                        </div>
                        <div className="crew_detail_info">
                        </div>
                    </div>
                </div>
                <div className="crew_detail_crew_member">
                </div>
            </div>
            )
    }
}

export default CrewDetail;