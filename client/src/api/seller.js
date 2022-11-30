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
    method: 'PATCH',
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

      //  테스트용
      let data = null;
      if (page === 1) {
        data = {
          data: [
            {
              boardId: 12,
              sellerId: 1,
              title: '상품 게시판 제목 12',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 11,
              sellerId: 1,
              title: '상품 게시판 제목 11',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 10,
              sellerId: 1,
              title: '상품 게시판 제목 10',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 9,
              sellerId: 1,
              title: '상품 게시판 제목 9',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 8,
              sellerId: 1,
              title: '상품 게시판 제목 8',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
          ],
          pageInfo: {
            page,
            size,
            totalPage: 3,
          },
        };
      } else if (page === 2) {
        data = {
          data: [
            {
              boardId: 7,
              sellerId: 1,
              title: '상품 게시판 제목 7',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 6,
              sellerId: 1,
              title: '상품 게시판 제목 6',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 5,
              sellerId: 1,
              title: '상품 게시판 제목 5',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 4,
              sellerId: 1,
              title: '상품 게시판 제목 4',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 3,
              sellerId: 1,
              title: '상품 게시판 제목 3',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
          ],
          pageInfo: {
            page,
            size,
            totalPage: 3,
          },
        };
      } else if (page === 3) {
        data = {
          data: [
            {
              boardId: 2,
              sellerId: 1,
              title: '상품 게시판 제목 2',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
            {
              boardId: 1,
              sellerId: 1,
              title: '상품 게시판 제목 1',
              name: '김소비',
              price: 25000,
              phone: '010-2222-2222',
              stock: 30,
              category: 'nuts',
              leftStock: 10,
            },
          ],
          pageInfo: {
            page,
            size,
            totalPage: 3,
          },
        };
      }

      if (data !== null) {
        callback(data);
      }
    });
};
