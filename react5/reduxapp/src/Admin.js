import { useState } from "react";
import { useDispatch } from "react-redux";
import { addMovie, removeMovie } from "./MovieSlice";
import { addBook, removeBook } from "./BookSlice";

const Admin = () => {
  const dispatch = useDispatch();

  const [movieName, setMovieName] = useState("");
  const [tickets, setTickets] = useState("");

  const [bookName, setBookName] = useState("");
  const [quantity, setQuantity] = useState("");

  const handleAddMovie = () => {
    dispatch(
      addMovie({
        movieName: movieName,
        tickets: Number(tickets),
      })
    );

    setMovieName("");
    setTickets("");
  };

  const handleAddBook = () => {
    dispatch(
      addBook({
        bookName: bookName,
        quantity: Number(quantity),
      })
    );

    setBookName("");
    setQuantity("");
  };

  return (
    <>
      <h1>Admin Panel</h1>

      <h2>Movie Management</h2>

      <input
        type="text"
        placeholder="Enter movie name"
        value={movieName}
        onChange={(e) => setMovieName(e.target.value)}
      />

      <input
        type="number"
        placeholder="Enter tickets"
        value={tickets}
        onChange={(e) => setTickets(e.target.value)}
      />

      <button onClick={handleAddMovie}>Add Movie</button>

      <br />
      <br />

      <input
        type="text"
        placeholder="Movie name to remove"
        onChange={(e) => setMovieName(e.target.value)}
      />

      <button onClick={() => dispatch(removeMovie(movieName))}>
        Remove Movie
      </button>

     

      <h2>Book Management</h2>

      <input
        type="text"
        placeholder="Enter book name"
        value={bookName}
        onChange={(e) => setBookName(e.target.value)}
      />

      <input
        type="number"
        placeholder="Enter quantity"
        value={quantity}
        onChange={(e) => setQuantity(e.target.value)}
      />

      <button onClick={handleAddBook}>Add Book</button>

      <br />
      <br />

      <input
        type="text"
        placeholder="Book name to remove"
        onChange={(e) => setBookName(e.target.value)}
      />

      <button onClick={() => dispatch(removeBook(bookName))}>
        Remove Book
      </button>
    </>
  );
};

export default Admin;