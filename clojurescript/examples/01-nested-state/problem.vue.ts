import { defineComponent, reactive, ref } from 'vue';

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

export default defineComponent({
  name: 'NestedStateProblem',
  setup() {
    const state = reactive<NestedState>({
      user: {
        profile: {
          settings: {
            theme: 'light',
            notifications: true,
          },
        },
      },
    });

    const updateTheme = (newTheme: string) => {
      // Manual deep update - reactivity works but verbose
      state.user.profile.settings.theme = newTheme;
    };

    const toggleNotifications = () => {
      state.user.profile.settings.notifications = !state.user.profile.settings.notifications;
    };

    // Problem: destructuring breaks reactivity
    const { theme } = state.user.profile.settings;
    const themeRef = ref(theme); // Won't update when state changes!

    return {
      state,
      updateTheme,
      toggleNotifications,
      themeRef,
    };
  },
  template: `
    <div>
      <h3>Vue Nested State Problem</h3>
      <p>Theme: {{ state.user.profile.settings.theme }}</p>
      <p>Notifications: {{ state.user.profile.settings.notifications ? 'On' : 'Off' }}</p>
      <p>Broken destructured theme: {{ themeRef }} (won't update!)</p>
      <button @click="updateTheme('dark')">Set Dark Theme</button>
      <button @click="toggleNotifications">Toggle Notifications</button>
    </div>
  `,
});