import { useState } from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import { apiServer } from '../../features/axios';
import { DotSpinner } from '@uiball/loaders';

const PayFormBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ImgInfo = styled.div`
  width: 20em;
  height: 20em;
  img {
    border-radius: 2em;
  }
  @media (max-width: 1199px) {
    display: flex;
    justify-content: center;
    align-items: center;
    img {
      width: 13em;
      height: 15em;
    }
  }
  @media (max-width: 991px) {
    img {
      width: 10em;
      height: 10em;
    }
  }
  @media (max-width: 768px) {
    display: none;
  }
`;

const PayInfo = styled.div`
  width: 20em;
  height: 20em;
  padding: 2em;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  border-radius: 1em;
  h2 {
    font-size: 44px;
  }
  p {
    font-size: 14px;
    color: red;
  }
  .checkbox {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  label {
    font-size: 16px;
  }
  button {
    background: var(--blue);
    color: var(--white);
    border: 2px solid var(--white);
    border-radius: 1em;
    width: 10em;
    height: 3em;
    transition: 0.3s;
    :hover {
      transform: scale(1.2);
    }
  }
  @media (max-width: 1199px) {
    height: 80%;
    padding: 1em;
    h2 {
      font-size: 40px;
    }
    div {
      font-size: 26px;
    }
  }
  @media (max-width: 991px) {
    padding: 0.5em;
    h2 {
      font-size: 32px;
    }
    div {
      font-size: 20px;
    }
    p {
      font-size: 10px;
    }
    label {
      font-size: 13px;
    }
  }
`;

export const PayForm = ({ price }) => {
  const [isCheck, setIsCheck] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const orderId = useSelector((state) => state.pay.orderid);

  const changeCheck = (e) => {
    if (e.target.checked) {
      setIsCheck(true);
    } else {
      setIsCheck(false);
    }
  };

  const handleOnClick = async () => {
    if (isCheck) {
      setIsLoading(true);
      await apiServer({ method: 'GET', url: `/order/pay/${orderId}` })
        .then((res) => {
          window.location.href = res.data.next_redirect_pc_url;
        })
        .catch((err) => console.log(err));
    }
  };
  return (
    <>
      <PayFormBox>
        <ImgInfo>
          <img src="https://ifh.cc/g/TPDLfn.jpg" alt="결제" />
        </ImgInfo>
        {!isLoading && (
          <PayInfo>
            <h2>총금액</h2>
            <div>가격</div>
            <div>{price}원</div>
            <p>생산자 산지직거래 상품으로 환불, 반품은 불가합니다.</p>
            <div className="checkbox">
              <input type={'checkbox'} id="결제확인" name="결제확인" onClick={(e) => changeCheck(e)} />
              <label htmlFor="결제확인">결제 확인 했습니다.</label>
            </div>
            <button onClick={handleOnClick}>결제하기</button>
          </PayInfo>
        )}
        {isLoading && <DotSpinner className="animation" size={70} speed={1.75} color="var(--green)" />}
      </PayFormBox>
    </>
  );
};
