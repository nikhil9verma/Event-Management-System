import React, { type ReactNode } from 'react'
import { useAuth } from './features/auth/AuthContext'
import { Navigate } from 'react-router-dom';


interface ProtectedRouteProps {
  children: ReactNode;
  requiredRole?: string;
}

const ProtectedRoute = ({children,requiredRole}:ProtectedRouteProps) => {
  const {token,role} = useAuth();

  if(!token){
    return <Navigate to="/auth/login" replace/>
  }
  if (requiredRole && role !== requiredRole) {
      return <Navigate to="/" replace />;
  }
  return children;
}

export default ProtectedRoute
