import styled from 'styled-components';
import { Fade } from 'react-awesome-reveal';

// 첫번째 info
const Homeinfobox = styled.div`
  width: 100%;
  height: 740px;
  display: flex;
  justify-content: center;
  padding: 30px;
  position: relative;
  .farmer {
    position: absolute;
    width: 200px;
    height: 200px;
  }
  .image1 {
    background: url('https://ifh.cc/g/ZsOAoQ.png');
    bottom: 100px;
    left: 110px;
    border-radius: 20px;
  }
  .image2 {
    background: url('https://ifh.cc/g/MGCrNd.png');
    clip-path: polygon(30% 0%, 70% 0%, 100% 30%, 100% 70%, 70% 100%, 30% 100%, 0% 70%, 0% 30%);
    bottom: 200px;
    left: 500px;
  }
  .image3 {
    background: url('https://ifh.cc/g/tsOf9W.png');
    bottom: 500px;
    left: 30px;
    border-radius: 50%;
  }
`;

// 두번째 info
const Homeinfobox2 = styled.div`
  background: var(--light-brown);
  width: 100%;
  height: 740px;
  display: flex;
  justify-content: center;
  align-items: center;
  .paragraph {
    h2 {
      color: var(--off-white);
    }
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-left: 150px;
    font-size: 30px;
    font-weight: bold;
  }
  .image {
    margin-right: 150px;
    width: 400px;
    height: 500px;
    border-radius: 180px;
    background-image: url('https://ifh.cc/g/aCHPlw.jpg');
  }
`;

// 세번째 info
const Homeinfobox3 = styled.div`
  width: 100%;
  height: 740px;
  background-image: url('https://ifh.cc/g/BlaWDP.jpg');
  background-size: cover;
`;

const Homeinfoparagraphbox = styled.div`
  margin: 100px 200px 200px 0;
  display: flex;
  flex-direction: column;
  gap: 5px;
  font-weight: bold;
  .first {
    color: var(--brown);
    font-size: 40px;
  }
  p {
    font-size: 30px;
  }
`;

const Homeinfoimagebox = styled.div`
  width: 400px;
  height: 600px;
  background: url('https://ifh.cc/g/hnQZV7.jpg');
  border-top-left-radius: 200px;
  border-top-right-radius: 200px;
`;

function HomeInfo() {
  return (
    <>
      <Homeinfobox>
        <Homeinfoparagraphbox>
          <Fade cascade>
            <p className="first">17시 내고향은</p>
            <p>농가와 소비자의 직거래를 서비스하여</p>
            <p> 불필요한 유통과정을 줄이고 </p>
            <p> 지역 살림 운동을 하는 서비스 입니다.</p>
          </Fade>
        </Homeinfoparagraphbox>
        <Fade direction="right" delay={2000}>
          <Homeinfoimagebox></Homeinfoimagebox>
        </Fade>
        <Fade cascade delay={2000}>
          <div className="farmer image3"></div>
          <div className="farmer image2"></div>
          <div className="farmer image1"></div>
        </Fade>
      </Homeinfobox>
      <Homeinfobox2>
        <Fade direction="left" delay={800}>
          <div className="image"></div>
        </Fade>
        <div className="paragraph">
          <Fade cascade direction="right" delay={1200}>
            <h2> 서비스 목표</h2>
            <p>소상공인 농산물을 온라인 판매하여</p>
            <p> 많은 농민들의 새로운 판로가 되어주며</p>
            <p> 소비자에게는 낮은 가격의 높은 품질과</p>
            <p> 가치 있고 따뜻한 소비를</p>
            <p> 만들어 나가고 있습니다.</p>
          </Fade>
        </div>
      </Homeinfobox2>
      <Homeinfobox3></Homeinfobox3>
    </>
  );
}

export default HomeInfo;
