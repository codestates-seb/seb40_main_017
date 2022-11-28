import { useCallback, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getUser } from '../../features/user/userSlice';

import { BiEditAlt } from 'react-icons/bi';
import { EditableInputText, EditableTextArea, useFile, useInput } from '../../components/Input';
import { Form, FormInput } from '../../components/Form';
import { getClient, getClientOrderList, updateClient } from '../../api/client';
import { deleteMember } from '../../api/signup';
import { getSeller, getSellerSaleList, updateSeller } from '../../api/seller';
import { LearnMorePagination } from '../../components/Pagination';

import SELLER_DEFAULT_PROFILE_IMAGE from '../../assets/images/f9bfadecab5b9feaae6ba584a7d18bc7-sticker.png';
import { uploadImage } from '../../features/s3';
import { useSessionCheck } from '../../api/login';

const StyledDiv = styled.div`
  padding: 0 40px;

  & + div {
    margin-top: 20px;
    padding-top: 40px;

    border-top: 5px solid #5d9061;
  }
`;
const StyledEditForm = styled.div`
  display: flex;
  margin-bottom: 55px;

  form {
    flex: 1;

    input[type='text'] {
      background-color: #f0e9df;
      border-radius: 15px;
    }

    p {
      border-radius: 15px;
    }
  }

  @media (max-width: 1000px) {
    flex-wrap: wrap;
  }
`;
const StyledAside = styled.div`
  position: relative;
  width: 100px;
  margin-left: 20px;
`;
const StyledEditButton = styled.button`
  font-size: 0;
  border: none;
  background-color: transparent;
`;
const StyledEditIcon = styled(BiEditAlt)`
  font-size: 40px;
`;
const StyledButtonGroup = styled.div`
  display: flex;
  justify-content: flex-end;

  margin-top: 40px;
`;
const StyledButton = styled.button`
  min-width: 90px;

  font-size: 16px;

  margin-left: 8px;
  padding: 8px;

  border: none;
  border-radius: 5px;
  background-color: #5d9061;

  &:hover {
    background-color: #517454;
  }
`;
const StyledLinkButton = styled(Link)`
  min-width: 90px;

  font-size: 16px;

  margin-left: 8px;
  padding: 8px;

  border: none;
  border-radius: 5px;
  background-color: #5d9061;

  &:hover {
    background-color: #517454;
  }
`;
const StyledTable = styled.table`
  width: 100%;

  border-collapse: collapse;
  border-spacing: 0;

  text-align: center;

  th {
    font-size: 20px;
    padding: 15px 10px;
    word-break: keep-all;
  }

  td {
    font-size: 16px;
    padding: 15px 10px;
  }
`;
const StyledClientTable = styled(StyledTable)`
  colgroup {
    col:nth-child(1) {
      width: 20%;
    }
    col:nth-child(2) {
      width: 15%;
    }
    col:nth-child(3) {
      width: 20%;
    }
    col:nth-child(4) {
      width: 20%;
    }
    col:nth-child(5) {
      width: 25%;
    }
  }

  tbody {
    tr {
      & + tr {
        border-top: 1px solid #9c9c9c;
      }
    }
  }

  tr {
    > :nth-child(5) {
      text-align: left;
    }
  }
`;
const StyledCriticalButton = styled(StyledButton)`
  background-color: #faa0a0;

  &:hover {
    background-color: #e18080;
  }
`;

