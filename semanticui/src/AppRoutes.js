import { Routes, Route, Navigate } from "react-router-dom";

import Home from "./Home";
import About from "./About";
import Contact from "./Contact";

function AppRoutes() {
  return (
    <Routes>

      <Route path="/" element={<Navigate to="/home" />} />

      <Route path="/home" element={<Home />} />

      <Route path="/about" element={<About />} />

      <Route path="/contact" element={<Contact />} />

    </Routes>
  );
}

export default AppRoutes;