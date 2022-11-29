import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { MultiStepBuyForm } from '../../components/buyform/MultiStepBuyForm';
import { MultiStepBuyProgressBar } from '../../components/buyform/MultiStepBuyProgressBar';
import { DotSpinner } from '@uiball/loaders';
import { useSelector } from 'react-redux';
import { apiServer } from '../../features/axios';
import { useLocation } from 'react-router-dom';

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
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ProgressBarBox = styled.div`
  width: 20em;
  height: 2em;
  margin-bottom: 2em;
`;

function BuyFoamPage() {
  const [index, setIndex] = useState(1);
  const [isLoading, setIsLoading] = useState(false);

  const [userData, setUserData] = useState({
    memberId: 1,
    clientId: 2,
    email: 'hello2@naver.com',
    name: '김통신',
    phone: '010-4444-4444',
    address: '통신광역시 통신구 통신',
    role: 'SELLER',
    introduce: null,
    imageUrl: null,
  });

  const [itemData, setItemData] = useState({
    boardId: 1,
    productId: 1,
    sellerId: 1,
    name: '박응답',
    title: '통신테스트',
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
  });

  const location = useLocation();
  const boardInfo = location.state.boardId;
  const countInfo = location.state.quantity;
  console.log(boardInfo);
  console.log(countInfo);
  const userInfo = useSelector((state) => state.user.clientId);

  useEffect(() => {
    console.log('데이터 받아오기');
    const getItem = async () => {
      // await axios
      //   .get(`${process.env.REACT_APP_API_URL}/boards/4`)
      //   .then((res) => {
      //     console.log(res.data);
      //     setItemData({ ...itemData, ...res.data });
      //   })
      //   .catch((error) => console.log(error));
      await apiServer({ method: 'GET', url: `/boards/${boardInfo}` })
        .then((res) => {
          console.log(res.data);
          setItemData({ ...itemData, ...res.data });
        })
        .catch((error) => console.log(error));
    };
    const getUserData = async () => {
      await apiServer({ method: 'GET', url: `/members/client/${userInfo}` })
        .then((res) => {
          console.log(res.data);
          setUserData({ ...userData, ...res.data });
        })
        .catch((error) => console.log(error));
    };
    getUserData();
    getItem();
  }, []);

  const count = countInfo;
  const totalPrice = itemData.price * count;

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
            {!isLoading && (
              <MultiStepBuyForm
                index={index}
                nextButton={nextButton}
                userData={userData}
                itemData={itemData}
                count={count}
                price={totalPrice}
                setIsLoading={setIsLoading}
              />
            )}
            {isLoading && <DotSpinner size={40} speed={0.9} color="var(--green)" />}
          </MultiStepBox>
          {/* <UserBox></UserBox>
          <ItemBox></ItemBox> */}
        </FormBox>
      </FormLayout>
    </>
  );
}

export default BuyFoamPage;
