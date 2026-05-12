import { configureStore } from "@reduxjs/toolkit";
import bookReducer from "./Bookslice";

export const store = configureStore({
  reducer: {
    books: bookReducer,
  },
});