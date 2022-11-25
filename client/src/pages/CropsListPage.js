import InfiniteScroll from 'react-infinite-scroll-component';
import { useState, useEffect } from 'react';
import CropBoard from '../components/CropBoard';
import Loader from '../components/Loader';
import styled from 'styled-components';

function CropListPage() {
  const [items, setItems] = useState([]);
  const [hasMore, sethasMore] = useState(true);
  const [page, setPage] = useState(2);

  useEffect(() => {
    const getBoards = async () => {
      const res = await fetch(`${process.env.REACT_APP_API_URL}/boards?page=1&size=10`);
      const data = await res.json();
      setItems(data);
      console.log('data:', data);
    };
    getBoards();
  }, []);

  console.log('items:', items);

  const fetchBoards = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards?page=${page}&size=10`);
    const data = await res.json();
    return data;
  };

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
            <li>전체상품</li>
            <li>과일</li>
            <li>야채</li>
            <li>쌀/잡곡</li>
            <li>견과류</li>
          </ol>
        </CategoryList>
        <InfiniteScroll
          dataLength={items.length} //This is important field to render the next data
          next={fetchData}
          hasMore={hasMore}
          loader={<Loader />}
          endMessage={'endMsg'}
        >
          <BoardList>
            {items.map((item) => {
              return <CropBoard key={item.boardId} item={item} />;
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
