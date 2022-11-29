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

function BuyFormPage() {
  const [index, setIndex] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [userData, setUserData] = useState({});
  const [itemData, setItemData] = useState({});

  const location = useLocation();
  const boardInfo = location.state.boardId;
  const countInfo = location.state.quantity;
  const userInfo = useSelector((state) => state.user.clientId);

  useEffect(() => {
    console.log('데이터 받아오기');
    const getItem = async () => {
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
        </FormBox>
      </FormLayout>
    </>
  );
}

export default BuyFormPage;
