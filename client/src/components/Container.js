import styled from 'styled-components';

const Container = ({ name, children }) => {
  return (
    <StyledWrapper>
      <StyledContainer>
        <StyledTitle>{name}</StyledTitle>
        {children}
      </StyledContainer>
    </StyledWrapper>
  );
};

const StyledWrapper = styled.div`
  display: flex;
  flex-direction: column;

  padding: 40px 0;
  flex: 1;
`;

const StyledContainer = styled.div`
  align-self: center;

  width: 100%;
  max-width: 400px;

  margin: auto;
  padding: 60px;

  border-radius: 40px;

  background-color: #d5ccbe;
`;

const StyledTitle = styled.h2`
  font-size: 36px;

  margin-bottom: 20px;

  text-align: center;
`;

export default Container;
