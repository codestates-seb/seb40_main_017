import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

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
  /* background-color: forestgreen; */
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
  /* background-color: blue; */
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
    width: 10em;
  }
  margin-bottom: 0em;
`;

export const ClientBuyForm = ({ nextButton, userData, itemData, count, price }) => {
  const navigate = useNavigate();
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm();
  const onSubmit = (data) => {
    console.log(data);
    if (window.confirm('주문 확인')) {
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
              <input placeholder="이름" defaultValue={userData.name} readOnly></input>
              <p>전화번호</p>
              <InputBox>
                <input name="phone" placeholder="전화번호" defaultValue={userData.phone} {...register('phone', { required: true })} />
                <ErrorBox>{errors.phone && <p>전화번호를 적어 주세요</p>}</ErrorBox>
              </InputBox>
              <p>주소</p>
              <InputBox>
                <input name="address" placeholder="주소" defaultValue={userData.address} {...register('address', { required: true })}></input>
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
            <p>{itemData.title}</p>
            <p>
              수량: <span>{count}</span>
            </p>
            <p>
              판매자 : <span>{itemData.name}</span>
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