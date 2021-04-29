import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import search_icon from '../../assets/images/common/search.jpg';
import reset_icon from '../../assets/images/common/reset.png';
import { bindActionCreators } from 'redux';
import { connect } from "react-redux";
import {actionCreators} from "../../redux/reducers/index"



class Crew extends React.Component {
    state = {}
    
    constructor(props){
        super(props);
    }


    render() {
        const {crewDatas} = this.props.store.state;
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="main_contents_container">
            <div className="region_filter">
            {
                    
                }
            </div>
            <div className="crew_filter">
                <select>
                   
                </select>
                <div className="search_bar">
                    <input id="crew_search" placeholder="크루명을 입력해주세요."/>
                    <img src={search_icon} alt="" className="search_bar"/>
                </div>
                <div className="reset_btn">
                <img src={reset_icon} alt="" className="reset_btn"/>
                </div>
            </div>
            <div className="crew_info_container">
                {
                    crewDatas.content.map((crewData) => (
                        <div>
                            <div>
                                {crewData.name}
                            </div>
                            <div>
                                {crewData.description}
                            </div>
                        </div>
                    ))
                }
            </div>
        </div>

      </section>
    );
  }
}

export default connect(store => ({ store }),dispatch => bindActionCreators(actionCreators, dispatch))(Crew);