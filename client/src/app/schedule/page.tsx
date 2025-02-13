"use client"

import { Card } from "../../components/ui/card"
import { Button } from "../../components/ui/button"
import { Calendar } from "../../components/ui/calendar"
import { useState } from "react"

const events = [
  {
    id: 1,
    title: "Introduction to JavaScript",
    type: "Lecture",
    time: "10:00 AM - 11:30 AM",
    instructor: "Prof. David Wilson",
  },
  {
    id: 2,
    title: "UI Design Workshop",
    type: "Workshop",
    time: "2:00 PM - 4:00 PM",
    instructor: "Sarah Chen",
  },
  {
    id: 3,
    title: "Marketing Strategy Session",
    type: "Seminar",
    time: "3:30 PM - 5:00 PM",
    instructor: "Michael Rodriguez",
  },
]

export default function SchedulePage() {
  const [date, setDate] = useState<Date | undefined>(new Date())

  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Schedule</h1>
        <Button>Add Event</Button>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card className="p-4">
          <Calendar
            mode="single"
            selected={date}
            onSelect={setDate}
            className="rounded-md border"
          />
        </Card>

        <Card className="p-4">
          <h2 className="text-xl font-semibold mb-4">Today's Events</h2>
          <div className="space-y-4">
            {events.map((event) => (
              <Card key={event.id} className="p-4">
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="font-medium">{event.title}</h3>
                    <p className="text-sm text-muted-foreground">{event.time}</p>
                    <p className="text-sm text-muted-foreground">
                      Instructor: {event.instructor}
                    </p>
                  </div>
                  <span className="px-2 py-1 text-xs rounded-full bg-secondary">
                    {event.type}
                  </span>
                </div>
              </Card>
            ))}
          </div>
        </Card>
      </div>
    </div>
  )
}