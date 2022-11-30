import InfiniteScroll from 'react-infinite-scroll-component';
import { useState, useEffect } from 'react';
import CropBoard from '../components/CropBoard';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

function CropListPage() {
  const [items, setItems] = useState([]);
  const [hasMore, sethasMore] = useState(true);
  const [page, setPage] = useState(2);

  //fetch
  const getBoards = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards?page=1&size=10`);
    const data = await res.json();
    console.log('data:', data);
    setItems(data.data);
  };
  useEffect(() => {
    getBoards();
  }, []);

  //axios
  // useEffect(() => {
  //   const getBoards = async () => {
  //     const res = await axios.get(`${process.env.REACT_APP_API_URL}/boards?page=1&size=10`)
  //     setItems(res);
  //   };
  //   getBoards();
  // }, []);

  console.log('items:', items.data);

  //fetch
  const fetchBoards = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards?page=${page}&size=10`);
    const data = await res.json();
    return data.data;
  };

  //axios
  // const fetchBoards = async () => {
  //   const res = await axios.get(`${process.env.REACT_APP_API_URL}/boards?page=${page}&size=10`);
  //   return res;
  // };

  const fetchData = async () => {
    const commentsFormServer = await fetchBoards();
    setItems([...items, ...commentsFormServer]);
    if (commentsFormServer.length === 0 || commentsFormServer.length < page.length) {
      sethasMore(false);
    }
    setPage(page + 1);
  };

  return (
    <Background>
      <div>
        <CropInfo>
          <p>Your Best Vegetable</p>
          <p>“If you don’t try this, you won’t become the superhero you were meant to be”</p>
        </CropInfo>
        <CategoryList>
          <ol>
            <Link to="/boards">
              <li>전체상품</li>
            </Link>
            <Link to="/boards/fruit">
              <li>과일</li>
            </Link>
            <Link to="/boards/vegetable">
              <li>야채</li>
            </Link>
            <Link to="/boards/grain">
              <li>쌀/잡곡</li>
            </Link>
            <Link to="/boards/nut">
              <li>견과류</li>
            </Link>
          </ol>
        </CategoryList>
        <InfiniteScroll
          dataLength={items.length} //This is important field to render the next data
          next={fetchData}
          hasMore={hasMore}
          loader={'로딩중'}
          endMessage={'endMsg'}
        >
          <BoardList>
            {items.map((item) => {
              return <CropBoard key={item.boardId} boardId={item.boardId} item={item} />;
            })}
          </BoardList>
        </InfiniteScroll>
      </div>
    </Background>
  );
}

export default CropListPage;

const Background = styled.div`
  background-color: var(--off-white);
  width: 100%;
  height: 100%;
`;

const BoardList = styled.div`
  display: flex;
  flex-wrap: wrap;
  overflow: hidden;
`;

const CropInfo = styled.div`
  font-size: 30px;
  text-align: center;
  padding: 100px;
`;

const CategoryList = styled.div`
  margin-left: 5%;
  width: 90vw;
  height: 50px;
  border-bottom: 1px solid var(--darker-gray);
  li {
    float: left;
    margin-right: 20px;
    font-size: 20px;
    cursor: pointer;
    :after {
      display: block;
      content: '';
      border-bottom: solid 3px var(--darker-gray);
      transform: scaleX(0);
      transition: transform 250ms ease-in-out;
    }
    :hover:after {
      transform: scaleX(1);
      transform-origin: 0% 50%;
    }
  }
`;
