import styled from 'styled-components';
import { useState, useRef } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/swiper-bundle.min.css';
import 'swiper/swiper.min.css';

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
`;

function HomeVisual() {
  const [swiper, setSwiper] = useState(null);

  const visualRef = useRef(null);
  const headerRef = useRef(null);
  const darkheaderRef = useRef(null);

  const colors = ['#5d9061', '#d26a51', '#aba35a', '#5561c7'];
  const whiteTexts = ['신선한 농산물', '유기농 채소', '맛있는 우리쌀', '건강한 먹거리'];
  const darkerTexts = ['바로 직거래', '친환경 채소', '건강한 면역력', '식탁의 즐거움'];

  const handleSwiperChange = (swiper) => {
    swiper.slideNext();
  };
  const handleChange = () => {
    swiper.slideNext();
    console.log(swiper);
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
    <Homevisualbox ref={visualRef} onClick={handleChange}>
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
          centeredSlides={false}
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
  );
}

export default HomeVisual;
