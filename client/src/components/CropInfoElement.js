import { Link } from 'react-router-dom';
import styled from 'styled-components';

export const PurchaseButton = (props) => {
  return (
    <Link to={`/order/${props.boardId}/${props.quantity}`} state={{ boardId: props.boardId, quantity: props.quantity }}>
      <Button>구매</Button>
    </Link>
  );
};

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

export const Linktoseller = () => {
  return (
    <Link to="/sell">
      <p>생산자 정보 바로가기</p>
    </Link>
  );
};
