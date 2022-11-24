import { apiServer } from '../features/axios';

//  Axios 회원가입 구현
export const signup = ({ userId, userPassword, userPasswordCheck, userName, userPhone, userAddress, userRole }, callback) => {
  return () => {
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
