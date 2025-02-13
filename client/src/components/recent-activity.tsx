"use client"

import { Avatar, AvatarImage } from "../components/ui/avatar"

const activities = [
  {
    user: "John Doe",
    action: "submitted assignment",
    course: "Introduction to Programming",
    time: "2 hours ago",
    avatar: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=32&h=32&q=80",
  },
  {
    user: "Jane Smith",
    action: "completed course",
    course: "UI/UX Design Fundamentals",
    time: "4 hours ago",
    avatar: "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=32&h=32&q=80",
  },
  {
    user: "Mike Johnson",
    action: "joined course",
    course: "Digital Marketing Essentials",
    time: "6 hours ago",
    avatar: "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=32&h=32&q=80",
  },
]

export function RecentActivity() {
  return (
    <div className="space-y-4">
      {activities.map((activity, index) => (
        <div key={index} className="flex items-center space-x-4">
          <Avatar>
            <AvatarImage src={activity.avatar} alt={activity.user} />
          </Avatar>
          <div>
            <p className="text-sm">
              <span className="font-semibold">{activity.user}</span>{" "}
              {activity.action}{" "}
              <span className="font-semibold">{activity.course}</span>
            </p>
            <p className="text-sm text-muted-foreground">{activity.time}</p>
          </div>
        </div>
      ))}
    </div>
  )
}