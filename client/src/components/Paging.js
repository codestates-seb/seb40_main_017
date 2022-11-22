import styled from 'styled-components';
import ReactPaginate from 'react-paginate';
import { useState, useEffect } from 'react';

const Paging = () => {
  const [items, setItems] = useState([]);

  const [pageCount, setpageCount] = useState(0);

  let limit = 5;

  useEffect(() => {
    const getComments = async () => {
      const res = await fetch(`http://localhost:3004/comments?_page=1&_limit=${limit}`);
      const data = await res.json();
      const total = res.headers.get('x-total-count');
      setpageCount(Math.ceil(total / 5));
      // console.log(Math.ceil(total / 5));
      setItems(data);
    };
    getComments();
  }, []);

  console.log(items);

  const fetchComments = async (currentPage) => {
    const res = await fetch(`http://localhost:3004/comments?_page=${currentPage}&_limit=5`);
    const data = await res.json();
    return data;
  };

  const handlePageClick = async (page) => {
    console.log(page.selected);

    let currentPage = page.selected + 1;

    const commentsFormServer = await fetchComments(currentPage);

    setItems(commentsFormServer);
  };

  return (
    <div>
      {items.map((review) => {
        return (
          <Review key={review.id}>
            <h5>{review.id}</h5>
            <h6>{review.email}</h6>
            <Context>{review.body}</Context>
          </Review>
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
  );
};

export default Paging;

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

const Review = styled.div`
  display: flex;
`;

const Context = styled.div`
  width: 100%;
  height: 100%;
`;
