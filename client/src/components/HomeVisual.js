import styled from 'styled-components';
import { useState, useRef, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/swiper-bundle.min.css';
import 'swiper/swiper.min.css';

const Homevisuallayout = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
`;

const Homevisualbox = styled.div`
  background: var(--green);
  box-sizing: border-box;
  width: 100%;
  height: 1017px;
  padding: 30px 0 30px 30px;
  display: flex;
  justify-content: center;
  padding-left: 150px;
  overflow: hidden;

  .cursor {
    z-index: 80;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;

    top: 0; // 초기 위치값을 설정해줍니다.
    left: 0; // 초기 위치값을 설정해줍니다.

    width: 100px; //원 가로사이즈
    height: 100px; //원 세로사이즈
    border: dashed 2px white;
    border-radius: 50%; // 원의 형태설정
    background-color: transparent; //원 컬러설정
    color: var(--white);
    opacity: 0.7;
    transform: translate(-50%, -50%); // 원을 정가운데로 맞추기위해서 축을-50%이동해줍니다.
    filter: sepia(1px);
    transition-timing-function: ease;
    :active {
      opacity: 0.1;
      transition: 0.3s;
    }
  }

  .text {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: auto;
  }
  h1 {
    font-size: 82px;
    font-weight: bold;
  }
  .text-white {
    color: var(--white);
  }
  .text-darktan {
    color: var(--darker-gray);
  }
  .effect {
    animation: fadein 0.4s ease-in-out;
    @keyframes fadein {
      0% {
        opacity: 0;
        transform: translateY(40px);
      }
      100% {
        opacity: 1;
        transform: none;
      }
    }
  }
  .effect2 {
    animation: fadein 0.8s ease-in-out;
    @keyframes fadein {
      0% {
        opacity: 0;
        transform: translateY(40px);
      }
      100% {
        opacity: 1;
        transform: none;
      }
    }
  }
  .slide {
    width: 850px;
    display: flex;
    justify-content: center;
    margin: auto;
  }
  .first-slide {
    display: 'inline-block';
    width: 500px;
    height: 500px;
    border-radius: 150px;
    background-image: url('https://ifh.cc/g/h5FqTF.png');
    background-size: cover;
  }
  .second-slide {
    display: 'inline-block';
    width: 500px;
    height: 500px;
    clip-path: polygon(20% 0%, 80% 0%, 100% 20%, 100% 80%, 80% 100%, 20% 100%, 0% 80%, 0% 20%);
    background-image: url('https://ifh.cc/g/3RYsFo.png');
    background-size: cover;
  }
  .third-slide {
    display: 'inline-block';
    width: 500px;
    height: 500px;
    clip-path: ellipse(45% 50% at 50% 50%);
    background-image: url('https://ifh.cc/g/10F68l.png');
    background-size: cover;
  }
  .fourth-slide {
    display: 'inline-block';
    width: 500px;
    height: 500px;
    clip-path: polygon(50% 0%, 90% 20%, 100% 60%, 75% 100%, 25% 100%, 0% 60%, 10% 20%);
    background-image: url('https://ifh.cc/g/lonaMp.jpg');
    background-size: cover;
  }
  @media (max-width: 1500px) {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding-left: 0;
    .slide {
      width: 40em;
      margin: 0;
      margin-left: 140px;
    }
    .swiper-slide {
      margin-right: 0;
    }
  }
  @media (max-width: 1199px) {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding-left: 0;
    .text {
      h1 {
        font-size: 72px;
      }
    }
  }
  @media (max-width: 991px) {
    height: 80%;
    padding-left: 0;
    .swiper-slide {
      display: flex;
      align-items: center;
    }
    .first-slide {
      width: 400px;
      height: 400px;
    }
    .second-slide {
      width: 400px;
      height: 400px;
    }
    .third-slide {
      width: 400px;
      height: 400px;
    }
    .fourth-slide {
      width: 400px;
      height: 400px;
    }
    .text {
      h1 {
        font-size: 60px;
      }
    }
  }
  @media (max-width: 768px) {
    height: 30%;
    .slide {
      transition: 0.3s;
      scale: 0.001;
    }
    .text {
      h1 {
        font-size: 50px;
      }
    }
  }
  @media (max-width: 700px) {
    .slide {
      display: none;
    }
  }
`;

function HomeVisual() {
  const [swiper, setSwiper] = useState(null);
  const [xy, setXY] = useState({ x: 0, y: 0 });

  useEffect(() => {
    const updateMousePosition = (e) => {
      setXY({ x: e.pageX, y: e.pageY - 85 });
    };

    window.addEventListener('mousemove', updateMousePosition);

    return () => {
      window.removeEventListener('mousemove', updateMousePosition);
    };
  }, []);

  const visualRef = useRef(null);
  const headerRef = useRef(null);
  const darkheaderRef = useRef(null);
  const cursorRef = useRef(null);

  const colors = ['#5d9061', '#d26a51', '#aba35a', '#5561c7'];
  const whiteTexts = ['신선한 농산물', '유기농 채소', '맛있는 우리쌀', '건강한 먹거리'];
  const darkerTexts = ['바로 직거래', '친환경 채소', '건강한 면역력', '식탁의 즐거움'];

  const handleSwiperChange = (swiper) => {
    swiper.slideNext();
  };
  const handleChange = () => {
    swiper.slideNext();
    visualRef.current.style.backgroundColor = colors[swiper.realIndex];
    headerRef.current.innerText = whiteTexts[swiper.realIndex];
    darkheaderRef.current.innerText = darkerTexts[swiper.realIndex];
    headerRef.current.classList.remove('effect');
    darkheaderRef.current.classList.remove('effect2');
    void headerRef.current.offsetWidth;
    void darkheaderRef.current.offsetWidth;
    headerRef.current.classList.add('effect');
    darkheaderRef.current.classList.add('effect2');
  };

  return (
    <Homevisuallayout>
      <Homevisualbox ref={visualRef} onClick={handleChange}>
        <div ref={cursorRef} className="cursor" style={{ position: 'absolute', left: xy.x, top: xy.y }}>
          Click
        </div>
        <div className="text effect">
          <h1 className="text-white effect" ref={headerRef}>
            {' '}
            신선한 농산물{' '}
          </h1>
          <h1 className="text-darktan effect2" ref={darkheaderRef}>
            {' '}
            바로 직거래{' '}
          </h1>
        </div>
        <div className="slide">
          <Swiper
            spaceBetween={80}
            slidesPerView={1}
            centeredSlides={true}
            loop={true}
            onClick={handleSwiperChange}
            onSwiper={setSwiper}
            speed={350}
            ref={setSwiper}
          >
            <SwiperSlide>
              <div className="first-slide"></div>
            </SwiperSlide>
            <SwiperSlide>
              <div className="second-slide"></div>
            </SwiperSlide>
            <SwiperSlide>
              <div className="third-slide"></div>
            </SwiperSlide>
            <SwiperSlide>
              <div className="fourth-slide"></div>
            </SwiperSlide>
          </Swiper>
        </div>
      </Homevisualbox>
    </Homevisuallayout>
  );
}

export default HomeVisual;
