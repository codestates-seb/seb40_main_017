import styled from 'styled-components';

const StyledLearnMoreButton = styled.button`
  display: block;

  width: 100%;
  max-width: 200px;

  padding: 10px 20px;
  margin: 20px auto;

  color: #fff;
  background-color: #5d9061;
  border: none;

  &:hover {
    background-color: #4b704e;
  }
`;
export const LearnMorePagination = ({ pagination, handleLearnMore }) => {
  const { page, totalPages } = pagination;
  return <>{page < totalPages && <StyledLearnMoreButton onClick={handleLearnMore}>더보기</StyledLearnMoreButton>}</>;
};
