// JavaScript equivalents of ClojureScript features
// These would require lodash or manual implementation

// Destructuring equivalent (ES6 has some, but not as powerful)
const user = {
  name: "Alice",
  profile: { age: 25, address: { city: "NYC", country: "USA" } },
  hobbies: ["reading", "coding", "gaming"]
};

// Basic destructuring (available in ES6)
const { name } = user;
console.log("Name:", name);

// Nested destructuring (possible but verbose)
const { profile: { age, address: { city } } } = user;
console.log("Age:", age, "City:", city);

// Array destructuring
const [firstHobby, secondHobby, ...restHobbies] = user.hobbies;
console.log("Hobbies:", firstHobby, secondHobby, restHobbies);

// Threading equivalent using lodash
// const _ = require('lodash');

const data = {
  users: [
    { name: "Alice", age: 25, city: "NYC" },
    { name: "Bob", age: 30, city: "LA" },
    { name: "Charlie", age: 35, city: "NYC" }
  ]
};

// Manual chaining (lodash would help)
// const result = _.chain(data)
//   .get('users')
//   .filter(u => u.age >= 30)
//   .map('name')
//   .sort()
//   .value();

// Manual implementation
const result = data.users
  .filter(u => u.age >= 30)
  .map(u => u.name)
  .sort();

console.log("Result:", result);

// Function composition (manual or lodash)
const add = x => y => x + y;
const multiply = x => y => x * y;
const divide = x => y => y / x;

// Manual composition
const processNumber = x => divide(2)(multiply(3)(add(1)(x)));
console.log("Process 10:", processNumber(10));

// Or with reduceRight
const compose = (...fns) => x => fns.reduceRight((acc, fn) => fn(acc), x);
const processNumber2 = compose(x => x / 2, x => x * 3, x => x + 1);
console.log("Process 10 (composed):", processNumber2(10));

// Partial application (lodash.partial or manual)
const partial = (fn, ...args) => (...moreArgs) => fn(...args, ...moreArgs);
const addFive = partial(add, 5);
console.log("Add five:", addFive(3)(0)); // Curried

// Much more verbose and error-prone than ClojureScript equivalents!