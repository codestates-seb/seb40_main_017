import styled from 'styled-components';
import lettuce from '../assets/styles/img/lettuce.jpg';
import farmer from '../assets/styles/img/farmer.jpeg';
import { AiFillStar } from 'react-icons/ai';

const Container = styled.div`
  background-color: var(--white);
  width: 300px;
  height: 500px;
  margin: 50px;
  cursor: pointer;
`;

const CropImage = styled.img`
  width: 300px;
  height: 300px;
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
  > :first-child {
    margin-right: 10px;
  }
  > :nth-child(3) {
    margin: 0 0 0 90px;
  }
`;

const Star = styled(AiFillStar)`
  color: var(--light-orange);
`;

const Review = styled.div`
  display: flex;
`;

function CropCard(props) {
  return (
    <div>
      <Container>
        <CropImage src={lettuce} alt="양배추" />
        <CropInfo>
          <p>{props.crops.title}</p>
          <p>5,700원</p>
          <p>리뷰 45개</p>
          <SellerInfo>
            <p>김상추</p>
            <Review>
              <Star />
              <p>4.8/5</p>
            </Review>
            <SellerImage src={farmer} alt="판매자사진" className="sellerImage" />
          </SellerInfo>
        </CropInfo>
      </Container>
    </div>
  );
}

export default CropCard;
