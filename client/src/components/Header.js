import styled from 'styled-components';
import { FiMenu } from 'react-icons/fi';
import { getUser } from '../features/user/userSlice';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';

const Headerbox = styled.header`
  width: 100%;
  height: 80px;
  background-color: var(--green);
  position: sticky;
  top: 0;
  z-index: 10;
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

const Logo = styled(Link)`
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
  a {
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

const Header = () => {
  //  Redux Provider 로 부터 사용자 정보 수신
  const user = useSelector(getUser);

  return (
    <Headerbox>
      <Menu />
      <Logo to="/">17시 내고향</Logo>
      <UserInfo>
        {user.memberId ? (
          <>
            <Link to="/mypage">마이페이지</Link>
            <Link to="/logout">로그아웃</Link>
          </>
        ) : (
          <>
            <Link to="/login">로그인</Link>
            <Link to="/signup">회원가입</Link>
          </>
        )}
      </UserInfo>
    </Headerbox>
  );
};

export default Header;
