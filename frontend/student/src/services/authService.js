import axiosInstance from "../api/axisConfig"

export const registerUser = async (data) => {
  return await axiosInstance.post("/register", data);
};

export const loginUser = async (data) => {
  return await axiosInstance.post("/login", data);
};
