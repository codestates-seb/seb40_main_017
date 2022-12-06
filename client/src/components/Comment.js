import styled from 'styled-components';
import ReactPaginate from 'react-paginate';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { apiServer } from '../features/axios';
import { useSelector } from 'react-redux';
import { getUser } from '../features/user/userSlice';
import { CommentElement } from './CommentElement';

export const Comment = () => {
  const [items, setItems] = useState([]);
  const { boardId } = useParams();
  const [pageCount, setpageCount] = useState(0);
  const memberId = useSelector(getUser);
  const user = useSelector(getUser);

  const getComment = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/comments/${boardId}?page=1&size=5`);
    const data = await res.json();
    setpageCount(Math.ceil(data.pageInfo.totalElements / 5));
    setItems(data.data);
  };

  useEffect(() => {
    getComment();
  }, []);

  const fetchComment = async (currentPage) => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/comments/${boardId}?page=${currentPage}&size=5`);
    const data = await res.json();
    return data.data;
  };

  const handlePageClick = async (page) => {
    console.log(page.selected);

    let currentPage = page.selected + 1;

    const commentsFormServer = await fetchComment(currentPage);

    setItems(commentsFormServer);
  };

  const CommentOnSubmitHandler = async (e) => {
    e.preventDefault();
    const context = e.target.context.value;
    e.target.reset();
    await apiServer({
      method: 'POST',
      url: `/comments`,
      data: JSON.stringify({
        context: context,
        memberId: memberId.memberId,
        boardId: boardId,
      }),
      headers: { 'Content-Type': 'application/json' },
    })
      .then((response) => console.log(response))
      .catch((res) => {
        const { response } = res;
        if (response.data.fieldErrors[0].reason) {
          alert(response.data.fieldErrors[0].reason);
        }
      });
    getComment();
  };

  return (
    <Container>
      <div id="d">
        <h2>문의</h2>
        {items.length === 0 ? (
          <NoComment>첫번째 문의를 남겨주세요!</NoComment>
        ) : (
          <div>
            {items.map((comment) => {
              return (
                <CommentElement
                  key={comment.commentId}
                  content={comment.context}
                  name={comment.name}
                  userId={user.memberId}
                  memberId={comment.memberId}
                  createdAt={comment.createdAt}
                  CommentId={comment.commentId}
                  getComment={getComment}
                  boardId={boardId}
                  memBerId={memberId}
                />
              );
            })}
            <PaginationBox>
              <ReactPaginate
                previousLabel={'<'}
                nextLabel={'>'}
                breackLabel={'...'}
                pageCount={pageCount}
                marginPagesDisplayed={3}
                pageRangeDisplayed={3}
                onPageChange={handlePageClick}
                containerClassName={'pagination'}
                activeClassName={'active'}
              />
            </PaginationBox>
          </div>
        )}
      </div>
      <div id="e">
        <h2>문의작성</h2>
        <Layout>
          <p>상세문의</p>
          <Form onSubmit={CommentOnSubmitHandler}>
            <TextBox name="context" placeholder="10글자이상 작성해주세요." />
            <Submitbox type="submit" value="등록하기" />
          </Form>
        </Layout>
      </div>
    </Container>
  );
};

const Container = styled.div`
  > div {
    padding: 130px 0 10px 0;
  }
`;

const PaginationBox = styled.div`
  .pagination {
    display: flex;
    justify-content: center;
    margin-top: 15px;
  }
  ul {
    list-style: none;
    padding: 0;
  }
  ul.pagination li {
    display: inline-block;
    width: 30px;
    height: 30px;
    border: 1px solid #e2e2e2;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1rem;
  }
  ul.pagination li:first-child {
    border-radius: 5px 0 0 5px;
  }
  ul.pagination li:last-child {
    border-radius: 0 5px 5px 0;
  }
  ul.pagination li a {
    text-decoration: none;
    color: var(--green);
    font-size: 1rem;
  }
  ul.pagination li.active a {
    color: white;
  }
  ul.pagination li.active {
    background-color: var(--green);
  }
  ul.pagination li:hover {
    background-color: var(--green);
    a {
      color: var(--white);
    }
  }
`;

const Layout = styled.div`
  display: flex;
  text-align: center;
  > :first-child {
    margin-right: 30px;
  }
`;

const Form = styled.form`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const TextBox = styled.textarea`
  resize: none;
  width: 90%;
  height: 100px;
  border-radius: 5px;
  padding: 5px;
`;

const Submitbox = styled.input`
  all: unset;
  margin-top: 20px;
  margin-left: 66.5%;
  background-color: var(--green);
  color: var(--white);
  width: 60px;
  padding: 20px 80px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 0.9rem;
`;

const NoComment = styled.div`
  margin: auto;
  text-align: center;
  width: 500px;
  font-size: 20px;
`;
