import { useCallback, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logout } from '../../api/login';

const LogoutPage = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const submitCallback = useCallback(() => {
    navigate('/');
  }, [navigate]);

  useEffect(() => {
    dispatch(logout(submitCallback));
  }, [dispatch, submitCallback]);
};

export default LogoutPage;
