import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import vue from '@vitejs/plugin-vue';
import { resolve } from 'path';
import { readdirSync, statSync } from 'fs';

// Автоматически находим все .tsx файлы в src/react
function getReactEntries() {
  const entries: Record<string, string> = {};
  const reactDir = resolve(__dirname, 'src/react');

  try {
    const files = readdirSync(reactDir);
    files.forEach(file => {
      if (file.endsWith('.tsx') || file.endsWith('.ts')) {
        const name = file.replace(/\.(tsx|ts)$/, '');
        entries[`react/${name}`] = resolve(reactDir, file);
      }
    });
  } catch (e) {
    console.log('No React files found yet');
  }

  return entries;
}

// Автоматически находим все .ts файлы в src/vue
function getVueEntries() {
  const entries: Record<string, string> = {};
  const vueDir = resolve(__dirname, 'src/vue');

  try {
    const files = readdirSync(vueDir);
    files.forEach(file => {
      if (file.endsWith('.ts')) {
        const name = file.replace(/\.ts$/, '');
        entries[`vue/${name}`] = resolve(vueDir, file);
      }
    });
  } catch (e) {
    console.log('No Vue files found yet');
  }

  return entries;
}

export default defineConfig({
  plugins: [react(), vue()],

  build: {
    outDir: 'public',
    emptyOutDir: false, // Не удаляем public, там лежат HTML и стили

    rollupOptions: {
      input: {
        ...getReactEntries(),
        ...getVueEntries(),
      },

      output: {
        entryFileNames: '[name].js',
        chunkFileNames: 'chunks/[name]-[hash].js',
        assetFileNames: 'assets/[name]-[hash][extname]'
      }
    }
  },

  server: {
    port: 3001,
  }
});
