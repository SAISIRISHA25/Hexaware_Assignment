import { Menu } from "antd";
import { useNavigate, useLocation } from "react-router-dom";

function Navbar() {

  const navigate = useNavigate();

  const location = useLocation();

  const items = [
    {
      key: "/home",
      label: "Home",
    },

    {
      key: "/about",
      label: "About",
    },

    {
      key: "/contact",
      label: "Contact",
    },
  ];

  const handleClick = (e) => {
    navigate(e.key);
  };

  return (
    <Menu
      mode="horizontal"
      items={items}
      onClick={handleClick}
      selectedKeys={[location.pathname]}
    />
  );
}

export default Navbar;