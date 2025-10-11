# ClojureScript Workshop - Product Requirements Document

## Project Goal

Immerse frontend developers (Vue3/React) into ClojureScript by demonstrating solutions to real framework problems and building a fullstack application (online cinema).

**Target Audience:** Junior+ / Middle+ developers with functional programming knowledge in JS/TS

## Repository Structure

```
clojurescript_demo/
├── PRD.md
│
├── w1/                                    # Workshop 1: Introduction & Core Concepts
│   ├── examples/
│   │   ├── PROBLEMS.md                   # Problem descriptions + comparison table
│   │   ├── 01-nested-state/
│   │   │   ├── problem.react.tsx
│   │   │   ├── problem.vue.ts
│   │   │   └── solution.cljs
│   │   ├── 02-useeffect-hell/
│   │   │   ├── problem.react.tsx
│   │   │   ├── problem.vue.ts
│   │   │   └── solution.cljs
│   │   ├── 03-reactivity-footguns/
│   │   │   ├── problem.vue.ts
│   │   │   └── solution.cljs
│   │   ├── 04-performance-memo/
│   │   │   ├── problem.react.tsx
│   │   │   └── solution.cljs
│   │   ├── 05-runtime-validation/
│   │   │   ├── problem.ts
│   │   │   └── solution.cljs
│   │   ├── 06-shared-code/
│   │   │   ├── problem-frontend.ts
│   │   │   ├── problem-backend.ts
│   │   │   ├── solution-shared.cljc
│   │   │   ├── solution-frontend.cljs
│   │   │   └── solution-backend.clj
│   │   └── 07-syntactic-sugar/
│   │       ├── destructuring.cljs
│   │       ├── threading-macros.cljs
│   │       ├── composition.cljs
│   │       └── comparison.js
│   ├── faq/
│   │   └── FAQ.md
│   └── bootstrap/
│       ├── shadow-cljs.edn
│       ├── package.json
│       ├── deps.edn
│       └── src/
│           ├── app/
│           │   └── core.cljs
│           └── shared/
│
└── w2/                                    # Workshop 2: Fullstack Application
    ├── app/
    │   ├── shadow-cljs.edn
    │   ├── package.json
    │   ├── deps.edn
    │   ├── src/
    │   │   ├── frontend/
    │   │   │   ├── core.cljs
    │   │   │   ├── views/
    │   │   │   ├── events.cljs
    │   │   │   └── subs.cljs
    │   │   ├── backend/
    │   │   │   ├── core.clj
    │   │   │   ├── api.clj
    │   │   │   └── websocket.clj
    │   │   └── shared/
    │   │       ├── models.cljc
    │   │       └── validation.cljc
    │   └── resources/
    │       └── public/
    └── guide/
        ├── 01-architecture.md
        ├── 02-backend-setup.md
        ├── 03-websocket-sync.md
        ├── 04-frontend-ui.md
        ├── 05-shared-validation.md
        └── 06-interop.md
```

---

## W1: Introduction & Core Concepts

### examples/PROBLEMS.md Structure

Each problem includes:
1. Problem name
2. Plain language description
3. Technical explanation
4. Comparison table
5. Links to example directories

**Comparison Table Format:**

| Problem              | React                        | Vue3                      | ClojureScript            |
|----------------------|------------------------------|---------------------------|--------------------------|
| Deep Nested State    | Spread operator hell         | Reactive footguns         | assoc-in                 |
| useEffect Hell       | Dependency array issues      | watch/watchEffect confusion | add-watch              |
| Performance          | Manual memo/useCallback      | Reactivity overhead       | Structural sharing       |
| Runtime Validation   | TypeScript compile-time only | Manual checks             | spec/malli built-in      |
| Shared Code          | Monorepo complexity          | Code duplication          | .cljc files              |

### Problem Examples

