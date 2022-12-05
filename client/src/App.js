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
  const store = configureStore({
    reducer: {
      user: userSlice,
      pay: paySlice,
    },
  });

  useEffect(() => {
    const user = localStorage.getItem('user');
    if (user) {
      store.dispatch(setUser(JSON.parse(user)));

      const accessToken = getCookie('accessToken');
      if (accessToken) {
        store.dispatch(updateSession());
      }
    }
  }, []);

  return (
    <>
      <BrowserRouter>
        <Provider store={store}>
          <StyledApp>
            <Header />

            <StyledContent>
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
