"use client"

import { Card } from "../../components/ui/card"
import { Button } from "../../components/ui/button"
import { Input } from "../../components/ui/input"
import { Search, Clock, Users, BookOpen, PlayCircle } from "lucide-react"

const lessons = [
  {
    id: 1,
    title: "Introduction to JavaScript Basics",
    course: "Introduction to Programming",
    duration: "45 minutes",
    students: 156,
    thumbnail: "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?w=500&h=300&fit=crop",
    status: "Published",
  },
  {
    id: 2,
    title: "Understanding UI Design Principles",
    course: "UI/UX Design Fundamentals",
    duration: "60 minutes",
    students: 89,
    thumbnail: "https://images.unsplash.com/photo-1545235617-9465d2a55698?w=500&h=300&fit=crop",
    status: "Draft",
  },
  {
    id: 3,
    title: "Social Media Marketing Strategies",
    course: "Digital Marketing Essentials",
    duration: "50 minutes",
    students: 124,
    thumbnail: "https://images.unsplash.com/photo-1432888498266-38ffec3eaf0a?w=500&h=300&fit=crop",
    status: "Published",
  },
]

export default function LessonsPage() {
  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Lessons</h1>
        <Button>Create Lesson</Button>
      </div>

      <div className="flex items-center space-x-4">
        <div className="flex items-center space-x-2 flex-1 max-w-sm">
          <Input placeholder="Search lessons..." />
          <Button size="icon" variant="ghost">
            <Search className="h-4 w-4" />
          </Button>
        </div>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {lessons.map((lesson) => (
          <Card key={lesson.id} className="overflow-hidden">
            <div className="aspect-video w-full relative group">
              <img
                src={lesson.thumbnail}
                alt={lesson.title}
                className="object-cover w-full h-full"
              />
              <div className="absolute inset-0 bg-black/60 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center">
                <PlayCircle className="h-12 w-12 text-white" />
              </div>
            </div>
            <div className="p-4">
              <div className="flex justify-between items-start mb-2">
                <h3 className="text-xl font-semibold">{lesson.title}</h3>
                <span className={`px-2 py-1 rounded-full text-xs ${
                  lesson.status === "Published" 
                    ? "bg-green-100 text-green-800" 
                    : "bg-yellow-100 text-yellow-800"
                }`}>
                  {lesson.status}
                </span>
              </div>
              <p className="text-muted-foreground text-sm mb-4">{lesson.course}</p>
              <div className="flex items-center space-x-4 text-sm text-muted-foreground">
                <div className="flex items-center">
                  <Clock className="h-4 w-4 mr-1" />
                  {lesson.duration}
                </div>
                <div className="flex items-center">
                  <Users className="h-4 w-4 mr-1" />
                  {lesson.students} students
                </div>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  )
}