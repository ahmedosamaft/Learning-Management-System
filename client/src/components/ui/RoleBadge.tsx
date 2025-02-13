import React from 'react';
import { Role } from '../../models/user'; 
interface RoleBadgeProps {
  role: Role;
}

const RoleBadge: React.FC<RoleBadgeProps> = ({ role }) => {
  let bgColor = 'bg-gray-300/50';

  // Use more transparent colors by setting opacity (e.g., 50% opacity)
  switch (role) {
    case Role.ADMIN:
      bgColor = 'bg-red-500/80';
      break;
    case Role.INSTRUCTOR:
      bgColor = 'bg-yellow-500/80';
      break;
    case Role.STUDENT:
      bgColor = 'bg-green-500/75';
      break;
    default:
      bgColor = 'bg-gray-300';
  }

  return (
    <div
      className={`inline-block px-2 py-0.5 rounded ${bgColor} text-black text-xs font-medium`}
    >
      {role}
    </div>
  );
};

export default RoleBadge;
