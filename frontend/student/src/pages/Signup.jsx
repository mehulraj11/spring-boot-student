import React, { useState } from "react";
import { registerUser } from "../services/authService";
import { useNavigate } from "react-router-dom";

const Signup = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
    role: "STUDENT",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await registerUser(form);
    navigate("/login");
  };

  return (
    <div className="flex justify-center items-center h-screen">
      <form onSubmit={handleSubmit} className="p-6 border rounded w-80">
        <h2 className="text-xl mb-4">Signup</h2>

        <input
          type="email"
          name="email"
          placeholder="Email"
          className="w-full mb-2 p-2 border"
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          className="w-full mb-2 p-2 border"
          onChange={handleChange}
        />

        <select
          name="role"
          className="w-full mb-2 p-2 border"
          onChange={handleChange}
        >
          <option value="STUDENT">Student</option>
          <option value="ADMIN">Admin</option>
          <option value="TEACHER">Teacher</option>
        </select>

        <button className="w-full bg-blue-500 text-white p-2">
          Register
        </button>
        <button onClick={()=> navigate("/")}>Login</button>
      </form>
    </div>
  );
};

export default Signup;
