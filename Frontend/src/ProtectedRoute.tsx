import React, { type ReactNode } from 'react'
import { useAuth } from './features/auth/AuthContext'
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({children}:{children:ReactNode}) => {
  const {token} = useAuth();

  if(!token){
    return <Navigate to="/login" replace/>
  }

    return children;
}

export default ProtectedRoute
