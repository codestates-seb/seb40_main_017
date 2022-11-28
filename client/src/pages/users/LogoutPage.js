import { useCallback, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logout } from '../../api/login';

const LogoutPage = () => {
  //  Redux Provider 로 부터 dispatch (상태 관리를 위한 함수) 수신
  const dispatch = useDispatch();
  //  로그아웃 후 페이지 이동을 위해 navigate 정의
  const navigate = useNavigate();

  //  리다이렉션을 위한 콜백 함수
  const submitCallback = useCallback(() => {
    navigate('/');
  }, [navigate]);

  //  로그아웃 수행
  useEffect(() => {
    dispatch(logout(submitCallback));
  }, [dispatch, submitCallback]);
};

export default LogoutPage;
