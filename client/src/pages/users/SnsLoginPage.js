import { useCallback, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { snsLogin } from '../../api/login';
import { snsSignupMember } from '../../api/signup';
import { SubmitButton } from '../../components/Button';
import Container from '../../components/Container';
import { Form, FormRadioGroup } from '../../components/Form';
import { useInput } from '../../components/Input';

const SnsLoginPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const params = new URLSearchParams(useLocation().search);
  const accessToken = params.get('accessToken');
  const memberId = params.get('memberId');

  const handleSuccessCallback = useCallback((status) => {
    if (status) {
      navigate('/');
    }
  }, []);

  useEffect(() => {
    if (memberId === null) {
      dispatch(snsLogin({ accessToken }, handleSuccessCallback));
    }
  }, []);

  const [userRole, handleChangeUserRole] = useInput('');

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();

      if (userRole === '') {
        alert('회원 구분을 선택해주세요.');
      } else {
        dispatch(snsSignupMember({ accessToken, memberId, userRole }, handleSuccessCallback));
      }
    },
    [dispatch, handleSuccessCallback, memberId, userRole]
  );

  return (
    <>
      {memberId === null ? (
        <>SNS 로그인 중입니다...</>
      ) : (
        <Container name="소셜 회원가입">
          <Form onSubmit={handleSubmit}>
            <FormRadioGroup
              name="role"
              onChange={handleChangeUserRole}
              items={[
                { value: 'SELLER', text: '판매자', checked: false },
                { value: 'CLIENT', text: '구매자', checked: false },
              ]}
            />
            <SubmitButton>가입완료</SubmitButton>
          </Form>
        </Container>
      )}
    </>
  );
};

export default SnsLoginPage;
