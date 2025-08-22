import React from 'react';
import PropTypes from 'prop-types';
import { useLocation } from 'react-router-dom';
import ContractCard from './ContractCard.jsx';
import PropertyPhotoCarousel from './PropertyPhotoCarousel.jsx';
import PropertyDetailsCard from './PropertyDetailsCard.jsx';

const RealEstateDetails = () => {
  const location = useLocation();
  const { realEstateObject} = location.state || {};

  if (!realEstateObject) {
    return <div>Loading...</div>;
  }

  // Separate active and inactive contracts
  const contracts = realEstateObject.contracts || [];
  const activeContracts = contracts?.filter(contract => contract.endDate === undefined || contract.endDate === null) || [];
  const inactiveContracts = contracts?.filter(contract => contract.endDate !== undefined && contract.endDate !== null && !isNaN(Date.parse(contract.endDate))) || [];

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('de-DE');
  };

  return (
    <div className='container-fluid'>
      <div className='row'>
        {/* Photo Carousel - Full Width */}
        <div className='col-12 mb-4'>
          <PropertyPhotoCarousel photos={realEstateObject.photos || []} />
        </div>

        {/* Property Details */}
        <div className='col-md-6 mb-3'>
          <PropertyDetailsCard 
            realEstateObject={realEstateObject}
            formatDate={formatDate}
          />
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
                  <ContractCard key={`active-${contract.id || index}`} contract={contract} isActive={true} isExpandedDefault={true} />
                ))}
              </div>
            )}

            {/* Inactive Contracts */}
            {inactiveContracts.length > 0 && (
              <div className="mb-3">
                <div className="card">
                  <div className="card-header">
                    <h4 className="card-title mb-0">
                      <i className="fas fa-history me-2 text-secondary"></i>
                      Previous Contracts ({inactiveContracts.length})
                    </h4>
                  </div>
                  <div className="card-body">
                    {inactiveContracts.map((contract, index) => (
                      <div key={`inactive-${contract.id || index}`} className={index < inactiveContracts.length - 1 ? "mb-3" : ""}>
                        <ContractCard contract={contract} isActive={false} />
                      </div>
                    ))}
                  </div>
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

            {/* Tickets Section */}
            <div className='col-md-6 mb-3'>
              <h4 className="mb-3">
                <i className="fas fa-ticket-alt me-2 text-primary"></i>
                Tickets
              </h4>
              {/* Render tickets related to the real estate object */}
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