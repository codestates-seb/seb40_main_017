import styled from 'styled-components';
import { AiFillStar } from 'react-icons/ai';
import { Link } from 'react-router-dom';

function CropBoard({ item: { boardId, name, title, price, mainImage, reviewAvg, reviewNum, sellerImage } }) {
  return (
    <Container>
      <Link to={`/boards/${boardId}`}>
        <CropImage src={mainImage} alt="썸네일" />
        <CropInfo>
          <p>{title}</p>
          <p>{price}원</p>
          <p>리뷰 {reviewNum}개</p>
          <SellerInfo>
            <div className="layout">
              <p>{name}</p>
              <Review>
                <Star />
                <p>{reviewAvg}/5</p>
              </Review>
            </div>
            <SellerImage src={sellerImage} alt="판매자사진" />
          </SellerInfo>
        </CropInfo>
      </Link>
    </Container>
  );
}

export default CropBoard;

const Container = styled.div`
  background-color: var(--white);
  width: 270px;
  height: 500px;
  margin: 50px;
  border-radius: 10px;
`;

const CropImage = styled.img`
  width: 270px;
  height: 270px;
  border-radius: 10px 10px 0 0;
`;

const SellerImage = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 50px;
`;

const CropInfo = styled.div`
  width: 300px;
  height: 300px;
  padding: 20px;
  > * {
    padding: 5px 0;
  }
  > :first-child {
    font-size: 20px;
  }
  > :nth-child(2) {
    font-size: 30px;
  }
`;

const SellerInfo = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 230px;
  .layout {
    display: flex;
    > :first-child {
      margin-right: 5px;
    }
  }
`;

const Star = styled(AiFillStar)`
  color: var(--light-orange);
`;

const Review = styled.div`
  display: flex;
`;
