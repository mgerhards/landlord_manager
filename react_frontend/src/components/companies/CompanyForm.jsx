import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ENDPOINTS } from '../../config/api';
import { apiCall } from '../../config/auth';

const CompanyForm = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [contactPersons, setContactPersons] = useState([]);
    const [showContactPersonModal, setShowContactPersonModal] = useState(false);
    const [contactPersonForm, setContactPersonForm] = useState({
        name: '',
        phoneNumber: '',
        role: 'CRAFTSMAN'
    });
    
    const [formData, setFormData] = useState({
        // Basic Company Info
        companyName: '',
        vatNumber: '',
        registrationNumber: '',
        
        // Contact Info
        primaryContactName: '',
        phone: '',
        emergencyPhone: '',
        email: '',
        website: '',
        
        // Address
        street: '',
        houseNumber: '',
        postalCode: '',
        city: '',
        country: 'Deutschland',
        
        // Business Details
        trades: [],
        isEmergencyServiceProvider: false,
        availabilityHours: '',
        
        // Service Area
        servicePostalCodes: [],
        maxTravelRadiusKm: '',
        
        // Company Rates
        standardHourlyRate: '',
        emergencyHourlyRate: '',
        travelCostPerKm: '',
        standardWarrantyMonths: ''
    });

    const tradeTypes = [
        { value: 'PLUMBING', label: 'Sanitär' },
        { value: 'ELECTRICAL', label: 'Elektrik' },
        { value: 'HEATING', label: 'Heizung' },
        { value: 'CARPENTRY', label: 'Tischlerei' },
        { value: 'PAINTING', label: 'Malerei' },
        { value: 'ROOFING', label: 'Dachdecker' },
        { value: 'MASONRY', label: 'Maurer' },
        { value: 'LOCKSMITH', label: 'Schlüsseldienst' },
        { value: 'GARDENING', label: 'Gartenbau' },
        { value: 'CLEANING', label: 'Reinigung' },
        { value: 'HVAC', label: 'Klimaanlage' },
        { value: 'FLOORING', label: 'Bodenleger' },
        { value: 'GLAZIER', label: 'Glaser' },
        { value: 'PEST_CONTROL', label: 'Schädlingsbekämpfung' }
    ];

    const handleInputChange = (e) => {
        const { name, value, type, checked } = e.target;
        
        if (type === 'checkbox') {
            setFormData(prev => ({
                ...prev,
                [name]: checked
            }));
        } else if (name === 'trades') {
            const currentTrades = formData.trades || [];
            if (checked) {
                setFormData(prev => ({
                    ...prev,
                    trades: [...currentTrades, value]
                }));
            } else {
                setFormData(prev => ({
                    ...prev,
                    trades: currentTrades.filter(trade => trade !== value)
                }));
            }
        } else {
            setFormData(prev => ({
                ...prev,
                [name]: value
            }));
        }
    };

    const handleContactPersonInputChange = (e) => {
        const { name, value } = e.target;
        setContactPersonForm(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleAddContactPerson = () => {
        if (contactPersonForm.name.trim() && contactPersonForm.phoneNumber.trim()) {
            setContactPersons(prev => [...prev, { ...contactPersonForm, id: Date.now() }]);
            setContactPersonForm({
                name: '',
                phoneNumber: '',
                role: 'CRAFTSMAN'
            });
            setShowContactPersonModal(false);
        }
    };

    const handleRemoveContactPerson = (id) => {
        setContactPersons(prev => prev.filter(person => person.id !== id));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            // Prepare company data
            const companyData = {
                ...formData,
                // Convert string numbers to actual numbers where needed
                maxTravelRadiusKm: formData.maxTravelRadiusKm ? parseInt(formData.maxTravelRadiusKm) : null,
                standardHourlyRate: formData.standardHourlyRate ? parseFloat(formData.standardHourlyRate) : null,
                emergencyHourlyRate: formData.emergencyHourlyRate ? parseFloat(formData.emergencyHourlyRate) : null,
                travelCostPerKm: formData.travelCostPerKm ? parseFloat(formData.travelCostPerKm) : null,
                standardWarrantyMonths: formData.standardWarrantyMonths ? parseInt(formData.standardWarrantyMonths) : null,
                // Convert servicePostalCodes from string to array if needed
                servicePostalCodes: formData.servicePostalCodes.length > 0 ? 
                    (typeof formData.servicePostalCodes === 'string' ? 
                        formData.servicePostalCodes.split(',').map(code => code.trim()) : 
                        formData.servicePostalCodes) : []
            };

            // Create the company
            const response = await apiCall(ENDPOINTS.COMPANIES, {
                method: 'POST',
                body: JSON.stringify(companyData)
            }, navigate);

            if (response.ok) {
                const createdCompany = await response.json();
                
                // Create contact persons if any
                if (contactPersons.length > 0) {
                    for (const contactPerson of contactPersons) {
                        await apiCall(`${ENDPOINTS.COMPANIES}/${createdCompany.id}/contact-persons`, {
                            method: 'POST',
                            body: JSON.stringify({
                                name: contactPerson.name,
                                phoneNumber: contactPerson.phoneNumber,
                                role: contactPerson.role
                            })
                        }, navigate);
                    }
                }
                
                // Success - redirect to companies overview
                navigate('/companies');
            } else {
                const errorText = await response.text();
                setError(`Fehler beim Erstellen der Firma: ${errorText || 'Unbekannter Fehler'}`);
            }
        } catch (error) {
            console.error('Error creating company:', error);
            setError('Fehler beim Erstellen der Firma. Bitte versuchen Sie es erneut.');
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = () => {
        navigate('/companies');
    };

    return (
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-md-10">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Neue Firma erstellen</h3>
                        </div>
                        <form onSubmit={handleSubmit}>
                            <div className="card-body">
                                {error && (
                                    <div className="alert alert-danger">
                                        {error}
                                    </div>
                                )}
                                
                                {/* Basic Company Information */}
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <h5>Grundinformationen</h5>
                                    </div>
                                </div>
                                
                                <div className="row mb-3">
                                    <div className="col-md-6">
                                        <label htmlFor="companyName" className="form-label">
                                            Firmenname <span className="text-danger">*</span>
                                        </label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="companyName"
                                            name="companyName"
                                            value={formData.companyName}
                                            onChange={handleInputChange}
                                            required
                                        />
                                    </div>
                                    <div className="col-md-3">
                                        <label htmlFor="vatNumber" className="form-label">USt-IdNr.</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="vatNumber"
                                            name="vatNumber"
                                            value={formData.vatNumber}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                    <div className="col-md-3">
                                        <label htmlFor="registrationNumber" className="form-label">Handelsregisternummer</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="registrationNumber"
                                            name="registrationNumber"
                                            value={formData.registrationNumber}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                </div>

                                {/* Contact Information */}
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <h5>Kontaktinformationen</h5>
                                    </div>
                                </div>
                                
                                <div className="row mb-3">
                                    <div className="col-md-4">
                                        <label htmlFor="primaryContactName" className="form-label">Hauptansprechpartner</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="primaryContactName"
                                            name="primaryContactName"
                                            value={formData.primaryContactName}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                    <div className="col-md-4">
                                        <label htmlFor="phone" className="form-label">Telefon</label>
                                        <input
                                            type="tel"
                                            className="form-control"
                                            id="phone"
                                            name="phone"
                                            value={formData.phone}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                    <div className="col-md-4">
                                        <label htmlFor="emergencyPhone" className="form-label">Notfalltelefon</label>
                                        <input
                                            type="tel"
                                            className="form-control"
                                            id="emergencyPhone"
                                            name="emergencyPhone"
                                            value={formData.emergencyPhone}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                </div>
                                
                                <div className="row mb-3">
                                    <div className="col-md-6">
                                        <label htmlFor="email" className="form-label">E-Mail</label>
                                        <input
                                            type="email"
                                            className="form-control"
                                            id="email"
                                            name="email"
                                            value={formData.email}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <label htmlFor="website" className="form-label">Website</label>
                                        <input
                                            type="url"
                                            className="form-control"
                                            id="website"
                                            name="website"
                                            value={formData.website}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                </div>

                                {/* Address */}
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <h5>Adresse</h5>
                                    </div>
                                </div>
                                
                                <div className="row mb-3">
                                    <div className="col-md-6">
                                        <label htmlFor="street" className="form-label">Straße</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="street"
                                            name="street"
                                            value={formData.street}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                    <div className="col-md-2">
                                        <label htmlFor="houseNumber" className="form-label">Nr.</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="houseNumber"
                                            name="houseNumber"
                                            value={formData.houseNumber}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                    <div className="col-md-2">
                                        <label htmlFor="postalCode" className="form-label">PLZ</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="postalCode"
                                            name="postalCode"
                                            value={formData.postalCode}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                    <div className="col-md-2">
                                        <label htmlFor="city" className="form-label">Stadt</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="city"
                                            name="city"
                                            value={formData.city}
                                            onChange={handleInputChange}
                                        />
                                    </div>
                                </div>

                                {/* Business Details */}
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <h5>Geschäftsdetails</h5>
                                    </div>
                                </div>
                                
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <label className="form-label">Gewerke</label>
                                        <div className="row">
                                            {tradeTypes.map((trade) => (
                                                <div key={trade.value} className="col-md-3 mb-2">
                                                    <div className="form-check">
                                                        <input
                                                            className="form-check-input"
                                                            type="checkbox"
                                                            id={`trade-${trade.value}`}
                                                            name="trades"
                                                            value={trade.value}
                                                            checked={formData.trades.includes(trade.value)}
                                                            onChange={handleInputChange}
                                                        />
                                                        <label className="form-check-label" htmlFor={`trade-${trade.value}`}>
                                                            {trade.label}
                                                        </label>
                                                    </div>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                </div>
                                
                                <div className="row mb-3">
                                    <div className="col-md-4">
                                        <div className="form-check">
                                            <input
                                                className="form-check-input"
                                                type="checkbox"
                                                id="isEmergencyServiceProvider"
                                                name="isEmergencyServiceProvider"
                                                checked={formData.isEmergencyServiceProvider}
                                                onChange={handleInputChange}
                                            />
                                            <label className="form-check-label" htmlFor="isEmergencyServiceProvider">
                                                Notdienst verfügbar
                                            </label>
                                        </div>
                                    </div>
                                    <div className="col-md-8">
                                        <label htmlFor="availabilityHours" className="form-label">Verfügbarkeitszeiten</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="availabilityHours"
                                            name="availabilityHours"
                                            value={formData.availabilityHours}
                                            onChange={handleInputChange}
                                            placeholder="z.B. Mo-Fr: 8-17 Uhr, Sa: 9-14 Uhr"
                                        />
                                    </div>
                                </div>

                                {/* Contact Persons */}
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <div className="d-flex justify-content-between align-items-center">
                                            <h5>Ansprechpartner</h5>
                                            <button
                                                type="button"
                                                className="btn btn-secondary btn-sm"
                                                onClick={() => setShowContactPersonModal(true)}
                                            >
                                                <i className="bi bi-plus"></i> Ansprechpartner hinzufügen
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                
                                {contactPersons.length > 0 && (
                                    <div className="row mb-3">
                                        <div className="col-12">
                                            <div className="table-responsive">
                                                <table className="table table-sm">
                                                    <thead>
                                                        <tr>
                                                            <th>Name</th>
                                                            <th>Telefon</th>
                                                            <th>Rolle</th>
                                                            <th>Aktionen</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {contactPersons.map((person) => (
                                                            <tr key={person.id}>
                                                                <td>{person.name}</td>
                                                                <td>{person.phoneNumber}</td>
                                                                <td>{person.role === 'CRAFTSMAN' ? 'Handwerker' : 'Verwaltungspersonal'}</td>
                                                                <td>
                                                                    <button
                                                                        type="button"
                                                                        className="btn btn-danger btn-sm"
                                                                        onClick={() => handleRemoveContactPerson(person.id)}
                                                                    >
                                                                        <i className="bi bi-trash"></i>
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        ))}
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                )}
                            </div>
                            
                            <div className="card-footer">
                                <button 
                                    type="submit" 
                                    className="btn btn-primary"
                                    disabled={loading}
                                >
                                    {loading ? 'Erstelle...' : 'Firma erstellen'}
                                </button>
                                <button 
                                    type="button" 
                                    className="btn btn-secondary ms-2"
                                    onClick={handleCancel}
                                    disabled={loading}
                                >
                                    Abbrechen
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            
            {/* Contact Person Modal */}
            {showContactPersonModal && (
                <div className="modal d-block" tabIndex="-1" style={{backgroundColor: 'rgba(0,0,0,0.5)'}}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">Ansprechpartner hinzufügen</h5>
                                <button 
                                    type="button" 
                                    className="btn-close"
                                    onClick={() => setShowContactPersonModal(false)}
                                ></button>
                            </div>
                            <div className="modal-body">
                                <div className="mb-3">
                                    <label htmlFor="contactPersonName" className="form-label">
                                        Name <span className="text-danger">*</span>
                                    </label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="contactPersonName"
                                        name="name"
                                        value={contactPersonForm.name}
                                        onChange={handleContactPersonInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="contactPersonPhone" className="form-label">
                                        Telefonnummer <span className="text-danger">*</span>
                                    </label>
                                    <input
                                        type="tel"
                                        className="form-control"
                                        id="contactPersonPhone"
                                        name="phoneNumber"
                                        value={contactPersonForm.phoneNumber}
                                        onChange={handleContactPersonInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="contactPersonRole" className="form-label">Rolle</label>
                                    <select
                                        className="form-control"
                                        id="contactPersonRole"
                                        name="role"
                                        value={contactPersonForm.role}
                                        onChange={handleContactPersonInputChange}
                                    >
                                        <option value="CRAFTSMAN">Handwerker</option>
                                        <option value="ADMINISTRATIVE_PERSONNEL">Verwaltungspersonal</option>
                                    </select>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button 
                                    type="button" 
                                    className="btn btn-secondary"
                                    onClick={() => setShowContactPersonModal(false)}
                                >
                                    Abbrechen
                                </button>
                                <button 
                                    type="button" 
                                    className="btn btn-primary"
                                    onClick={handleAddContactPerson}
                                    disabled={!contactPersonForm.name.trim() || !contactPersonForm.phoneNumber.trim()}
                                >
                                    Hinzufügen
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default CompanyForm;