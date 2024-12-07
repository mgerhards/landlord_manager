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
    <div className='container-fluid'>
      <div className='row'>
        <div className='col-12'>
          <div className='real-estate-details-container p-3 mb-3'>
            <h1>{realEstateObject.name}</h1>
            <div>{realEstateObject.description}</div><br />
            <div>Address: {realEstateObject.address}</div><br />
            <div>Size: {realEstateObject.size} sqm</div><br />
            <div>Number of Rooms: {realEstateObject.numberOfRooms}</div><br />
            <div>Latitude: {realEstateObject.latitude}</div><br />
            <div>Longitude: {realEstateObject.longitude}</div><br />
            <div>Property Type: {realEstateObject.propertyType}</div><br />
            <div>Year Built: {realEstateObject.yearBuilt}</div><br />
            <div>Floor: {realEstateObject.floor}</div><br />
            <div>Has Elevator: {realEstateObject.hasElevator ? 'Yes' : 'No'}</div><br />
            <div>Monthly Maintenance Costs: {realEstateObject.monthlyMaintenanceCosts}</div><br />
            <div>Property Tax: {realEstateObject.propertyTax}</div><br />
            <div>Heating Type: {realEstateObject.heatingType}</div><br />
            <div>Has Air Conditioning: {realEstateObject.hasAirConditioning ? 'Yes' : 'No'}</div><br />
            <div>Has Cellar: {realEstateObject.hasCellar ? 'Yes' : 'No'}</div><br />
            <div>Has Balcony: {realEstateObject.hasBalcony ? 'Yes' : 'No'}</div><br />
            <div>Has Garden: {realEstateObject.hasGarden ? 'Yes' : 'No'}</div><br />
            <div>Outdoor Space: {realEstateObject.outdoorSpace} sqm</div><br />
            <div>Is Furnished: {realEstateObject.isFurnished ? 'Yes' : 'No'}</div><br />
            <div>Last Renovation Date: {realEstateObject.lastRenovationDate}</div><br />
            <div>Next Inspection Due: {realEstateObject.nextInspectionDue}</div><br />
          </div>
        </div>
      </div>
    </div>
  );
};

RealEstateDetails.propTypes = {
  realEstateObject: PropTypes.object
};

export default RealEstateDetails;