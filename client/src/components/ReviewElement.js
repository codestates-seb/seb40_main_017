import styled from 'styled-components';
import { apiServer } from '../features/axios';

export const ReviewElement = (props) => {
  const removeReview = async () => {
    await apiServer({
      method: 'DELETE',
      url: `/boards/reviews/${props.ReviewId}?clientId=${props.reviewClientId}`,
    })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
    props.getReviews();
  };

  return (
    <Reviewlist>
      <div>{props.context}</div>
      <div>{props.name}</div>
      {props.userClientId === props.reviewClientId ? <button onClick={removeReview}>삭제</button> : ''}
      <div>{props.createdAt}</div>
    </Reviewlist>
  );
};

const Reviewlist = styled.div`
  display: flex;
  border-bottom: 1px solid var(--darker-gray);
  padding: 10px 8px;
  div {
    padding: 10px;
    width: 8%;
    height: 100%;
    text-align: center;
  }
  :nth-child(1) {
    border-top: 2px solid var(--darker-gray);
  }

  div:nth-child(1) {
    flex-grow: 5;
    width: 50%;
    text-align: left;
  }
  div:nth-child(3) {
    flex-grow: 1;
  }
  div:nth-child(4) {
    text-align: left;
    width: 12%;
  }
  button {
    all: unset;
    flex-grow: 1;
    padding: 10px;
    width: 25px;
    height: 8px;
    border: 1px solid var(--darker-gray);
    text-align: center;
    line-height: 10px;
    border-radius: 5px;
    font-size: 15px;
  }
`;
