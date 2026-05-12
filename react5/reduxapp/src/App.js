import {
  BrowserRouter,
  Routes,
  Route,
  Link,
} from "react-router-dom";

import Admin from "./Admin";
import User from "./User";

const App = () => {
  return (
    <BrowserRouter>

      <nav>
        <Link to="/admin">Admin</Link> |{" "}
        <Link to="/user">User</Link>
      </nav>

      <Routes>
        <Route path="/admin" element={<Admin />} />
        <Route path="/user" element={<User />} />
      </Routes>

    </BrowserRouter>
  );
};

export default App;