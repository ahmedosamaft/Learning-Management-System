"use client"

import { Card } from "../../components/ui/card"
import { Input } from "../../components/ui/input"
import { Button } from "../../components/ui/button"
import { Avatar, AvatarImage } from "../../components/ui/avatar"
import { Search } from "lucide-react"

const conversations = [
  {
    id: 1,
    name: "Prof. David Wilson",
    avatar: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=96&h=96&fit=crop&crop=face",
    lastMessage: "When will the next assignment be due?",
    time: "2m ago",
    unread: 2,
  },
  {
    id: 2,
    name: "Sarah Chen",
    avatar: "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=96&h=96&fit=crop&crop=face",
    lastMessage: "The workshop materials are ready",
    time: "1h ago",
    unread: 0,
  },
  {
    id: 3,
    name: "Michael Rodriguez",
    avatar: "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=96&h=96&fit=crop&crop=face",
    lastMessage: "Thanks for your help with the project",
    time: "2h ago",
    unread: 0,
  },
]

export default function MessagesPage() {
  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Messages</h1>
        <Button>New Message</Button>
      </div>

      <div className="flex items-center space-x-2">
        <Input placeholder="Search messages..." className="max-w-sm" />
        <Button size="icon" variant="ghost">
          <Search className="h-4 w-4" />
        </Button>
      </div>

      <div className="space-y-4">
        {conversations.map((conversation) => (
          <Card key={conversation.id} className="p-4 hover:bg-accent cursor-pointer">
            <div className="flex items-center space-x-4">
              <Avatar>
                <AvatarImage src={conversation.avatar} alt={conversation.name} />
              </Avatar>
              <div className="flex-1 min-w-0">
                <div className="flex items-center justify-between">
                  <h3 className="font-medium truncate">{conversation.name}</h3>
                  <span className="text-xs text-muted-foreground">
                    {conversation.time}
                  </span>
                </div>
                <p className="text-sm text-muted-foreground truncate">
                  {conversation.lastMessage}
                </p>
              </div>
              {conversation.unread > 0 && (
                <span className="flex items-center justify-center w-5 h-5 bg-primary text-primary-foreground rounded-full text-xs">
                  {conversation.unread}
                </span>
              )}
            </div>
          </Card>
        ))}
      </div>
    </div>
  )
}