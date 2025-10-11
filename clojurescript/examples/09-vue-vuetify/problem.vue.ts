import { defineComponent, ref } from 'vue';
import { VBtn, VTextField } from 'vuetify/components';

export default defineComponent({
  name: 'VuetifyForm',
  components: { VBtn, VTextField },
  setup() {
    const name = ref('');

    const submit = () => {
      console.log('Submitted:', name.value);
    };

    return {
      name,
      submit,
    };
  },
  template: `
    <div>
      <h3>Vue Vuetify (Native)</h3>
      <v-text-field 
        label="Name" 
        v-model="name" 
      />
      <v-btn variant="flat" @click="submit">
        Submit
      </v-btn>
      <p>Vuetify is native in Vue - but can't use React pkgs like MUI directly.</p>
    </div>
  `,
});