import ReactDOM from 'react-dom/client';
import App from './App';
import GlobalStyle from './assets/styles/GlobalStyles';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <>
    <GlobalStyle />
    <App />
  </>
);
