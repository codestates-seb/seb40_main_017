import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { payActions } from '../../features/pay/paySlice';
import { useEffect } from 'react';
import { apiServer } from '../../features/axios';

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
  @media (max-width: 1199px) {
    .navbox {
      width: 100%;
      position: absolute;
      bottom: 0em;
      left: 0;
    }
    h3 {
      font-size: 30px;
    }
  }
  @media (max-width: 991px) {
    .navbox {
      p {
        font-size: 14px;
      }
    }
    h3 {
      font-size: 26px;
    }
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
  @media (max-width: 991px) {
    div {
      row-gap: 1em;
      p {
        font-size: 14px;
      }
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
  @media (max-width: 1199px) {
    display: flex;
    justify-content: space-around;
  }
  @media (max-width: 991px) {
    button,
    input {
      width: 8em;
      height: 3em;
    }
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
  @media (max-width: 1199px) {
    h3 {
      font-size: 30px;
    }
  }
  @media (max-width: 991px) {
    h3 {
      font-size: 26px;
    }
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
  @media (max-width: 991px) {
    p {
      font-size: 14px;
    }
    span {
      font-size: 14px;
    }
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
  const navigate = useNavigate();
  const dispatch = useDispatch();
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
    const newData = { ...orderData, ...data };

    if (window.confirm('주문 확인')) {
      setIsLoading(true);
      await apiServer({ method: 'POST', url: `/orders`, data: JSON.stringify(newData), headers: { 'Content-Type': 'application/json' } })
        .then((res) => {
          dispatch(payActions.setPay({ orderid: res.data.ordId }));
        })
        .catch((err) => console.log(err));

      setIsLoading(false);
      nextButton();
    }
  };
  // 주문 폼 뒤로가기
  const handleCancelClick = () => {
    if (window.confirm('주문 취소')) {
      navigate(-1);
    }
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
          <div className="navbox">
            <p>구매 정보를 확인했습니다.</p>
            <ButtonBox>
              <button type="button" className="cancel" onClick={handleCancelClick}>
                이전단계
              </button>
              <input className="order" type="submit" value="주문하기" />
            </ButtonBox>
          </div>
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
