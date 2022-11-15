import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import { FiPackage } from 'react-icons/fi';
import { GiShinyApple, GiCarrot, GiGrain, GiPeanut } from 'react-icons/gi';

const Homenavbox = styled.div`
  background: var(--white);
  width: 100%;
  height: 350px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  div {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 15px;
  }
`;
const StyledLink = styled(NavLink)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 15px;
  padding-bottom: 5px;
  border-bottom: 3px solid var(--brown);
  p {
    font-size: 20px;
  }
  .icon {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    background: var(--brown);
    :hover {
      background: var(--light-brown);
    }
  }
`;

function HomeNav() {
  return (
    <Homenavbox>
      <StyledLink to={'/boards'}>
        <div className="icon">
          <FiPackage size={50} color={'var(--white)'} />
        </div>
        <p>전체</p>
      </StyledLink>
      <StyledLink to={'/boards/fruit'}>
        <div className="icon">
          <GiShinyApple size={50} color={'var(--white)'} />
        </div>
        <p>과일</p>
      </StyledLink>
      <StyledLink to={'/boards/vegetable'}>
        <div className="icon">
          <GiCarrot size={50} color={'var(--white)'} />
        </div>
        <p>채소</p>
      </StyledLink>
      <StyledLink to={'/boards/grain'}>
        <div className="icon">
          <GiGrain size={75} color={'var(--white)'} />
        </div>
        <p>곡물</p>
      </StyledLink>
      <StyledLink to={'/boards/nut'}>
        <div className="icon">
          <GiPeanut size={50} color={'var(--white)'} />
        </div>
        <p>견과류</p>
      </StyledLink>
    </Homenavbox>
  );
}

export default HomeNav;