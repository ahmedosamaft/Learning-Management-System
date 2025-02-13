'use client';

import { useState } from 'react';
import { Card } from '../../components/ui/card';
import { Button } from '../../components/ui/button';
import { Avatar, AvatarImage } from '../../components/ui/avatar';
import { Bell, Pin, Search, Filter } from 'lucide-react';
import { Input } from '../../components/ui/input';

const announcements = [
  {
    id: 1,
    title: 'New Course Materials Available',
    content:
      'Updated materials for Introduction to Programming are now available in the course portal.',
    author: {
      name: 'Prof. David Wilson',
      avatar:
        'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=96&h=96&fit=crop&crop=face',
    },
    date: '2 hours ago',
    isPinned: true,
    course: 'Introduction to Programming',
    tags: ['Course Update', 'Materials'],
  },
  {
    id: 2,
    title: 'Upcoming Workshop: Advanced UI Design',
    content:
      'Join us for a special workshop on advanced UI design techniques this Friday at 2 PM.',
    author: {
      name: 'Sarah Chen',
      avatar:
        'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=96&h=96&fit=crop&crop=face',
    },
    date: '1 day ago',
    isPinned: false,
    course: 'UI/UX Design Fundamentals',
    tags: ['Workshop', 'Event'],
  },
  {
    id: 3,
    title: 'System Maintenance Notice',
    content:
      'The platform will undergo maintenance this Sunday from 2 AM to 4 AM EST.',
    author: {
      name: 'System Admin',
      avatar:
        'https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=96&h=96&fit=crop&crop=face',
    },
    date: '2 days ago',
    isPinned: false,
    course: 'All Courses',
    tags: ['Maintenance', 'System Update'],
  },
];

export default function AnnouncementsPage() {
  const [searchQuery, setSearchQuery] = useState('');

  const filteredAnnouncements = announcements.filter(
    (announcement) =>
      announcement.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
      announcement.content.toLowerCase().includes(searchQuery.toLowerCase()) ||
      announcement.course.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const pinnedAnnouncements = filteredAnnouncements.filter((a) => a.isPinned);
  const regularAnnouncements = filteredAnnouncements.filter((a) => !a.isPinned);

  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Announcements</h1>
        <Button>Create Announcement</Button>
      </div>

      <div className="flex items-center space-x-4">
        <div className="flex items-center space-x-2 flex-1 max-w-sm">
          <Input
            placeholder="Search announcements..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
          <Button size="icon" variant="ghost">
            <Search className="h-4 w-4" />
          </Button>
        </div>
        <Button variant="outline" size="icon">
          <Filter className="h-4 w-4" />
        </Button>
      </div>

      <div className="space-y-6">
        {pinnedAnnouncements.length > 0 && (
          <div className="space-y-4">
            <h2 className="text-lg font-semibold flex items-center">
              <Pin className="h-4 w-4 mr-2" />
              Pinned Announcements
            </h2>
            {pinnedAnnouncements.map((announcement) => (
              <AnnouncementCard
                key={announcement.id}
                announcement={announcement}
              />
            ))}
          </div>
        )}

        <div className="space-y-4">
          {regularAnnouncements.length > 0 && (
            <h2 className="text-lg font-semibold flex items-center">
              <Bell className="h-4 w-4 mr-2" />
              All Announcements
            </h2>
          )}
          {regularAnnouncements.map((announcement) => (
            <AnnouncementCard
              key={announcement.id}
              announcement={announcement}
            />
          ))}
        </div>
      </div>
    </div>
  );
}

interface Announcement {
  id: number;
  title: string;
  content: string;
  author: {
    name: string;
    avatar: string;
  };
  date: string;
  isPinned: boolean;
  course: string;
  tags: string[];
}

function AnnouncementCard({ announcement }: { announcement: Announcement }) {
  return (
    <Card className="p-6">
      <div className="flex items-start space-x-4">
        <Avatar className="h-10 w-10">
          <AvatarImage
            src={announcement.author.avatar}
            alt={announcement.author.name}
          />
        </Avatar>
        <div className="flex-1">
          <div className="flex items-center space-x-2">
            <h3 className="font-semibold">{announcement.title}</h3>
            {announcement.isPinned && (
              <Pin className="h-4 w-4 text-muted-foreground" />
            )}
          </div>
          <p className="text-sm text-muted-foreground mt-1">
            {announcement.content}
          </p>
          <div className="flex flex-wrap items-center gap-2 mt-4">
            <span className="text-sm font-medium">{announcement.course}</span>
            {announcement.tags.map((tag, index) => (
              <span
                key={index}
                className="px-2 py-1 bg-secondary text-secondary-foreground rounded-full text-xs"
              >
                {tag}
              </span>
            ))}
          </div>
          <div className="flex items-center space-x-4 mt-4 text-sm text-muted-foreground">
            <span>{announcement.author.name}</span>
            <span>•</span>
            <span>{announcement.date}</span>
          </div>
        </div>
      </div>
    </Card>
  );
}
