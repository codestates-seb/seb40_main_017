import { Routes, Route, BrowserRouter } from 'react-router-dom';

import HomePage from './pages/HomePage';
import LoginPage from './pages/users/LoginPage';
import SignupPage from './pages/users/SignupPage';
import MyPage from './pages/users/MyPage';
import CropsListPage from './pages/CropsListPage';
import FruitsPage from './pages/crops/FruitsPage';
import VegetablePage from './pages/crops/VegetablePage';
import RicePage from './pages/crops/RicePage';
import NutsPage from './pages/crops/NutsPage';

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/boards" element={<CropsListPage />} />
          <Route path="/boards/fruit" element={<FruitsPage />} />
          <Route path="/boards/vegetable" element={<VegetablePage />} />
          <Route path="/boards/grain" element={<RicePage />} />
          <Route path="/boards/nut" element={<NutsPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
