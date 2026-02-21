import React, { useState } from "react";
import axiosInstance from "../api/axisConfig";

const UploadDocuments = () => {
  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setMessage("");
  };

  const handleUpload = async () => {
    if (!file) {
      setMessage("Please select a file first.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      setLoading(true);

      await axiosInstance.post("/documents/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      setMessage("Document uploaded successfully ✅");
      setFile(null);
    } catch (error) {
      console.error(error);
      setMessage("Upload failed ❌");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center p-6">
      <div className="bg-white shadow-lg rounded-2xl p-8 w-full max-w-md">
        <h2 className="text-2xl font-bold mb-6 text-center">
          Upload Document
        </h2>

        {/* File Input */}
        <div className="mb-4">
          <input
            type="file"
            onChange={handleFileChange}
            className="w-full border p-2 rounded"
          />
        </div>

        {/* Selected File */}
        {file && (
          <p className="text-sm text-gray-600 mb-4">
            Selected: <strong>{file.name}</strong>
          </p>
        )}

        {/* Upload Button */}
        <button
          onClick={handleUpload}
          disabled={loading}
          className="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg transition disabled:opacity-50"
        >
          {loading ? "Uploading..." : "Upload"}
        </button>

        {/* Message */}
        {message && (
          <p className="mt-4 text-center text-sm font-medium">
            {message}
          </p>
        )}
      </div>
    </div>
  );
};

export default UploadDocuments;
