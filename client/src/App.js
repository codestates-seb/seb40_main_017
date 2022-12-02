import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Header from './components/Header';

import HomePage from './pages/HomePage';
import LoginPage from './pages/users/LoginPage';
import SignupPage from './pages/users/SignupPage';
import MyPage from './pages/users/MyPage';
import CropsListPage from './pages/CropsListPage';
import FruitsPage from './pages/crops/FruitsPage';
import VegetablePage from './pages/crops/VegetablePage';
import GrainPage from './pages/crops/GrainPage';
import NutsPage from './pages/crops/NutsPage';
import SellerformPage from './pages/sellers/SellerformPage';
import BuyFormPage from './pages/payments/BuyFormPage';
import styled from 'styled-components';
import { configureStore } from '@reduxjs/toolkit';
import userSlice, { setUser } from './features/user/userSlice';
import { Provider } from 'react-redux';
import LogoutPage from './pages/users/LogoutPage';
import paySlice from './features/pay/paySlice';
import CompletePage from './pages/payments/CompletePage';
import FailPage from './pages/payments/FailPage';
import SellerPatchPage from './pages/sellers/SellerPatchPage';
import SellerInfoPage from './pages/sellers/SellerInfoPage';
import CropInfoPage from './pages/crops/CropInfoPage';
import Footer from './components/Footer';
import { useEffect } from 'react';
import { updateSession } from './api/login';
import { getCookie } from './features/cookie';
import SnsLoginPage from './pages/users/SnsLoginPage';

const StyledApp = styled.main`
  display: flex;
  flex-direction: column;

  min-height: 100vh;

  background-color: #f0e9df;
`;

const StyledContent = styled.div`
  display: flex;
  flex-direction: column;

  flex: 1;
`;

const App = () => {
  //  Redux 상태 관리에 user 등록
  const store = configureStore({
    reducer: {
      user: userSlice,
      pay: paySlice,
    },
  });
  //  Provider 로부터 상태를 받는 위치가 아니기 때문에 store 변수에서 직접 dispatch 받아옴\

  //  새로고침 시 localStorage에서 user 데이터를 불러옴
  useEffect(() => {
    const user = localStorage.getItem('user');
    if (user) {
      //  데이터가 유효한 경우 JSON Object로 변환하여 Redux에 저장
      store.dispatch(setUser(JSON.parse(user)));

      // 불러온 사용자 데이터가 만료되었는지 체크하기 위해 추가로 세션 확인 코드 적용
      const accessToken = getCookie('accessToken');
      if (accessToken) {
        // 쿠키에 token 이 존재하면 사용자 세션을 다시 불러오도록 요청
        store.dispatch(updateSession());
      }
    }
  }, []);

  return (
    <>
      <BrowserRouter>
        {/*  사용자 정보 등을 저장하는 Redux 상태를 하위 컴포넌트에 위임 */}
        <Provider store={store}>
          <StyledApp>
            <Header />
            {/*  react-router-dom 활용하여 페이지 URL 별로 표시할 컴포넌트 연결 */}
            <StyledContent>
              {/*  Header / Footer 제외한 본문 컨텐츠만 URL 에 따라 변경되도록 Routes 적용 */}
              <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/logout" element={<LogoutPage />} />
                <Route path="/social" element={<SnsLoginPage />} />
                <Route path="/signup" element={<SignupPage />} />
                <Route path="/mypage" element={<MyPage />} />
                <Route path="/seller/:sellerId" element={<SellerInfoPage />} />
                <Route path="/boards" element={<CropsListPage />} />
                <Route path="/boards/fruit" element={<FruitsPage />} />
                <Route path="/boards/vegetable" element={<VegetablePage />} />
                <Route path="/boards/grain" element={<GrainPage />} />
                <Route path="/boards/nut" element={<NutsPage />} />
                <Route path="/boards/:boardId" element={<CropInfoPage />} />
                <Route path="/sell" element={<SellerformPage />} />
                <Route path="/sell/patch/:boardId" element={<SellerPatchPage />} />
                <Route path="/order/:boardId/:quantity" element={<BuyFormPage />} />
                <Route path="/order/pay/completed" element={<CompletePage />} />
                <Route path="/order/pay/fail" element={<FailPage />} />
                <Route path="/order/pay/cancel" element={<FailPage />} />
              </Routes>
            </StyledContent>
            <Footer />
          </StyledApp>
        </Provider>
      </BrowserRouter>
    </>
  );
};

export default App;
