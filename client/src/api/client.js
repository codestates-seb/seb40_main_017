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
    method: 'PATCH',
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
      clientId,
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
              ordId: 12,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 12',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 11,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 11',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 10,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 10',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 9,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 9',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 8,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 8',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
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
              ordId: 7,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 7',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 6,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 6',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 5,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 5',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 4,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 4',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 3,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 3',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
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
              ordId: 2,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 2',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
            },
            {
              ordId: 1,
              clientId: 1,
              productId: 1,
              boardId: 1,
              title: '상품 게시판 제목 1',
              name: '김소비',
              address: '서울시 ~ OO동',
              phone: '010-1111-1111',
              productNum: 2,
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
