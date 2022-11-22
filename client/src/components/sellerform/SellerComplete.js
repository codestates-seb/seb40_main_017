import styled from 'styled-components';
import { FiCheckCircle } from 'react-icons/fi';
// import axios from 'axios';

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

export const SellerComplete = ({ formData }) => {
  const handleOnClick = async () => {
    // await axios({
    //   url: `${process.env.REACT_APP_API_URL}/boards`,
    //   method: 'post',
    //   data: JSON.stringify(formData),
    //   headers: { 'Content-Type': 'application/json' },
    // })
    //   .then((res) => console.log(res))
    //   .catch((err) => console.log(err));
    console.log(formData);
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
