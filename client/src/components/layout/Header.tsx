import React, { useState } from 'react';
import { Bell, Search,BookOpen, Menu, X  } from 'lucide-react';
import { formatDistanceToNow } from '../../utils/dateUtils';
import { useNotificationStore } from '../../store/notificationStore'; // Import the Zustand store
import NotificationPanel from './NotificationPanel';

export function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isNotificationOpen, setIsNotificationOpen] = useState(false);
  const unreadCount = useNotificationStore((state) => state.unreadCount);


  return (
    <header className="flex h-16 items-center justify-between border-b bg-white px-6">
      <div className="flex flex-1 items-center">
        <div className="w-full max-w-lg">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-gray-400" />
            <input
              type="search"
              placeholder="Search courses, assignments..."
              className="h-10 w-full rounded-md border border-gray-300 pl-10 pr-4 focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
            />
          </div>
        </div>
      </div>
      
      <div className="flex items-center">
            <div className="relative">
              <button
                onClick={() => setIsNotificationOpen(!isNotificationOpen)}
                className="p-2 rounded-full text-indigo-200 hover:text-indigo-600 focus:outline-none focus:ring-2 focus:ring-white"
                aria-label="View notifications"
              >
                <Bell className="h-6 w-6" aria-hidden="true" />
                {unreadCount > 0 && (
                  <span className="absolute top-0 right-0 block h-5 w-5 rounded-full bg-red-500 text-white text-xs flex items-center justify-center">
                    {unreadCount}
                  </span>
                )}
              </button>
              {isNotificationOpen && (
                <NotificationPanel onClose={() => setIsNotificationOpen(false)} />
              )}
            </div>
          </div>
    </header>
  );
}
