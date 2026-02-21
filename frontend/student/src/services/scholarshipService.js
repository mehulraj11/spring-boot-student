import axiosInstance from "../api/axisConfig";

export const getAllScholarships = async (
  { page = 0, size = 5, sortBy = "title", order = "asc" },
  token
) => {
  return await axiosInstance.get("/scholarship/get-all", {
    params: { page, size, sortBy, order },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};


export const applyScholarship = async(token) =>{
    return await axiosInstance.post()
}