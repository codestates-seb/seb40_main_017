import styled from 'styled-components';
import ReactPaginate from 'react-paginate';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import StarRate from './StarRate';

//REVIEW GET, POST

export const Review = () => {
  const [items, setItems] = useState([]);
  const { boardId } = useParams();
  const [pageCount, setpageCount] = useState(0);

  let limit = 5;
  //ReviewGet
  const getReviews = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards/reviews/${boardId}?page=1&size=${limit}`);
    const data = await res.json();
    const total = res.headers.get('x-total-count');
    setpageCount(Math.ceil(total / 5));
    // console.log(Math.ceil(total / 5));
    setItems(data.data);
  };

  useEffect(() => {
    getReviews();
  }, []);

  console.log(items);
  console.log(items.clientId);

  const fetchReviews = async (currentPage) => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards/reviews/${boardId}?page=${currentPage}&size=${limit}`);
    const data = await res.json();
    return data.data;
  };

  //Pagination
  const handlePageClick = async (page) => {
    console.log(page.selected);

    let currentPage = page.selected + 1;

    const commentsFormServer = await fetchReviews(currentPage);

    setItems(commentsFormServer);
  };

  // ReviewPost
  const ReviewOnSubmitHandler = async (e) => {
    e.preventDefault();
    const context = e.target.context.value;
    await axios.post(`${process.env.REACT_APP_API_URL}/boards/${boardId}/reviews`, { context: context, clientId: 3, image: 3 });
    getReviews();
  };

  return (
    <div>
      <div id="b">
        <h2>리뷰</h2>
        {items.map((review) => {
          return (
            <Reviewlist key={review.clientId}>
              <div>{review.reviewId}</div>
              <div>{review.context}</div>
              {/* <button>수정하기</button> */}
              <div>{review.name}</div>
              <div>{review.createdAt}</div>
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
      <div id="c">
        <h2>리뷰작성</h2>
        <Layout className="firstlayout">
          <p>별점</p>
          <StarRate borardId={boardId} />
        </Layout>
        <Layout>
          <p>상세리뷰</p>
          <form onSubmit={ReviewOnSubmitHandler}>
            <textarea name="context" />
            <input type="submit" value="등록하기" />
          </form>
        </Layout>
      </div>
    </div>
  );
};

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