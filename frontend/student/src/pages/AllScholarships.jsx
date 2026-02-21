import React, { useEffect, useState } from "react";
import { getAllScholarships } from "../services/scholarshipService";

const AllScholarships = () => {
  const [scholarships, setScholarships] = useState([]);
  const [loading, setLoading] = useState(true);

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [sortBy, setSortBy] = useState("title");
  const [order, setOrder] = useState("asc");

  const role = localStorage.getItem("role");


  useEffect(() => {
    fetchScholarships();
  }, [page, sortBy, order]);

  const fetchScholarships = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await getAllScholarships(
        { page, size: 5, sortBy, order },
        token
      );

      setScholarships(response.data.content);
      setTotalPages(response.data.totalPages);

    } catch (error) {
      console.error("Error fetching scholarships", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10 text-lg font-semibold">
        Loading scholarships...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-3xl font-bold mb-6">Scholarships</h1>

      {/* Sorting Controls */}
      <div className="flex gap-4 mb-6">
        <select
          value={sortBy}
          onChange={(e) => {
            setSortBy(e.target.value);
            setPage(0); // reset page when sorting changes
          }}
          className="border p-2 rounded"
        >
          <option value="title">Sort by Title</option>
          <option value="amount">Sort by Amount</option>
        </select>

        <select
          value={order}
          onChange={(e) => {
            setOrder(e.target.value);
            setPage(0);
          }}
          className="border p-2 rounded"
        >
          <option value="asc">Ascending</option>
          <option value="desc">Descending</option>
        </select>
      </div>

      {/* Scholarship List */}
    <div className="space-y-4">
  {scholarships.map((s, index) => (
    <div
      key={index}
      className="bg-white p-6 rounded-xl shadow flex flex-col md:flex-row md:justify-between md:items-center gap-4"
    >
      {/* Left Section */}
      <div>
        <h2 className="font-bold text-lg">{s.title}</h2>
        <p>Eligibility: {s.eligibility}</p>
        <p>Amount: ₹{s.amount}</p>
      </div>

      {/* Right Section */}
      <div className="flex flex-col md:items-end gap-3">
        {/* Status Badge */}
        <span
          className={`px-4 py-1 rounded-full text-sm font-semibold ${
            s.active
              ? "bg-green-100 text-green-600"
              : "bg-red-100 text-red-600"
          }`}
        >
          {s.active ? "Active" : "Inactive"}
        </span>

        {/* Apply Button - Only for STUDENT */}
        {role === "STUDENT" && (
          <button
            disabled={!s.active}
            onClick={() => handleApply(s.id)}
            className={`px-6 py-2 rounded-lg text-white transition ${
              s.active
                ? "bg-blue-500 hover:bg-blue-600"
                : "bg-gray-400 cursor-not-allowed"
            }`}
          >
            Apply
          </button>
        )}
      </div>
    </div>
  ))}
</div>


      {/* Pagination Controls */}
      <div className="flex justify-center gap-4 mt-8 items-center">
        <button
          disabled={page === 0}
          onClick={() => setPage(page - 1)}
          className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
        >
          Prev
        </button>

        <span className="font-medium">
          Page {page + 1} of {totalPages}
        </span>

        <button
          disabled={page + 1 >= totalPages}
          onClick={() => setPage(page + 1)}
          className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default AllScholarships;
