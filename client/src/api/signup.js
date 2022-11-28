import { apiServer } from '../features/axios';
import { setCookie } from '../features/cookie';
import { setUser } from '../features/user/userSlice';

//  Axios 회원가입 구현
export const signupMember = ({ userId, userPassword, userPasswordCheck, userName, userPhone, userAddress, userRole }, callback) => {
  return (dispatch) => {
    const data = {
      email: userId,
      name: userName,
      password: userPassword,
      passwordCheck: userPasswordCheck,
      phone: userPhone,
      address: userAddress,
      role: userRole,
    };

    apiServer({
      method: 'POST',
      url: '/members/signup',
      data,
    })
      .then((response) => {
        if (response.data.memberId) {
          //  Response 데이터에 사용자 정보가 있다면 저장
          dispatch(setUser(response.data));

          //  페이지 새로 로드 시에도 로그인 세션을 유지하기 위해 Token 저장
          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);

          alert('회원가입 성공');

          callback(true);
        } else {
          //  오류 발생 시 메시지 경고 창 표시
          if (response.data.message) {
            alert(response.data.message);
          }
          callback(false);
        }
      })
      .catch((reason) => {
        const { response } = reason;

        //  오류 발생 시 메시지 경고 창 표시
        if (response.data.message) {
          alert(response.data.message);
        }

        callback(false);
      });
  };
};

//  회원 탈퇴
export const deleteMember = ({ memberId }, callback) => {
  apiServer({
    method: 'DELETE',
    url: `/members/${memberId}`,
  })
    .then((response) => {
      callback(response.data);
    })
    .catch((reason) => {
      console.error(reason);
    });
};