export const ClientMyPage = ({ handleDeleteMember }) => {
  const user = useSelector(getUser);
  const { clientId } = user;

  const [client, setClient] = useState({
    name: '',
    phone: '',
    address: '',
  });
  const [editMode, setEditMode] = useState(false);

  const [userName, handleChangeUserName, setUserName] = useInput(client.name);
  const [userPhone, handleChangeUserPhone, setUserPhone] = useInput(client.phone);
  const [userAddress, handleChangeUserAddress, setUserAddress] = useInput(client.address);

  const [orderList, setOrderList] = useState([]);
  const [pagination, setPagination] = useState({
    page: 1,
    size: 5,
    totalPage: 0,
  });
  const { page, size } = pagination;

  //  Edit 모드 토글 핸들러
  const handleChangeEditMode = useCallback(() => {
    if (editMode) {
      //  ON => OFF 데이터 초기화
      setUserName(client.name);
      setUserPhone(client.phone);
      setUserAddress(client.address);
    }
    setEditMode(!editMode);
  }, [editMode]);

  //  마이페이지 데이터 저장 후 콜백 함수
  const handleUpdateClient = useCallback((client) => {
    alert('저장되었습니다.');

    handleRenderClient(client);
    setEditMode(false);
  }, []);

  //  마이페이지 데이터 저장
  const handleSubmitClient = useCallback(
    (event) => {
      event.preventDefault();

      if (userName === '') {
        alert('이름을 입력해주세요.');
      } else if (userPhone === '') {
        alert('전화번호를 입력해주세요.');
      } else if (userPhone.match(/^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/) === null) {
        //  정규 표현식을 활용하여 전화번호 검증
        alert('전화번호 형식이 올바르지 않습니다.');
      } else if (userAddress === '') {
        alert('주소를 입력해주세요.');
      } else {
        //  마이페이지 데이터 업데이트
        updateClient({ clientId, userName, userPhone, userAddress }, handleUpdateClient);
      }
    },
    [userName, userPhone, userAddress]
  );

  //  마이페이지 조회 결과를 client 상태에 저장
  const handleRenderClient = useCallback((client) => {
    const data = {
      name: client.name,
      phone: client.phone,
      address: client.address,
    };

    setClient(data);
  }, []);

  //  마이페이지 주문 목록 조회 결과를 orderList 상태에 저장
  const handleRenderClientOrderList = useCallback(
    (orderData) => {
      const newOrderList = orderData.data;
      const data = newOrderList.map((order) => {
        return {
          ordId: order.ordId,
          productId: order.productId,
          boardId: order.boardId,
          title: order.title,
          name: order.name,
          address: order.address,
          phone: order.phone,
          productNum: order.productNum,
        };
      });

      setOrderList([...orderList, ...data]);

      const pageData = orderData.pageInfo;
      const { page, size, totalPage } = pageData;

      setPagination({
        page,
        size,
        totalPage,
      });
    },
    [orderList]
  );

  //  마이페이지 조회
  useEffect(() => {
    //  마이페이지 데이터 조회
    getClient({ clientId }, handleRenderClient);

    //  주문 목록 새로 조회
    getClientOrderList({ clientId, page, size }, handleRenderClientOrderList);

    return () => {
      setClient({
        name: '',
        phone: '',
        address: '',
      });
      setOrderList([]);
      setPagination({
        page: 1,
        size: 5,
        totalPage: 0,
      });
    };
  }, [user]);

  //  client 상태 변경 시 데이터 반영
  useEffect(() => {
    setUserName(client.name);
    setUserPhone(client.phone);
    setUserAddress(client.address);
  }, [client]);

  //  더보기 기능 구현
  const handleLearnMoreOrderList = useCallback(() => {
    getClientOrderList({ clientId, page: page + 1, size }, handleRenderClientOrderList);
  }, [handleRenderClientOrderList, page, size]);

  return (
    <>
      <StyledDiv>
        <StyledEditForm>
          <Form>
            <FormInput text="이름">
              <EditableInputText edit={editMode} onChange={handleChangeUserName} defaultValue={userName} placeholder="이름" />
            </FormInput>
            <FormInput text="전화번호">
              <EditableInputText edit={editMode} onChange={handleChangeUserPhone} defaultValue={userPhone} placeholder="전화번호" />
            </FormInput>
            <FormInput text="주소">
              <EditableInputText edit={editMode} onChange={handleChangeUserAddress} defaultValue={userAddress} placeholder="주소" />
            </FormInput>
          </Form>
          <StyledAside>
            <StyledEditButton onClick={handleChangeEditMode}>
              <StyledEditIcon />
            </StyledEditButton>
          </StyledAside>
        </StyledEditForm>
        <StyledButtonGroup>{editMode && <StyledButton onClick={handleSubmitClient}>저장</StyledButton>}</StyledButtonGroup>
      </StyledDiv>
      <StyledDiv>
        <StyledClientTable>
          <colgroup>
            <col />
            <col />
            <col />
            <col />
            <col />
          </colgroup>
          <thead>
            <tr>
              <th>주문 목록</th>
              <th>이름</th>
              <th>전화번호</th>
              <th>상품 주문 개수</th>
              <th>주소</th>
            </tr>
          </thead>
          <tbody>
            {orderList.map((order) => {
              return (
                <tr key={order.ordId}>
                  <td>{order.title}</td>
                  <td>{order.name}</td>
                  <td>{order.phone}</td>
                  <td>{order.productNum}</td>
                  <td>{order.address}</td>
                </tr>
              );
            })}
          </tbody>
        </StyledClientTable>
        <LearnMorePagination pagination={pagination} handleLearnMore={handleLearnMoreOrderList} />
        <StyledButtonGroup>{editMode && <StyledCriticalButton onClick={handleDeleteMember}>회원탈퇴</StyledCriticalButton>}</StyledButtonGroup>
      </StyledDiv>
    </>
  );
};

