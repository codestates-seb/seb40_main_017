import { createSlice } from '@reduxjs/toolkit';

const paySlice = createSlice({
  name: 'pay',
  initialState: {
    ordId: 0,
    tid: 'testtid',
  },
  reducers: {
    setPay(state, action) {
      state.ordId = action.payload.ordId;
      state.tid = action.payload.tid;
    },
  },
});

export const payActions = paySlice.actions;
export default paySlice.reducer;
