import ReactDOM from 'react-dom/client';
import App from './App';
import GlobalFonts from './assets/styles/fonts/fonts';
import GlobalStyle from './assets/styles/GlobalStyles';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <>
    <GlobalFonts />
    <GlobalStyle />
    <App />
  </>
);
