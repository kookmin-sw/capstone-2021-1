import React, { Component } from 'react';
import "./MarkerInfo.css";
class MarkerInfo extends Component {
    render() {
        return (
            <>
                <div className = "MarkerInfoContainer">
                    <div className="MarkerTitle">{this.props.title}</div>
                </div>

            </>
        );
    }
}


export default MarkerInfo;