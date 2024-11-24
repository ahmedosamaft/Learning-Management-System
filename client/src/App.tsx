import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Login } from '@/pages/auth/Login';
import { Register } from '@/pages/auth/Register';
import { AuthGuard } from '@/components/AuthGuard';
import { Sidebar } from '@/components/layout/Sidebar';
import { Header } from '@/components/layout/Header';
import { useAuthStore } from '@/store/auth';
import { useWebSocket } from './hooks/useWebSocket';

function DashboardLayout({ children }: { children: React.ReactNode }) {
  const userId = "user123";
  useWebSocket(userId);
  return (
    <div className="flex h-screen bg-gray-100">
      <Sidebar />
      <div className="flex flex-1 flex-col overflow-hidden">
        <Header />
        <main className="flex-1 overflow-auto p-6">
          {children}
        </main>
      </div>
    </div>
  );
}

function Dashboard() {
  const user = useAuthStore((state) => state.user);
  
  return (
    <div className="space-y-6">
      <div className="rounded-lg bg-white p-6 shadow">
        <h1 className="text-2xl font-semibold text-gray-900">
          Welcome back, {user?.name}!
        </h1>
        <p className="mt-2 text-gray-600">
          Here's what's happening with your courses today.
        </p>
      </div>
      
      {/* Add dashboard content here */}
    </div>
  );
}

function App() {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated);

  return (
    <Router>
      <Routes>
        <Route path="/login" element={
          isAuthenticated ? <Navigate to="/dashboard" replace /> : <Login />
        } />
        <Route path="/register" element={
          isAuthenticated ? <Navigate to="/dashboard" replace /> : <Register />
        } />
        
        <Route path="/" element={
          <AuthGuard>
            <DashboardLayout>
              <Dashboard />
            </DashboardLayout>
          </AuthGuard>
        } />
        
        <Route path="/dashboard" element={
          <AuthGuard>
            <DashboardLayout>
              <Dashboard />
            </DashboardLayout>
          </AuthGuard>
        } />
        
        {/* Add more protected routes here */}
        
        <Route path="*" element={<Navigate to="/dashboard" replace />} />
      </Routes>
    </Router>
  );
}

export default App;