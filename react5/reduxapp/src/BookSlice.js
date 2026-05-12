import { createSlice } from "@reduxjs/toolkit";

const savedBooks = localStorage.getItem("books");

const initialState = savedBooks
  ? JSON.parse(savedBooks)
  : {
      Java: 100,
      DSA: 80,
    };

const bookSlice = createSlice({
  name: "books",

  initialState,

  reducers: {
    addBook: (state, action) => {
      const { bookName, quantity } = action.payload;
      state[bookName] = quantity;

      localStorage.setItem("books", JSON.stringify(state));
    },

    removeBook: (state, action) => {
      delete state[action.payload];

      localStorage.setItem("books", JSON.stringify(state));
    },

    buyBook: (state, action) => {
      const bookName = action.payload;

      if (state[bookName] > 0) {
        state[bookName] -= 1;
      }

      localStorage.setItem("books", JSON.stringify(state));
    },

    returnBook: (state, action) => {
      const bookName = action.payload;
      state[bookName] += 1;

      localStorage.setItem("books", JSON.stringify(state));
    },
  },
});

export const { addBook, removeBook, buyBook, returnBook } = bookSlice.actions;

export default bookSlice.reducer;