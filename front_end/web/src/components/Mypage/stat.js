/*global kakao*/
import React from "react";
import RadarChart from 'react-svg-radar-chart';
import 'react-svg-radar-chart/build/css/index.css'
import "../../assets/css/Matching/Matching.css";
import "../../assets/css/Common/common.css";


class Stat extends React.Component {
  render() { 
    const DATAS = this.props.data;
    return(
        <RadarChart 
        captions={{
          manner: '매너지수',
          physical: '피지컬',
          intellect: '능지',
          comprehension: '이해력',
          affinity: '친화도',
          dsa:'참여도'
        }}
        data={[
          {
            data: {
              dsa: 0.6,
              manner: DATAS.manner,
              affinity: DATAS.affinity,
              physical: DATAS.physical,
              intellect: DATAS.intellect,
              comprehension: DATAS.comprehension
            },
            meta: { color: 'red' }
          },
        ]}
        size={200}
      />
    )  
    }
}



export default Stat;
