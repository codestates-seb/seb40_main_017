const SECOND = 1000;
const MINUTE = SECOND * 60;
const HOUR = MINUTE * 60;
const DAY = HOUR * 24;

//  쿠키 데이터를 작성하기 위해 만든 스크립트
export const setCookie = (name, value, expire = 1) => {
  const date = new Date();
  date.setTime(date.getTime() + expire * DAY);

  document.cookie = `${name}=${value}; expires=${date.toUTCString()}`;
};

//  쿠키 데이터를 받아오기 위해 만든 스크립트
export const getCookie = (name) => {
  const cookies = document.cookie.split(/;/);
  const cookieData = cookies.find((cookie) => cookie.split(/=/)[0] === name);

  if (cookieData) {
    const cookieSplitValue = cookieData.split(/=/);

    cookieSplitValue.splice(0, 1);

    return cookieSplitValue.join('=');
  }

  return null;
};

//  쿠키 데이터를 제거하기 위해 만든 스크립트
export const removeCookie = (name) => {
  const date = new Date(0);

  document.cookie = `${name}=; expires=${date.toUTCString()}`;
};
