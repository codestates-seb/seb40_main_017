import styled from 'styled-components';
import lettuce from '../../assets/styles/img/lettuce.jpg';
import CropDetail from '../../assets/styles/img/CropDetail.png';
import { AiOutlinePlusCircle, AiOutlineMinusCircle } from 'react-icons/ai';
import StarRate from '../../components/StarRate';
import { Link } from 'react-scroll';
import { useState, useEffect } from 'react';
import axios from 'axios';

const Background = styled.div`
  background-color: var(--off-white);
  width: 100%;
  height: 100%;
  padding: 0 10%;
`;

const Container = styled.div`
  background-color: var(--white);
  width: 100%;
  height: 100%;
  padding: 80px;
`;

const Crop = styled.div`
  background-color: var(--white);
  height: 400px;
  display: flex;
`;

const CropImage = styled.img`
  width: 350px;
  height: 350px;
  border-radius: 20px;
  margin-right: 100px;
`;

const CropInfo = styled.div`
  height: 500px;
  > * {
    margin: 10px 0 30px 0;
  }
`;

const CropTitle = styled.div`
  font-weight: 700;
  border-bottom: 1px solid var(--light-gray);
  padding-bottom: 30px;
  > :first-child {
    font-size: 30px;
    margin-bottom: 25px;
  }
  > :nth-child(2) {
    font-size: 24px;
  }
`;

const PurchaseCount = styled.div`
  width: 170px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Count = styled.div`
  border: 1px solid var(--light-gray);
  width: 50px;
  height: 20px;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Plus = styled(AiOutlineMinusCircle)`
  font-size: 25px;
  color: var(--light-gray);
`;

const Minus = styled(AiOutlinePlusCircle)`
  font-size: 25px;
  color: var(--light-gray);
`;

const GreenButton = styled.button`
  all: unset;
  margin-top: 20px;
  background-color: var(--green);
  color: var(--white);
  width: 30px;
  padding: 20px 90px;
  border-radius: 5px;
  cursor: pointer;
`;

const CropDetails = styled.img`
  width: 100%;
  height: 100%;
`;

const ContentDiv = styled.div``;

const Menubar = styled.div`
  position: sticky;
  top: 0;
  z-index: 10;
  ul {
    width: 100%;
    position: relative;
    li {
      background-color: var(--lighter-gray);
      position: relative;
      height: 50px;
      width: 20%;
      float: left;
      border: 1px solid var(--light-gray);
      border-width: 1px 0px 1px 1px;
      text-align: center;
      line-height: 50px;
      :hover {
        cursor: pointer;
        color: var(--green);
      }
    }
    .lastlist {
      border-right: 1px solid var(--light-gray);
    }
  }
`;

const MenuLink = styled.div`
  > div {
    padding: 60px 0;
  }
  h2 {
    margin-bottom: 20px;
  }
  p {
    width: 70px;
    height: 100px;
    line-height: 100px;
  }
  .firstlayout {
    border-bottom: 1px solid var(--light-gray);
    margin-bottom: 50px;
  }
`;

const ContentBox = styled.textarea`
  width: 700px;
  height: 200px;
  resize: none;
  border: 1px solid var(--light-gray);
`;

const Layout = styled.div`
  display: flex;
  text-align: center;
  > :first-child {
    margin-right: 30px;
  }
`;

const SERVER_URL = 'http://localhost:4000/boards/reviews';

function CropInfoPage() {
  const [reviewList, setReviewList] = useState(null);
  // const [commentList, setCommentList] = useState(null);

  const fetchData = async () => {
    const response = await axios.get(SERVER_URL);
    setReviewList(response.data);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const onSubmitHandler = async (e) => {
    e.preventDefault();
    const context = e.target.context.value;
    await axios.post(SERVER_URL, { context });
    fetchData();
    // fetch('http://localhost:4000/boards/reviews', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify({
    //     context,
    //   }),
    // }).then(() => fetchData());
  };

  return (
    <Background>
      <Container>
        <Crop>
          <CropImage src={lettuce} alt="상추" />
          <CropInfo>
            <CropTitle>
              <p>양파(2kg)</p>
              <p>5,700원</p>
            </CropTitle>
            <PurchaseCount>
              <p>구매수량</p>
              <Plus />
              <Count>1</Count>
              <Minus />
            </PurchaseCount>
            <p>남은수량 999개</p>
            <p>생산자 정보 바로가기</p>
            <GreenButton>구매</GreenButton>
          </CropInfo>
        </Crop>
        <ContentDiv>
          <Menubar>
            <ul>
              <Link to="a" spy={true} smooth={true}>
                <li>상품설명</li>
              </Link>
              <Link to="b" spy={true} smooth={true}>
                <li>후기</li>
              </Link>
              <Link to="c" spy={false} smooth={true}>
                <li>후기작성</li>
              </Link>
              <Link to="d" spy={false} smooth={true}>
                <li>문의</li>
              </Link>
              <Link to="e" spy={false} smooth={true}>
                <li className="lastlist">문의작성</li>
              </Link>
            </ul>
          </Menubar>
          <MenuLink>
            <div id="a">
              <CropDetails src={CropDetail} alt="상품설명" />
            </div>
            <div id="b">
              <h2>후기</h2>
              {reviewList?.map((review) => (
                <ol key={review.reviewId}>
                  {/* reviewId 가 undefined여서 unique key prop 오류가 뜨는거 같아서 서버연결해서 확인해봐야함 */}
                  <li>{review.reviewId}</li>
                  <li>{review.context}</li>
                  <li>{review.name}</li>
                  <li>{review.createdAt}</li>
                </ol>
              ))}
            </div>
            <div id="c">
              <h2>후기작성</h2>
              <Layout className="firstlayout">
                <p>별점</p>
                <StarRate />
              </Layout>
              <Layout>
                <p>상세리뷰</p>
                <form onSubmit={onSubmitHandler}>
                  <input name="context" />
                  <input type="submit" value="등록하기" />
                </form>
              </Layout>
            </div>
            <div id="d">
              <h2>문의</h2>
              <ol>
                <li>4</li>
                <li>3</li>
                <li>2</li>
                <li>1</li>
              </ol>
            </div>
            <div id="e">
              <h2>문의작성</h2>
              <Layout>
                <p>문의</p>
                <ContentBox></ContentBox>
              </Layout>
              <GreenButton>등록하기</GreenButton>
            </div>
          </MenuLink>
        </ContentDiv>
      </Container>
    </Background>
  );
}

export default CropInfoPage;
