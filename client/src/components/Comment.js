import styled from 'styled-components';
import ReactPaginate from 'react-paginate';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { apiServer } from '../features/axios';
import { useSelector } from 'react-redux';
import { getUser } from '../features/user/userSlice';

//Comment GET, POST

export const Comment = () => {
  const [items, setItems] = useState([]);
  const { boardId } = useParams();
  const [pageCount, setpageCount] = useState(0);
  const memberId = useSelector(getUser);

  //CommentGet
  const getComment = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/comments/${boardId}?page=1&size=5`);
    const data = await res.json();
    setpageCount(Math.ceil(data.data.length / 2));
    setItems(data.data);
  };

  useEffect(() => {
    getComment();
  }, []);

  console.log(items);

  const fetchComment = async (currentPage) => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/comments/${boardId}?page=${currentPage}&size=5`);
    const data = await res.json();
    return data.data;
  };

  //Pagination
  const handlePageClick = async (page) => {
    console.log(page.selected);

    let currentPage = page.selected + 1;

    const commentsFormServer = await fetchComment(currentPage);

    setItems(commentsFormServer);
  };

  // CommentPost
  // const ReviewOnSubmitHandler = async (e) => {
  //   e.preventDefault();
  //   const context = e.target.context.value;
  //   await axios.post(`${process.env.REACT_APP_API_URL}/comments`, { context: context, boardId: 1, memberId: 3 });
  //   getComment();
  // };

  const CommentOnSubmitHandler = async (e) => {
    e.preventDefault();
    const context = e.target.context.value;
    await apiServer({
      method: 'POST',
      url: `/comments`,
      data: JSON.stringify({
        context: context,
        memberId: memberId,
        boardId: boardId,
      }),
      headers: { 'Content-Type': 'application/json' },
    })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
    getComment();
  };

  return (
    <Container>
      <div id="d">
        <h2>문의</h2>
        {items.map((comment) => {
          return (
            <Reviewlist key={comment.clientId}>
              <div>{comment.commentId}</div>
              <div>{comment.context}</div>
              {/* <button>수정하기</button> */}
              <div>{comment.name}</div>
              <div>{comment.createdAt}</div>
            </Reviewlist>
          );
        })}
        <PaginationBox>
          <ReactPaginate
            previousLabel={'<'}
            nextLabel={'>'}
            breackLabel={'...'}
            pageCount={pageCount}
            // 앞뒤 페이지 수
            marginPagesDisplayed={3}
            //...클릭시 나오는 페이지수
            pageRangeDisplayed={3}
            onPageChange={handlePageClick}
            containerClassName={'pagination'}
            activeClassName={'active'}
          />
        </PaginationBox>
      </div>
      <div id="e">
        <h2>문의작성</h2>
        <Layout>
          <p>상세문의</p>
          <Form onSubmit={CommentOnSubmitHandler}>
            <TextBox name="context" placeholder="8글자이상 작성해주세요." />
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
    width: 4%;
    text-align: left;
  }

  div:nth-child(2) {
    flex-grow: 5;
    width: 50%;
    text-align: left;
  }
  div:nth-child(4) {
    flex-grow: 1;
  }
  div:nth-child(5) {
    text-align: left;
    width: 8%;
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
