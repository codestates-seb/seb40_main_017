import styled from 'styled-components';

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
  p {
    font-size: 16px;
    color: red;
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
    :hover {
      transform: scale(1.2);
    }
  }
`;

export const PayForm = ({ price }) => {
  const handleOnClick = () => {
    const checkbox = document.getElementById('결제확인');
    const is_checked = checkbox.checked;
    if (is_checked) {
      let link = 'https://online-pay.kakao.com/mockup/v1/7ad25dbdc72ea560c9b67e567d9a1d32db19abb52bcf8a5bc7da1cde2a424a37/info';
      window.location.href = link;
    }
  };
  return (
    <>
      <PayFormBox>
        <ImgInfo>
          <img src="https://ifh.cc/g/TPDLfn.jpg" alt="결제" />
        </ImgInfo>
        <PayInfo>
          <h2>총금액</h2>
          <div>가격</div>
          <div>{price}원</div>
          <p>생산자 산지직거래 상품으로 환불, 반품은 불가합니다.</p>
          <div>
            <input type={'checkbox'} id="결제확인" name="결제확인" />
            <label htmlFor="결제확인">결제 확인 했습니다.</label>
          </div>
          <button onClick={handleOnClick}>결제하기</button>
        </PayInfo>
      </PayFormBox>
    </>
  );
};
