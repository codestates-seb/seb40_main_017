import { apiServer } from '../features/axios';

//  마이페이지 접근 시 불러오는 사용자 데이터
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

//  마이페이지 데이터 수정
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

//  주문 목록 조회
export const getSellerSaleList = ({ sellerId, page, size }, callback) => {
  apiServer({
    method: 'GET',
    url: `/mypage/sold/${sellerId}`,
    params: {
      sellerId,
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
