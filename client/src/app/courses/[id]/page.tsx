import { CourseDetails } from "./course-details"

// This is required for static export with dynamic routes
export function generateStaticParams() {
  return [
    { id: "1" },
    { id: "2" },
    { id: "3" },
  ]
}

export default function CourseDetailsPage() {
  return <CourseDetails />
}