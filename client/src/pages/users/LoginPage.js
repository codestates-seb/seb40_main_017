import { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { LinkButton, SubmitButton } from '../../components/Button';
import Container from '../../components/Container';
import { Form, FormInput } from '../../components/Form';
import { InputPassword, InputText, useInput } from '../../components/Input';
import { login } from '../../api/login';

const LoginPage = () => {
  //  Redux Provider 로 부터 dispatch (상태 관리를 위한 함수) 수신
  const dispatch = useDispatch();
  //  로그인 후 페이지 이동을 위해 navigate 정의
  const navigate = useNavigate();

  //  아이디와 패스워드 데이터를 관리하는 CustomHook 을 통해 데이터와 핸들러 정의
  const [userId, handleChangeUserId] = useInput('');
  const [userPassword, handleChangeUserPassword] = useInput('');

  //  useCallback 은 컴포넌트가 렌더링 될 때마다 반복해서 정의되지 않도록 메모리에 저장해두기 위한 Hook 방법
  //  로그인 시도 결과에 따라 처리하기 위한 콜백 함수 정의
  const submitCallback = useCallback(
    (success) => {
      if (success) {
        navigate('/');
      }
    },
    [navigate]
  );

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();

      if (userId === '') {
        alert('이메일을 입력해주세요.');
      } else if (userPassword === '') {
        alert('비밀번호를 입력해주세요.');
      } else {
        dispatch(login({ userId, userPassword }, submitCallback));
      }
    },
    [dispatch, submitCallback, userId, userPassword]
  );

  return (
    <>
      <Container name="로그인">
        <Form onSubmit={handleSubmit}>
          <FormInput text="이메일">
            <InputText onChange={handleChangeUserId} placeholder="이메일" />
          </FormInput>
          <FormInput text="비밀번호">
            <InputPassword onChange={handleChangeUserPassword} placeholder="비밀번호" />
          </FormInput>
          <SubmitButton>로그인</SubmitButton>
          <LinkButton to="/signup">회원가입</LinkButton>
        </Form>
      </Container>
    </>
  );
};

export default LoginPage;
