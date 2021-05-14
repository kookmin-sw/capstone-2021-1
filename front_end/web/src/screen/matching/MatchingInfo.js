import React from "react";
import axios from "axios";

var MATCHING_DATA_COMPLETE=false;
var MATCHING_DATA;
async function getMatchingDetail(id){
    const data = await axios({
      method:'get',
      url: "http://54.180.98.138:8080"+"/matching/detail/"+id,
    }).then(function(response){
      return response.data
    }).catch(function(error){
      alert(error.message);
    })
    MATCHING_DATA = data;
    MATCHING_DATA_COMPLETE = true;
}


class MatchingInfo extends React.Component{
    constructor(props){
        super(props);
        const {location} = props;
        MATCHING_DATA = location.state.data;
        getMatchingDetail(MATCHING_DATA.id);
    }
    componentDidMount(){
        
    }

    render(){
        var repeat = setInterval(function(){
            if (MATCHING_DATA_COMPLETE){
                clearInterval(repeat);
            }
        },500)

        // 전역변수 MATCHING_DATA에 필요한 데이터가 전부 들어있습니다.
        return (
            <div className=""></div>
            )
    }
}


export default MatchingInfo;