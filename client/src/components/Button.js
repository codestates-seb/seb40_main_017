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

//  <button> 태그에 스타일 적용
const StyledButton = styled.button`
  ${ButtonStyle}
`;

//  type 속성을 받아 <button> 태그와 매칭하는 컴포넌트 구현
export const Button = ({ type, children }) => {
  return <StyledButton type={type || 'button'}>{children}</StyledButton>;
};

//  type을 submit 으로 적용하는 컴포넌트 구현 (Form 에서 서버로 전송하기 위한 버튼)
export const SubmitButton = ({ children }) => {
  return <StyledButton type="submit">{children}</StyledButton>;
};

//  React Router 에서 제공되는 Link 컴포넌트에 스타일 적용
const StyledLinkButton = styled(Link)`
  ${ButtonStyle}
`;

//  to 속성을 받아 페이지를 넘어갈 수 있도록 컴포넌트 구현
export const LinkButton = ({ to, children }) => {
  return <StyledLinkButton to={to}>{children}</StyledLinkButton>;
};
