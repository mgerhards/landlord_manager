import React from 'react';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';

const OpenDetailsButton = ({ company }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/companies/details/${company.id}`, { state: { company: company } });
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

OpenDetailsButton.propTypes = {
  company: PropTypes.shape({
    id: PropTypes.number.isRequired
  }).isRequired
};

export default OpenDetailsButton;