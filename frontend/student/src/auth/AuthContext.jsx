import React, { createContext, useState } from "react";

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [userRole, setUserRole] = useState(
    localStorage.getItem("role")
  );

  const login = (token, role) => {
    localStorage.setItem("token", token);
    localStorage.setItem("role", role);
    setUserRole(role);
  };

  const logout = () => {
    localStorage.clear();
    setUserRole(null);
  };

  return (
    <AuthContext.Provider value={{ userRole, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
