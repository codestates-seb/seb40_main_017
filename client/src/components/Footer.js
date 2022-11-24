import styled from 'styled-components';

const StyledFooter = styled.footer`
  display: flex;
  justify-content: center;
  align-items: center;

  margin-top: auto;
  padding: 16px 0;

  border-top: 1px solid #787878;
  background-color: #d5ccbe;
`;

const CompanyInfo = styled.div`
  max-width: 640px;

  line-height: 1.5;

  span {
    margin-right: 16px;
    white-space: nowrap;
  }
`;

const Footer = () => {
  return (
    <StyledFooter>
      <CompanyInfo>
        <span>사업자등록번호: 123-45-6789</span>
        <span>통신판매업신고번호: 제2022-대한민국-777호</span>
        <span>대표이사: 김길동</span>
        <span>
          사업자등록번호확인이메일:
          <Email address="abc123@gmail.com" />
        </span>
        <span>주소: 대한특별시 민국구 사랑로 486, 17시내고향</span>
        <span>
          대표전화:
          <Tel number="1588-1588" />
        </span>
        <span>서비스제공: 17시내고향 Cloud</span>
      </CompanyInfo>
    </StyledFooter>
  );
};

const Email = ({ address }) => <a href={'mailto:' + address}> {address}</a>;
const Tel = ({ number }) => <a href={'tel:' + number}> {number}</a>;

export default Footer;
