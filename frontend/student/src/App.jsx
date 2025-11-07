import React, { useState, useEffect } from "react";

const apiBase = "http://localhost:9090";

export default function App() {
  const [students, setStudents] = useState([]);
  const [form, setForm] = useState({
    name: "",
    email: "",
    registrationNo: "",
    course: "",
  });
  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${apiBase}/students`);
      const data = await res.json();
      setStudents(data);
      setError("");
    } catch (err) {
      setError("Failed to fetch students");
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.name || !form.email || !form.registrationNo || !form.course) {
      setError("All fields are required");
      return;
    }
    try {
      setLoading(true);
      if (editingId) {
        await fetch(`${apiBase}/students/${editingId}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(form),
        });
      } else {
        await fetch(`${apiBase}/students`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(form),
        });
      }
      setForm({ name: "", email: "", registrationNo: "", course: "" });
      setEditingId(null);
      setError("");
      fetchStudents();
    } catch {
      setError("Failed to save student");
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (student) => {
    setForm(student);
    setEditingId(student.registrationNo);
    setError("");
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this student?"))
      return;
    setLoading(true);
    try {
      await fetch(`${apiBase}/students/${id}`, { method: "DELETE" });
      fetchStudents();
    } catch {
      setError("Failed to delete student");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-5xl mx-auto p-4">
      <h1 className="text-4xl font-semibold mb-6 text-center text-blue-800">
        Student Management
      </h1>
      <form
        onSubmit={handleSubmit}
        className="bg-white shadow rounded p-6 mb-8 space-y-4"
      >
        {error && <p className="text-red-600 font-medium">{error}</p>}
        <div className="flex flex-col md:flex-row gap-4">
          <input
            type="text"
            name="name"
            placeholder="Name"
            value={form.name}
            onChange={handleChange}
            className="flex-1 border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={form.email}
            onChange={handleChange}
            className="flex-1 border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <div className="flex flex-col md:flex-row gap-4">
          <input
            type="text"
            name="registrationNo"
            placeholder="Registration No"
            value={form.registrationNo}
            onChange={handleChange}
            disabled={!!editingId}
            className={`flex-1 border rounded px-3 py-2 focus:outline-none focus:ring-2 ${
              editingId
                ? "bg-gray-100 cursor-not-allowed"
                : "focus:ring-blue-400"
            }`}
          />
          <input
            type="text"
            name="course"
            placeholder="Course"
            value={form.course}
            onChange={handleChange}
            className="flex-1 border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <button
          type="submit"
          disabled={loading}
          className="bg-blue-600 disabled:bg-blue-300 hover:bg-blue-700 text-white px-6 py-2 rounded transition"
        >
          {editingId ? "Update Student" : "Add Student"}
        </button>
      </form>

      {loading ? (
        <p className="text-center text-gray-500">Loading students...</p>
      ) : (
        <div className="overflow-x-auto">
          <table className="min-w-full bg-white rounded shadow">
            <thead>
              <tr className="bg-blue-100 text-blue-800 font-semibold">
                <th className="p-3 border border-blue-200 text-left">Name</th>
                <th className="p-3 border border-blue-200 text-left">Email</th>
                <th className="p-3 border border-blue-200 text-left">
                  Reg. Number
                </th>
                <th className="p-3 border border-blue-200 text-left">Course</th>
                <th className="p-3 border border-blue-200 text-center">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody>
              {students.length === 0 && (
                <tr>
                  <td colSpan="5" className="text-center text-gray-600 p-6">
                    No students found
                  </td>
                </tr>
              )}
              {students.map((student) => (
                <tr key={student.id} className="hover:bg-blue-50">
                  <td className="p-3 border border-blue-200">{student.name}</td>
                  <td className="p-3 border border-blue-200">
                    {student.email}
                  </td>
                  <td className="p-3 border border-blue-200">
                    {student.registrationNo}
                  </td>
                  <td className="p-3 border border-blue-200">
                    {student.course}
                  </td>
                  <td className="p-3 border border-blue-200 flex justify-center gap-3">
                    <button
                      onClick={() => handleEdit(student.id)}
                      className="text-yellow-600 hover:text-yellow-900 font-semibold"
                      aria-label={`Edit ${student.name}`}
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(student.id)}
                      className="text-red-600 hover:text-red-900 font-semibold"
                      aria-label={`Delete ${student.name}`}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
