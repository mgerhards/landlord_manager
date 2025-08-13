import React from 'react';
import { useNavigate } from 'react-router-dom';

const OpenDetailsButton = ({ obj }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/immobilien/details/${obj.id}`, { state: { realEstateObject: obj } });
  };


  return (
    <button 
      type="button"
      className="btn btn-block bg-gradient-primary btn-primary btn-sm w-100"
      onClick={handleClick}>
      Details
    </button>
  );
};

export default OpenDetailsButton;
