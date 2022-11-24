import axios from 'axios';
//axios.create 라는 메서드는 default config 를 적용하는 메서드인데,
//baseURL 옵션을 통해 API 서버 URL 을 기본으로 적용한 axios 환경이 생성되는거
export const apiServer = axios.create({
  baseURL: process.env.REACT_APP_API_SERVER,
  timeout: 10000,
});
