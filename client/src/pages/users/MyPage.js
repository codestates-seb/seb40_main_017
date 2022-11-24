import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getUser } from '../../features/user/userSlice';

import { BiEditAlt } from 'react-icons/bi';
import { EditableInputText, useInput, useToggle } from '../../components/Input';
import { Form, FormInput } from '../../components/Form';

const StyledEditForm = styled.div`
  display: flex;
  margin-bottom: 55px;

  form {
    flex: 1;

    input[type='text'] {
      background-color: #d5ccbe;
    }
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
  text-align: right;
`;
const StyledButton = styled.button`
  min-width: 90px;

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

  border-top: 5px solid #5d9061;

  border-collapse: collapse;
  border-spacing: 0;

  text-align: center;

  th {
    padding: 15px 10px;
  }

  td {
    padding: 5px 10px;
  }
`;
const StyledClientTable = styled(StyledTable)`
  margin: 40px 0 20px;

  colgroup {
    col:nth-child(1) {
      width: 20%;
    }
    col:nth-child(2) {
      width: 10%;
    }
    col:nth-child(3) {
      width: 20%;
    }
    col:nth-child(4) {
      width: 20%;
    }
    col:nth-child(5) {
      width: 30%;
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

export const ClientMyPage = () => {
  const [userName, handleChangeUserName] = useInput('김코딩');
  const [userPhone, handleChangeUserPhone] = useInput('010-1111-1111');
  const [userAddress, handleChangeUserAddress] = useInput('oo시 oo동');
  const [editMode, handleChangeEditMode, setEditMode] = useToggle(false);

  return (
    <>
      <div>
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
        <StyledButtonGroup>
          <StyledButton>주문목록</StyledButton>
          {editMode && <StyledButton onClick={() => setEditMode(false)}>저장</StyledButton>}
        </StyledButtonGroup>
      </div>
      <div>
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
            <tr>
              <td>사과 1kg</td>
              <td>김코딩</td>
              <td>010-xxxx-xxxx</td>
              <td>1</td>
              <td>서울특별시 서초구 00대로</td>
            </tr>
            <tr>
              <td>쌀 20kg</td>
              <td>김코딩</td>
              <td>010-xxxx-xxxx</td>
              <td>1</td>
              <td>서울특별시 서초구 00대로</td>
            </tr>
            <tr>
              <td>아몬드 1kg</td>
              <td>김코딩</td>
              <td>010-xxxx-xxxx</td>
              <td>1</td>
              <td>서울특별시 서초구 00대로</td>
            </tr>
          </tbody>
        </StyledClientTable>
        <StyledButtonGroup>{editMode && <StyledCriticalButton>회원탈퇴</StyledCriticalButton>}</StyledButtonGroup>
      </div>
    </>
  );
};

export const SellerMyPage = () => {
  return <>SellerMyPage</>;
};

export const SellerUserPage = () => {
  return <>SellerUserPage</>;
};

const StyledMyPage = styled.div`
  width: 100%;
  max-width: 720px;
  margin: 80px auto 0;
`;
const MyPage = () => {
  const navigate = useNavigate();
  const user = useSelector(getUser);

  useEffect(() => {
    if (!user.memberId) {
      navigate('/login');
    }
  }, [navigate, user]);

  return <StyledMyPage>{user.role === 'CLIENT' ? <ClientMyPage /> : <SellerMyPage />}</StyledMyPage>;
};

export default MyPage;
