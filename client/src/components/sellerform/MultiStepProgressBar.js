/* eslint-disable no-unused-vars */
import 'react-step-progress-bar/styles.css';
import styled from 'styled-components';
import { ProgressBar, Step } from 'react-step-progress-bar';

const ProgressBarLayout = styled.div`
  .step {
    color: black;
    width: 25px;
    height: 25px;
    font-size: 18px;
    background-color: white;
    border: 0.5px solid lightgray;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .step.completed {
    color: white;
    background-color: var(--green);
  }
`;

export const MultiStepProgressBar = (props) => {
  return (
    <ProgressBarLayout>
      <ProgressBar percent={((props.step - 1) * 100) / 4} filledBackground="linear-gradient(to right, #5d9061, #f5ff7b)">
        <Step transition="scale">{({ accomplished, index }) => <div className={`step ${accomplished ? 'completed' : ''}`}>1</div>}</Step>
        <Step transition="scale">{({ accomplished, index }) => <div className={`step ${accomplished ? 'completed' : ''}`}>2</div>}</Step>
        <Step transition="scale">{({ accomplished, index }) => <div className={`step ${accomplished ? 'completed' : ''}`}>3</div>}</Step>
        <Step transition="scale">{({ accomplished, index }) => <div className={`step ${accomplished ? 'completed' : ''}`}>4</div>}</Step>
        <Step transition="scale">{({ accomplished, index }) => <div className={`step ${accomplished ? 'completed' : ''}`}>👨‍🌾</div>}</Step>
      </ProgressBar>
    </ProgressBarLayout>
  );
};
