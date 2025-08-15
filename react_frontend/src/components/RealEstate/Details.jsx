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
          <div className="card">
            <div className="card-header">
              <h3 className="card-title mb-0">
                <i className="fas fa-building me-2"></i>
                {realEstateObject.name}
              </h3>
            </div>
            <div className="card-body">
              {realEstateObject.description && (
                <div className="mb-4">
                  <p className="text-muted">{realEstateObject.description}</p>
                </div>
              )}

              <div className="table-responsive">
                <table className="table table-striped table-hover mb-0">
                  <tbody>
                    <tr>
                      <td><strong>Address</strong></td>
                      <td>{realEstateObject.address}</td>
                    </tr>
                    <tr>
                      <td><strong>Size</strong></td>
                      <td>{realEstateObject.size} sqm</td>
                    </tr>
                    <tr>
                      <td><strong>Number of Rooms</strong></td>
                      <td>{realEstateObject.numberOfRooms}</td>
                    </tr>
                    <tr>
                      <td><strong>Property Type</strong></td>
                      <td>{realEstateObject.propertyType}</td>
                    </tr>
                    <tr>
                      <td><strong>Year Built</strong></td>
                      <td>{realEstateObject.yearBuilt}</td>
                    </tr>
                    <tr>
                      <td><strong>Floor</strong></td>
                      <td>{realEstateObject.floor}</td>
                    </tr>
                    <tr>
                      <td><strong>Has Elevator</strong></td>
                      <td>
                        <span className={`badge ${realEstateObject.hasElevator ? 'bg-success' : 'bg-secondary'}`}>
                          {realEstateObject.hasElevator ? 'Yes' : 'No'}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Monthly Maintenance Costs</strong></td>
                      <td>€{realEstateObject.monthlyMaintenanceCosts}</td>
                    </tr>
                    <tr>
                      <td><strong>Property Tax</strong></td>
                      <td>€{realEstateObject.propertyTax}</td>
                    </tr>
                    <tr>
                      <td><strong>Heating Type</strong></td>
                      <td>{realEstateObject.heatingType}</td>
                    </tr>
                    <tr>
                      <td><strong>Air Conditioning</strong></td>
                      <td>
                        <span className={`badge ${realEstateObject.hasAirConditioning ? 'bg-success' : 'bg-secondary'}`}>
                          {realEstateObject.hasAirConditioning ? 'Yes' : 'No'}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Cellar</strong></td>
                      <td>
                        <span className={`badge ${realEstateObject.hasCellar ? 'bg-success' : 'bg-secondary'}`}>
                          {realEstateObject.hasCellar ? 'Yes' : 'No'}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Balcony</strong></td>
                      <td>
                        <span className={`badge ${realEstateObject.hasBalcony ? 'bg-success' : 'bg-secondary'}`}>
                          {realEstateObject.hasBalcony ? 'Yes' : 'No'}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Garden</strong></td>
                      <td>
                        <span className={`badge ${realEstateObject.hasGarden ? 'bg-success' : 'bg-secondary'}`}>
                          {realEstateObject.hasGarden ? 'Yes' : 'No'}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Outdoor Space</strong></td>
                      <td>{realEstateObject.outdoorSpace} sqm</td>
                    </tr>
                    <tr>
                      <td><strong>Furnished</strong></td>
                      <td>
                        <span className={`badge ${realEstateObject.isFurnished ? 'bg-success' : 'bg-secondary'}`}>
                          {realEstateObject.isFurnished ? 'Yes' : 'No'}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Last Renovation Date</strong></td>
                      <td>{realEstateObject.lastRenovationDate}</td>
                    </tr>
                    <tr>
                      <td><strong>Next Inspection Due</strong></td>
                      <td>{realEstateObject.nextInspectionDue}</td>
                    </tr>
                    <tr>
                      <td><strong>Location</strong></td>
                      <td>{realEstateObject.latitude}, {realEstateObject.longitude}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
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