import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { useSessionCheck } from '../../api/login';
import { getSeller, getSellerSaleList } from '../../api/seller';
import SELLER_DEFAULT_PROFILE_IMAGE from '../../assets/images/f9bfadecab5b9feaae6ba584a7d18bc7-sticker.png';
import { LearnMorePagination } from '../../components/Pagination';

const StyledSellerInfo = styled.div`
  width: 100%;
  max-width: 1300px;
  padding: 0 60px;
  margin: 80px auto;

  .seller-title {
    display: block;

    font-size: 24px;

    margin: 25px 0;

    text-align: center;
  }

  .seller-profile {
    display: flex;
    align-items: flex-start;

    .seller-profile-image {
      width: 350px;
      margin-right: 50px;

      picture {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        height: 100%;

        img {
          max-width: 100%;
          max-height: 100%;
        }
      }

      @media (max-width: 1200px) {
        width: 250px;
      }
    }

    .seller-profile-info {
      flex: 1;
    }

    @media (max-width: 1000px) {
      flex-direction: column;
      align-items: center;

      .seller-profile-image {
        position: static;
        top: auto;

        flex: 1;
        width: auto;
        margin-right: 0;

        picture {
          max-width: 300px;
        }
      }

      .seller-profile-info {
        flex: 1;
        width: 100%;
      }
    }
  }
`;
const StyledParagraph = styled.p`
  font-weight: 700;
  font-size: 20px;

  & + p {
    margin-top: 20px;
  }
`;
const StyledPreWrap = styled.p`
  min-height: 200px;

  white-space: pre-wrap;

  @media (max-width: 800px) {
    min-height: 100px;

    border: 1px solid #a16d6d;
  }
`;
const StyledBlock = styled.div`
  padding: 0 40px;

  & + div {
    margin-top: 40px;
    padding-top: 40px;

    border-top: 5px solid #5d9061;
  }

  @media (max-width: 1000px) {
    padding: 0;
  }
`;
const StyledDoubleBlock = styled.div`
  position: relative;

  display: flex;
  justify-content: space-between;

  > * {
    flex: 0 0 calc((100% - 80px) / 2);
  }

  &::before {
    content: '';
    position: absolute;
    top: 0;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);

    width: 1px;

    background-color: #5f5f5f;
  }

  @media (max-width: 800px) {
    flex-direction: column;
    justify-content: auto;

    &::before {
      content: none;
    }
  }
`;
const StyledContentBox = styled.div`
  strong {
    display: block;

    font-size: 30px;
  }

  .content-box {
    padding: 40px;

    border-radius: 15px;
    background-color: #d5ccbe;
  }
`;
const StyledContentSubBox = styled.div`
  strong {
    display: block;

    font-size: 20px;
  }

  .content-sub-box {
    margin-top: 20px;
  }

  @media (max-width: 800px) {
    & + div {
      margin-top: 20px;
    }
  }
`;
const StyledTable = styled.table`
  width: 100%;

  border-collapse: collapse;
  border-spacing: 0;

  text-align: center;

  th {
    font-size: 14px;
    font-weight: 400;
    padding: 10px 5px;

    word-break: keep-all;
  }

  td {
    font-size: 12px;
    font-weight: 400;
    padding: 10px 5px;
  }

  colgroup {
    col:nth-child(1) {
      width: 40%;
    }
    col:nth-child(2) {
      width: 30%;
    }
    col:nth-child(3) {
      width: 30%;
    }
  }

  thead {
    tr {
      border-bottom: 1px solid #888;
    }
  }
`;
const SellerInfoPage = () => {
  useSessionCheck();

  const navigate = useNavigate();
  const params = useParams();
  const { sellerId } = params;

  const [seller, setSeller] = useState({
    name: '',
    phone: '',
    address: '',

    photo: '',
    introduce: '',
  });
  const [saleList, setSaleList] = useState([]);
  const [pagination, setPagination] = useState({
    page: 1,
    size: 5,
    totalPages: 0,
  });
  const { page, size } = pagination;

  //  생산자 조회 실패 시 메인 페이지로 리다이렉션
  const handleErrorFindSeller = useCallback(
    (message) => {
      alert(message);
      navigate('/');
    },
    [navigate]
  );

  //  생산자 조회 결과를 seller 상태에 저장
  const handleRenderSeller = useCallback((seller) => {
    const data = {
      name: seller.name,
      phone: seller.phone,
      address: seller.address,

      photo: seller.imageUrl,
      introduce: seller.introduce,
    };

    setSeller(data);
  }, []);

  //  생산자 주문 목록 조회 결과를 saleList 상태에 저장
  const handleRenderSellerSaleList = useCallback(
    (saleData) => {
      const newSaleList = saleData.data;
      const data = newSaleList.map((sale) => {
        return {
          boardId: sale.boardId,
          sellerId: sale.sellerId,
          title: sale.title,
          name: sale.name,
          price: sale.price,
          phone: sale.phone,
          stock: sale.stock,
          category: sale.category,
          leftStock: sale.leftStock,
          createAt: sale.createAt,
          modifiedAt: sale.modifiedAt,
        };
      });

      setSaleList([...saleList, ...data]);

      const pageData = saleData.pageInfo;
      const { page, size, totalPages } = pageData;

      setPagination({
        page,
        size,
        totalPages,
      });
    },
    [saleList]
  );

  //  생산자 조회
  useEffect(() => {
    //  생산자 데이터 조회
    getSeller({ sellerId }, handleRenderSeller, handleErrorFindSeller);

    //  생산자 판매 목록 조회
    getSellerSaleList({ sellerId, page, size }, handleRenderSellerSaleList);

    return () => {
      console.log('clear');

      setSeller({
        name: '',
        phone: '',
        address: '',

        photo: '',
        introduce: '',
      });
      setSaleList([]);
      setPagination({
        page: 1,
        size: 5,
        totalPages: 0,
      });
    };
  }, []);

  //  더보기 기능 구현
  const handleLearnMoreOrderList = useCallback(() => {
    getSellerSaleList({ sellerId, page: page + 1, size }, handleRenderSellerSaleList);
  }, [handleRenderSellerSaleList, page, size]);

  return (
    <StyledSellerInfo>
      <strong className="seller-title">생산자 소개 정보</strong>
      <div className="seller-profile">
        <div className="seller-profile-image">
          <picture>
            <img src={seller.photo || SELLER_DEFAULT_PROFILE_IMAGE} alt={seller.name + ' 프로필'} />
          </picture>
        </div>
        <div className="seller-profile-info">
          <StyledBlock>
            <StyledContentBox>
              <div className="content-box">
                <StyledParagraph>{seller.name}</StyledParagraph>
                <StyledParagraph>{seller.phone}</StyledParagraph>
                <StyledParagraph>{seller.address}</StyledParagraph>
              </div>
            </StyledContentBox>
          </StyledBlock>
          <StyledBlock>
            <StyledContentBox>
              <div className="content-box">
                <StyledDoubleBlock>
                  <StyledContentSubBox>
                    <strong>판매자 소개글</strong>
                    <div className="content-sub-box">
                      <StyledPreWrap>{seller.introduce}</StyledPreWrap>
                    </div>
                  </StyledContentSubBox>
                  <StyledContentSubBox>
                    <strong>판매자 상품</strong>
                    <div className="content-sub-box">
                      <StyledTable>
                        <colgroup>
                          <col />
                          <col />
                          <col />
                        </colgroup>
                        <thead>
                          <tr>
                            <th>판매 목록</th>
                            <th>재고량</th>
                            <th>생성 일자</th>
                          </tr>
                        </thead>
                        <tbody>
                          {saleList.map((sale) => {
                            return (
                              <tr key={sale.boardId}>
                                <td>{sale.title}</td>
                                <td>{sale.leftStock}</td>
                                <td>{sale.createAt}</td>
                              </tr>
                            );
                          })}
                        </tbody>
                      </StyledTable>
                      <LearnMorePagination pagination={pagination} handleLearnMore={handleLearnMoreOrderList} />
                    </div>
                  </StyledContentSubBox>
                </StyledDoubleBlock>
              </div>
            </StyledContentBox>
          </StyledBlock>
        </div>
      </div>
    </StyledSellerInfo>
  );
};

export default SellerInfoPage;
