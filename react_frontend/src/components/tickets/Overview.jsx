import React, { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../config/api';

const TicketsOverview = () => {
    const [tickets, setTickets] = useState([]);

    useEffect(() => {
        fetch(ENDPOINTS.TICKETS, {
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        });
    }); 

    return (
        <div className="container">
            <h2>Tickets</h2>
            <div className="table-responsive">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Ticket ID</th>
                            <th>Status</th>
                            <th>Created At</th>
                        </tr>
                    </thead>
                    <tbody> 
                        {tickets.map((ticket) => (
                            <tr key={ticket.id}>
                                <td>{ticket.id}</td>
                                <td>{ticket.status}</td>
                                <td>{ticket.createdAt}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                </div>
        </div>
    );
};

export default TicketsOverview; 
