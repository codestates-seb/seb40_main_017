import styled from 'styled-components';
import { FiMenu } from 'react-icons/fi';
import { getUser } from '../features/user/userSlice';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import Sidebar from './Sidebar';
import { useState } from 'react';

const Headerbox = styled.header`
  width: 100%;
  height: 80px;
  background-color: var(--green);
  position: sticky;
  top: 0;
  z-index: 10;
  display: flex;
  justify-content: space-between;
  .active {
    left: 0em;
  }
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
  margin-left: 100px;
  cursor: pointer;
  @font-face {
    font-family: 'BMEuljiro10yearslater';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.0/BMEuljiro10yearslater.woff') format('woff');
    font-weight: normal;
    font-style: normal;
  }
  font-family: 'BMEuljiro10yearslater';
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
  const user = useSelector(getUser);
  const [sidebar, setSidebar] = useState(false);
  const handleOnClick = () => {
    if (!sidebar) {
      setSidebar(true);
      document.getElementById('sidebar').classList.add('active');
    } else {
      setSidebar(false);
      document.getElementById('sidebar').classList.remove('active');
    }
  };
  return (
    <Headerbox>
      <Menu onClick={handleOnClick} />
      <Sidebar />
      <Logo to="/">17시 내고향</Logo>
      <UserInfo>
        {user ? (
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
