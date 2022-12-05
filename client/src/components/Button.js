import { Link } from 'react-router-dom';
import styled from 'styled-components';

import KakaoLoginImage from '../assets/images/kakao_login_medium_wide.png';

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

const StyledAnchorButton = styled.a`
  ${ButtonStyle}
`;

export const AnchorButton = ({ href, children }) => {
  return <StyledAnchorButton href={href}>{children}</StyledAnchorButton>;
};

const StyleSnsButton = styled.a`
  display: block;
  margin-top: 12px;
  font-size: 0;
  cursor: pointer;
`;

export const KakaoSubmitbutton = ({ href }) => {
  return (
    <StyleSnsButton href={href}>
      <img src={KakaoLoginImage} alt="카카오 로그인" width={'100%'} />
    </StyleSnsButton>
  );
};
