import styled from 'styled-components';
import { AiFillStar } from 'react-icons/ai';
import { useState, useEffect } from 'react';
import axios from 'axios';

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

  useEffect(() => {
    sendReview();
  }, [clicked]);

  const sendReview = () => {
    let score = clicked.filter(Boolean).length;
    axios.post(`${process.env.REACT_APP_API_URL}/boards/${props.boardId}/reviews`, { star: score });
    console.log(score);
  };
  //     fetch('http://52.78.63.175:8000/movie', {
  //       method: 'POST',
  //       Headers: {
  //         Authroization: 'e7f59ef4b4900fe5aa839fcbe7c5ceb7',
  //       },
  //       body: JSON.stringify({
  //         movie_id:1
  //         star: score,
  //       }),
  //     });
  //   };

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
