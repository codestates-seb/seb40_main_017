import { MultiStepProgressBar } from '../../components/sellerform/MultiStepProgressBar';
import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { MultiStepForm } from '../../components/sellerform/MultiStepForm';
import { DotSpinner } from '@uiball/loaders';
import { useSelector } from 'react-redux';

const FormLayout = styled.div`
  background: var(--off-white);
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SellerPostHeader = styled.div`
  width: 39.5em;
  height: 50px;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  font-size: 30px;
  font-weight: 500;
  gap: 10px;
  border-bottom: 5px solid var(--green);
  .head {
    color: var(--green);
  }
`;

const FormBox = styled.div`
  position: relative;
  font-size: 30px;
  width: 40em;
  height: 25em;
  padding: 2em;
  background: var(--white);
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 0.5em;
  box-shadow: 0px 4px 5px 1px #8e8e8e;
`;

const BackImage = styled.div`
  position: absolute;
  bottom: 0px;
  left: 1em;
  width: 200px;
  height: 200px;
  background: url('https://ifh.cc/g/y7qhrw.png');
  z-index: 1;
`;

const ProgressBarBox = styled.div`
  width: 20em;
  height: 2em;
  margin-bottom: 2em;
`;

const FormBodyBox = styled.div`
  width: 30em;
  height: 15em;
  display: flex;
  justify-content: center;
  z-index: 10;
`;

function SellerformPage() {
  const userInfo = useSelector((state) => state.user.sellerId);
  const [index, setIndex] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState({
    sellerId: 1,
    title: '',
    stock: '',
    price: '',
    category: '',
    mainImage: '이미지테스트',
    content: '테스트',
  });
  console.log(userInfo);
  useEffect(() => {
    console.log(formData);
    // setFormData({ ...formData });
  }, [formData]);
  const prevButton = () => {
    if (index > 1) {
      setIndex((prevIndex) => prevIndex - 1);
    }
  };
  const nextButton = () => {
    if (index < 5) {
      setIndex((prevIndex) => prevIndex + 1);
    }
  };

  return (
    <FormLayout>
      <SellerPostHeader>
        <p className="head">판매 물품</p>
        <p>등록</p>
      </SellerPostHeader>
      <FormBox>
        <ProgressBarBox>
          <MultiStepProgressBar step={index} />
        </ProgressBarBox>
        <FormBodyBox>
          {!isLoading && (
            <MultiStepForm
              index={index}
              nextButton={nextButton}
              prevButton={prevButton}
              formData={formData}
              setFormData={setFormData}
              setIsLoading={setIsLoading}
            />
          )}
          {isLoading && <DotSpinner className="animation" size={100} speed={1.75} color="var(--green)" />}
        </FormBodyBox>
        <BackImage />
      </FormBox>
    </FormLayout>
  );
}

export default SellerformPage;
