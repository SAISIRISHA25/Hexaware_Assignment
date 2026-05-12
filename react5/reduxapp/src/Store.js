import { configureStore } from "@reduxjs/toolkit";
import movieReducer from "./MovieSlice";
import bookReducer from "./BookSlice";

export const store = configureStore({
  reducer: {
    movies: movieReducer,
    books: bookReducer,
  },
});