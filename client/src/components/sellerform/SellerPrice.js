import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { Fade } from 'react-awesome-reveal';

const PriceBox = styled.div`
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
  input,
  select {
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

export const SellerPrice = ({ nextButton, formData, setFormData }) => {
  const {
    register,
    formState: { errors },
    watch,
    handleSubmit,
  } = useForm();
  const onSubmit = (data) => {
    if (watch('price') && watch('category')) {
      setFormData({ ...formData, ...data });
      nextButton();
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <PriceBox>
          <Fade cascade damping={0.4}>
            <h2>상품가격과 카테고리를 입력해주세요</h2>
            <span>상품 가격 ￦</span>
            <InputBox>
              <input placeholder="상품가격" name="price" type="number" {...register('price', { required: true })} />
              <ErrorBox>{errors.price && <p>상품가격을 적어 주세요</p>}</ErrorBox>
            </InputBox>
            <span> 카테 고리 </span>
            <InputBox>
              <select name="category" {...register('category')}>
                <option selected value={1}>
                  과일
                </option>
                <option value={2}>채소</option>
                <option value={3}>곡물</option>
                <option value={4}>견과류</option>
              </select>
            </InputBox>
          </Fade>
          <ButtonBox>
            <input type="submit" value="Next" />
          </ButtonBox>
        </PriceBox>
      </form>
    </>
  );
};
