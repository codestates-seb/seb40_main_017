import styled from 'styled-components';
import { FiCheckCircle } from 'react-icons/fi';
import { useNavigate } from 'react-router-dom';

const CompleteBox = styled.div`
  width: 100%;
  height: 100%;
  padding-bottom: 6em;
  display: flex;
  position: relative;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
`;
const ButtonBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  position: absolute;
  bottom: -2em;
  button {
    color: var(--black);
    width: 10em;
    height: 3em;
    font-size: 16px;
    background: var(--white);
    border-radius: 15px;
    border: 3px solid var(--green);
    transition: 0.25s;
    :hover {
      background: var(--green);
      color: var(--white);
      transform: scale(1.2);
    }
  }
`;

export const SellerComplete = () => {
  const navigate = useNavigate();

  const handleOnClick = async () => {
    navigate('/', { replace: true });
  };

  return (
    <>
      <CompleteBox>
        <FiCheckCircle size={80} color="var(--green)" />
        <h3>판매물품</h3>
        <h3>등록이 완료 되었습니다</h3>
        <ButtonBox>
          <button onClick={handleOnClick}> 확인</button>
        </ButtonBox>
      </CompleteBox>
    </>
  );
};
