import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { Fade } from 'react-awesome-reveal';

const TitleBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
  flex-direction: column;
  justify-content: center;
  padding: 15px;
  font-size: 14px;
  span {
    font-size: 18px;
  }
  h2 {
    text-align: center;
    position: absolute;
    top: 5px;
    left: 25%;
  }
`;

const InputBox = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 1em;
  margin-bottom: 3em;
  input {
    border-radius: 5px;
    border: 1px solid var(--green);
    width: 40em;
    height: 2.5em;
  }
`;

const ErrorBox = styled.div`
  font-size: 14px;
  position: absolute;
  color: red;
  display: flex;
  justify-content: center;
  top: 3em;
`;

const ButtonBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  position: absolute;
  bottom: -5em;
  button,
  input {
    color: var(--white);
    width: 10em;
    height: 3em;
    font-size: 16px;
    background: var(--green);
    border-radius: 15px;
    border: 1px solid var(--white);
    transition: 0.3s;
    :hover {
      background-color: #9bdd9f;
      scale: 1.2;
    }
  }
`;

export const SellerTitle = ({ nextButton, formData, setFormData }) => {
  const {
    register,
    formState: { errors },
    watch,
    handleSubmit,
  } = useForm();
  const onSubmit = (data) => {
    if (watch('title') && watch('stock')) {
      setFormData({ ...formData, ...data });
      nextButton();
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <TitleBox>
          <Fade cascade damping={0.4}>
            <h2>상품명과 판매수량을 입력해주세요</h2>
            <span>상품명</span>

            <InputBox>
              <input placeholder="상품명" {...register('title', { required: true, maxLength: 20 })} />
              <ErrorBox>
                {errors.title?.type === 'required' && <p>상품명을 적어 주세요</p>}
                {errors.title?.type === 'maxLength' && <p> 20자 이하로 적어 주세요</p>}
              </ErrorBox>
            </InputBox>
            <span>판매 수량</span>
            <InputBox>
              <input placeholder="판매수량" type="number" {...register('stock', { required: true, min: 1, max: 50 })} />
              <ErrorBox>
                {errors.stock?.type === 'required' && <p>판매 수량을 적어주세요</p>}
                {errors.stock?.type === 'min' && <p>1개이상 50개 이하의 수량을 입력 해주세요</p>}
                {errors.stock?.type === 'max' && <p> 50개 이하의 수량을 입력 해주세요</p>}
              </ErrorBox>
            </InputBox>
          </Fade>
          <ButtonBox>
            <input type="submit" value="Next" />
          </ButtonBox>
        </TitleBox>
      </form>
    </>
  );
};
