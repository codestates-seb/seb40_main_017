import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

import { getCookie, removeCookie, setCookie } from '../cookie';

const userSlice = createSlice({
  name: 'user',
  initialState: {
    memberId: 0,
    name: '',
    email: '',
    role: '',
  },
  reducers: {
    setUser(state, action) {
      state.memberId = action.payload.memberId;
      state.name = action.payload.name;
      state.email = action.payload.email;
      state.role = action.payload.role;
    },

    clearUser(state) {
      return { ...state, ...userSlice.getInitialState() };
    },
  },
});

export default userSlice.reducer;

export const { setUser, clearUser } = userSlice.actions;

export const getUser = (state) => (state.user.memberId > 0 ? state.user : null);

const login = ({ userId, userPassword }, callback) => {
  return (dispatch) => {
    const data = {
      email: userId,
      password: userPassword,
    };

    // 임시 적용
    const response = {
      data: {
        memberId: 1,
        name: '홍길동',
        email: 'gildong.hong@gmail.com',
        role: 'CLIENT',
      },
    };
    dispatch(setUser(response.data));
    callback(true);

    console.log(setCookie, data);

    /*
    axios({
      method: 'POST',
      url: 'http://localhost:8080/login',
      data,
    })
      .then((response) => {
        if (response.data.memberId) {
          // @webius - Response 데이터에 사용자 정보가 있다면 저장
          dispatch(setUser(response.data));

          // @webius - 페이지 새로 로드 시에도 로그인 세션을 유지하기 위해 Token 저장
          const accessToken = response.headers['authorization'];
          const refreshToken = response.headers['refresh'];
          setCookie('accessToken', accessToken);
          setCookie('refreshToken', refreshToken);
          console.log(response.headers);

          // @webius - 로그인 성공을 알림
          callback(true);
        } else {
          callback(false);
        }
      })
      .catch((reason) => {
        console.error(reason);

        callback(false);
      });
    */
  };
};

const logout = (callback) => {
  return (dispatch) => {
    dispatch(clearUser());

    removeCookie('accessToken');
    removeCookie('refreshToken');

    callback();
  };
};

const signup = ({ userId, userPassword, userPasswordCheck, userName, userPhone, userAddress, userRole }, callback) => {
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

    axios({
      method: 'POST',
      url: 'http://localhost:8080/members/signup',
      data,
    })
      .then((response) => {
        if (response.data.memberId) {
          alert('회원가입 성공');
          callback(true);
        } else {
          callback(false);
        }
      })
      .catch((reason) => {
        console.error(reason);
        callback(false);
      });
  };
};

const updateSession = () => {
  return (dispatch) => {
    const accessToken = getCookie('token');

    console.log(accessToken, dispatch);

    /* 로그인 연결 후 로직 설계 필요 */
  };
};

export const userService = {
  login,
  logout,
  signup,
  updateSession,
};
