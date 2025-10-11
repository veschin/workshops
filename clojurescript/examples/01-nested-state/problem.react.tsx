import React, { useState } from 'react';

interface NestedState {
  user: {
    profile: {
      settings: {
        theme: string;
        notifications: boolean;
      };
    };
  };
}

const initialState: NestedState = {
  user: {
    profile: {
      settings: {
        theme: 'light',
        notifications: true,
      },
    },
  },
};

export const NestedStateProblem: React.FC = () => {
  const [state, setState] = useState<NestedState>(initialState);

  const updateTheme = (newTheme: string) => {
    // Verbose spread operator hell for deep updates
    setState({
      ...state,
      user: {
        ...state.user,
        profile: {
          ...state.user.profile,
          settings: {
            ...state.user.profile.settings,
            theme: newTheme,
          },
        },
      },
    });
  };

  const toggleNotifications = () => {
    // Even more verbose for boolean toggle
    setState({
      ...state,
      user: {
        ...state.user,
        profile: {
          ...state.user.profile,
          settings: {
            ...state.user.profile.settings,
            notifications: !state.user.profile.settings.notifications,
          },
        },
      },
    });
  };

  return (
    <div>
      <h3>React Nested State Problem</h3>
      <p>Theme: {state.user.profile.settings.theme}</p>
      <p>Notifications: {state.user.profile.settings.notifications ? 'On' : 'Off'}</p>
      <button onClick={() => updateTheme('dark')}>Set Dark Theme</button>
      <button onClick={toggleNotifications}>Toggle Notifications</button>
    </div>
  );
};