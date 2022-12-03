import { useCallback, useState } from 'react';
import styled from 'styled-components';

const StyledInput = styled.input`
  display: block;

  font-family: 'Malgun Gothic', Arial, sans-serif;
  font-size: 18px;

  width: 100%;
  min-height: 40px;

  padding: 10px;

  border: 1px solid #8d8d8d;
  border-radius: 4px;

  background-color: #f0e9df;
`;

const StyledInputCheckable = styled.input`
  margin-right: 4px;
`;

const StyledViewInput = styled.p`
  font-family: 'Malgun Gothic', Arial, sans-serif;
  font-size: 18px;

  margin-top: 10px;
  padding: 10px;

  border: 1px solid #8d8d8d;
  border-radius: 4px;

  background-color: #d5ccbe;
`;

export const Input = ({ type, defaultValue, defaultChecked, onChange, ...props }) => {
  const EnhancedStyledInput = type === 'checkbox' || type === 'radio' ? StyledInputCheckable : StyledInput;
  return <EnhancedStyledInput type={type} defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
};

export const EditableInput = ({ edit, type, defaultValue, defaultChecked, onChange, ...props }) => {
  if (edit) {
    const EnhancedStyledInput = type === 'checkbox' || type === 'radio' ? StyledInputCheckable : StyledInput;
    return <EnhancedStyledInput type={type} defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
  }

  return <StyledViewInput>{defaultValue === '' ? '\u00A0' : defaultValue}</StyledViewInput>;
};

export const InputText = ({ defaultValue, onChange, ...props }) => {
  return <Input type="text" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

export const EditableInputText = ({ edit, defaultValue, onChange, ...props }) => {
  return <EditableInput edit={edit} type="text" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

export const InputPassword = ({ defaultValue, onChange, ...props }) => {
  return <Input type="password" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

export const InputRadio = ({ defaultValue, defaultChecked, onChange, ...props }) => {
  return <Input type="radio" defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
};

const StyledPreWrap = styled.p`
  min-height: 200px;

  white-space: pre-wrap;
`;
const StyledTextArea = styled.textarea`
  font: inherit;

  width: 100%;
  min-height: 200px;

  padding: 15px;

  background-color: #f0e9df;

  resize: vertical;
`;

export const EditableTextArea = ({ edit, defaultValue, onChange, ...props }) => {
  if (edit) {
    return <StyledTextArea defaultValue={defaultValue} onChange={onChange} {...props} />;
  }
  return <StyledPreWrap>{defaultValue}</StyledPreWrap>;
};

export const useInput = (defaultValue) => {
  const [value, setValue] = useState(defaultValue);

  const handleChange = useCallback((event) => {
    setValue(event.target.value);
  }, []);

  return [value, handleChange, setValue];
};

export const useFile = (callback) => {
  const [file, setFile] = useState(null);

  const handleChange = useCallback((event) => {
    const files = event.target.files;
    const fileData = files[0];

    if (fileData) {
      setFile(fileData);
      callback(fileData);
    } else {
      setFile(null);
      callback(null);
    }

    event.target.value = '';
  }, []);

  return [file, handleChange, setFile];
};

export const useToggle = (defaultBoolean) => {
  const [value, setValue] = useState(defaultBoolean);

  const handleChange = useCallback(() => {
    setValue(!value);
  }, [value]);

  return [value, handleChange, setValue];
};
