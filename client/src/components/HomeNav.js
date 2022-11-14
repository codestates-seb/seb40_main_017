import styled from 'styled-components';
import { FiPackage } from 'react-icons/fi';
import { GiShinyApple, GiCarrot, GiGrain, GiPeanut } from 'react-icons/gi';

const Homenavbox = styled.div`
  background: var(--darker-gray);
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
  p {
    font-size: 20px;
  }
  .icon {
    width: 150px;
    height: 150px;
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
      <div>
        <div className="icon">
          <FiPackage size={75} color={'var(--white)'} />
        </div>
        <p>전체</p>
      </div>
      <div>
        <div className="icon">
          <GiShinyApple size={75} color={'var(--white)'} />
        </div>
        <p>과일</p>
      </div>
      <div>
        <div className="icon">
          <GiCarrot size={75} color={'var(--white)'} />
        </div>
        <p>채소</p>
      </div>
      <div>
        <div className="icon">
          <GiGrain size={75} color={'var(--white)'} />
        </div>
        <p>곡물</p>
      </div>
      <div>
        <div className="icon">
          <GiPeanut size={75} color={'var(--white)'} />
        </div>
        <p>견과류</p>
      </div>
    </Homenavbox>
  );
}

export default HomeNav;
