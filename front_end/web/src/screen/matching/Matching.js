import React from "react";
//import axios from "axios";
import Header from "../../components/common/header";
import "../../assets/css/Matching/Matching.css";
import Map from "../../components/map/Map";
import { connect } from 'react-redux';
import { actionMarkerclick } from '../../redux/actions/index';
import MarkerInfo from "../../components/map/MarkerInfo";

class Matching extends React.Component {
  constructor(props) {
    super(props);
  }
  componentDidMount() {
    // 전체 데이터 받아오기
    // 1. 매칭데이터
    // 2. 크루데이터
    // 3. 대회데이터
  }

  render() {
    return (
      <section className="container">
        <Header/>
        <Map store={this.props.store}/>
        <MarkerInfo store={this.props.store}/>
      </section>
    );
  }
}

let mapDispatchToProps = (dispatch) => {
  return {
      onMarkerClick: () => dispatch(actionMarkerclick()),
  }
}

let mapStateToProps = (state) => {
  return {
      marker_clicked : state.markerClick.marker_clicked
  };
}

//mapStateToProps를 사용하여 컴포넌트를 store에 연결시킨다.
Matching = connect(mapStateToProps,mapDispatchToProps)(Matching);

export default Matching;