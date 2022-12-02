import styled from 'styled-components';
import { AiOutlinePlusCircle, AiOutlineMinusCircle } from 'react-icons/ai';
import { Link } from 'react-scroll';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Review } from '../../components/Review';
import { Comment } from '../../components/Comment';
import { PurchaseButton, PatchButton, Linktoseller } from '../../components/CropInfoElement';
import { apiServer } from '../../features/axios';
import { useSelector } from 'react-redux';
import { getUser } from '../../features/user/userSlice';
import { Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';

function CropInfoPage() {
  const { boardId } = useParams();
  const [board, setBoard] = useState({});
  const [quantity, setQuantity] = useState(0);
  const user = useSelector(getUser);

  //BoardGet
  const GetCropInfo = async () => {
    const response = await axios.get(`${process.env.REACT_APP_API_URL}/boards/${boardId}`);
    setBoard(response.data);
  };

  useEffect(() => {
    GetCropInfo();
  }, []);

  //BoardDelete
  const BoardDelete = async () => {
    await apiServer({
      method: 'DELETE',
      url: `/boards/${boardId}`,
    })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
    document.location.href = '/boards';
  };

  return (
    <Background>
      <Container>
        <Crop>
          <CropImage src={board.mainImage} alt="상품이미지" />
          <CropInfo>
            <CropTitle>
              <p>{board.title}</p>
              <Layout>
                <p>{board.price} 원</p>
                {user.sellerId === board.sellerId ? (
                  <div>
                    <PatchButton boardId={boardId} />
                    <DeleteButton onClick={BoardDelete}>삭제</DeleteButton>
                  </div>
                ) : (
                  <></>
                )}
              </Layout>
            </CropTitle>
            <PurchaseCount>
              <p>구매수량</p>
              <Minus
                onClick={() => {
                  if (quantity > 0) {
                    setQuantity(quantity - 1);
                  }
                }}
              />
              <Count>{quantity}</Count>
              <Plus
                onClick={() => {
                  if (quantity < board.leftStock) {
                    setQuantity(quantity + 1);
                  }
                }}
              />
            </PurchaseCount>
            <p>남은수량 {board.leftStock}개</p>
            <Flexbox>
              <Linktoseller sellerId={board.sellerId} />
              {board.leftStock === 0 ? 'SOLDOUT' : user.clientId ? <PurchaseButton boardId={boardId} quantity={quantity} /> : '회원가입해주세요😊'}
            </Flexbox>
          </CropInfo>
        </Crop>
        <ContentDiv>
          <Menubar>
            <ul>
              <Link to="a" spy={true} smooth={true}>
                <li>상품설명</li>
              </Link>
              <Link to="b" spy={true} smooth={true}>
                <li>리뷰</li>
              </Link>
              <Link to="c" spy={false} smooth={true}>
                <li>리뷰작성</li>
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
            <div id="a">{board.content && <Viewer initialValue={board.content} />}</div>
            <Review />
            <Comment />
          </MenuLink>
        </ContentDiv>
      </Container>
    </Background>
  );
}

export default CropInfoPage;

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
  width: 100%;
  > * {
    margin: 10px 0 30px 0;
  }
`;

const CropTitle = styled.div`
  font-weight: 700;
  width: 100%;
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
  height: 25px;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Minus = styled(AiOutlineMinusCircle)`
  font-size: 25px;
  color: var(--light-gray);
  cursor: pointer;
`;

const Plus = styled(AiOutlinePlusCircle)`
  font-size: 25px;
  color: var(--light-gray);
  cursor: pointer;
`;

const ContentDiv = styled.div``;

const Menubar = styled.div`
  position: sticky;
  top: 80px;
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
    padding-top: 145px;
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

  .toastui-editor-contents {
    height: 100%;
    p {
      height: 100%;
      width: 100%;
      text-align: center;
      img {
        height: 100%;
        width: 70%;
      }
    }
  }
`;

const Flexbox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 5px;
`;

const Layout = styled.div`
  display: flex;
  justify-content: space-between;
`;

const DeleteButton = styled.button`
  padding: 3px 5px;
  margin-left: 5px;
`;
