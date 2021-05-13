import React from "react";
import axios from "axios";

var MATCHING_DATA=null;
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
  }


class MatchingInfo extends React.Component{
    
    componentDidMount(){
        console.log(this.props)
        getMatchingDetail(this.props.location.data.data.id);
    }

    render(){
        var repeat = setInterval(function(){
            if (MATCHING_DATA != null){
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