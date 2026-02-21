import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { FaUserPlus, FaBookOpen, FaUpload } from "react-icons/fa";

const StudentDashboard = () => {
  const navigate = useNavigate();

  const [stats, setStats] = useState({
    totalApplied: 0,
    verified: 0,
    rejected: 0,
  });

  useEffect(() => {
    fetchDashboardStats();
  }, []);

  const handleLogout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  navigate("/");
};

  const fetchDashboardStats = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/student/dashboard",
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      setStats(response.data);
    } catch (error) {
      console.error("Error fetching dashboard stats", error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-8">
     <div className="flex justify-between items-center mb-8">
  <h1 className="text-3xl font-bold text-gray-800">
    Student Dashboard
  </h1>

  <button
    onClick={handleLogout}
    className="bg-red-500 hover:bg-red-600 text-white px-5 py-2 rounded-lg shadow transition"
  >
    Logout
  </button>
</div>



      {/* Stats Section */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
        
        
        <div className="bg-white shadow-lg rounded-2xl p-6 border-l-4 border-blue-500">
          <h2 className="text-gray-500 text-sm">Total Applied</h2>
          <p className="text-3xl font-bold text-blue-600 mt-2">
            {stats.totalApplied}
          </p>
        </div>

        <div className="bg-white shadow-lg rounded-2xl p-6 border-l-4 border-green-500">
          <h2 className="text-gray-500 text-sm">Verified</h2>
          <p className="text-3xl font-bold text-green-600 mt-2">
            {stats.verified}
          </p>
        </div>

        <div className="bg-white shadow-lg rounded-2xl p-6 border-l-4 border-red-500">
          <h2 className="text-gray-500 text-sm">Rejected</h2>
          <p className="text-3xl font-bold text-red-600 mt-2">
            {stats.rejected}
          </p>
        </div>

      </div>

      {/* Action Buttons Section */}
      <div className="bg-white shadow-md rounded-2xl p-6">
        <h2 className="text-xl font-semibold mb-6 text-gray-700">
          Quick Actions
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          
          {/* Create Profile */}
          <button
            onClick={() => navigate("/create-profile")}
            className="flex items-center justify-center gap-3 bg-blue-500 hover:bg-blue-600 text-white py-4 rounded-xl shadow-md transition"
          >
            <FaUserPlus size={18} />
            Create Profile
          </button>

          {/* See All Scholarships */}
          <button
            onClick={() => navigate("/scholarships")}
            className="flex items-center justify-center gap-3 bg-green-500 hover:bg-green-600 text-white py-4 rounded-xl shadow-md transition"
          >
            <FaBookOpen size={18} />
            See All Scholarships
          </button>

          {/* Upload Documents */}
          <button
            onClick={() => navigate("/upload-docs")}
            className="flex items-center justify-center gap-3 bg-purple-500 hover:bg-purple-600 text-white py-4 rounded-xl shadow-md transition"
          >
            <FaUpload size={18} />
            Upload Documents
          </button>

        </div>
      </div>
    </div>
  );
};

export default StudentDashboard;
