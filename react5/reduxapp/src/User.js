import { useDispatch, useSelector } from "react-redux";
import { bookMovie, cancelMovie } from "./MovieSlice";
import { buyBook, returnBook } from "./BookSlice";

const User = () => {
  const movies = useSelector((state) => state.movies);
  const books = useSelector((state) => state.books);

  const dispatch = useDispatch();

  return (
    <>
      <h1>User Panel</h1>

      <h2>Movies</h2>

      {Object.keys(movies).map((movie) => (
        <div key={movie}>
          <h3>
            {movie} - Tickets: {movies[movie]}
          </h3>

          <button onClick={() => dispatch(bookMovie(movie))}>
            Book Ticket
          </button>

          <button onClick={() => dispatch(cancelMovie(movie))}>
            Cancel Ticket
          </button>
        </div>
      ))}

      

      <h2>Books</h2>

      {Object.keys(books).map((book) => (
        <div key={book}>
          <h3>
            {book} - Quantity: {books[book]}
          </h3>

          <button onClick={() => dispatch(buyBook(book))}>
            Buy Book
          </button>

          <button onClick={() => dispatch(returnBook(book))}>
            Return Book
          </button>
        </div>
      ))}
    </>
  );
};

export default User;