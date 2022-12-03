import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
  name: 'user',
  initialState: {
    memberId: 0,
    name: '',
    role: '',
    sellerId: 0,
    clientId: 0,
  },
  reducers: {
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

      localStorage.setItem('user', JSON.stringify(state));
    },

    clearUser(state) {
      localStorage.removeItem('user');

      return { ...state, ...userSlice.getInitialState() };
    },
  },
});

export default userSlice.reducer;

export const { setUser, clearUser } = userSlice.actions;

export const getUser = (state) => state.user;
