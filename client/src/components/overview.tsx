"use client"

import { Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts"

const data = [
  {
    name: "Jan",
    average: 85,
    highest: 95,
    lowest: 75,
  },
  {
    name: "Feb",
    average: 82,
    highest: 92,
    lowest: 72,
  },
  {
    name: "Mar",
    average: 88,
    highest: 98,
    lowest: 78,
  },
  {
    name: "Apr",
    average: 86,
    highest: 96,
    lowest: 76,
  },
  {
    name: "May",
    average: 89,
    highest: 99,
    lowest: 79,
  },
  {
    name: "Jun",
    average: 87,
    highest: 97,
    lowest: 77,
  },
]

export function Overview() {
  return (
    <ResponsiveContainer width="100%" height={350}>
      <LineChart data={data}>
        <XAxis
          dataKey="name"
          stroke="hsl(var(--muted-foreground))"
          fontSize={12}
          tickLine={false}
          axisLine={false}
          padding={{ left: 10, right: 10 }}
        />
        <YAxis
          stroke="hsl(var(--muted-foreground))"
          fontSize={12}
          tickLine={false}
          axisLine={false}
          tickFormatter={(value: number) => `${value}%`}
          padding={{ top: 10, bottom: 10 }}
        />
        <Tooltip 
          contentStyle={{ 
            backgroundColor: "hsl(var(--background))",
            border: "1px solid hsl(var(--border))"
          }}
          labelStyle={{
            color: "hsl(var(--foreground))"
          }}
          formatter={(value: number) => [`${value}%`, ""]}
        />
        <Line
          type="monotone"
          dataKey="average"
          stroke="hsl(var(--primary))"
          strokeWidth={2}
          dot={false}
        />
        <Line
          type="monotone"
          dataKey="highest"
          stroke="hsl(var(--chart-1))"
          strokeWidth={2}
          dot={false}
        />
        <Line
          type="monotone"
          dataKey="lowest"
          stroke="hsl(var(--chart-2))"
          strokeWidth={2}
          dot={false}
        />
      </LineChart>
    </ResponsiveContainer>
  )
}