// Frontend validation logic - duplicated from backend
interface User {
  id: number;
  name: string;
  email: string;
  role: 'admin' | 'user';
}

function validateEmailFrontend(email: string): boolean {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validateUserFrontend(user: any): user is User {
  return (
    typeof user === 'object' &&
    user !== null &&
    typeof user.id === 'number' &&
    typeof user.name === 'string' &&
    user.name.length >= 2 &&
    typeof user.email === 'string' &&
    validateEmailFrontend(user.email) &&
    ['admin', 'user'].includes(user.role)
  );
}

function canEditPostFrontend(user: User, postAuthorId: number): boolean {
  return user.role === 'admin' || user.id === postAuthorId;
}

// Frontend usage
const currentUser = { id: 1, name: "Alice", email: "alice@example.com", role: "admin" as const };
console.log('Can edit post?', canEditPostFrontend(currentUser, 2)); // true (admin)

// Problem: This logic must be duplicated on the backend