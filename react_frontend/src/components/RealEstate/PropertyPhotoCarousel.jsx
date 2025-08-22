import React from 'react';
import PropTypes from 'prop-types';

const PropertyPhotoCarousel = ({ photos = [] }) => {
  return (
    <div className="card">
      <div className="card-header">
        <h3 className="card-title mb-0">
          <i className="fas fa-images me-2"></i>
          Property Photos
        </h3>
      </div>
      <div className="card-body p-0">
        {photos && photos.length > 0 ? (
          <div id="propertyCarousel" className="carousel slide" data-bs-ride="carousel">
            {/* Carousel Indicators */}
            <div className="carousel-indicators">
              {photos.map((_, index) => (
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
              {photos.map((photo, index) => (
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
  );
};

PropertyPhotoCarousel.propTypes = {
  photos: PropTypes.arrayOf(
    PropTypes.oneOfType([
      PropTypes.string,
      PropTypes.shape({
        url: PropTypes.string,
        title: PropTypes.string,
        description: PropTypes.string
      })
    ])
  )
};

export default PropertyPhotoCarousel;