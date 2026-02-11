import React, { createContext, useContext, useEffect, useState } from 'react'

import type { ReactNode } from 'react'

interface AuthState{
    token:string | null;
    role:string| null;
}

interface AuthContextType extends AuthState{
    login:(token:string ) =>void;
    logout:()=>void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({children}:{children:ReactNode}) => {
    const [token,setToken] = useState<string|null>(
        localStorage.getItem("token")
    );
    const [role, setrole] = useState<string | null>(null);
    
    useEffect(()=>{
        if(token){
            const payload = JSON.parse(atob(token.split(".")[1]));
            setrole(payload.role);
        }
    },[token])

    const login = (jwt:string)=>{
        localStorage.setItem("token",jwt);
        setToken(jwt);
    }

    const logout = ()=>{
        localStorage.removeItem("token");
        setToken(null);
        setrole(null);
    }

    return (
    <AuthContext.Provider value={{token,role,login,logout}}>
        {children}
    </AuthContext.Provider>
  )
};



export const useAuth=()=>{
    const context = useContext(AuthContext);
    if(!context){
        throw new Error("useAuth must be used within AuthProvider");
    }
    return context;
}
