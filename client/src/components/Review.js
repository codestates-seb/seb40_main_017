import styled from 'styled-components';
import ReactPaginate from 'react-paginate';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
// import axios from 'axios';
import StarRate from './StarRate';
import { apiServer } from '../features/axios';
import { useSelector } from 'react-redux';
import { getUser } from '../features/user/userSlice';
import { ReviewElement } from './ReviewElement';

//REVIEW GET, POST
export const Review = () => {
  const [items, setItems] = useState([]);
  const { boardId } = useParams();
  const [pageCount, setpageCount] = useState(0);
  const clientId = useSelector((state) => state.user.clientId);
  const [starCount, setStarCount] = useState(0);
  const user = useSelector(getUser);

  //ReviewGet
  const getReviews = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards/reviews/${boardId}?page=1&size=5`);
    const data = await res.json();
    setpageCount(data.pageInfo.totalElements / 5);
    setItems(data.data);
  };

  useEffect(() => {
    getReviews();
  }, []);

  const fetchReviews = async (currentPage) => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards/reviews/${boardId}?page=${currentPage}&size=5`);
    console.log(boardId);
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
  const reviewOnSubmitHandler = async (e) => {
    e.preventDefault();
    const context = e.target.context.value;
    e.target.reset();
    await apiServer({
      method: 'POST',
      url: `/boards/${boardId}/reviews`,
      data: JSON.stringify({
        context: context,
        clientId: clientId,
        image: '',
        star: starCount,
      }),
      headers: { 'Content-Type': 'application/json' },
    })
      .then((response) => console.log(response))
      .catch((res) => {
        const { response } = res;
        if (response.data.message) {
          alert(response.data.message);
        } else if (context.length < 8) {
          alert(response.data.fieldErrors[0].reason);
        }
      });
    getReviews();
  };

  return (
    <Container>
      <div id="b">
        <h2>리뷰</h2>
        {items.length === 0 ? (
          <NoReview> 첫번째 리뷰를 남겨주세요!</NoReview>
        ) : (
          <div>
            {items.map((review) => {
              return (
                <ReviewElement
                  key={review.reviewId}
                  context={review.context}
                  name={review.name}
                  userClientId={user.clientId}
                  CommentId={review.commentId}
                  reviewClientId={review.clientId}
                  createdAt={review.createdAt}
                  ReviewId={review.reviewId}
                  getReviews={getReviews}
                />
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
        )}
      </div>
      <div id="c">
        <h2>리뷰작성</h2>
        <Layout>
          <p>별점</p>
          <StarRate setStarCount={setStarCount} />
        </Layout>
        <Layout className="formlayout">
          <p>상세리뷰</p>
          <Form onSubmit={reviewOnSubmitHandler}>
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

const Layout = styled.div`
  display: flex;
  text-align: center;
  > :first-child {
    margin-right: 30px;
  }
  .formlayout {
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

const NoReview = styled.div`
  margin: auto;
  text-align: center;
  width: 500px;
  font-size: 20px;
`;
