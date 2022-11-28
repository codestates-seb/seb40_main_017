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
  font-size: 30px;
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
        <p>FE</p>
        <p>BE</p>
      </Team>
    </Footerbox>
  );
}

export default Footer;
