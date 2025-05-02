import React from 'react';

interface HistoryProps {
    messages?: string[];
}

const History: React.FC<HistoryProps> = ({ messages = [] }) => {
    return (
        <div className="history">
            <h2>History</h2>
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>{message}</li>
                ))}
            </ul>
        </div>
    );
};

export { History };