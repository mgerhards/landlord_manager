import PropTypes from 'prop-types';
import { useState } from 'react';

// In Details.jsx
const formatDate = (dateString) => {
  if (!dateString) return 'N/A';
  return new Date(dateString).toLocaleDateString('de-DE');
};

const ContractCard = ({ contract, isActive, isExpandedDefault }) => {
  const [isExpanded, setIsExpanded] = useState(   isExpandedDefault || false);
  const toggleExpanded = () => {
    setIsExpanded(!isExpanded);
  };

  return (
    <div className={`card mb-3 ${isActive ? 'border-success' : 'border-secondary'}`}>
      <div 
        className="card-header" 
        style={{ cursor: 'pointer' }}
        onClick={toggleExpanded}
      >
        <h5 className="card-title mb-0 d-flex justify-content-between align-items-center w-100">
          <div className="w-100">
            <i className={`fas fa-file-contract me-2 ${isActive ? 'text-success' : 'text-secondary'}`}></i>
            Contract #{contract.contractNumber}
            <span className={`badge ms-2 ${isActive ? 'bg-success' : 'bg-secondary'}`}>
              {isActive ? 'Active' : 'Inactive'}
            </span>
          </div>
          <i className={`fas ${isExpanded ? 'fa-chevron-up' : 'fa-chevron-down'}`}></i>
        </h5>
      </div>
      {isExpanded && (
        <div className="card-body">
          <div className="table-responsive">
            <table className="table table-striped table-hover mb-0" style={{ fontSize: '0.85rem' }}>
              <tbody>
                <tr>
                  <td><strong>Tenant Name</strong></td>
                  <td>{contract.tenants.map(tenant => tenant.name).join(', ')}</td>
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
      )}
    </div>
  );
};
ContractCard.propTypes = {
  contract: PropTypes.shape({
    contractNumber: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    tenantName: PropTypes.string,
    tenants: PropTypes.arrayOf(PropTypes.shape({
      name: PropTypes.string,
    })),
    startDate: PropTypes.oneOfType([PropTypes.string, PropTypes.instanceOf(Date)]),
    endDate: PropTypes.oneOfType([PropTypes.string, PropTypes.instanceOf(Date)]),
    monthlyRent: PropTypes.number,
    deposit: PropTypes.number,
    utilities: PropTypes.number,
    contractType: PropTypes.string,
    notes: PropTypes.string,
  }).isRequired,
  isActive: PropTypes.bool.isRequired,
  isExpandedDefault: PropTypes.bool,
};

export default ContractCard;
