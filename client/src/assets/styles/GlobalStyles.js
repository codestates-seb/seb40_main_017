import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    font-size: 100%;
  }

  li {
    list-style: none;
  }

  a {
    text-decoration: none;
    color: inherit;
  }

  button {
    cursor: pointer;
  }
  :root {
    --black: #000;
    --white: #fff;
    --off-white: #f0e9df;
    --orange: #d26a51;
    --yellow: #aba35a;
    --blue: #5561c7;
    --green: #5d9061;
    --brown: #543939;
    --light-brown: #b2956d;
    --darker-gray: #d5ccbe; 
  }

`;

export default GlobalStyle;