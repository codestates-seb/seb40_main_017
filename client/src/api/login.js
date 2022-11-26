import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { apiServer } from '../features/axios';

import { removeCookie, setCookie } from '../features/cookie';
import { clearUser, getUser, setUser } from '../features/user/userSlice';

//  Axios 로그인 및 Redux 상태 관리
export const login = ({ userId, userPassword }, callback) => {
  //  입력 값을 활용하여 dispatch 와 통신하도록 Thunk 형 함수 리턴
  return (dispatch) => {
    const data = {
      email: userId,
      password: userPassword,
    };

    apiServer({
      method: 'POST',
      url: '/login',
      data,
    })
      .then((response) => {
        if (response.data.memberId) {
          //  Response 데이터에 사용자 정보가 있다면 저장
          dispatch(setUser(response.data));

          //  페이지 새로 로드 시에도 로그인 세션을 유지하기 위해 Token 저장
          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);

          //  로그인 성공을 알림
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

//  Axios 로그아웃 및 Redux 상태 관리
export const logout = (callback) => {
  return (dispatch) => {
    //  사용자 로그인 상태 제거
    dispatch(clearUser());

    //  쿠키 토큰 폐기
    removeCookie('accessToken');

    callback();
  };
};

//  Axios 페이지 새로고침 시 로그인 세션 불러오기
export const updateSession = (callback) => {
  return (dispatch) => {
    apiServer({
      method: 'GET',
      url: '/access',
    })
      .then((response) => {
        if (response.data.memberId) {
          //  Response 데이터에 사용자 정보가 있다면 저장
          dispatch(setUser(response.data));

          //  토큰 새로 저장
          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);
        }
      })
      .catch((reason) => {
        console.log(reason);
      })
      .finally(() => {
        callback();
      });
  };
};

//  로그인 세션 검증
export const useSessionCheck = (needLogin = true, to = '/login') => {
  const navigate = useNavigate();

  const user = useSelector(getUser);
  const { memberId } = user;
  const hasSession = memberId > 0;

  useEffect(() => {
    const delayId = setTimeout(() => {
      if (hasSession !== needLogin) {
        navigate(to);
      }
    }, 100);

    return () => clearTimeout(delayId);
  }, [navigate, hasSession]);
};
