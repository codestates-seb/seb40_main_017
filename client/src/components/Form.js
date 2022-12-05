import styled from 'styled-components';
import { InputRadio } from './Input';

export const Form = ({ method, onSubmit, children }) => {
  return (
    <form method={method} onSubmit={onSubmit}>
      {children}
    </form>
  );
};

const StyledFormInput = styled.div`
  margin-top: 16px;

  &:first-child {
    margin-top: 0;
  }

  input {
    margin-top: 10px;
  }
`;

export const FormInput = ({ text, children }) => {
  return (
    <StyledFormInput>
      <label>
        {text}
        {children}
      </label>
    </StyledFormInput>
  );
};

const StyledFormGroup = styled.div`
  display: flex;
  justify-content: center;

  margin-top: 16px;

  &:first-child {
    margin-top: 0;
  }

  > * {
    flex: 1;

    text-align: center;
  }
`;

export const FormRadioGroup = ({ name, items, onChange }) => {
  return (
    <StyledFormGroup>
      {items.map((item) => (
        <label key={item.value}>
          <InputRadio name={name} defaultValue={item.value} defaultChecked={item.checked} onChange={onChange} />
          {item.text}
        </label>
      ))}
    </StyledFormGroup>
  );
};
