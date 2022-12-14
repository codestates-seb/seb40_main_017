import { Link } from 'react-router-dom';
import styled from 'styled-components';

export const PurchaseButton = (props) => {
  return (
    <Link to={`/order/${props.boardId}/${props.quantity}`} state={{ boardId: props.boardId, quantity: props.quantity }}>
      <Button>구매</Button>
    </Link>
  );
};

export const PatchButton = (props) => {
  return (
    <Link to={`/sell/patch/${props.boardId}`} state={{ boardId: props.boardId }}>
      <Patchbutton>수정</Patchbutton>
    </Link>
  );
};

const Patchbutton = styled.button`
  padding: 3px 5px;
`;

const Button = styled.button`
  all: unset;
  margin-top: 20px;
  background-color: var(--green);
  color: var(--white);
  width: 40px;
  padding: 20px 90px;
  border-radius: 5px;
  cursor: pointer;
`;

export const Linktoseller = (props) => {
  return (
    <Link to={`/seller/${props.sellerId}`}>
      <Info>생산자 정보 바로가기</Info>
    </Link>
  );
};

const Info = styled.p`
  background-color: var(--light-green);
  border-radius: 5px;
  padding: 5px;
  :hover {
    background-color: var(--green);
    color: var(--white);
    transition: 0.5s;
  }
`;
