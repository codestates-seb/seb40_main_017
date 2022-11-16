import { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { SubmitButton } from '../../components/Button';
import Container from '../../components/Container';
import { Form, FormInput, FormRadioGroup } from '../../components/Form';
import { InputPassword, InputText, useInput } from '../../components/Input';
import { userService } from '../../features/user/userSlice';

const SignupPage = () => {
  const dispatch = useDispatch();

  const [userId, handleChangeUserId] = useInput('');
  const [userPassword, handleChangeUserPassword] = useInput('');
  const [userPasswordCheck, handleChangeUserPasswordCheck] = useInput('');
  const [userName, handleChangeUserName] = useInput('');
  const [userPhone, handleChangeUserPhone] = useInput('');
  const [userAddress, handleChangeUserAddress] = useInput('');
  const [userRole, handleChangeUserRole] = useInput('');

  const submitCallback = useCallback(
    (success) => {
      if (success) {
        dispatch(userService.login(userId, userPassword));
      }
    },
    [dispatch, userId, userPassword]
  );

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();

      if (userId === '') {
        alert('이메일을 입력해주세요.');
      } else if (userId.match(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/) === null) {
        alert('이메일 형식이 올바르지 않습니다.');
      } else if (userPassword === '') {
        alert('비밀번호를 입력해주세요.');
      } else if (userPassword.match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/) === null) {
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
        alert('전화번호 형식이 올바르지 않습니다.');
      } else if (userAddress === '') {
        alert('주소를 입력해주세요.');
      } else if (userRole === '') {
        alert('회원 구분을 선택해주세요.');
      } else {
        dispatch(userService.signup({ userId, userPassword, userPasswordCheck, userName, userPhone, userAddress, userRole }, submitCallback));
      }
    },
    [dispatch, submitCallback, userId, userPassword, userPasswordCheck, userName, userPhone, userAddress, userRole]
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
