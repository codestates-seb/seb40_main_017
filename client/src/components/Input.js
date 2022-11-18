import { useCallback, useState } from 'react';
import styled from 'styled-components';

const StyledInput = styled.input`
  display: block;

  font-size: 18px;

  width: 100%;

  padding: 8px;

  border: 1px solid #8d8d8d;
  border-radius: 4px;

  background-color: #f0e9df;
`;

const StyledInputCheckable = styled.input`
  margin-right: 4px;
`;

export const Input = ({ type, defaultValue, defaultChecked, onChange, ...props }) => {
  const EnhancedStyledInput = type === 'checkbox' || type === 'radio' ? StyledInputCheckable : StyledInput;
  return <EnhancedStyledInput type={type} defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
};

export const InputText = ({ defaultValue, onChange, ...props }) => {
  return <Input type="text" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

export const InputPassword = ({ defaultValue, onChange, ...props }) => {
  return <Input type="password" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

export const InputRadio = ({ defaultValue, defaultChecked, onChange, ...props }) => {
  return <Input type="radio" defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
};

export const useInput = (defaultValue) => {
  const [value, setValue] = useState(defaultValue);

  const handleChange = useCallback((event) => {
    setValue(event.target.value);
  }, []);

  return [value, handleChange];
};
