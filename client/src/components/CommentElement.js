import styled from 'styled-components';
import { useState } from 'react';
import { apiServer } from '../features/axios';

export const CommentElement = (props) => {
  //CommentPatch
  const patchComment = async (e) => {
    e.preventDefault();
    await apiServer({
      method: 'PATCH',
      url: `/comments/${props.CommentId}`,
      data: JSON.stringify({
        context: text,
        memberId: props.memBerId.memberId,
        boardId: props.boardId,
      }),
      headers: { 'Content-Type': 'application/json' },
    })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
    props.getComment();
    setEdit(!edit);
  };

  const removeComment = async () => {
    await apiServer({
      method: 'DELETE',
      url: `/comments/${props.CommentId}?memberId=${props.memBerId.memberId}`,
    })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
    props.getComment();
  };

  const [edit, setEdit] = useState(false);
  const [text, setText] = useState('');
  const onChange = (e) => {
    const editText = e.target.value;
    setText(editText);
  };

  const handleChangeOnClick = () => {
    if (window.confirm('수정하시겠습니까?')) {
      setEdit(!edit);
    }
  };

  return (
    <Commentlist>
      {!edit && <div>{props.content}</div>}
      {edit && <input defaultValue={props.content} onChange={onChange} />}
      <div>{props.name}</div>
      {edit && <button onClick={patchComment}> 수정 완료</button>}
      {props.userId === props.memberId ? <PatchButton type="submit" value="수정" onClick={handleChangeOnClick} /> : ''}
      {props.userId === props.memberId ? <button onClick={removeComment}>삭제</button> : ''}
      <div>{props.createdAt}</div>
    </Commentlist>
  );
};

const Commentlist = styled.div`
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
    width: 40%;
    text-align: left;
  }
  div:nth-child(3) {
    flex-grow: 1;
  }
  div:nth-child(5) {
    text-align: left;
    width: 13%;
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

const PatchButton = styled.input`
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
  margin-right: 3px;
`;
