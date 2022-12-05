import styled from 'styled-components';
import { Fade } from 'react-awesome-reveal';

// 첫번째 info
const Homeinfobox = styled.div`
  width: 100%;
  height: 740px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 30px;
  position: relative;
  transition: 0.3s;
  .farmer {
    position: absolute;
    width: 200px;
    height: 200px;
  }
  .image1 {
    background: url('https://ifh.cc/g/ZsOAoQ.png');
    bottom: 100px;
    left: 20%;
    border-radius: 20px;
    transition: 0.3s;
    background-size: cover;
  }
  .image2 {
    background: url('https://ifh.cc/g/MGCrNd.png');
    clip-path: polygon(30% 0%, 70% 0%, 100% 30%, 100% 70%, 70% 100%, 30% 100%, 0% 70%, 0% 30%);
    bottom: 530px;
    left: 40%;
    background-size: cover;
    transition: 0.3s;
  }
  .image3 {
    background: url('https://ifh.cc/g/tsOf9W.png');
    bottom: 500px;
    left: 30px;
    border-radius: 50%;
    transition: 0.3s;
    background-size: cover;
  }
  @media (max-width: 1500px) {
    .image3 {
      scale: 0.0001;
    }
  }
  @media (max-width: 1199px) {
    .image2,
    .image1 {
      width: 130px;
      height: 130px;
    }
  }
  @media (max-width: 768px) {
    .image2 {
      scale: 0.0001;
    }
    .image1 {
      width: 80px;
      height: 80px;
    }
  }
  @media (max-width: 480px) {
    .image1 {
      scale: 0.0001;
    }
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
  overflow: hidden;
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
    @media (max-width: 1199px) {
      h2 {
        font-size: 34px;
      }
      p {
        font-size: 24px;
      }
    }
    @media (max-width: 991px) {
      margin-left: 50px;
      h2 {
        font-size: 28px;
      }
      p {
        font-size: 18px;
      }
    }
    @media (max-width: 768px) {
      margin-left: 20px;
      h2 {
        font-size: 23px;
      }
      p {
        font-size: 14px;
      }
    }
  }
  .image {
    margin-right: 150px;
    width: 400px;
    height: 500px;
    border-radius: 180px;
    background-image: url('https://ifh.cc/g/aCHPlw.jpg');
    background-size: cover;
    transition: 0.3s;
    @media (max-width: 1199px) {
      width: 300px;
      height: 450px;
    }
    @media (max-width: 768px) {
      width: 200px;
      height: 300px;
      margin-right: 0px;
    }
    @media (max-width: 480px) {
      display: none;
    }
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
  transition: 0.3s;
  .first {
    color: var(--brown);
    font-size: 40px;
  }
  p {
    font-size: 30px;
  }
  @media (max-width: 1199px) {
    .first {
      font-size: 34px;
    }
    p {
      font-size: 24px;
    }
  }
  @media (max-width: 991px) {
    margin-right: 50px;
    .first {
      font-size: 28px;
    }
    p {
      font-size: 18px;
    }
  }
  @media (max-width: 768px) {
    margin-right: 20px;
    .first {
      font-size: 23px;
    }
    p {
      font-size: 14px;
    }
  }
`;

const Homeinfoimagebox = styled.div`
  width: 400px;
  height: 600px;
  background: url('https://ifh.cc/g/hnQZV7.jpg');
  background-size: cover;
  border-top-left-radius: 200px;
  border-top-right-radius: 200px;
  transition: 0.3s;
  @media (max-width: 1199px) {
    width: 300px;
    height: 450px;
  }
  @media (max-width: 768px) {
    width: 200px;
    height: 300px;
  }
  @media (max-width: 480px) {
    display: none;
  }
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
