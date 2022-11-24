import { useState } from 'react';
import styled from 'styled-components';
import { MultiStepBuyForm } from '../../components/buyform/MultiStepBuyForm';
import { MultiStepBuyProgressBar } from '../../components/buyform/MultiStepBuyProgressBar';

const FormLayout = styled.div`
  background: var(--off-white);
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const FormBox = styled.div`
  font-size: 30px;
  width: 40em;
  height: 25em;
  padding: 2em;
  background: var(--white);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 0.5em;
  box-shadow: 0px 4px 5px 1px #8e8e8e;
`;

const MultiStepBox = styled.div`
  width: 90%;
  height: 100%;
  /* background: darkmagenta; */
`;

const ProgressBarBox = styled.div`
  width: 20em;
  height: 2em;
  margin-bottom: 2em;
`;

function BuyFoamPage() {
  const [index, setIndex] = useState(1);

  const userData = {
    memberId: 3,
    sellerId: 1,
    email: 'hello2@naver.com',
    name: '박생산',
    phone: '010-2222-2222',
    address: '제주도 서귀포시 성산읍',
    role: 'SELLER',
    introduce: null,
    imageUrl: null,
  };

  const itemData = {
    boardId: 1,
    productId: 1,
    sellerId: 1,
    name: '박생산',
    title: '쌀을 팝니다',
    content: '이 쌀은 맛이 좋아요!',
    price: 1000,
    stock: 200,
    category: 2,
    status: 'PRD_SELLING',
    view: 3,
    createdAt: '2022-11-20T22:03:07.452223',
    modifiedAt: '2022-11-20T22:16:35.8949572',
    reviewAvg: 2.0,
    soldStock: 0,
    mainImage: 'https://waymophototest.s3-ap-northeast-2.amazonaws.com/당근-1.png',
  };

  const count = 4;
  const totalPrice = itemData.price * 4;

  const nextButton = () => {
    if (index < 3) {
      setIndex((prevIndex) => prevIndex + 1);
    }
  };
  return (
    <>
      <FormLayout>
        <ProgressBarBox>
          <MultiStepBuyProgressBar step={index} />
        </ProgressBarBox>
        <FormBox>
          <MultiStepBox>
            <MultiStepBuyForm index={index} nextButton={nextButton} userData={userData} itemData={itemData} count={count} price={totalPrice} />
          </MultiStepBox>
          {/* <UserBox></UserBox>
          <ItemBox></ItemBox> */}
        </FormBox>
      </FormLayout>
    </>
  );
}

export default BuyFoamPage;
