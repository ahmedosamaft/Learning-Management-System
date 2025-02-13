"use client"

import { cn } from "../lib/utils"
import { Button } from "../components/ui/button"
import { ScrollArea } from "../components/ui/scroll-area"
import { Avatar, AvatarImage } from "../components/ui/avatar"
import { 
  LayoutDashboard, 
  BookOpen, 
  GraduationCap, 
  Bell, 
  Users, 
  Settings,
  BookMarked,
  Calendar,
  MessageSquare,
  HelpCircle,
  ChevronRight
} from "lucide-react"
import Link from "next/link"
import { usePathname } from "next/navigation"

const routes = [
  {
    label: 'Dashboard',
    icon: LayoutDashboard,
    href: '/dashboard',
    color: 'text-sky-500',
  },
  {
    label: 'Courses',
    icon: BookOpen,
    href: '/courses',
    color: 'text-violet-500',
  },
  {
    label: 'Lessons',
    icon: BookMarked,
    href: '/lessons',
    color: 'text-pink-500',
  },
  {
    label: 'Users',
    icon: Users,
    href: '/users',
    color: 'text-orange-500',
  },
  {
    label: 'Schedule',
    icon: Calendar,
    href: '/schedule',
    color: 'text-emerald-500',
  },
  {
    label: 'Announcements',
    icon: Bell,
    href: '/announcements',
    color: 'text-blue-500',
  },
  {
    label: 'Messages',
    icon: MessageSquare,
    href: '/messages',
    color: 'text-yellow-500',
  },
  {
    label: 'Help Center',
    icon: HelpCircle,
    href: '/help',
    color: 'text-rose-500',
  },
  {
    label: 'Settings',
    icon: Settings,
    href: '/settings',
    color: 'text-gray-500',
  },
];

export default function Sidebar() {
  const pathname = usePathname()

  return (
    <div className="w-[240px] flex flex-col h-full bg-slate-900 text-white">
      <div className="px-3 py-2">
        <Link href="/dashboard" className="flex items-center px-3 py-4">
          <GraduationCap className="h-6 w-6 text-indigo-500" />
          <h1 className="text-lg font-bold ml-2">EduFlow</h1>
        </Link>
      </div>
      
      <ScrollArea className="flex-1 px-3">
        <div className="space-y-1">
          {routes.map((route) => (
            <Button
              key={route.href}
              variant={pathname === route.href ? "secondary" : "ghost"}
              className={cn(
                "w-full justify-start h-9",
                pathname === route.href ? "bg-white/10" : "hover:bg-white/10",
                "transition-colors"
              )}
              asChild
            >
              <Link href={route.href}>
                <route.icon className={cn("h-4 w-4 mr-2", route.color)} />
                {route.label}
              </Link>
            </Button>
          ))}
        </div>
      </ScrollArea>

      <div className="mt-auto p-4">
        <div className="flex items-center space-x-3 bg-white/10 rounded-lg p-3 cursor-pointer hover:bg-white/20 transition-colors">
          <Avatar className="h-8 w-8">
            <AvatarImage src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=96&h=96&fit=crop&crop=face" alt="User" />
          </Avatar>
          <div className="flex-1 min-w-0">
            <p className="text-sm font-medium truncate">John Smith</p>
            <p className="text-xs text-white/60 truncate">john@example.com</p>
          </div>
          <ChevronRight className="h-4 w-4 text-white/60" />
        </div>
      </div>
    </div>
  )
}