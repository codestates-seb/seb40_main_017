import styled from 'styled-components';

const CompleteLayout = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: var(--off-white);
`;
const CompleteBox = styled.div`
  font-size: 30px;
  width: 25em;
  height: 25em;
  padding: 0.5em;
  background: var(--white);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5em;
  border-radius: 0.5em;
  box-shadow: 0px 4px 5px 1px #8e8e8e;
`;
const InfoBox = styled.div`
  width: 100%;
  height: 20em;
  display: flex;
`;

const ButtonBox = styled.div`
  width: 50%;
  height: 100%;
  background: rebeccapurple;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  div {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    bottom: 8em;
    button {
      margin-top: 2em;
    }
  }
`;
const ItemBox = styled.div`
  width: 50%;
  height: 100%;
  background: darkgreen;
  display: flex;
  flex-direction: column;
`;

function CompletePage() {
  return (
    <>
      <CompleteLayout>
        <CompleteBox>
          <h1>주문 완료</h1>
          <InfoBox>
            <ButtonBox>
              <div>
                <p>고객님의</p>
                <p>주문이 완료 되었습니다.</p>
                <button>확인</button>
              </div>
            </ButtonBox>
            <ItemBox>
              <h3>상품 정보</h3>
              <div>가격</div>
              <div>상품명</div>
              <div>성함</div>
              <div>전화번호</div>
              <div>주소</div>
            </ItemBox>
          </InfoBox>
        </CompleteBox>
      </CompleteLayout>
    </>
  );
}

export default CompletePage;
