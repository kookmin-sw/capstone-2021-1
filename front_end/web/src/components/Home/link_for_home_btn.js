import React from 'react';
import {Link} from 'react-router-dom';
import "../../assets/css/Home/Home.css";

function LinkForHomeBtn({ toLink,link }) {
  
  return (
    <Link to={link} className="common_link"><div className="home_to_league">{toLink}</div></Link>
  )
}

export default LinkForHomeBtn;

