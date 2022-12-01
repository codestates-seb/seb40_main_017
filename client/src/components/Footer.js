import styled from 'styled-components';

const Footerbox = styled.footer`
  width: 100%;
  height: 200px;
  padding: 0 100px;
  background-color: var(--brown);
  display: flex;
  align-items: center;
  color: var(--white);
`;

const Logo = styled.p`
  font-size: 187.5%;
`;

const Team = styled.div`
  border-right: 2px solid var(--white);
  padding: 35px 300px 35px 100px;
`;

function Footer() {
  return (
    <Footerbox>
      <Logo>17시 내고향</Logo>
      <Team>
        <p>FE : 남궁태욱 김민성 권수현</p>
        <p>BE : 신지훈 가빈 신승현</p>
      </Team>
    </Footerbox>
  );
}

export default Footer;
