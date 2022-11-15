import styled from 'styled-components';
import { FiMenu } from 'react-icons/fi';

const Headerbox = styled.header`
  width: 100%;
  height: 80px;
  background-color: var(--green);
  position: sticky;
  display: flex;
  justify-content: space-between;
`;

const Menu = styled(FiMenu)`
  color: var(--white);
  font-size: 50px;
  margin-left: 20px;
  margin-top: 15px;
  cursor: pointer;
  opacity: 0.8;
  :hover {
    opacity: 1;
    transition: 0.4s;
  }
`;

const Logo = styled.p`
  color: var(--white);
  line-height: 80px;
  font-size: 30px;
  margin-left: 20px;
  cursor: pointer;
`;

const UserInfo = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  button {
    all: unset;
    cursor: pointer;
    border: 1px solid var(--white);
    color: var(--white);
    padding: 10px 20px;
    margin-right: 20px;
    border-radius: 5px;
    opacity: 0.8;
    :hover {
      opacity: 1;
      transition: 0.4s;
    }
  }
`;

function Header() {
  return (
    <Headerbox>
      <Menu />
      <Logo>6시 내고향</Logo>
      <UserInfo>
        <button>로그인</button>
        <button>회원가입</button>
      </UserInfo>
    </Headerbox>
  );
}

export default Header;
