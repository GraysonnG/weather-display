import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	assetsInclude: ['**/*.ttf'],
	esbuild: {
		jsxFactory: 'h',
		jsxFragment: 'Fragment',
		include: /src\/.*\.jsx?$/,
	},
});
