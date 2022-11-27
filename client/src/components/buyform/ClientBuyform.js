import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { payActions } from '../../features/pay/paySlice';
import axios from 'axios';
import { useEffect } from 'react';

const BuyFormBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
`;
const UserBox = styled.form`
  position: relative;
  width: 66%;
  height: 100%;
  display: flex;
  flex-direction: column;
  p {
    font-size: 16px;
    text-align: center;
  }
`;
const UserInfo = styled.div`
  margin-top: 2em;
  margin-bottom: 7em;
  div {
    display: grid;
    grid-template-columns: 0.5fr 2fr;
    row-gap: 2em;
    padding-right: 1em;
    p {
      font-size: 16px;
      display: flex;
      align-items: center;
    }
    input {
      width: 20em;
      height: 2.5em;
    }
  }
`;

const ButtonBox = styled.div`
  margin-top: 1em;
  display: flex;
  justify-content: space-around;
  button,
  input {
    width: 10em;
    height: 3em;
    transition: 0.3s;
    :hover {
      transform: scale(1.2);
    }
  }
  .order {
    color: var(--white);
    background: var(--green);
    border: 2px solid var(--green);
    border-radius: 0.5em;
  }
  .cancel {
    background: var(--white);
    border: 1px solid var(--black);
    border-radius: 0.5em;
  }
`;

const ItemBox = styled.div`
  width: 34%;
  height: 100%;
  border-left: 1px solid black;
  padding-left: 1em;
  span {
    margin-right: 2em;
    font-size: 23px;
  }
`;

const ItemInfo = styled.div`
  margin-top: 1em;
  display: flex;
  flex-direction: column;
  gap: 0.5em;
  img {
    width: 3em;
    height: 3em;
    background: darkgreen;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  p {
    font-size: 16px;
  }
  span {
    font-size: 16px;
  }
`;

const ItemPrice = styled.div`
  border-top: 1px solid var(--green);
  border-bottom: 1px solid var(--green);
  padding: 0.5em 0;
  margin: 0.5em 0;
  display: grid;
  grid-template-columns: 1fr 1fr;
  row-gap: 0.5em;
  p {
    font-size: 16px;
  }
`;
const InputBox = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 10em;
  height: 1em;
  margin-bottom: 0em;
`;
const ErrorBox = styled.div`
  position: absolute;
  font-size: 16px;
  color: red;
  top: 2.5em;
  p {
    width: 11em;
    font-size: 10px;
  }
  margin-bottom: 0em;
`;

export const ClientBuyForm = ({ nextButton, userData, itemData, count, price, setIsLoading }) => {
  const userFormData = userData;
  console.log(userFormData);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const payInfo = useSelector((state) => state.pay.tid);
  const orderInfo = useSelector((state) => state.pay.orderid);
  const {
    register,
    formState: { errors },
    handleSubmit,
    reset,
  } = useForm({ defaultValues: { phone: userFormData.phone, address: userFormData.address } });

  useEffect(() => {
    reset({ phone: userFormData.phone, address: userFormData.address });
  }, [userFormData]);
  const orderData = {
    clientId: userData.clientId,
    boardId: itemData.boardId,
    address: '~~~',
    phone: '~~~',
    quantity: count,
    totalPrice: price,
  };

  const onSubmit = async (data) => {
    console.log(data);
    const newData = { ...orderData, ...data };
    console.log(newData);
    if (window.confirm('주문 확인')) {
      setIsLoading(true);
      await axios
        .post(`${process.env.REACT_APP_API_URL}/orders`, JSON.stringify(newData), { headers: { 'Content-Type': 'application/json' } })
        .then((res) => {
          console.log(res);
          console.log(res.data.ordId);
          let orderId = res.data.ordId;
          console.log(orderId);
          dispatch(payActions.setPay({ orderid: res.data.ordId }));
        })
        .catch((err) => console.log(err));
      // dispatch(payActions.setPay({ orderid: 1, tid: 777 }));
      console.log(payInfo);
      console.log(orderInfo);
      setIsLoading(false);
      nextButton();
    }
  };

  const handleOnClick = () => {
    navigate(-1);
  };
  return (
    <>
      <BuyFormBox>
        <UserBox onSubmit={handleSubmit(onSubmit)}>
          <h3>주문 정보 확인</h3>
          <UserInfo>
            <div>
              <p>이름</p>
              <input placeholder="이름" key={userFormData.name} defaultValue={userFormData.name} readOnly></input>
              <p>전화번호</p>
              <InputBox>
                <input name="phone" placeholder="전화번호" {...register('phone', { required: true })} />
                <ErrorBox>{errors.phone && <p>전화번호를 적어 주세요</p>}</ErrorBox>
              </InputBox>
              <p>주소</p>
              <InputBox>
                <input name="address" placeholder="주소" {...register('address', { required: true })}></input>
                <ErrorBox>{errors.address && <p>주소를 적어 주세요</p>}</ErrorBox>
              </InputBox>
            </div>
          </UserInfo>
          <p>구매 정보를 확인했습니다.</p>
          <ButtonBox>
            <button className="cancel" onClick={handleOnClick}>
              이전단계
            </button>
            <input className="order" type="submit" value="주문하기" />
          </ButtonBox>
        </UserBox>
        <ItemBox>
          <h3>상품</h3>
          <ItemInfo>
            <img src={itemData.mainImage} alt="사진" />
            <p>
              상품명: <span>{itemData.title}</span>
            </p>
            <p>
              판매자 : <span>{itemData.name}</span>
            </p>
            <p>
              수량: <span>{count}</span>
            </p>
          </ItemInfo>
          <ItemPrice>
            <p>상품 금액</p>
            <p>{itemData.price}원</p>
          </ItemPrice>
          <span>총액</span>
          <span>{price}원</span>
        </ItemBox>
      </BuyFormBox>
    </>
  );
};
