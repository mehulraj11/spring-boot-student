import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";

const ProtectedRoute = ({ children, allowedRoles }) => {
  const { userRole } = useContext(AuthContext);

  if (!userRole) {
    return <Navigate to="/" />;
  }

  if (!allowedRoles.includes(userRole)) {
    return <Navigate to="/" />;
  }

  return children;
};

export default ProtectedRoute;
