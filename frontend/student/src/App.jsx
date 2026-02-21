import { BrowserRouter, Routes, Route } from "react-router-dom";
import Signup from "./pages/Signup";
import Login from "./pages/Login";
import AdminDashboard from "./pages/AdminDashboard";
import StudentDashboard from "./pages/StudentDashboard";
import TeacherDashboard from "./pages/TeacherDashboard";
import ProtectedRoute from "./auth/ProtectedRoute";
import AuthProvider from "./auth/AuthContext";
import CreateProfile from "./pages/CreateStudentProfile";
import AllScholarships from "./pages/AllScholarships";
import UploadDocuments from "./pages/UploadDocuments";

function App() {  
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/signup" element={<Signup />} />
          <Route path="/" element={<Login />} />

          <Route
            path="/admin"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <AdminDashboard />
              </ProtectedRoute>
            }
          />

          <Route
            path="/student"
            element={
              <ProtectedRoute allowedRoles={["STUDENT"]}>
                <StudentDashboard />
              </ProtectedRoute>
            }
          />

          <Route
            path="/teacher"
            element={
              <ProtectedRoute allowedRoles={["TEACHER"]}>
                <TeacherDashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/create-profile"
            element={
              <ProtectedRoute allowedRoles={["STUDENT"]}>
                <CreateProfile />
              </ProtectedRoute>
            }
          />
          <Route
            path="/scholarships"
            element={
              <ProtectedRoute allowedRoles={["STUDENT", "TEACHER", "ADMIN"]}>
                <AllScholarships />
              </ProtectedRoute>
            }
          />
          <Route
            path="/upload-docs"
            element={
              <ProtectedRoute allowedRoles={["STUDENT"]}>
                <UploadDocuments />
              </ProtectedRoute>
            }
          />

        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
