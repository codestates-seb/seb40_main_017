import styled from 'styled-components';

const SellerPostLayout = styled.div`
  background: var(--off-white);
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SellerPostHeader = styled.div`
  width: 1100px;
  height: 50px;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  font-size: 30px;
  font-weight: 500;
  gap: 10px;
  border-bottom: 5px solid var(--green);
  .head {
    color: var(--green);
  }
`;

const SellerPostContent = styled.div`
  width: 1100px;
  height: 700px;
  background: blanchedalmond;
  display: grid;
  grid-template-columns: 1fr 3.5fr;
  grid-template-rows: 4fr 1fr 1fr 1fr 1fr;
  div {
    border-bottom: 1px solid white;
  }
  .headtext {
    background: var(--green);
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    font-weight: bold;
    color: var(--white);
  }
  .content {
  }
`;

function SellerPostPage() {
  return (
    <SellerPostLayout>
      <SellerPostHeader>
        <p className="head">판매 물품</p>
        <p>등록</p>
      </SellerPostHeader>
      <SellerPostContent>
        <div className="headtext">이미지</div>
        <div>2</div>
        <div className="headtext">제목</div>
        <div>
          <input></input>
        </div>
        <div className="headtext">판매수량</div>
        <div>
          <input></input>
        </div>
        <div className="headtext">상품가격</div>
        <div>
          <input></input>
        </div>
        <div className="headtext">카테고리</div>
        <div>0</div>
      </SellerPostContent>
    </SellerPostLayout>
  );
}

export default SellerPostPage;
