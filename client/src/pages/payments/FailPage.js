import styled from 'styled-components';

const FailLayout = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: var(--off-white);
`;

const FailBox = styled.section`
  font-size: 30px;
  width: 25em;
  height: 25em;
  padding: 1em;
  background: var(--white);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5em;
  border-radius: 0.5em;
  box-shadow: 0px 4px 5px 1px #8e8e8e;
  h1 {
    font-size: 30px;
  }
`;

const FailInfo = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  p {
    font-size: 16px;
  }
  div {
    width: 400px;
    height: 200px;
    background: url('https://ifh.cc/g/DZMzWN.png');
  }
  button {
    margin-top: 2em;
    width: 7em;
    height: 3em;
    font-size: 14px;
    border-radius: 1em;
    background: var(--green);
    color: var(--white);
    border: 1px solid transparent;
    transition: 0.3s;
    :hover {
      scale: 1.1;
    }
  }
`;

function FailPage() {
  return (
    <>
      <FailLayout>
        <FailBox>
          <h1>주문 실패</h1>
          <FailInfo>
            <div></div>
            <p>주문이 실패하였습니다</p>
            <button>확인</button>
          </FailInfo>
        </FailBox>
      </FailLayout>
    </>
  );
}

export default FailPage;
