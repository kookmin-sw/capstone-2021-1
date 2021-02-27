import RadarChart from 'react-svg-radar-chart';
import 'react-svg-radar-chart/build/css/index.css'
import React from 'react';
  
function statChart(props){
    const data = [
        {
          data: {
            battery: 0.7,
            design: .8,
            useful: 0.9,
            speed: 0.67,
            weight: 0.8
          },
          meta: { color: 'blue' }
        },
        {
          data: {
            battery: 0.6,
            design: .85,
            useful: 0.5,
            speed: 0.6,
            weight: 0.7
          },
          meta: { color: 'red' }
        }
      ];
    
    const captions = {
        // columns
        battery: 'Battery Capacity',
        design: 'Design',
        useful: 'Usefulness',
        speed: 'Speed',
        weight: 'Weight'
      };
  
return (
    <div>
        <RadarChart
            captions={{
              // columns
              battery: 'Battery Capacity',
              design: 'Design',
              useful: 'Usefulness',
              speed: 'Speed',
              weight: 'Weight'
            }}
            data={[
              // data
              {
                data: {
                  battery: 0.7,
                  design: .8,
                  useful: 0.9,
                  speed: 0.67,
                  weight: 0.8
                },
                meta: { color: '#58FCEC' }
              },
            ]}
            size={400}
          />
        </div>
  );
};

export default statChart;