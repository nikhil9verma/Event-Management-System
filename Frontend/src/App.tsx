import { Route, Routes, Navigate } from "react-router-dom";
import MainLayout from "./components/layout/MainLayout";
import Login from "./features/auth/pages/Login";
import ProtectedRoute from "./ProtectedRoute";
import MyRegistrations from "./features/registrations/pages/MyRegistrations"
import MyEvents from "./features/events/pages/MyEvents"
import Events from "./features/events/pages/Events"
import CreateEvent from "./features/events/pages/CreateEvent";
import Register from "./features/auth/pages/Register";
import RoleRequests from "./features/admin/pages/RoleRequests";

function App() {
  return (
    <MainLayout>
      <Routes>
        {/* Public */}
        <Route path="/auth/login" element={<Login />} />

        {/* Redirect root to /events */}
        <Route path="/" element={<Navigate to="/events" />} />

        {/* Protected - All authenticated users */}
        <Route
          path="/events"
          element={
            <ProtectedRoute>
              <Events />
            </ProtectedRoute>
          }
        />
        <Route path="/auth/register" element={<Register />} />

        <Route
          path="/my-registrations"
          element={
            <ProtectedRoute>
              <MyRegistrations />
            </ProtectedRoute>
          }
        />

        {/* HOST Only */}
        <Route
          path="/my-events"
          element={
            <ProtectedRoute requiredRole="HOST">
              <MyEvents />
            </ProtectedRoute>
          }
        />
        <Route
          path="/create-event"
          element={
            <ProtectedRoute requiredRole="HOST">
              <CreateEvent />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/role-requests"
          element={
            <ProtectedRoute requiredRole="SUPER_ADMIN">
              <RoleRequests />
            </ProtectedRoute>
          }
        />

      </Routes>
    </MainLayout>
  );
}

export default App;
