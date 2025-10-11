// Backend validation logic - DUPLICATED from frontend
interface User {
  id: number;
  name: string;
  email: string;
  role: 'admin' | 'user';
}

function validateEmailBackend(email: string): boolean {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validateUserBackend(user: any): user is User {
  return (
    typeof user === 'object' &&
    user !== null &&
    typeof user.id === 'number' &&
    typeof user.name === 'string' &&
    user.name.length >= 2 &&
    typeof user.email === 'string' &&
    validateEmailBackend(user.email) &&
    ['admin', 'user'].includes(user.role)
  );
}

function canEditPostBackend(user: User, postAuthorId: number): boolean {
  return user.role === 'admin' || user.id === postAuthorId;
}

// Backend API endpoint
function handleEditPost(userId: number, postId: number, postAuthorId: number) {
  // Fetch user from database
  const user = { id: userId, name: "Alice", email: "alice@example.com", role: "admin" as const };

  if (!validateUserBackend(user)) {
    throw new Error('Invalid user');
  }

  if (!canEditPostBackend(user, postAuthorId)) {
    throw new Error('Permission denied');
  }

  // Edit post logic...
  console.log('Post edited successfully');
}

// Problem: Code duplication between frontend and backend
// Changes must be made in both places, risking inconsistencies