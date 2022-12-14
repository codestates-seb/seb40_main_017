import InfiniteScroll from 'react-infinite-scroll-component';
import { useState, useEffect } from 'react';
import CropBoard from '../../components/CropBoard';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

function CropListPage() {
  const [items, setItems] = useState([]);
  const [hasMore, sethasMore] = useState(true);
  const [page, setPage] = useState(2);

  useEffect(() => {
    const getBoards = async () => {
      const res = await fetch(`${process.env.REACT_APP_API_URL}/boards/category/1?page=1&size=10`);
      const data = await res.json();
      setItems(data.data);
    };
    getBoards();
  }, []);

  const fetchBoards = async () => {
    const res = await fetch(`${process.env.REACT_APP_API_URL}/boards/category/1?page=${page}&size=10`);
    const data = await res.json();
    return data.data;
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
          <p>이 상품 어때요?</p>
          <p>“당신의 달콤한 하루를 위한 과일”</p>
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
          loader={' '}
          endMessage={' '}
        >
          <Outer>
            <BoardList>
              {items.map((item) => {
                if (item.category === 1) {
                  return <CropBoard className="board" key={item.boardId} item={item} />;
                }
              })}
            </BoardList>
          </Outer>
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

const Outer = styled.div`
  display: flex;
  justify-content: center;
`;

const BoardList = styled.div`
  width: 1500px;
  display: flex;
  flex-wrap: wrap;
  overflow: hidden;
  justify-content: flex-start;
  .board {
    width: 25%;
  }
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
