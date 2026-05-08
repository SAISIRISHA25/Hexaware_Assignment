import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function MarkEntry() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    rollNo: '', name: '', s1: '', s2: '', s3: '', s4: '', s5: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const marks = [formData.s1, formData.s2, formData.s3, formData.s4, formData.s5].map(Number);
    const total = marks.reduce((acc, curr) => acc + curr, 0);
    const avg = total / 5;
    const rank = avg >= 90 ? "Distinction" : avg >= 60 ? "First Class" : "Fail";

    navigate('/result', { state: { ...formData, total, avg, rank } });
  };

  return (
    <div className="page-wrapper">
      <div className="main-card">
        <h2 className="title">Student Portal</h2>
        <form onSubmit={handleSubmit} className="entry-form">
          <input name="rollNo" placeholder="Roll Number" onChange={handleChange} required />
          <input name="name" placeholder="Student Name" onChange={handleChange} required />
          <div className="marks-grid">
            <input name="s1" type="number" placeholder="Sub 1" onChange={handleChange} required />
            <input name="s2" type="number" placeholder="Sub 2" onChange={handleChange} required />
            <input name="s3" type="number" placeholder="Sub 3" onChange={handleChange} required />
            <input name="s4" type="number" placeholder="Sub 4" onChange={handleChange} required />
            <input name="s5" type="number" placeholder="Sub 5" onChange={handleChange} required />
          </div>
          <button type="submit" className="submit-btn">Generate Report</button>
        </form>
      </div>
    </div>
  );
}

export default MarkEntry;