
import { Card } from "../../../components/ui/card"
import { Button } from "../../../components/ui/button"
import { Progress } from "../../../components/ui/progress"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "../../../components/ui/tabs"
import { BookOpen, Clock, Users, PlayCircle, CheckCircle2 } from "lucide-react"

const courseDetails = {
  id: 1,
  title: "Introduction to Programming",
  description: "Learn the fundamentals of programming with JavaScript. This comprehensive course covers everything from basic syntax to advanced concepts.",
  instructor: "Prof. David Wilson",
  duration: "12 weeks",
  students: 156,
  progress: 75,
  image: "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?w=1200&h=400&fit=crop",
  lessons: [
    {
      id: 1,
      title: "Getting Started with JavaScript",
      duration: "45 minutes",
      completed: true,
    },
    {
      id: 2,
      title: "Variables and Data Types",
      duration: "60 minutes",
      completed: true,
    },
    {
      id: 3,
      title: "Control Flow and Loops",
      duration: "55 minutes",
      completed: false,
    },
    {
      id: 4,
      title: "Functions and Scope",
      duration: "50 minutes",
      completed: false,
    },
  ],
  announcements: [
    {
      id: 1,
      title: "Week 5 Assignment Due Date Extended",
      content: "The deadline for the Week 5 assignment has been extended to next Friday.",
      date: "2 days ago",
    },
    {
      id: 2,
      title: "Live Coding Session This Weekend",
      content: "Join us for a live coding session this Saturday at 2 PM EST.",
      date: "4 days ago",
    },
  ],
}

export function CourseDetails() {
  return (
    <div className="space-y-6">
      <div className="relative h-[300px]">
        <img
          src={courseDetails.image}
          alt={courseDetails.title}
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-background/80 to-background/20" />
        <div className="absolute bottom-0 left-0 right-0 p-6">
          <h1 className="text-3xl font-bold text-white mb-2">{courseDetails.title}</h1>
          <div className="flex items-center space-x-4 text-white/80">
            <span className="flex items-center">
              <Clock className="h-4 w-4 mr-1" />
              {courseDetails.duration}
            </span>
            <span className="flex items-center">
              <Users className="h-4 w-4 mr-1" />
              {courseDetails.students} students
            </span>
            <span className="flex items-center">
              <BookOpen className="h-4 w-4 mr-1" />
              {courseDetails.lessons.length} lessons
            </span>
          </div>
        </div>
      </div>

      <div className="p-6">
        <Tabs defaultValue="overview" className="space-y-6">
          <TabsList>
            <TabsTrigger value="overview">Overview</TabsTrigger>
            <TabsTrigger value="lessons">Lessons</TabsTrigger>
            <TabsTrigger value="announcements">Announcements</TabsTrigger>
          </TabsList>

          <TabsContent value="overview" className="space-y-6">
            <Card className="p-6">
              <h2 className="text-xl font-semibold mb-4">About This Course</h2>
              <p className="text-muted-foreground">{courseDetails.description}</p>
              <div className="mt-6">
                <div className="flex items-center justify-between mb-2">
                  <span className="text-sm font-medium">Course Progress</span>
                  <span className="text-sm text-muted-foreground">{courseDetails.progress}%</span>
                </div>
                <Progress value={courseDetails.progress} className="h-2" />
              </div>
            </Card>
          </TabsContent>

          <TabsContent value="lessons" className="space-y-4">
            {courseDetails.lessons.map((lesson) => (
              <Card key={lesson.id} className="p-4">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-4">
                    <div className="h-10 w-10 rounded-full bg-secondary flex items-center justify-center">
                      {lesson.completed ? (
                        <CheckCircle2 className="h-5 w-5 text-primary" />
                      ) : (
                        <PlayCircle className="h-5 w-5 text-primary" />
                      )}
                    </div>
                    <div>
                      <h3 className="font-medium">{lesson.title}</h3>
                      <p className="text-sm text-muted-foreground">{lesson.duration}</p>
                    </div>
                  </div>
                  <Button variant={lesson.completed ? "secondary" : "default"}>
                    {lesson.completed ? "Completed" : "Start"}
                  </Button>
                </div>
              </Card>
            ))}
          </TabsContent>

          <TabsContent value="announcements" className="space-y-4">
            {courseDetails.announcements.map((announcement) => (
              <Card key={announcement.id} className="p-4">
                <h3 className="font-medium mb-2">{announcement.title}</h3>
                <p className="text-sm text-muted-foreground mb-2">{announcement.content}</p>
                <p className="text-xs text-muted-foreground">{announcement.date}</p>
              </Card>
            ))}
          </TabsContent>
        </Tabs>
      </div>
    </div>
  )
}