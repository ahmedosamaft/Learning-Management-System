import React from 'react';
import { NavLink } from 'react-router-dom';
import { 
  BookOpen, 
  GraduationCap, 
  Users, 
  Calendar,
  FileText,
  Bell,
  Settings,
  BarChart
} from 'lucide-react';
import { useAuthStore } from '@/store/auth';
import { cn } from '@/lib/utils';

const navigation = {
  admin: [
    { name: 'Dashboard', href: '/dashboard', icon: BarChart },
    { name: 'Users', href: '/users', icon: Users },
    { name: 'Courses', href: '/courses', icon: BookOpen },
    { name: 'Settings', href: '/settings', icon: Settings },
  ],
  instructor: [
    { name: 'Dashboard', href: '/dashboard', icon: BarChart },
    { name: 'My Courses', href: '/courses', icon: BookOpen },
    { name: 'Assignments', href: '/assignments', icon: FileText },
    { name: 'Calendar', href: '/calendar', icon: Calendar },
  ],
  student: [
    { name: 'Dashboard', href: '/dashboard', icon: BarChart },
    { name: 'My Courses', href: '/courses', icon: BookOpen },
    { name: 'Assignments', href: '/assignments', icon: FileText },
    { name: 'Grades', href: '/grades', icon: GraduationCap },
  ],
};

export function Sidebar() {
  const { user } = useAuthStore();
  const role = user?.role || 'student';

  return (
    <div className="flex h-full w-64 flex-col bg-gray-900">
      <div className="flex h-16 items-center gap-2 px-4">
        <GraduationCap className="h-8 w-8 text-blue-500" />
        <span className="text-xl font-bold text-white">EduLMS</span>
      </div>
      
      <nav className="flex-1 space-y-1 px-2 py-4">
        {navigation[role].map((item) => (
          <NavLink
            key={item.name}
            to={item.href}
            className={({ isActive }) =>
              cn(
                'group flex items-center rounded-md px-2 py-2 text-sm font-medium',
                isActive
                  ? 'bg-gray-800 text-white'
                  : 'text-gray-300 hover:bg-gray-700 hover:text-white'
              )
            }
          >
            <item.icon className="mr-3 h-5 w-5 flex-shrink-0" />
            {item.name}
          </NavLink>
        ))}
      </nav>
      
      <div className="border-t border-gray-800 p-4">
        <div className="flex items-center gap-3">
          <img
            src={user?.avatar || `https://ui-avatars.com/api/?name=${user?.name}`}
            alt={user?.name}
            className="h-8 w-8 rounded-full"
          />
          <div className="flex-1">
            <p className="text-sm font-medium text-white">{user?.name}</p>
            <p className="text-xs text-gray-400 capitalize">{user?.role}</p>
          </div>
        </div>
      </div>
    </div>
  );
}