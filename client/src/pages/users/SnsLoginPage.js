import { useCallback, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { snsLogin } from '../../api/login';

const SnsLoginPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const params = new URLSearchParams(useLocation().search);
  const accessToken = params.get('accessToken');

  const handleSuccessCallback = useCallback((status) => {
    if (status) {
      navigate('/');
    }
  }, []);

  useEffect(() => {
    dispatch(snsLogin({ accessToken }, handleSuccessCallback));
  }, []);

  return <>SNS 로그인 처리 중입니다.</>;
};

export default SnsLoginPage;
