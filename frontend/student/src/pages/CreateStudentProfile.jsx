import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const CreateProfile = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    dob: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!formData.name || !formData.dob) {
      setError("All fields are required");
      return;
    }

    try {
      setLoading(true);

      await axios.post(
        "http://localhost:8080/api/student/profile",
        formData,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      alert("Profile created successfully!");
      navigate("/dashboard");
    } catch (err) {
      setError("Failed to create profile");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 p-6">
      <div className="bg-white shadow-lg rounded-2xl w-full max-w-md p-8">
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
          Create Student Profile
        </h2>

        {error && (
          <div className="bg-red-100 text-red-600 p-3 rounded mb-4 text-sm">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-5">
          
          {/* Name */}
          <div>
            <label className="block text-sm text-gray-600 mb-1">
              Full Name
            </label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Enter your full name"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* DOB */}
          <div>
            <label className="block text-sm text-gray-600 mb-1">
              Date of Birth
            </label>
            <input
              type="date"
              name="dob"
              value={formData.dob}
              onChange={handleChange}
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Submit */}
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-500 hover:bg-blue-600 text-white py-3 rounded-lg transition font-semibold"
          >
            {loading ? "Creating..." : "Create Profile"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateProfile;
