import { apiServer } from '../features/axios';
import { setCookie } from '../features/cookie';
import { setUser } from '../features/user/userSlice';

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
          dispatch(setUser(response.data));

          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);

          alert('회원가입 성공');

          callback(true);
        } else {
          if (response.data.message) {
            alert(response.data.message);
          }
          callback(false);
        }
      })
      .catch((reason) => {
        const { response } = reason;

        if (response.data.message) {
          alert(response.data.message);
        }

        callback(false);
      });
  };
};

export const snsSignupMember = ({ accessToken, memberId, userRole }, callback) => {
  return (dispatch) => {
    const data = {
      role: userRole,
    };

    apiServer({
      method: 'PUT',
      url: `/social/${memberId}`,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data,
    })
      .then((response) => {
        if (response.data.memberId) {
          dispatch(setUser(response.data));

          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);

          alert('회원가입 성공');

          callback(true);
        } else {
          if (response.data.message) {
            alert(response.data.message);
          }
          callback(false);
        }
      })
      .catch((reason) => {
        const { response } = reason;

        if (response.data.message) {
          alert(response.data.message);
        }

        callback(false);
      });
  };
};

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
