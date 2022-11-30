import { apiServer } from '../features/axios';

//  마이페이지 접근 시 불러오는 사용자 데이터
export const getClient = ({ clientId }, callback) => {
  apiServer({
    method: 'GET',
    url: `/members/client/${clientId}`,
  })
    .then((response) => {
      callback(response.data);
    })
    .catch((reason) => {
      console.error(reason);
    });
};

//  마이페이지 데이터 수정
export const updateClient = ({ clientId, userName, userPhone, userAddress }, callback) => {
  const data = {
    clientId,
    name: userName,
    phone: userPhone,
    address: userAddress,
  };

  apiServer({
    method: 'PUT',
    url: `/members/client/${clientId}`,
    data,
  })
    .then((response) => {
      callback(response.data);
    })
    .catch((reason) => {
      console.error(reason);
    });
};

//  주문 목록 조회
export const getClientOrderList = ({ clientId, page, size }, callback) => {
  console.log('getClientOrderList', page, size);
  apiServer({
    method: 'GET',
    url: `/mypage/${clientId}`,
    params: {
      page,
      size,
    },
  })
    .then((response) => {
      callback(response.data);
    })
    .catch((reason) => {
      console.error(reason);
    });
};
