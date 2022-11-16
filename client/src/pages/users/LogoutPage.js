import { useCallback, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { userService } from '../../features/user/userSlice';

const LogoutPage = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const submitCallback = useCallback(() => {
    navigate('/');
  }, [navigate]);

  useEffect(() => {
    dispatch(userService.logout(submitCallback));
  }, [dispatch, submitCallback]);
};

export default LogoutPage;
