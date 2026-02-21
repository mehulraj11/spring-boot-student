import React, { useState, useContext } from "react";
import { loginUser } from "../services/authService";
import { AuthContext } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await loginUser(form);
    console.log(response.data.token);
    console.log(response.data.role);

    const token = response.data.token;
    const role = response.data.role;

    login(token, role);

    if (role === "ADMIN") navigate("/admin");
    if (role === "STUDENT") navigate("/student");
    if (role === "TEACHER") navigate("/teacher");
  };

  return (
    <div className="flex justify-center items-center h-screen">
      <form onSubmit={handleSubmit} className="p-6 border rounded w-80">
        <h2 className="text-xl mb-4">Login</h2>

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

        <button className="w-full bg-green-500 text-white p-2">Login</button>
        <button onClick={() => navigate("/signup")}>Signup</button>
      </form>
    </div>
  );
};

export default Login;
