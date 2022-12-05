import styled from 'styled-components';
import { AiFillStar } from 'react-icons/ai';
import { useState } from 'react';
// import axios from 'axios';

const ARRAY = [0, 1, 2, 3, 4];

function StarRate(props) {
  const [clicked, setClicked] = useState([false, false, false, false, false]);

  const handleStarClick = (index) => {
    let clickStates = [...clicked];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index ? true : false;
    }
    setClicked(clickStates);
  };

  let score = clicked.filter(Boolean).length;
  props.setStarCount(score);

  return (
    <Wrap>
      <Stars>
        {ARRAY.map((el, idx) => {
          return <AiFillStar key={idx} size="50" onClick={() => handleStarClick(el)} className={clicked[el] && 'yellowStar'} />;
        })}
      </Stars>
    </Wrap>
  );
}

export default StarRate;

const Wrap = styled.div`
  display: flex;
  flex-direction: column;
`;

const Stars = styled.div`
  display: flex;
  & svg {
    color: gray;
    cursor: pointer;
    height: 100px;
    line-height: 100px;
  }

  :hover svg {
    color: var(--light-orange);
  }

  & svg:hover ~ svg {
    color: gray;
  }

  .yellowStar {
    color: var(--light-orange);
  }
`;