**01-nested-state/**
Deep nested state updates require verbose spread operators or libraries.
- React: `{...state, nested: {...state.nested, deep: {...state.nested.deep, value: 1}}}`
- Vue: Reactivity lost on dynamic properties
- CLJS: `(swap! state assoc-in [:nested :deep :value] 1)`

**02-useeffect-hell/**
Stale closures, infinite loops, missing dependencies.
- React: useEffect dependency array management
- Vue: watch vs watchEffect vs computed confusion
- CLJS: add-watch without closure issues

**03-reactivity-footguns/**
Destructuring breaks reactivity (Vue), ref vs reactive confusion.
- Vue: `const {count} = state` loses reactivity
- React: Different issues (covered in other examples)
- CLJS: Immutable data prevents breakage

**04-performance-memo/**
Manual optimization required for performance.
- React: memo, useMemo, useCallback everywhere
- Vue: computed vs methods not obvious
- CLJS: Structural sharing handles optimization automatically

**05-runtime-validation/**
TypeScript types only exist at compile-time.
- React/Vue: External libraries (Zod, io-ts, Yup)
- CLJS: spec/malli built-in runtime validation

**06-shared-code/**
Code duplication between frontend and backend.
- React/Vue: Monorepo setup or copy-paste
- CLJS: .cljc files work on both platforms

**07-syntactic-sugar/**
ClojureScript language features without JS/TS equivalents:
- Advanced destructuring (`:keys`, nested, rest)
- Threading macros (`->`, `->>`, `some->`)
- Function composition (`comp`, `partial`)
- Pattern matching (via libraries)
- Transducers

### faq/FAQ.md

Comprehensive FAQ covering language basics, ecosystem, performance, team considerations, interop, migration, and market questions with real data and statistics.

### bootstrap/

Starter project for development in under 5 minutes.

**Includes:**
- `shadow-cljs.edn` - build configuration
- `package.json` - npm dependencies (React, shadow-cljs)
- `deps.edn` - Clojure dependencies (reagent, re-frame)
- Hello World + Counter example with explanatory comments
- Configured REPL
- Hot reload enabled

**Startup commands:**
```bash
npm install
npx shadow-cljs watch app
# Open http://localhost:3000
```

---

## W2: Fullstack Application (Online Cinema)

### Functionality (MVP)

**Required Features:**
- Nickname-based entry (no full authentication)
- Room creation
- Room entry via link/code
- Synchronized play/pause/seek (YouTube iframe)
- Online participant list
- Basic chat

**Optional Features:**
- Video playlist/queue
- Reactions/emoji
- Room history

### Technical Stack

**Backend:**
- Ring - HTTP server
- Reitit - routing
- Sente - WebSocket (with long-polling fallback)
- In-memory atom for state (can extend to DataScript)

**Frontend:**
- Reagent - React wrapper
- re-frame - state management
- react-player - YouTube API wrapper (demonstrates interop)
- Material-UI or similar (demonstrates React component interop)

**Shared (.cljc):**
- Data models (room, user, message)
- Validation (spec or malli)
- Business logic (room access rules)

### Architecture

```
Frontend                Backend
--------                -------
[re-frame]             [Ring/Reitit]
    |                       |
    |-- Events         API Routes --|
    |-- Subs           WebSocket  --|
    |-- Views                |
                             |
    |---- WebSocket ---------|

         [Shared .cljc]
         - Models
         - Validation
```

**Data Flow:**
1. User action triggers re-frame event
2. Event sends HTTP/WebSocket message to backend
3. Backend updates state (atom)
4. Backend broadcasts to all clients via WebSocket
5. Frontend receives message, dispatches re-frame event, subscriptions update, views re-render

### guide/

Step-by-step guides covering architecture, backend/frontend setup, WebSocket synchronization, shared validation, and library interop.

---

## Success Metrics

Participants should:
1. Understand basic ClojureScript syntax
2. Create simple Reagent applications
3. Recognize how CLJS solves React/Vue problems
4. Have working bootstrap for experimentation
5. Know where to find documentation and help

Not required:
- Write production CLJS code
- Know all language features
- Rewrite existing projects

---

## Implementation Steps

1. Create w1/ structure
2. Write PROBLEMS.md with examples
3. Implement problem examples
4. Compile FAQ
5. Prepare bootstrap
6. Create w2/ structure
7. Implement cinema backend
8. Implement cinema frontend
9. Write step-by-step guide
10. Test complete workshop
