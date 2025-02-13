import { Card } from "../../components/ui/card"
import { Overview } from "../../components/overview"
import { RecentActivity } from "../../components/recent-activity"
import { CourseStats } from "../../components/course-stats"

export default function DashboardPage() {
  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Dashboard</h1>
      </div>
      
      <div className="grid gap-4 md:grid-cols-3">
        <Card className="p-4">
          <h3 className="font-semibold mb-4">Total Students</h3>
          <p className="text-3xl font-bold">1,234</p>
        </Card>
        <Card className="p-4">
          <h3 className="font-semibold mb-4">Active Courses</h3>
          <p className="text-3xl font-bold">12</p>
        </Card>
        <Card className="p-4">
          <h3 className="font-semibold mb-4">Average Grade</h3>
          <p className="text-3xl font-bold">85%</p>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <Card className="md:col-span-4 p-4">
          <h3 className="font-semibold mb-4">Performance Overview</h3>
          <Overview />
        </Card>
        <Card className="md:col-span-3 p-4">
          <h3 className="font-semibold mb-4">Course Statistics</h3>
          <CourseStats />
        </Card>
      </div>

      <Card className="p-4">
        <h3 className="font-semibold mb-4">Recent Activity</h3>
        <RecentActivity />
      </Card>
    </div>
  )
}