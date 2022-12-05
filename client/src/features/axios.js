import axios from 'axios';
import { getCookie } from './cookie';

export const apiServer = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  timeout: 10000,
});

const excludeURL = ['/login', '/signup'];

apiServer.interceptors.request.use(
  (config) => {
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
    return Promise.reject(error);
  }
);
