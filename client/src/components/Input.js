import { useCallback, useState } from 'react';
import styled from 'styled-components';

const StyledInput = styled.input`
  display: block;

  font-size: 18px;

  width: 100%;
  min-height: 40px;

  padding: 8px;

  border: 1px solid #8d8d8d;
  border-radius: 4px;

  background-color: #f0e9df;
`;

const StyledInputCheckable = styled.input`
  margin-right: 4px;
`;

const StyledViewInput = styled.p`
  font-size: 18px;

  margin-top: 8px;
  padding: 8px;
`;

// type 속성이 checkbox 또는 radio 인 경우 StyledInputCheckable 스타일 적용, 아닌 경우 일반적인 StyledInput 스타일 적용
export const Input = ({ type, defaultValue, defaultChecked, onChange, ...props }) => {
  const EnhancedStyledInput = type === 'checkbox' || type === 'radio' ? StyledInputCheckable : StyledInput;
  return <EnhancedStyledInput type={type} defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
};

// 수정 모드 ON/OFF 가능한 input. type 속성이 checkbox 또는 radio 인 경우 StyledInputCheckable 스타일 적용, 아닌 경우 일반적인 StyledInput 스타일 적용
export const EditableInput = ({ edit, type, defaultValue, defaultChecked, onChange, ...props }) => {
  if (edit) {
    const EnhancedStyledInput = type === 'checkbox' || type === 'radio' ? StyledInputCheckable : StyledInput;
    return <EnhancedStyledInput type={type} defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
  }
  return <StyledViewInput>{defaultValue}</StyledViewInput>;
};

//  일반 텍스트 용 Input 요소를 리턴하는 컴포넌트 작성
export const InputText = ({ defaultValue, onChange, ...props }) => {
  return <Input type="text" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

//  수정 모드 ON/OFF 가능한 input text. 일반 텍스트 용 Input 요소를 리턴하는 컴포넌트 작성
export const EditableInputText = ({ edit, defaultValue, onChange, ...props }) => {
  return <EditableInput edit={edit} type="text" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

//  비밀번호 텍스트 용 Input 요소를 리턴하는 컴포넌트 작성
export const InputPassword = ({ defaultValue, onChange, ...props }) => {
  return <Input type="password" defaultValue={defaultValue} onChange={onChange} {...props} />;
};

//  라디오 체크박스 요소를 리턴하는 컴포넌트 작성
export const InputRadio = ({ defaultValue, defaultChecked, onChange, ...props }) => {
  //  선택하는 요소마다 개별 value 가 있어야 값을 가공할 수 있기 때문에 defaultChecked 이외에도 defaultValue 를 함께 받음
  return <Input type="radio" defaultValue={defaultValue} defaultChecked={defaultChecked} onChange={onChange} {...props} />;
};

//  Custom Hook 을 활용하여 State 값과 handle 콜백 함수를 리턴하도록 구현
export const useInput = (defaultValue) => {
  const [value, setValue] = useState(defaultValue);

  //  새로 작성하지 않음
  const handleChange = useCallback((event) => {
    setValue(event.target.value);
  }, []);

  return [value, handleChange, setValue];
};

//  Custom Hook 을 활용하여 State 값과 handle 콜백 함수를 리턴하도록 구현
export const useToggle = (defaultBoolean) => {
  const [value, setValue] = useState(defaultBoolean);

  //  새로 작성하지 않음
  const handleChange = useCallback(() => {
    setValue(!value);
  }, [value]);

  return [value, handleChange, setValue];
};
