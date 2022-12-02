import { createSlice } from '@reduxjs/toolkit';

//  사용자 정보를 관리하는 Redux Slice 생성
const userSlice = createSlice({
  name: 'user', //  Redux Slice 네이밍
  initialState: {
    //  초기 데이터
    memberId: 0,
    name: '',
    role: '',
    sellerId: 0,
    clientId: 0,
  },
  reducers: {
    //  사용자 세션을 설정하는 Redux Action
    setUser(state, action) {
      state.memberId = action.payload.memberId;
      state.name = action.payload.name;
      state.role = action.payload.role;

      if (action.payload.sellerId) {
        state.sellerId = action.payload.sellerId;
      }

      if (action.payload.clientId) {
        state.clientId = action.payload.clientId;
      }

      //  Redux에 저장되는 사용자 세션 정보를 localStorage에 저장 (Object 형식이기 때문에 JSON String 변환 후 저장)
      localStorage.setItem('user', JSON.stringify(state));
    },
    //  사용자 세션을 초기화하는 Redux Action
    clearUser(state) {
      //  로그아웃 시 localStorage에서 정보 삭제
      localStorage.removeItem('user');
      //  기존에 있던 State 값을 모두 저장하며, 초기값을 덮어 씌우도록 설정
      return { ...state, ...userSlice.getInitialState() };
    },
  },
});

//  Redux Reducer 배포
export default userSlice.reducer;
//  Redux 상태를 변경하는 action 배포
export const { setUser, clearUser } = userSlice.actions;
//  Redux 상태를 리턴하는 action 배포
export const getUser = (state) => state.user;
