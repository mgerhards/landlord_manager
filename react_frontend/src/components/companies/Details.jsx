import React from 'react';
import PropTypes from 'prop-types';
import { useLocation } from 'react-router-dom';

const CompaniesDetails = () => {
  const location = useLocation();
  const { company } = location.state || {};

  if (!company) {
    return <div>Loading...</div>;
  }

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('de-DE');
  };

  const formatCurrency = (amount) => {
    if (!amount) return 'N/A';
    return new Intl.NumberFormat('de-DE', {
      style: 'currency',
      currency: 'EUR'
    }).format(amount);
  };

  const formatArray = (arr) => {
    if (!arr || arr.length === 0) return 'N/A';
    return arr.join(', ');
  };

  return (
    <div className="container-fluid">
      <div className="row">
        {/* Company Details Card */}
        <div className="col-md-6 mb-3">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title mb-0">
                <i className="fas fa-building me-2"></i>
                Company Details
              </h3>
            </div>
            <div className="card-body">
              <div className="table-responsive">
                <table className="table table-striped table-hover mb-0" style={{ fontSize: '0.85rem' }}>
                  <tbody>
                    {/* Basic Company Information */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Basic Information</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Company Name</strong></td>
                      <td>{company.companyName || 'N/A'}</td>
                    </tr>
                    <tr>
                      <td><strong>VAT Number</strong></td>
                      <td>{company.vatNumber || 'N/A'}</td>
                    </tr>
                    <tr>
                      <td><strong>Registration Number</strong></td>
                      <td>{company.registrationNumber || 'N/A'}</td>
                    </tr>

                    {/* Contact Information */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Contact Information</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Primary Contact</strong></td>
                      <td>{company.primaryContactName || 'N/A'}</td>
                    </tr>
                    <tr>
                      <td><strong>Phone</strong></td>
                      <td>{company.phone || 'N/A'}</td>
                    </tr>
                    <tr>
                      <td><strong>Emergency Phone</strong></td>
                      <td>{company.emergencyPhone || 'N/A'}</td>
                    </tr>
                    <tr>
                      <td><strong>Email</strong></td>
                      <td>{company.email || 'N/A'}</td>
                    </tr>
                    <tr>
                      <td><strong>Website</strong></td>
                      <td>
                        {company.website ? (
                          <a href={company.website} target="_blank" rel="noopener noreferrer">
                            {company.website}
                          </a>
                        ) : 'N/A'}
                      </td>
                    </tr>

                    {/* Address */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Address</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Street & Number</strong></td>
                      <td>
                        {company.street && company.houseNumber ? 
                          `${company.street} ${company.houseNumber}` : 'N/A'}
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Postal Code & City</strong></td>
                      <td>
                        {company.postalCode && company.city ? 
                          `${company.postalCode} ${company.city}` : 'N/A'}
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Country</strong></td>
                      <td>{company.country || 'N/A'}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>

        {/* Business Details Card */}
        <div className="col-md-6 mb-3">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title mb-0">
                <i className="fas fa-briefcase me-2"></i>
                Business Details
              </h3>
            </div>
            <div className="card-body">
              <div className="table-responsive">
                <table className="table table-striped table-hover mb-0" style={{ fontSize: '0.85rem' }}>
                  <tbody>
                    {/* Business Information */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Business Information</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Trades</strong></td>
                      <td>{formatArray(company.trades)}</td>
                    </tr>
                    <tr>
                      <td><strong>Emergency Service</strong></td>
                      <td>
                        {company.isEmergencyServiceProvider ? 
                          <span className="badge bg-success">Yes</span> : 
                          <span className="badge bg-secondary">No</span>
                        }
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Availability Hours</strong></td>
                      <td>{company.availabilityHours || 'N/A'}</td>
                    </tr>

                    {/* Service Area */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Service Area</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Service Postal Codes</strong></td>
                      <td>{formatArray(company.servicePostalCodes)}</td>
                    </tr>
                    <tr>
                      <td><strong>Max Travel Radius</strong></td>
                      <td>{company.maxTravelRadiusKm ? `${company.maxTravelRadiusKm} km` : 'N/A'}</td>
                    </tr>

                    {/* Rates */}
                    <tr className="table-secondary">
                      <td colSpan="2"><strong>Rates</strong></td>
                    </tr>
                    <tr>
                      <td><strong>Standard Hourly Rate</strong></td>
                      <td>{formatCurrency(company.standardHourlyRate)}</td>
                    </tr>
                    <tr>
                      <td><strong>Emergency Hourly Rate</strong></td>
                      <td>{formatCurrency(company.emergencyHourlyRate)}</td>
                    </tr>
                    <tr>
                      <td><strong>Travel Cost per km</strong></td>
                      <td>{formatCurrency(company.travelCostPerKm)}</td>
                    </tr>
                    <tr>
                      <td><strong>Standard Warranty</strong></td>
                      <td>{company.standardWarrantyMonths ? `${company.standardWarrantyMonths} months` : 'N/A'}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>

        {/* Performance & Additional Info */}
        <div className="col-md-12 mb-3">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title mb-0">
                <i className="fas fa-chart-line me-2"></i>
                Performance & Additional Information
              </h3>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-md-6">
                  <div className="table-responsive">
                    <table className="table table-striped table-hover mb-0" style={{ fontSize: '0.85rem' }}>
                      <tbody>
                        <tr className="table-secondary">
                          <td colSpan="2"><strong>Performance Metrics</strong></td>
                        </tr>
                        <tr>
                          <td><strong>Average Rating</strong></td>
                          <td>
                            {company.averageRating ? (
                              <span>
                                {company.averageRating.toFixed(1)} / 5.0
                                <div className="mt-1">
                                  {[...Array(5)].map((_, i) => (
                                    <i
                                      key={i}
                                      className={`fas fa-star ${
                                        i < Math.floor(company.averageRating) ? 'text-warning' : 'text-muted'
                                      }`}
                                    ></i>
                                  ))}
                                </div>
                              </span>
                            ) : 'N/A'}
                          </td>
                        </tr>
                        <tr>
                          <td><strong>Completed Jobs</strong></td>
                          <td>{company.completedJobsCount || 0}</td>
                        </tr>
                        <tr>
                          <td><strong>Cancelled Jobs</strong></td>
                          <td>{company.cancelledJobsCount || 0}</td>
                        </tr>
                        <tr>
                          <td><strong>Emergency Response Time</strong></td>
                          <td>{company.emergencyResponseTimeMinutes ? `${company.emergencyResponseTimeMinutes} minutes` : 'N/A'}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div className="col-md-6">
                  <div className="table-responsive">
                    <table className="table table-striped table-hover mb-0" style={{ fontSize: '0.85rem' }}>
                      <tbody>
                        <tr className="table-secondary">
                          <td colSpan="2"><strong>System Information</strong></td>
                        </tr>
                        <tr>
                          <td><strong>Created At</strong></td>
                          <td>{formatDate(company.createdAt)}</td>
                        </tr>
                        <tr>
                          <td><strong>Updated At</strong></td>
                          <td>{formatDate(company.updatedAt)}</td>
                        </tr>
                        <tr>
                          <td><strong>Created By</strong></td>
                          <td>{company.createdBy || 'N/A'}</td>
                        </tr>
                        <tr>
                          <td><strong>Last Modified By</strong></td>
                          <td>{company.lastModifiedBy || 'N/A'}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Related Data */}
        <div className="col-md-12 mb-3">
          <div className="row">
            {/* Framework Contracts */}
            <div className="col-md-4">
              <div className="card">
                <div className="card-header">
                  <h4 className="card-title mb-0">
                    <i className="fas fa-handshake me-2"></i>
                    Framework Contracts
                  </h4>
                </div>
                <div className="card-body">
                  {company.frameworkContracts && company.frameworkContracts.length > 0 ? (
                    <div>
                      <p className="text-success">
                        <strong>{company.frameworkContracts.length}</strong> active contracts
                      </p>
                      {company.frameworkContracts.slice(0, 3).map((contract, index) => (
                        <div key={index} className="mb-2 p-2 border rounded">
                          <small>
                            <strong>Contract:</strong> {contract.contractNumber || 'N/A'}<br/>
                            <strong>Valid until:</strong> {formatDate(contract.endDate)}
                          </small>
                        </div>
                      ))}
                      {company.frameworkContracts.length > 3 && (
                        <small className="text-muted">
                          And {company.frameworkContracts.length - 3} more...
                        </small>
                      )}
                    </div>
                  ) : (
                    <div className="text-center text-muted py-3">
                      <i className="fas fa-handshake fa-2x mb-2"></i>
                      <p>No framework contracts</p>
                    </div>
                  )}
                </div>
              </div>
            </div>

            {/* Tickets */}
            <div className="col-md-4">
              <div className="card">
                <div className="card-header">
                  <h4 className="card-title mb-0">
                    <i className="fas fa-ticket-alt me-2"></i>
                    Tickets
                  </h4>
                </div>
                <div className="card-body">
                  {company.tickets && company.tickets.length > 0 ? (
                    <div>
                      <p className="text-info">
                        <strong>{company.tickets.length}</strong> tickets assigned
                      </p>
                      {company.tickets.slice(0, 3).map((ticket, index) => (
                        <div key={index} className="mb-2 p-2 border rounded">
                          <small>
                            <strong>Description:</strong> {ticket.description || 'N/A'}<br/>
                            <strong>Status:</strong> 
                            <span className={`badge ms-1 ${
                              ticket.status === 'open' ? 'bg-danger' : 
                              ticket.status === 'in-progress' ? 'bg-warning' : 'bg-success'
                            }`}>
                              {ticket.status || 'N/A'}
                            </span>
                          </small>
                        </div>
                      ))}
                      {company.tickets.length > 3 && (
                        <small className="text-muted">
                          And {company.tickets.length - 3} more...
                        </small>
                      )}
                    </div>
                  ) : (
                    <div className="text-center text-muted py-3">
                      <i className="fas fa-ticket-alt fa-2x mb-2"></i>
                      <p>No tickets assigned</p>
                    </div>
                  )}
                </div>
              </div>
            </div>

            {/* Contact Persons */}
            <div className="col-md-4">
              <div className="card">
                <div className="card-header">
                  <h4 className="card-title mb-0">
                    <i className="fas fa-users me-2"></i>
                    Contact Persons
                  </h4>
                </div>
                <div className="card-body">
                  {company.contactPersons && company.contactPersons.length > 0 ? (
                    <div>
                      <p className="text-primary">
                        <strong>{company.contactPersons.length}</strong> contact persons
                      </p>
                      {company.contactPersons.slice(0, 3).map((contact, index) => (
                        <div key={index} className="mb-2 p-2 border rounded">
                          <small>
                            <strong>Name:</strong> {contact.name || 'N/A'}<br/>
                            <strong>Role:</strong> {contact.role || 'N/A'}<br/>
                            <strong>Phone:</strong> {contact.phone || 'N/A'}
                          </small>
                        </div>
                      ))}
                      {company.contactPersons.length > 3 && (
                        <small className="text-muted">
                          And {company.contactPersons.length - 3} more...
                        </small>
                      )}
                    </div>
                  ) : (
                    <div className="text-center text-muted py-3">
                      <i className="fas fa-users fa-2x mb-2"></i>
                      <p>No contact persons</p>
                    </div>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

CompaniesDetails.propTypes = {
  company: PropTypes.object
};

export default CompaniesDetails;