import styled from 'styled-components';
import { useState } from 'react';
import CropCard from './CropCard';
import data from './data';

const Background = styled.div`
  background-color: var(--off-white);
  width: 100%;
  height: 100%;
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

const CardList = styled.div`
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`;

function CropListPageUI() {
  let [crops] = useState(data);

  return (
    <Background>
      <CropInfo>
        <p>Your Best Value Proposition</p>
        <p>“If you don’t try this app, you won’t become the superhero you were meant to be”</p>
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
      <CardList>
        <CropCard crops={crops[0]} />
        <CropCard crops={crops[0]} />
        <CropCard crops={crops[0]} />
        <CropCard crops={crops[0]} />
        <CropCard crops={crops[0]} />
        <CropCard crops={crops[0]} />
        {/* {crops.map((a, i) => {
          <CropCard key={i}></CropCard>;
        })} */}
      </CardList>
    </Background>
  );
}

export default CropListPageUI;
