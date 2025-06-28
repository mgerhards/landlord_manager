import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { ENDPOINTS } from '../../config/api';

const RealEstateForm = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { realEstateObject, isEdit } = location.state || { realEstateObject: null, isEdit: false };

  const [formData, setFormData] = useState({
    address: '',
    size: '',
    numberOfRooms: '',
    description: '',
    latitude: '',
    longitude: '',
    propertyType: 'APARTMENT',
    yearBuilt: '',
    floor: '',
    hasElevator: false,
    monthlyMaintenanceCosts: '',
    propertyTax: '',
    heatingType: 'ZENTRALHEIZUNG',
    hasAirConditioning: false,
    hasCellar: false,
    hasBalcony: false,
    hasGarden: false,
    outdoorSpace: '',
    isFurnished: false,
    lastRenovationDate: '',
    nextInspectionDue: ''
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (isEdit && realEstateObject) {
      setFormData({
        address: realEstateObject.address || '',
        size: realEstateObject.size || '',
        numberOfRooms: realEstateObject.numberOfRooms || '',
        description: realEstateObject.description || '',
        latitude: realEstateObject.latitude || '',
        longitude: realEstateObject.longitude || '',
        propertyType: realEstateObject.propertyType || 'APARTMENT',
        yearBuilt: realEstateObject.yearBuilt || '',
        floor: realEstateObject.floor || '',
        hasElevator: realEstateObject.hasElevator || false,
        monthlyMaintenanceCosts: realEstateObject.monthlyMaintenanceCosts || '',
        propertyTax: realEstateObject.propertyTax || '',
        heatingType: realEstateObject.heatingType || 'ZENTRALHEIZUNG',
        hasAirConditioning: realEstateObject.hasAirConditioning || false,
        hasCellar: realEstateObject.hasCellar || false,
        hasBalcony: realEstateObject.hasBalcony || false,
        hasGarden: realEstateObject.hasGarden || false,
        outdoorSpace: realEstateObject.outdoorSpace || '',
        isFurnished: realEstateObject.isFurnished || false,
        lastRenovationDate: realEstateObject.lastRenovationDate || '',
        nextInspectionDue: realEstateObject.nextInspectionDue || ''
      });
    }
  }, [isEdit, realEstateObject]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const url = isEdit 
        ? `${ENDPOINTS.REAL_ESTATE_API}/${realEstateObject.id}`
        : ENDPOINTS.REAL_ESTATE_API;
      
      const method = isEdit ? 'PUT' : 'POST';

      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        navigate('/real-estate', { 
          state: { 
            message: isEdit ? 'Property updated successfully!' : 'Property created successfully!' 
          }
        });
      } else {
        throw new Error(`Failed to ${isEdit ? 'update' : 'create'} property`);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card">
            <div className="card-header">
              <h3>{isEdit ? 'Edit Property' : 'Create New Property'}</h3>
            </div>
            <div className="card-body">
              {error && (
                <div className="alert alert-danger" role="alert">
                  {error}
                </div>
              )}
              
              <form onSubmit={handleSubmit}>
                {/* Basic Information */}
                <div className="row mb-3">
                  <div className="col-12">
                    <h5>Basic Information</h5>
                  </div>
                </div>
                
                <div className="row mb-3">
                  <div className="col-md-12">
                    <label htmlFor="address" className="form-label">Address *</label>
                    <input
                      type="text"
                      className="form-control"
                      id="address"
                      name="address"
                      value={formData.address}
                      onChange={handleChange}
                      required
                    />
                  </div>
                </div>

                <div className="row mb-3">
                  <div className="col-md-4">
                    <label htmlFor="size" className="form-label">Size (m²) *</label>
                    <input
                      type="number"
                      step="0.1"
                      className="form-control"
                      id="size"
                      name="size"
                      value={formData.size}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="col-md-4">
                    <label htmlFor="numberOfRooms" className="form-label">Number of Rooms *</label>
                    <input
                      type="number"
                      className="form-control"
                      id="numberOfRooms"
                      name="numberOfRooms"
                      value={formData.numberOfRooms}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="col-md-4">
                    <label htmlFor="propertyType" className="form-label">Property Type</label>
                    <select
                      className="form-control"
                      id="propertyType"
                      name="propertyType"
                      value={formData.propertyType}
                      onChange={handleChange}
                    >
                      <option value="APARTMENT">Apartment</option>
                      <option value="HOUSE">House</option>
                      <option value="COMMERCIAL">Commercial</option>
                    </select>
                  </div>
                </div>

                <div className="row mb-3">
                  <div className="col-12">
                    <label htmlFor="description" className="form-label">Description</label>
                    <textarea
                      className="form-control"
                      id="description"
                      name="description"
                      rows="3"
                      value={formData.description}
                      onChange={handleChange}
                    />
                  </div>
                </div>

                {/* Property Details */}
                <div className="row mb-3">
                  <div className="col-12">
                    <h5>Property Details</h5>
                  </div>
                </div>

                <div className="row mb-3">
                  <div className="col-md-4">
                    <label htmlFor="yearBuilt" className="form-label">Year Built</label>
                    <input
                      type="number"
                      className="form-control"
                      id="yearBuilt"
                      name="yearBuilt"
                      value={formData.yearBuilt}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="col-md-4">
                    <label htmlFor="floor" className="form-label">Floor</label>
                    <input
                      type="number"
                      className="form-control"
                      id="floor"
                      name="floor"
                      value={formData.floor}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="col-md-4">
                    <label htmlFor="heatingType" className="form-label">Heating Type</label>
                    <select
                      className="form-control"
                      id="heatingType"
                      name="heatingType"
                      value={formData.heatingType}
                      onChange={handleChange}
                    >
                      <option value="ZENTRALHEIZUNG">Central Heating</option>
                      <option value="GAS">Gas</option>
                      <option value="ELECTRIC">Electric</option>
                      <option value="OIL">Oil</option>
                    </select>
                  </div>
                </div>

                {/* Amenities */}
                <div className="row mb-3">
                  <div className="col-12">
                    <h5>Amenities</h5>
                  </div>
                </div>

                <div className="row mb-3">
                  <div className="col-md-4">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="hasElevator"
                        name="hasElevator"
                        checked={formData.hasElevator}
                        onChange={handleChange}
                      />
                      <label className="form-check-label" htmlFor="hasElevator">
                        Has Elevator
                      </label>
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="hasBalcony"
                        name="hasBalcony"
                        checked={formData.hasBalcony}
                        onChange={handleChange}
                      />
                      <label className="form-check-label" htmlFor="hasBalcony">
                        Has Balcony
                      </label>
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="hasGarden"
                        name="hasGarden"
                        checked={formData.hasGarden}
                        onChange={handleChange}
                      />
                      <label className="form-check-label" htmlFor="hasGarden">
                        Has Garden
                      </label>
                    </div>
                  </div>
                </div>

                <div className="row mb-3">
                  <div className="col-md-4">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="hasCellar"
                        name="hasCellar"
                        checked={formData.hasCellar}
                        onChange={handleChange}
                      />
                      <label className="form-check-label" htmlFor="hasCellar">
                        Has Cellar
                      </label>
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="isFurnished"
                        name="isFurnished"
                        checked={formData.isFurnished}
                        onChange={handleChange}
                      />
                      <label className="form-check-label" htmlFor="isFurnished">
                        Is Furnished
                      </label>
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="hasAirConditioning"
                        name="hasAirConditioning"
                        checked={formData.hasAirConditioning}
                        onChange={handleChange}
                      />
                      <label className="form-check-label" htmlFor="hasAirConditioning">
                        Has Air Conditioning
                      </label>
                    </div>
                  </div>
                </div>

                {/* Form Actions */}
                <div className="row">
                  <div className="col-12">
                    <button
                      type="button"
                      className="btn btn-secondary me-2"
                      onClick={() => navigate('/real-estate')}
                      disabled={loading}
                    >
                      Cancel
                    </button>
                    <button
                      type="submit"
                      className="btn btn-primary"
                      disabled={loading}
                    >
                      {loading ? 'Saving...' : (isEdit ? 'Update Property' : 'Create Property')}
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RealEstateForm;