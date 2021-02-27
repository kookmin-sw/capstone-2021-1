import React from "react";
import Header from "../../components/common/header";
import SideContentsContainer from "../../components/common/side_contents_container";
import "../../assets/css/Common/main_contents_container.css";
import "../../assets/css/Crew/Crew.css";
import search_icon from '../../assets/images/common/search.jpg';
import reset_icon from '../../assets/images/common/reset.png';
import axios from 'axios';


async function getCrewData(){
    try {
        const response = await axios.get('http://54.180.98.138:8080/crew/search');
        console.log(response)
        return response;
        
      } catch (error) {
        console.error(error);
      }
}

class Crew extends React.Component {

    state = {
        regions:["전체","서울","경기","강원","충청","전라","경상","제주"],
        categorys: ["전체","보드게임","추리카페","방탈출"],
        crewData:{"content":[{"id":17,"name":"크루2","description":"피곤행...","maxCount":50,"activityArea":"경기","category":"ROOM ESCAPE","createdAt":"2021-02-11T06:26:23","host":null,"participantsCount":null,"participants":null},{"id":7,"name":"크루1","description":"잉여 인간 모임","maxCount":10,"activityArea":"서울","category":"BOARD GAME","createdAt":"2021-02-11T04:45:16","host":null,"participantsCount":null,"participants":null}],"pageable":{"sort":{"sorted":false,"unsorted":true,"empty":true},"pageNumber":0,"pageSize":20,"offset":0,"paged":true,"unpaged":false},"last":true,"totalElements":2,"totalPages":1,"sort":{"sorted":false,"unsorted":true,"empty":true},"numberOfElements":2,"first":true,"size":20,"number":0,"empty":false}
    }

    

    componentDidMount(){
       
    }

    render() {
        const {regions,categorys,crewData}=this.state;
    return (
      <section className="container">
        <Header/>
        <SideContentsContainer>
        </SideContentsContainer>
        <div className="main_contents_container">
            <div className="region_filter">
                {
                    regions.map((region,index) => (
                        <div className={region + index}>
                            {region}
                        </div>
                    ))
                }
            </div>
            <div className="crew_filter">
                <select>
                    {categorys.map((category,index) =>(
                        <option value={category}>{category}</option>
                    ))}
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
                    crewData.content.map((data)=>(<div className="crew_data_box">
                        <div className="crew_name">{data.name}</div>
                        <div className="crew_info">
                            <div className="crew_info_category">{data.category}</div>
                            <div className="crew_info_category">{data.description}</div>
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

export default Crew;