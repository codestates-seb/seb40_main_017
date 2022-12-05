import { apiServer } from '../features/axios';

export const getSeller = ({ sellerId }, callback, error) => {
  apiServer({
    method: 'GET',
    url: `/members/seller/${sellerId}`,
  })
    .then((response) => {
      const seller = response.data.data;

      callback(seller);
    })
    .catch((reason) => {
      console.error(reason);

      const { message } = reason.response.data;

      error(message);
    });
};

export const updateSeller = ({ sellerId, userName, userPhone, userAddress, userPhoto, userIntroduce }, callback) => {
  const data = {
    sellerId,
    name: userName,
    phone: userPhone,
    address: userAddress,
    imageUrl: userPhoto,
    introduce: userIntroduce,
  };

  apiServer({
    method: 'PUT',
    url: `/members/seller/${sellerId}`,
    data,
  })
    .then((response) => {
      callback(response.data);
    })
    .catch((reason) => {
      console.error(reason);
    });
};

export const getSellerSaleList = ({ sellerId, page, size }, callback) => {
  apiServer({
    method: 'GET',
    url: `/mypage/sold/${sellerId}`,
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
