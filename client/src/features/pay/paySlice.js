import { createSlice } from '@reduxjs/toolkit';

const paySlice = createSlice({
  name: 'pay',
  initialState: {
    orderid: 'id',
    tid: 'testtid',
  },
  reducers: {
    setPay(state, action) {
      state.orderid = action.payload.orderid;
      state.tid = action.payload.tid;
    },
  },
});

export const payActions = paySlice.actions;
export default paySlice.reducer;
