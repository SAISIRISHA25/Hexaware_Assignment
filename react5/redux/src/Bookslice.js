import { createSlice } from "@reduxjs/toolkit";

const bookSlice = createSlice({
  name: "books",

  initialState: {
    java: 100,
    dsa: 50,
    react: 75,
  },

  reducers: {
    addjava: (state) => {
      state.java += 1;
    },
    salejava: (state) => {
      state.java -= 1;
    },
    addNjava: (state, action) => {
      state.java += action.payload;
    },
    removeNjava: (state, action) => {
      state.java -= action.payload;
    },

    adddsa: (state) => {
      state.dsa += 1;
    },
    saledsa: (state) => {
      state.dsa -= 1;
    },
    addNdsa: (state, action) => {
      state.dsa += action.payload;
    },
    removeNdsa: (state, action) => {
      state.dsa -= action.payload;
    },

    addreact: (state) => {
      state.react += 1;
    },
    salereact: (state) => {
      state.react -= 1;
    },
    addNreact: (state, action) => {
      state.react += action.payload;
    },
    removeNreact: (state, action) => {
      state.react -= action.payload;
    },
  },
});

export const {
  addjava,
  salejava,
  addNjava,
  removeNjava,
  adddsa,
  saledsa,
  addNdsa,
  removeNdsa,
  addreact,
  salereact,
  addNreact,
  removeNreact,
} = bookSlice.actions;

export default bookSlice.reducer;