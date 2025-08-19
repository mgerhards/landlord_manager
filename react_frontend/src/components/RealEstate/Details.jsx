import React from 'react';
import PropTypes from 'prop-types';
import { useLocation } from 'react-router-dom';

const RealEstateDetails = () => {
  const location = useLocation();
  const { realEstateObject} = location.state || {};

  if (!realEstateObject) {
    return <div>Loading...</div>;
  }

  // Separate active and inactive contracts
  const contracts = realEstateObject.contracts || [];
  const activeContracts = contracts?.filter(contract => contract.isActive) || [];
  const inactiveContracts = contracts?.filter(contract => !contract.isActive) || [];

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('de-DE');
  };

  const ContractCard = ({ contract, isActive }) => (
    <div className={`card mb-3 ${isActive ? 'border-success' : 'border-secondary'}`}>
      <div className="card-header">
        <h5 className="card-title mb-0">
          <i className={`fas fa-file-contract me-2 ${isActive ? 'text-success' : 'text-secondary'}`}></i>
          Contract #{contract.contractNumber}
          <span className={`badge ms-2 ${isActive ? 'bg-success' : 'bg-secondary'}`}>
            {isActive ? 'Active' : 'Inactive'}
          </span>
        </h5>
      </div>
      <div className="card-body">
        <div className="table-responsive">
          <table className="table table-striped table-hover mb-0" style={{ fontSize: '0.85rem' }}>
            <tbody>
              <tr>
                <td><strong>Tenant Name</strong></td>
                <td>{contract.tenantName}</td>
              </tr>
              <tr>
                <td><strong>Start Date</strong></td>
                <td>{formatDate(contract.startDate)}</td>
              </tr>
              <tr>
                <td><strong>End Date</strong></td>
                <td>{formatDate(contract.endDate)}</td>
              </tr>
              <tr>
                <td><strong>Monthly Rent</strong></td>
                <td>€{contract.monthlyRent?.toFixed(2)}</td>
              </tr>
              <tr>
                <td><strong>Deposit</strong></td>
                <td>€{contract.deposit?.toFixed(2)}</td>
              </tr>
              {contract.utilities && (
                <tr>
                  <td><strong>Utilities</strong></td>
                  <td>€{contract.utilities.toFixed(2)}</td>
                </tr>
              )}
              {contract.contractType && (
                <tr>
                  <td><strong>Contract Type</strong></td>
                  <td>{contract.contractType}</td>
                </tr>
              )}
              {contract.notes && (
                <tr>
                  <td><strong>Notes</strong></td>
                  <td>{contract.notes}</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );

  return (
    <div className='container-fluid'>
      <div className='row'>
        {/* Photo Carousel - Full Width */}
        <div className='col-12 mb-4'>
          <div className="card">
            <div className="card-header">
              <h3 className="card-title mb-0">
                <i className="fas fa-images me-2"></i>
                Property Photos
              </h3>
            </div>
            <div className="card-body p-0">
              {realEstateObject.photos && realEstateObject.photos.length > 0 ? (
                <div id="propertyCarousel" className="carousel slide" data-bs-ride="carousel">
                  {/* Carousel Indicators */}
                  <div className="carousel-indicators">
                    {realEstateObject.photos.map((_, index) => (
                      <button
                        key={index}
                        type="button"
                        data-bs-target="#propertyCarousel"
                        data-bs-slide-to={index}
                        className={index === 0 ? 'active' : ''}
                        aria-current={index === 0 ? 'true' : 'false'}
                        aria-label={`Slide ${index + 1}`}
                      ></button>
                    ))}
                  </div>
                  
                  {/* Carousel Images */}
                  <div className="carousel-inner">
                    {realEstateObject.photos.map((photo, index) => (
                      <div key={index} className={`carousel-item ${index === 0 ? 'active' : ''}`}>
                        <img
                          src={photo.url || photo}
                          className="d-block w-100"
                          alt={photo.description || `Property photo ${index + 1}`}
                          style={{ 
                            height: '400px', 
                            objectFit: 'cover',
                            objectPosition: 'center'
                          }}
                        />
                        {photo.description && (
                          <div className="carousel-caption d-none d-md-block bg-dark bg-opacity-50 rounded p-2">
                            <h5>{photo.title || `Photo ${index + 1}`}</h5>
                            <p>{photo.description}</p>
                          </div>
                        )}
                      </div>
                    ))}
                  </div>
                  
                  {/* Carousel Controls */}
                  <button
                    className="carousel-control-prev"
                    type="button"
                    data-bs-target="#propertyCarousel"
                    data-bs-slide="prev"
                  >
                    <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span className="visually-hidden">Previous</span>
                  </button>
                  
                  <button
                    className="carousel-control-next"
                    type="button"
                    data-bs-target="#propertyCarousel"
                    data-bs-slide="next"
                  >
                    <span className="carousel-control-next-icon" aria-hidden="true"></span>
                    <span className="visually-hidden">Next</span>
                  </button>
                </div>
              ) : (
                <div className="text-center py-5 text-muted">
                  <i className="fas fa-image fa-3x mb-3 text-secondary"></i>
                  <h5>No Photos Available</h5>
                  <p>No photos have been uploaded for this property yet.</p>
                </div>
              )}
            </div>
          </div>
        </div>

        {/* Property Details */}
        <div className='col-md-6 mb-3'>
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
                <table className="table table-striped table-hover mb-0" style={{ fontSize: '0.85rem' }}>
                  <tbody>
                    {/* General Information */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>General Information</strong></td>
                    </tr>
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

                    {/* Features */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Features</strong></td>
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

                    {/* Financial */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Financial</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Monthly Maintenance Costs</strong></td>
                      <td>€{realEstateObject.monthlyMaintenanceCosts}</td>
                    </tr>
                    <tr>
                      <td><strong>Property Tax</strong></td>
                      <td>€{realEstateObject.propertyTax}</td>
                    </tr>

                    {/* Dates */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Dates</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Last Renovation Date</strong></td>
                      <td>{formatDate(realEstateObject.lastRenovationDate)}</td>
                    </tr>
                    <tr>
                      <td><strong>Next Inspection Due</strong></td>
                      <td>{formatDate(realEstateObject.nextInspectionDue)}</td>
                    </tr>

                    {/* Location */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Location</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Coordinates</strong></td>
                      <td>{realEstateObject.latitude}, {realEstateObject.longitude}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>

        {/* Contracts Section */}
        <div className='col-md-6 mb-3'>
          <div className="d-flex flex-column h-100">
            {/* Active Contracts */}
            {activeContracts.length > 0 && (
              <div className="mb-3">
                <h4 className="mb-3">
                  <i className="fas fa-handshake me-2 text-success"></i>
                  Active Contracts ({activeContracts.length})
                </h4>
                {activeContracts.map((contract, index) => (
                  <ContractCard key={`active-${contract.id || index}`} contract={contract} isActive={true} />
                ))}
              </div>
            )}

            {/* Inactive Contracts */}
            {inactiveContracts.length > 0 && (
              <div className="mb-3">
                <h4 className="mb-3">
                  <i className="fas fa-history me-2 text-secondary"></i>
                  Previous Contracts ({inactiveContracts.length})
                </h4>
                <div className="accordion" id="inactiveContractsAccordion">
                  {inactiveContracts.map((contract, index) => (
                    <div key={`inactive-${contract.id || index}`} className="accordion-item">
                      <h2 className="accordion-header" id={`heading-${index}`}>
                        <button
                          className="accordion-button collapsed"
                          type="button"
                          data-bs-toggle="collapse"
                          data-bs-target={`#collapse-${index}`}
                          aria-expanded="false"
                          aria-controls={`collapse-${index}`}
                        >
                          Contract #{contract.contractNumber} - {contract.tenantName}
                        </button>
                      </h2>
                      <div
                        id={`collapse-${index}`}
                        className="accordion-collapse collapse"
                        aria-labelledby={`heading-${index}`}
                        data-bs-parent="#inactiveContractsAccordion"
                      >
                        <div className="accordion-body p-0">
                          <ContractCard contract={contract} isActive={false} />
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            )}

            {/* No Contracts */}
            {(!contracts || contracts.length === 0) && (
              <div className="card">
                <div className="card-header">
                  <h3 className="card-title mb-0">
                    <i className="fas fa-file-contract me-2"></i>
                    Contract Data
                  </h3>
                </div>
                <div className="card-body text-center text-muted py-5">
                  <i className="fas fa-file-contract fa-3x mb-3"></i>
                  <h5>No Contracts Available</h5>
                  <p>No rental contracts have been created for this property yet.</p>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

RealEstateDetails.propTypes = {
  realEstateObject: PropTypes.object,
  contracts: PropTypes.arrayOf(PropTypes.object)
};

export default RealEstateDetails;