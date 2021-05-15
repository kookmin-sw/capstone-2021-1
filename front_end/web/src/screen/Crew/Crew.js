import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import hamburger_icon from '../../assets/images/common/Hamburger_icon.png';
import notice_icon from '../../assets/images/common/noticeBell_icon.png';
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"
import { Link } from "react-router-dom";
import DownHeader from "../../components/common/downHeader";



class Crew extends React.Component {
    state = {}
    
    constructor(props){
        super(props);
    }


    render() {
        const {crewDatas} = this.props.store.state;
        
    return (
      <div className="crew_main_container">
        <Header/>
        <div className="region_filter">
            <div className="hamburger_filter">
                <img src={hamburger_icon}/>
            </div>
            <div className="crew_notice">
                <img src={notice_icon}/>
            </div>
        </div>
            
            <div className="crew_info_container">
            <div className="matching_make_text">크루 모아보기</div>
                {
                    crewDatas.content.map((data) => (
                        <div>
                            <div className="crewdata_info_container"  style={{backgroundImage: `url("${data.imageList[0]}")`}}>
                                <div className="matching_text">
                                    <div className="matching_text_locate">{data.activityArea}</div>
                                    <div className="matching_text_title">{data.name}</div>
                                </div>
                            </div>
                            <Link to={{ pathname:'/crew/${data.id}', state:{data}}}>
                            <div className="crewdata_show_detail">
                            자세히 보기
                            <div className="crewdata_show_detail_btn">
                                >
                            </div>
                        </div>
                        </Link>
                        </div>
                    ))
                }
            </div>
            <DownHeader/>
        </div>
    );
  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Crew);