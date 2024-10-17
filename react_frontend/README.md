# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh


# React + Vite Frontend

This project is a minimal setup to get React working with Vite, including Hot Module Replacement (HMR) and ESLint configuration.

## Project Structure
react_frontend/ 
├── .gitignore 
├── eslint.config.js 
├── index.html 
├── package.json 
├── public/ 
├── README.md 
├── src/ 
│ ├── App.css 
│ ├── App.jsx 
│ ├── assets/ 
│ ├── index.css 
│ ├── main.jsx 
├── vite.config.js


## Available Scripts

In the project directory, you can run:

### `npm run dev`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm run build`

Builds the app for production to the `dist` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

### `npm run lint`

Runs ESLint to analyze the code for potential errors and code style issues.

### `npm run preview`

Serves the production build locally to preview it.

## ESLint Configuration

The project uses ESLint with the following plugins:

- `eslint-plugin-react`
- `eslint-plugin-react-hooks`
- `eslint-plugin-react-refresh`

The ESLint configuration can be found in [eslint.config.js](react_frontend/eslint.config.js).

## Vite Configuration

The Vite configuration is located in [vite.config.js](react_frontend/vite.config.js). It includes the `@vitejs/plugin-react` plugin for React support.

## Learn More

To learn more about React and Vite, check out the following resources:

- [React Documentation](https://react.dev)
- [Vite Documentation](https://vitejs.dev)

## License

This project is licensed under the MIT License.