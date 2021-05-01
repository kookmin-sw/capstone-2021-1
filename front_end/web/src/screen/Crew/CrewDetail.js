import React from "react";


class CrewDetail extends React.Component{

    componentDidMount(){
       
    }

    render(){
        const {location} = this.props;
        return (
            <div>{location.state.data.name}</div>
            )
    }
}

export default CrewDetail;