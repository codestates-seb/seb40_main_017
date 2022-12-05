import { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { KakaoSubmitbutton, LinkButton, SubmitButton } from '../../components/Button';
import Container from '../../components/Container';
import { Form, FormInput } from '../../components/Form';
import { InputPassword, InputText, useInput } from '../../components/Input';
import { login, useSessionCheck } from '../../api/login';

const LoginPage = () => {
  useSessionCheck(false, '/');

  const dispatch = useDispatch();

  const navigate = useNavigate();

  const [userId, handleChangeUserId] = useInput('');
  const [userPassword, handleChangeUserPassword] = useInput('');

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
        <KakaoSubmitbutton href={process.env.REACT_APP_KAKAO_LOGIN_URL}>카카오 로그인</KakaoSubmitbutton>
      </Container>
    </>
  );
};

export default LoginPage;
