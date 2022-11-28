import axios from 'axios';
import { getCookie } from './cookie';

export const apiServer = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  timeout: 10000,
});

//  accessToken 헤더 사용하지 않는 URL List
const excludeURL = ['/login', '/signup'];

//  요청 인터셉터 추가
apiServer.interceptors.request.use(
  (config) => {
    //  요청 전 처리부
    const accessToken = getCookie('accessToken');

    if (accessToken !== null) {
      if (excludeURL.indexOf(config.url) < 0) {
        config.headers = {
          ...config.headers,
          Authorization: `Bearer ${accessToken}`,
        };
      }
    }

    return config;
  },
  (error) => {
    //  오류 발생 시 처리부
    return Promise.reject(error);
  }
);
