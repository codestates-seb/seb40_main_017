import { apiServer } from '../features/axios';

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
