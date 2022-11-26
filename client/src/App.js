import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import HomePage from './pages/HomePage';
import LoginPage from './pages/users/LoginPage';
import SignupPage from './pages/users/SignupPage';
import MyPage from './pages/users/MyPage';
import CropsListPage from './pages/CropsListPage';
import FruitsPage from './pages/crops/FruitsPage';
import VegetablePage from './pages/crops/VegetablePage';
import RicePage from './pages/crops/RicePage';
import NutsPage from './pages/crops/NutsPage';
import SellerformPage from './pages/sellers/SellerformPage';
import BuyFormPage from './pages/payments/BuyFormPage';
import styled from 'styled-components';
import { configureStore } from '@reduxjs/toolkit';
import userSlice, { userService } from './features/user/userSlice';
import { Provider } from 'react-redux';
import { useEffect } from 'react';
import { getCookie } from './features/cookie';
import LogoutPage from './pages/users/LogoutPage';
import paySlice from './features/pay/paySlice';
import CompletePage from './pages/payments/CompletePage';
import FailPage from './pages/payments/FailPage';

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

  const { dispatch } = store;

  useEffect(() => {
    const accessToken = getCookie('token');
    if (accessToken) {
      dispatch(userService.updateSession());
    }
  }, [dispatch]);

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
                <Route path="/signup" element={<SignupPage />} />
                <Route path="/mypage" element={<MyPage />} />
                <Route path="/boards" element={<CropsListPage />} />
                <Route path="/boards/fruit" element={<FruitsPage />} />
                <Route path="/boards/vegetable" element={<VegetablePage />} />
                <Route path="/boards/grain" element={<RicePage />} />
                <Route path="/boards/nut" element={<NutsPage />} />
                <Route path="/sell" element={<SellerformPage />} />
                <Route path="/order" element={<BuyFormPage />} />
                <Route path="/order/complete" element={<CompletePage />} />
                <Route path="/order/fail" element={<FailPage />} />
              </Routes>
            </StyledContent>
          </StyledApp>
        </Provider>
      </BrowserRouter>
    </>
  );
};

export default App;
