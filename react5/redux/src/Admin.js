import { useDispatch, useSelector } from "react-redux";
import {
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
} from "./Bookslice";
import { useState } from "react";

const Admin = () => {
  const book = useSelector((state) => state.books);
  const [nbk, setnbk] = useState(0);

  const dispatch = useDispatch();

  const handlebk = (e) => {
    setnbk(Number(e.target.value));
  };

  return (
    <>
      <h1>ADMIN</h1>

      <h2>JAVA: {book.java}</h2>
      <h2>DSA: {book.dsa}</h2>
      <h2>REACT: {book.react}</h2>

      <input
        type="number"
        placeholder="Enter no of books"
        onChange={handlebk}
      />

      <br /><br />

      <button onClick={() => dispatch(addjava())}>Add Java</button>
      <button onClick={() => dispatch(salejava())}>Remove Java</button>
      <button onClick={() => dispatch(addNjava(nbk))}>Add N Java</button>
      <button onClick={() => dispatch(removeNjava(nbk))}>Remove N Java</button>

      <br /><br />

      <button onClick={() => dispatch(adddsa())}>Add DSA</button>
      <button onClick={() => dispatch(saledsa())}>Remove DSA</button>
      <button onClick={() => dispatch(addNdsa(nbk))}>Add N DSA</button>
      <button onClick={() => dispatch(removeNdsa(nbk))}>Remove N DSA</button>

      <br /><br />

      <button onClick={() => dispatch(addreact())}>Add React</button>
      <button onClick={() => dispatch(salereact())}>Remove React</button>
      <button onClick={() => dispatch(addNreact(nbk))}>Add N React</button>
      <button onClick={() => dispatch(removeNreact(nbk))}>Remove N React</button>
    </>
  );
};

export default Admin;