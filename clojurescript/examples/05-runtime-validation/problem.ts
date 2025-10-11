// TypeScript compile-time validation only
interface User {
  name: string;
  age: number;
  email: string;
}

// Compile-time validation - these would cause TypeScript errors:
// const invalidUser: User = { name: "Alice", age: "25", email: "invalid" }; // Error
// const incompleteUser: User = { name: "Bob" }; // Error

// But runtime validation requires manual work
function validateUser(data: any): data is User {
  return (
    typeof data === 'object' &&
    data !== null &&
    typeof data.name === 'string' &&
    typeof data.age === 'number' &&
    typeof data.email === 'string' &&
    data.email.includes('@') // Basic email check
  );
}

// Manual runtime validation
function processUserData(data: any): User | null {
  if (validateUser(data)) {
    return data;
  }
  console.error('Invalid user data:', data);
  return null;
}

// Usage examples
const validData = { name: "Alice", age: 25, email: "alice@example.com" };
const invalidData = { name: "Bob", age: "30", email: "bob" }; // TypeScript allows this at runtime

console.log('Valid data processed:', processUserData(validData));
console.log('Invalid data processed:', processUserData(invalidData));

// Problem: Need external libraries like Zod, io-ts, or Yup for better validation
// TypeScript types don't exist at runtime