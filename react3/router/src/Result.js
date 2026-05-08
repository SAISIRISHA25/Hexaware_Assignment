import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

function Result() {
  const { state } = useLocation();
  const navigate = useNavigate();

  return (
    <div className="page-wrapper">
      <div className="main-card">
        <h2 className="title">Academic Result</h2>
        <div className="result-info">
          <p className="welcome-text">Welcome, <strong>{state?.name}</strong></p>
          <div className="score-box">
            <div className="score-row"><span>Total:</span> <strong>{state?.total}</strong></div>
            <div className="score-row"><span>Average:</span> <strong>{state?.avg}%</strong></div>
          </div>
          <div className="rank-tag">{state?.rank}</div>
        </div>
        <button onClick={() => navigate('/entry')} className="back-btn">Go Back</button>
      </div>
    </div>
  );
}

export default Result;