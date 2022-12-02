import styled from 'styled-components';
import { FiTruck } from 'react-icons/fi';
import { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { apiServer } from '../../features/axios';

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
const InfoBox = styled.div`
  width: 100%;
  height: 20em;
  display: flex;
`;

const ButtonBox = styled.div`
  width: 50%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  section {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    gap: 1em;
    bottom: 6em;
    p {
      font-size: 18px;
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
  }
`;
const ItemBox = styled.div`
  width: 50%;
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 1em;
  padding-top: 5em;
  h3 {
    font-size: 23px;
  }
  ul {
    margin-top: 2em;
    display: flex;
    flex-direction: column;
    gap: 1em;
    font-size: 16px;
  }
`;

function CompletePage() {
  const [data, setData] = useState({});
  const [query] = useSearchParams();
  const navigate = useNavigate();
  let orderId = query.get('ordId');
  console.log(orderId);
  useEffect(() => {
    const completeData = async () => {
      // const order = await axios
      //   .get(`${process.env.REACT_APP_API_URL}/orders/${orderId}`, { headers: { 'Content-Type': 'application/json' } })
      //   .then((res) => {
      //     console.log(res);
      //     setData({ ...data, ...res.data });
      //   })
      //   .catch((error) => console.log(error));
      const order = await apiServer({ method: 'GET', url: `/orders/${orderId}` })
        .then((res) => {
          console.log(res);
          setData({ ...data, ...res.data });
        })
        .catch((error) => console.log(error));

      console.log(order);
    };

    completeData();
  }, []);

  const handleOnClick = () => {
    navigate('/', { replace: true });
  };
  return (
    <>
      <CompleteLayout>
        <CompleteBox>
          <h1>주문 완료</h1>
          <InfoBox>
            <ButtonBox>
              <section>
                <FiTruck size={50} color="var(--black)" fill="var(--green)" />
                <p>고객님의</p>
                <p>주문이 완료 되었습니다.</p>
                <button onClick={handleOnClick}>확인</button>
              </section>
            </ButtonBox>
            <ItemBox>
              <h3>상품 정보</h3>
              <ul>
                <li>
                  가격 : <span>{data.totalPrice}</span>
                </li>
                <li>
                  성함 : <span>{data.name}</span>
                </li>
                <li>
                  전화번호 : <span>{data.phone}</span>
                </li>
                <li>
                  주소 : <span>{data.address}</span>
                </li>
              </ul>
            </ItemBox>
          </InfoBox>
        </CompleteBox>
      </CompleteLayout>
    </>
  );
}

export default CompletePage;