const StyledPhoto = styled.div`
  width: 350px;
  height: 350px;
  margin-right: 50px;

  picture {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    height: 100%;

    img {
      max-width: 100%;
      max-height: 100%;
    }
  }

  @media (max-width: 1200px) {
    width: 200px;
    height: 200px;
  }

  @media (max-width: 1000px) {
    flex: 100%;

    width: auto;
    height: auto;

    picture {
      max-width: 300px;
      max-height: 300px;

      margin: 0 auto;
    }

    margin-bottom: 50px;
  }
`;

const StyledSellerAside = styled(StyledAside)`
  width: 250px;

  @media (max-width: 1200px) {
    width: 150px;
  }

  @media (max-width: 1000px) {
    width: 100px;
  }
`;

const StyledDoubleLayout = styled.div`
  position: relative;

  display: flex;
  justify-content: space-between;

  > * {
    flex: 0 0 calc((100% - 80px) / 2);
  }

  &::before {
    content: '';
    position: absolute;
    top: 0;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);

    width: 1px;

    background-color: #464646;
  }

  @media (max-width: 1000px) {
    flex-direction: column;
    justify-content: auto;

    &::before {
      content: none;
    }
  }
`;

const StyledContentBox = styled.div`
  strong {
    display: block;

    font-size: 30px;
  }

  .content-box {
    padding: 20px;
    margin-top: 20px;

    border-radius: 15px;
    background-color: #d5ccbe;
  }

  @media (max-width: 1000px) {
    margin-bottom: 20px;
  }
`;

const StyledSellerTable = styled(StyledTable)`
  colgroup {
    col:nth-child(1) {
      width: 40%;
    }
    col:nth-child(2) {
      width: 30%;
    }
    col:nth-child(3) {
      width: 30%;
    }
  }

  thead {
    tr {
      border-bottom: 1px solid #888;
    }
  }
`;

const StyledFile = styled.label`
  display: block;

  width: 100%;
  max-width: 250px;

  margin: 20px auto 0;

  text-align: center;
  cursor: pointer;

  input[type='file'] {
    display: none;
  }

  p {
    font-size: 18px;

    padding: 5px 20px;

    border-radius: 50px;
    background-color: #5d9061;

    &:hover {
      background-color: #517454;
    }
  }
`;

