"use client"

import { Card } from "../../components/ui/card"
import { Input } from "../../components/ui/input"
import { Button } from "../../components/ui/button"
import { Search, BookOpen, MessageSquare, FileQuestion, Video } from "lucide-react"

const helpCategories = [
  {
    title: "Getting Started",
    icon: BookOpen,
    description: "Learn the basics of using the platform",
    articles: ["Platform Overview", "Account Setup", "Navigation Guide"],
  },
  {
    title: "Course Management",
    icon: Video,
    description: "Managing your courses and content",
    articles: ["Creating Courses", "Adding Lessons", "Student Progress"],
  },
  {
    title: "FAQ",
    icon: FileQuestion,
    description: "Frequently asked questions",
    articles: ["Technical Requirements", "Billing Questions", "Access Issues"],
  },
  {
    title: "Support",
    icon: MessageSquare,
    description: "Get help from our support team",
    articles: ["Contact Support", "Report an Issue", "Feature Request"],
  },
]

export default function HelpPage() {
  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Help Center</h1>
        <Button>Contact Support</Button>
      </div>

      <div className="flex items-center space-x-2 max-w-lg mx-auto">
        <Input placeholder="Search help articles..." />
        <Button size="icon">
          <Search className="h-4 w-4" />
        </Button>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        {helpCategories.map((category, index) => (
          <Card key={index} className="p-6">
            <div className="flex items-start space-x-4">
              <div className="p-2 bg-primary/10 rounded-lg">
                <category.icon className="h-6 w-6 text-primary" />
              </div>
              <div className="flex-1">
                <h2 className="text-xl font-semibold mb-2">{category.title}</h2>
                <p className="text-muted-foreground mb-4">
                  {category.description}
                </p>
                <ul className="space-y-2">
                  {category.articles.map((article, i) => (
                    <li key={i}>
                      <Button variant="link" className="h-auto p-0">
                        {article}
                      </Button>
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  )
}