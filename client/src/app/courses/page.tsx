"use client"

import { Card } from "../../components/ui/card"
import { Button } from "../../components/ui/button"
import { Search, Filter, Clock, Users, BookOpen } from "lucide-react"
import { Input } from "../../components/ui/input"
import Link from "next/link"

const courses = [
  {
    id: 1,
    title: "Introduction to Programming",
    description: "Learn the basics of programming with JavaScript",
    instructor: "Prof. David Wilson",
    students: 156,
    duration: "12 weeks",
    progress: 75,
    lessons: 12,
    image: "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?w=500&h=300&fit=crop",
  },
  {
    id: 2,
    title: "UI/UX Design Fundamentals",
    description: "Master the principles of user interface design",
    instructor: "Sarah Chen",
    students: 89,
    duration: "8 weeks",
    progress: 60,
    lessons: 8,
    image: "https://images.unsplash.com/photo-1545235617-9465d2a55698?w=500&h=300&fit=crop",
  },
  {
    id: 3,
    title: "Digital Marketing Essentials",
    description: "Learn modern digital marketing strategies",
    instructor: "Michael Rodriguez",
    students: 124,
    duration: "10 weeks",
    progress: 45,
    lessons: 10,
    image: "https://images.unsplash.com/photo-1432888498266-38ffec3eaf0a?w=500&h=300&fit=crop",
  },
]

export default function CoursesPage() {
  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Courses</h1>
        <Button>Create Course</Button>
      </div>

      <div className="flex items-center space-x-4">
        <div className="flex items-center space-x-2 flex-1 max-w-sm">
          <Input placeholder="Search courses..." />
          <Button size="icon" variant="ghost">
            <Search className="h-4 w-4" />
          </Button>
        </div>
        <Button variant="outline" size="icon">
          <Filter className="h-4 w-4" />
        </Button>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {courses.map((course) => (
          <Link href={`/courses/${course.id}`} key={course.id}>
            <Card className="overflow-hidden hover:shadow-lg transition-shadow">
              <div className="aspect-video w-full">
                <img
                  src={course.image}
                  alt={course.title}
                  className="object-cover w-full h-full"
                />
              </div>
              <div className="p-4">
                <h3 className="text-xl font-semibold mb-2">{course.title}</h3>
                <p className="text-muted-foreground mb-4">{course.description}</p>
                <div className="flex items-center text-sm text-muted-foreground mb-4">
                  <span>By {course.instructor}</span>
                </div>
                <div className="flex justify-between items-center text-sm text-muted-foreground">
                  <div className="flex items-center space-x-4">
                    <span className="flex items-center">
                      <Clock className="h-4 w-4 mr-1" />
                      {course.duration}
                    </span>
                    <span className="flex items-center">
                      <Users className="h-4 w-4 mr-1" />
                      {course.students}
                    </span>
                    <span className="flex items-center">
                      <BookOpen className="h-4 w-4 mr-1" />
                      {course.lessons} lessons
                    </span>
                  </div>
                </div>
              </div>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  )
}