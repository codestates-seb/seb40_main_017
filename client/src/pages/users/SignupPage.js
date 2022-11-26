import { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { SubmitButton } from '../../components/Button';
import Container from '../../components/Container';
import { Form, FormInput, FormRadioGroup } from '../../components/Form';
import { InputPassword, InputText, useInput } from '../../components/Input';
import { signupMember } from '../../api/signup';
import { useSessionCheck } from '../../api/login';

const SignupPage = () => {
  useSessionCheck(false, '/');

  //  로그인 화면과 동일
  const dispatch = useDispatch();
  const navigate = useNavigate();

  //  로그인 화면과 동일
  const [userId, handleChangeUserId] = useInput('');
  const [userPassword, handleChangeUserPassword] = useInput('');
  const [userPasswordCheck, handleChangeUserPasswordCheck] = useInput('');
  const [userName, handleChangeUserName] = useInput('');
  const [userPhone, handleChangeUserPhone] = useInput('');
  const [userAddress, handleChangeUserAddress] = useInput('');
  const [userRole, handleChangeUserRole] = useInput('');

  //  회원가입 성공 시 콜백 함수
  const signupCallback = useCallback(
    (success) => {
      if (success) {
        navigate('/');
      }
    },
    [navigate]
  );
  //  로그인 화면과 동일
  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();

      if (userId === '') {
        alert('이메일을 입력해주세요.');
      } else if (userId.match(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/) === null) {
        //  정규 표현식을 활용하여 이메일 검증
        alert('이메일 형식이 올바르지 않습니다.');
      } else if (userPassword === '') {
        alert('비밀번호를 입력해주세요.');
      } else if (userPassword.match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/) === null) {
        //  정규 표현식을 활용하여 비밀번호 정책 검증
        alert('비밀번호는 영어, 숫자로 구성된 8자 ~ 15자만 사용할 수 있습니다.');
      } else if (userPasswordCheck === '') {
        alert('확인용 비밀번호를 입력해주세요.');
      } else if (userPassword !== userPasswordCheck) {
        alert('비밀번호가 다릅니다.');
      } else if (userName === '') {
        alert('이름을 입력해주세요.');
      } else if (userPhone === '') {
        alert('전화번호를 입력해주세요.');
      } else if (userPhone.match(/^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/) === null) {
        //  정규 표현식을 활용하여 전화번호 검증
        alert('전화번호 형식이 올바르지 않습니다.');
      } else if (userAddress === '') {
        alert('주소를 입력해주세요.');
      } else if (userRole === '') {
        alert('회원 구분을 선택해주세요.');
      } else {
        //  Redux 상태 업데이트까지 수행하는 회원가입 서비스 실행
        dispatch(signupMember({ userId, userPassword, userPasswordCheck, userName, userPhone, userAddress, userRole }, signupCallback));
      }
    },
    [dispatch, signupCallback, userId, userPassword, userPasswordCheck, userName, userPhone, userAddress, userRole]
  );

  return (
    <Container name="회원가입" temp={handleChangeUserRole}>
      <Form onSubmit={handleSubmit}>
        <FormInput text="이메일">
          <InputText onChange={handleChangeUserId} placeholder="이메일" />
        </FormInput>
        <FormInput text="비밀번호">
          <InputPassword onChange={handleChangeUserPassword} placeholder="비밀번호" />
        </FormInput>
        <FormInput text="비밀번호 확인">
          <InputPassword onChange={handleChangeUserPasswordCheck} placeholder="비밀번호 확인" />
        </FormInput>
        <FormInput text="이름">
          <InputText onChange={handleChangeUserName} placeholder="이름" />
        </FormInput>
        <FormInput text="전화번호">
          <InputText onChange={handleChangeUserPhone} placeholder="전화번호 (- 포함)" />
        </FormInput>
        <FormInput text="주소">
          <InputText onChange={handleChangeUserAddress} placeholder="주소" />
        </FormInput>
        {/*  FormRadioGroup 컴포넌트는 items 속성을 통해 라디오 버튼을 렌더링 하도록 함 */}
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
  );
};

export default SignupPage;
