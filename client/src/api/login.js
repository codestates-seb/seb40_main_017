import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { apiServer } from '../features/axios';

import { getCookie, removeCookie, setCookie } from '../features/cookie';
import { clearUser, getUser, setUser } from '../features/user/userSlice';

export const login = ({ userId, userPassword }, callback) => {
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
          dispatch(setUser(response.data));

          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);

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

export const snsLogin = ({ accessToken }, callback) => {
  return (dispatch) => {
    apiServer({
      method: 'GET',
      url: '/access',
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
      .then((response) => {
        if (response.data.memberId) {
          dispatch(setUser(response.data));

          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);

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

export const logout = (callback) => {
  return (dispatch) => {
    dispatch(clearUser());

    if (getCookie('accessToken')) {
      apiServer({
        method: 'GET',
        url: '/members/logout',
      })
        .catch((reason) => {
          console.error(reason);
        })
        .finally(() => {
          removeCookie('accessToken');
        });
    }

    if (typeof callback === 'function') {
      callback();
    }
  };
};

export const updateSession = (callback) => {
  return (dispatch) => {
    apiServer({
      method: 'GET',
      url: '/access',
    })
      .then((response) => {
        if (response.data.memberId) {
          dispatch(setUser(response.data));

          const accessToken = response.data.authorization || response.headers['authorization'];
          setCookie('accessToken', accessToken);
        }
      })
      .catch((reason) => {
        console.log(reason);

        dispatch(
          logout(() => {
            location.reload();
          })
        );
      })
      .finally(() => {
        if (typeof callback === 'function') {
          callback();
        }
      });
  };
};

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
    }, 1000);

    return () => clearTimeout(delayId);
  }, [navigate, hasSession]);
};
