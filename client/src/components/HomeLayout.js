import styled from 'styled-components';
import HomeInfo from './HomeInfo';
import HomeNav from './HomeNav';
import HomeVisual from './HomeVisual';

const Homelayout = styled.section`
  background: var(--off-white);
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
function HomeLayout() {
  return (
    <Homelayout>
      <HomeVisual></HomeVisual>
      <HomeNav></HomeNav>
      <HomeInfo></HomeInfo>
    </Homelayout>
  );
}

export default HomeLayout;