export const SellerMyPage = ({ handleDeleteMember }) => {
  const user = useSelector(getUser);
  const { sellerId } = user;

  const [seller, setSeller] = useState({
    name: '',
    phone: '',
    address: '',

    photo: '',
    introduce: '',
  });
  const [editMode, setEditMode] = useState(false);

  const [userName, handleChangeUserName, setUserName] = useInput(seller.name);
  const [userPhone, handleChangeUserPhone, setUserPhone] = useInput(seller.phone);
  const [userAddress, handleChangeUserAddress, setUserAddress] = useInput(seller.address);
  const [userPhoto, setUserPhoto] = useState(seller.photo);

  //  이미지 변경
  const handleChangeUserPhoto = useCallback((imageData) => {
    if (imageData) {
      const imageUrl = URL.createObjectURL(imageData);
      setUserPhoto(imageUrl);
    } else {
      setUserPhoto(seller.photo);
    }
  }, []);

  const [userPhotoFile, handleChangeUserPhotoFile, setUserPhotoFile] = useFile(handleChangeUserPhoto);
  const [userIntroduce, handleChangeUserIntroduce, setUserIntroduce] = useInput(seller.introduce);

  const [saleList, setSaleList] = useState([]);
  const [pagination, setPagination] = useState({
    page: 1,
    size: 5,
    totalPage: 0,
  });
  const { page, size } = pagination;

  //  Edit 모드 토글 핸들러
  const handleChangeEditMode = useCallback(() => {
    if (editMode) {
      //  ON => OFF 데이터 초기화
      setUserName(seller.name);
      setUserPhone(seller.phone);
      setUserAddress(seller.address);
      setUserPhoto(seller.photo);
      setUserPhotoFile(null);
      setUserIntroduce(seller.introduce);
    }
    setEditMode(!editMode);
  }, [editMode]);

  //  마이페이지 데이터 저장 후 콜백 함수
  const handleUpdateSeller = useCallback((seller) => {
    alert('저장되었습니다.');

    handleRenderSeller(seller);
    setEditMode(false);
  }, []);

  // 마이페이지 데이터 저장
  const handleSubmitSeller = useCallback(
    async (event) => {
      event.preventDefault();

      if (userName === '') {
        alert('이름을 입력해주세요.');
      } else if (userPhone === '') {
        alert('전화번호를 입력해주세요.');
      } else if (userPhone.match(/^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/) === null) {
        //  정규 표현식을 활용하여 전화번호 검증
        alert('전화번호 형식이 올바르지 않습니다.');
      } else if (userAddress === '') {
        alert('주소를 입력해주세요.');
      } else {
        let photo = seller.photo;
        if (userPhotoFile !== null) {
          const newPhoto = await uploadImage(userPhotoFile);
          if (newPhoto) {
            photo = newPhoto;
          }
        }

        //  마이페이지 데이터 업데이트
        updateSeller({ sellerId, userName, userPhone, userAddress, userPhoto: photo, userIntroduce }, handleUpdateSeller);
      }
    },
    [userName, userPhone, userAddress, userPhoto, userPhotoFile, userIntroduce]
  );

  //  마이페이지 조회 결과를 seller 상태에 저장
  const handleRenderSeller = useCallback((seller) => {
    const data = {
      name: seller.name,
      phone: seller.phone,
      address: seller.address,

      photo: seller.imageUrl,
      introduce: seller.introduce,
    };

    setSeller(data);
  }, []);

  //  마이페이지 주문 목록 조회 결과를 saleList 상태에 저장
  const handleRenderSellerSaleList = useCallback(
    (saleData) => {
      const newSaleList = saleData.data;
      const data = newSaleList.map((sale) => {
        return {
          sellerId: sale.sellerId,
          productId: sale.productId,
          boardId: sale.boardId,
          title: sale.title,
          name: sale.name,
          price: sale.price,
          stock: sale.stock,
          category: sale.category,
          soldStock: sale.soldStock,
        };
      });

      setSaleList([...saleList, ...data]);

      const pageData = saleData.pageInfo;
      const { page, size, totalPage } = pageData;

      setPagination({
        page,
        size,
        totalPage,
      });
    },
    [saleList]
  );

  //  마이페이지 조회
  useEffect(() => {
    //  마이페이지 데이터 조회
    getSeller({ sellerId }, handleRenderSeller);

    //  판매 목록 조회
    getSellerSaleList({ sellerId, page, size }, handleRenderSellerSaleList);

    return () => {
      setSeller({
        name: '',
        phone: '',
        address: '',

        photo: '',
        introduce: '',
      });
      setSaleList([]);
      setPagination({
        page: 1,
        size: 5,
        totalPage: 0,
      });
    };
  }, [user]);

  //  seller 상태 변경 시 데이터 반영
  useEffect(() => {
    setUserName(seller.name);
    setUserPhone(seller.phone);
    setUserAddress(seller.address);
    setUserPhoto(seller.photo);
    setUserPhotoFile(null);
    setUserIntroduce(seller.introduce);
  }, [seller]);

  //  더보기 기능 구현
  const handleLearnMoreOrderList = useCallback(() => {
    getSellerSaleList({ sellerId, page: page + 1, size }, handleRenderSellerSaleList);
  }, [handleRenderSellerSaleList, page, size]);

  return (
    <>
      <StyledDiv>
        <StyledEditForm>
          <StyledPhoto>
            <picture>
              <img src={userPhoto || SELLER_DEFAULT_PROFILE_IMAGE} alt={userName + ' 프로필'} />
            </picture>
            {editMode && (
              <StyledFile>
                <input type="file" onChange={handleChangeUserPhotoFile} />
                <p>이미지 업로드</p>
              </StyledFile>
            )}
          </StyledPhoto>
          <Form>
            <FormInput text="이름">
              <EditableInputText edit={editMode} onChange={handleChangeUserName} defaultValue={userName} placeholder="이름" />
            </FormInput>
            <FormInput text="전화번호">
              <EditableInputText edit={editMode} onChange={handleChangeUserPhone} defaultValue={userPhone} placeholder="전화번호" />
            </FormInput>
            <FormInput text="주소">
              <EditableInputText edit={editMode} onChange={handleChangeUserAddress} defaultValue={userAddress} placeholder="주소" />
            </FormInput>
          </Form>
          <StyledSellerAside>
            <StyledEditButton onClick={handleChangeEditMode}>
              <StyledEditIcon />
            </StyledEditButton>
          </StyledSellerAside>
        </StyledEditForm>
        <StyledButtonGroup>
          {editMode && <StyledButton onClick={handleSubmitSeller}>저장</StyledButton>}
          <StyledLinkButton to="/sell">판매 물품 등록</StyledLinkButton>
        </StyledButtonGroup>
      </StyledDiv>
      <StyledDiv>
        <StyledDoubleLayout>
          <StyledContentBox>
            <strong>판매자 소개글</strong>
            <div className="content-box">
              <EditableTextArea edit={editMode} onChange={handleChangeUserIntroduce} defaultValue={userIntroduce} />
            </div>
          </StyledContentBox>
          <StyledContentBox>
            <strong>판매자 상품</strong>
            <div className="content-box">
              <StyledSellerTable>
                <colgroup>
                  <col />
                  <col />
                  <col />
                </colgroup>
                <thead>
                  <tr>
                    <th>판매 목록</th>
                    <th>재고량</th>
                    <th>생성 일자</th>
                  </tr>
                </thead>
                <tbody>
                  {saleList.map((sale) => {
                    return (
                      <tr key={sale.productId}>
                        <td>{sale.title}</td>
                        <td>{sale.stock}</td>
                        <td>2022-11-14</td>
                      </tr>
                    );
                  })}
                </tbody>
              </StyledSellerTable>
              <LearnMorePagination pagination={pagination} handleLearnMore={handleLearnMoreOrderList} />
            </div>
          </StyledContentBox>
        </StyledDoubleLayout>
        <StyledButtonGroup>{editMode && <StyledCriticalButton onClick={handleDeleteMember}>회원탈퇴</StyledCriticalButton>}</StyledButtonGroup>
      </StyledDiv>
    </>
  );
};

