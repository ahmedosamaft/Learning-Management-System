// src\components\NotificationPanel.tsx
import React from 'react';
import { useNotificationStore } from '../../store/notificationStore';
import { formatDistanceToNow } from '../../utils/dateUtils';

interface NotificationPanelProps {
  onClose: () => void;
}

export default function NotificationPanel({ onClose }: NotificationPanelProps) {
  const { notifications, markAsRead, markAllAsRead } = useNotificationStore();

  return (
    <div className="absolute right-0 mt-2 w-96 bg-white rounded-lg shadow-xl py-2 z-50">
      <div className="px-4 py-2 border-b border-gray-200">
        <div className="flex justify-between items-center">
          <h3 className="text-lg font-semibold text-gray-900">Notifications</h3>
          <button
            onClick={markAllAsRead}
            className="text-sm text-indigo-600 hover:text-indigo-800"
          >
            Mark all as read
          </button>
        </div>
      </div>
      
      <div className="max-h-96 overflow-y-auto">
        {notifications.length === 0 ? (
          <div className="px-4 py-6 text-center text-gray-500">
            No notifications yet
          </div>
        ) : (
          notifications.map((notification) => (
            <div
              key={notification.id}
              className={`px-4 py-3 hover:bg-gray-50 cursor-pointer ${
                !notification.read ? 'bg-indigo-50' : ''
              }`}
              onClick={() => markAsRead(notification.id)}
            >
              <h4 className="text-sm font-semibold text-gray-900">
                {notification.title}
              </h4>
              <p className="text-sm text-gray-700 mt-1">{notification.body}</p>
              <p className="text-xs text-gray-500 mt-1">
                {formatDistanceToNow(notification.timestamp)}
              </p>
            </div>
          ))
        )}
      </div>
    </div>
  );
}