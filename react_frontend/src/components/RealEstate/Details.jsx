import React from 'react';
import { useParams } from 'react-router-dom';

const RealEstateDetails = () => {
    const { id } = useParams();
    
    return (
        <div>
            <h1>Real Estate Details for {id}</h1>
            <p>Details about the real estate will be displayed here.</p>
        </div>
    );
};

export default RealEstateDetails;