import { createSlice } from "@reduxjs/toolkit";

const savedMovies = localStorage.getItem("movies");

const initialState = savedMovies
  ? JSON.parse(savedMovies)
  : {
      Pushpa: 50,
      Jawan: 40,
    };

const movieSlice = createSlice({
  name: "movies",

  initialState,

  reducers: {
    addMovie: (state, action) => {
      const { movieName, tickets } = action.payload;
      state[movieName] = tickets;
      localStorage.setItem("movies", JSON.stringify(state));
    },

    removeMovie: (state, action) => {
      delete state[action.payload];
      localStorage.setItem("movies", JSON.stringify(state));
    },

    bookMovie: (state, action) => {
      const movieName = action.payload;

      if (state[movieName] > 0) {
        state[movieName] -= 1;
      }

      localStorage.setItem("movies", JSON.stringify(state));
    },

    cancelMovie: (state, action) => {
      const movieName = action.payload;
      state[movieName] += 1;

      localStorage.setItem("movies", JSON.stringify(state));
    },
  },
});

export const { addMovie, removeMovie, bookMovie, cancelMovie } =
  movieSlice.actions;

export default movieSlice.reducer;