const StyledMyPage = styled.div`
  width: 100%;
  max-width: 1300px;
  padding: 0 60px;
  margin: 80px auto;
`;
const MyPage = () => {
  useSessionCheck();

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const user = useSelector(getUser);

  // 소비자 테스트
  // const user = {
  //   memberId: 1,
  //   name: '김코딩',
  //   role: 'CLIENT',
  //   sellerId: 0,
  //   clientId: 1,
  // };

  // 생산자 테스트
  // const user = {
  //   memberId: 1,
  //   name: '김코딩',
  //   role: 'SELLER',
  //   sellerId: 1,
  //   clientId: 0,
  // };

  const { memberId } = user;

  //  회원 탈퇴 후 콜백 함수
  const callbackDeleteMember = useCallback(() => {
    alert('회원 탈퇴가 정상적으로 처리되었습니다.');

    navigate('/logout');
  }, []);

  //  회원 탈퇴 함수 제공
  const handleDeleteMember = useCallback(() => {
    if (confirm('정말로 회원 탈퇴 하시겠습니까?')) {
      //  마이페이지 데이터 업데이트
      dispatch(deleteMember({ memberId }, callbackDeleteMember));
    }
  }, [dispatch, memberId]);

  return (
    <StyledMyPage>
      {user.role === 'CLIENT' ? <ClientMyPage handleDeleteMember={handleDeleteMember} /> : <SellerMyPage handleDeleteMember={handleDeleteMember} />}
    </StyledMyPage>
  );
};

export default MyPage;
