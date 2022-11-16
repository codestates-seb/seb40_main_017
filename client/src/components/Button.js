import { Link } from 'react-router-dom';
import styled from 'styled-components';

const ButtonStyle = `
  display: block;

  font-size: 20px;
  line-height: 1.25;

  width: 100%;

  margin-top: 12px;
  padding: 8px;

  border: 0;
  border-radius: 4px;

  color: #fff;
  background-color: #5d9061;

  cursor: pointer;
  text-align: center;

  &:hover {
    background-color: #4b704e;
  }
`;

const StyledButton = styled.button`
  ${ButtonStyle}
`;

export const Button = ({ type, children }) => {
  return <StyledButton type={type || 'button'}>{children}</StyledButton>;
};

export const SubmitButton = ({ children }) => {
  return <StyledButton type="submit">{children}</StyledButton>;
};

const StyledLinkButton = styled(Link)`
  ${ButtonStyle}
`;

export const LinkButton = ({ to, children }) => {
  return <StyledLinkButton to={to}>{children}</StyledLinkButton>;
};
