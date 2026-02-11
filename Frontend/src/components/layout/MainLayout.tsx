import type {ReactNode } from "react";
import Navbar from "./Navbar";

interface MainLayoutProps{
    children:ReactNode;
}

const MainLayout = ({ children}:MainLayoutProps) => {
  return (
    <div className="min-h-screen bg-slate-950 text-white">
      <Navbar />
      <main className="max-w-7xl mx-auto px-6 py-10">
        {children}
      </main>
    </div>
  );
};

export default MainLayout;