import { useState, useEffect } from 'react';
import { apiCall } from '../config/auth';
import { ENDPOINTS } from '../config/api';

const Home = () => {
  const [dashboardData, setDashboardData] = useState({
    totalRevenue: 12500,
    totalCosts: 3200,
    overdueTickets: 5,
    missingRents: 2
  });

  // Dummy data for charts - in real implementation, this would come from API
  const revenueData = [
    { month: 'Jan', revenue: 10500, costs: 2100 },
    { month: 'Feb', revenue: 11200, costs: 2800 },
    { month: 'Mar', revenue: 12500, costs: 3200 },
    { month: 'Apr', revenue: 11800, costs: 2900 },
    { month: 'May', revenue: 13100, costs: 3500 },
    { month: 'Jun', revenue: 12800, costs: 3100 }
  ];

  useEffect(() => {
    // In a real implementation, we would fetch dashboard data from API
    // For now, we'll use dummy data
    const fetchDashboardData = async () => {
      try {
        // Example API call structure - would need actual endpoint
        // const response = await apiCall(`${ENDPOINTS.DASHBOARD}`, {
        //   method: 'GET'
        // });
        // if (response.ok) {
        //   const data = await response.json();
        //   setDashboardData(data);
        // }
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
      }
    };

    fetchDashboardData();
  }, []);

  const ChartPlaceholder = ({ title, data }) => {
    const maxValue = Math.max(...data.map(d => Math.max(d.revenue, d.costs)));
    
    return (
      <div className="chart-container">
        <h5 className="text-center mb-3">{title}</h5>
        <div className="row">
          {data.map((item, index) => (
            <div key={index} className="col-2">
              <div className="text-center mb-2">
                <small className="text-muted">{item.month}</small>
              </div>
              <div className="chart-bar-container" style={{ height: '120px', position: 'relative' }}>
                <div 
                  className="chart-bar revenue-bar"
                  style={{
                    height: `${(item.revenue / maxValue) * 100}%`,
                    backgroundColor: '#28a745',
                    width: '45%',
                    position: 'absolute',
                    bottom: '0',
                    left: '5%',
                    borderRadius: '2px 2px 0 0'
                  }}
                  title={`Revenue: €${item.revenue.toLocaleString()}`}
                ></div>
                <div 
                  className="chart-bar costs-bar"
                  style={{
                    height: `${(item.costs / maxValue) * 100}%`,
                    backgroundColor: '#dc3545',
                    width: '45%',
                    position: 'absolute',
                    bottom: '0',
                    right: '5%',
                    borderRadius: '2px 2px 0 0'
                  }}
                  title={`Costs: €${item.costs.toLocaleString()}`}
                ></div>
              </div>
              <div className="text-center mt-1">
                <div style={{ fontSize: '10px' }}>
                  <span className="text-success">●</span> Rev
                  <span className="text-danger ml-1">●</span> Costs
                </div>
              </div>
            </div>
          ))}
        </div>
        <div className="row mt-3">
          <div className="col-6 text-center">
            <span className="badge badge-success">Revenue Legend</span>
          </div>
          <div className="col-6 text-center">
            <span className="badge badge-danger">Costs Legend</span>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="container-fluid">
      {/* Page Header */}
      <div className="row mb-4">
        <div className="col-12">
          <h1>Dashboard Overview</h1>
          <p className="text-muted">Welcome to your property management dashboard</p>
        </div>
      </div>

      {/* Top Third - Chart Area */}
      <div className="row mb-4">
        <div className="col-12">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title">
                <i className="fas fa-chart-bar mr-2"></i>
                Financial Overview - Revenue vs Costs
              </h3>
            </div>
            <div className="card-body">
              <ChartPlaceholder 
                title="Monthly Revenue from Rents vs Maintenance & Repair Costs" 
                data={revenueData} 
              />
            </div>
          </div>
        </div>
      </div>

      {/* Bottom Third - KPIs and Alerts */}
      <div className="row">
        {/* KPIs Cards */}
        <div className="col-md-3 col-sm-6 col-12">
          <div className="info-box">
            <span className="info-box-icon bg-success">
              <i className="fas fa-euro-sign"></i>
            </span>
            <div className="info-box-content">
              <span className="info-box-text">Total Revenue</span>
              <span className="info-box-number">€{dashboardData.totalRevenue.toLocaleString()}</span>
              <div className="progress">
                <div className="progress-bar bg-success" style={{ width: '80%' }}></div>
              </div>
              <span className="progress-description">
                80% of target
              </span>
            </div>
          </div>
        </div>

        <div className="col-md-3 col-sm-6 col-12">
          <div className="info-box">
            <span className="info-box-icon bg-warning">
              <i className="fas fa-tools"></i>
            </span>
            <div className="info-box-content">
              <span className="info-box-text">Total Costs</span>
              <span className="info-box-number">€{dashboardData.totalCosts.toLocaleString()}</span>
              <div className="progress">
                <div className="progress-bar bg-warning" style={{ width: '60%' }}></div>
              </div>
              <span className="progress-description">
                60% of budget
              </span>
            </div>
          </div>
        </div>

        <div className="col-md-3 col-sm-6 col-12">
          <div className="info-box">
            <span className="info-box-icon bg-danger">
              <i className="fas fa-exclamation-triangle"></i>
            </span>
            <div className="info-box-content">
              <span className="info-box-text">Overdue Tickets</span>
              <span className="info-box-number">{dashboardData.overdueTickets}</span>
              <div className="progress">
                <div className="progress-bar bg-danger" style={{ width: '70%' }}></div>
              </div>
              <span className="progress-description">
                Require attention
              </span>
            </div>
          </div>
        </div>

        <div className="col-md-3 col-sm-6 col-12">
          <div className="info-box">
            <span className="info-box-icon bg-info">
              <i className="fas fa-home"></i>
            </span>
            <div className="info-box-content">
              <span className="info-box-text">Missing Rents</span>
              <span className="info-box-number">{dashboardData.missingRents}</span>
              <div className="progress">
                <div className="progress-bar bg-info" style={{ width: '30%' }}></div>
              </div>
              <span className="progress-description">
                Properties affected
              </span>
            </div>
          </div>
        </div>
      </div>

      {/* Alerts Section */}
      <div className="row mt-4">
        <div className="col-md-6">
          <div className="card card-warning">
            <div className="card-header">
              <h3 className="card-title">
                <i className="fas fa-exclamation-triangle mr-2"></i>
                Overdue Tickets
              </h3>
            </div>
            <div className="card-body">
              <div className="alert alert-warning">
                <h5><i className="icon fas fa-exclamation-triangle"></i> Attention!</h5>
                You have <strong>{dashboardData.overdueTickets}</strong> tickets that are overdue and require immediate attention.
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <i className="fas fa-wrench text-warning mr-2"></i>
                  Heating system repair - Apartment 3B (5 days overdue)
                </li>
                <li className="list-group-item">
                  <i className="fas fa-faucet text-danger mr-2"></i>
                  Plumbing issue - Apartment 1A (3 days overdue)
                </li>
                <li className="list-group-item">
                  <i className="fas fa-lightbulb text-warning mr-2"></i>
                  Electrical repair - Building 2 (2 days overdue)
                </li>
                <li className="list-group-item text-muted">
                  <i className="fas fa-ellipsis-h mr-2"></i>
                  +{dashboardData.overdueTickets - 3} more tickets...
                </li>
              </ul>
            </div>
          </div>
        </div>

        <div className="col-md-6">
          <div className="card card-danger">
            <div className="card-header">
              <h3 className="card-title">
                <i className="fas fa-home mr-2"></i>
                Missing Rents
              </h3>
            </div>
            <div className="card-body">
              <div className="alert alert-danger">
                <h5><i className="icon fas fa-home"></i> Payment Alert!</h5>
                <strong>{dashboardData.missingRents}</strong> properties have missing rent payments that need follow-up.
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <i className="fas fa-calendar-times text-danger mr-2"></i>
                  Jane Smith - Apartment 2C (15 days overdue)
                </li>
                <li className="list-group-item">
                  <i className="fas fa-calendar-times text-warning mr-2"></i>
                  Michael Brown - Apartment 1B (7 days overdue)
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;