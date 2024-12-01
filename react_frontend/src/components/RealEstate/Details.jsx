import React from 'react';
import PropTypes from 'prop-types';
import { useLocation } from 'react-router-dom';

const RealEstateDetails = () => {
  const location = useLocation();
  const { realEstateObject } = location.state || {};

  if (!realEstateObject) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>{realEstateObject.name}</h1>
      <p>{realEstateObject.description}</p>
      {/* Add more fields as necessary */}
    </div>
  );
};

RealEstateDetails.propTypes = {
  realEstateObject: PropTypes.object
};

export default RealEstateDetails;