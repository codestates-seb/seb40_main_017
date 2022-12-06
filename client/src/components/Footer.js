import styled from 'styled-components';
import logoImg from '../assets/images/logo.svg';

const Footerbox = styled.footer`
  width: 100%;
  height: 200px;
  padding: 0 100px;
  background-color: var(--brown);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--white);
`;

const Logo = styled.p`
  font-size: 30px;
`;

const Team = styled.div`
  margin-left: 30px;
`;

const LogoImg = styled.img`
  width: 180px;
  height: 180px;
  margin-right: 20px;
`;
function Footer() {
  return (
    <Footerbox>
      <LogoImg src={logoImg} alt="로고이미지" />
      <Logo>17시 내고향</Logo>
      <Team>
        <p>FE : 남궁태욱 김민성 권수현</p>
        <p>BE : 신지훈 가빈 신승현</p>
      </Team>
    </Footerbox>
  );
}

export default Footer;
