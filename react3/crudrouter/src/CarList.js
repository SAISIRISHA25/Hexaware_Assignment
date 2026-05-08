import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function CarList() {
  const [cars, setCars] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const storedCars = JSON.parse(sessionStorage.getItem("cars"));

    if (storedCars) {
      setCars(storedCars);
    } else {
      fetch("https://myfakeapi.com/api/cars/")
        .then((response) => response.json())
        .then((data) => {
          setCars(data.cars);
          sessionStorage.setItem("cars", JSON.stringify(data.cars));
        })
        .catch((error) => console.log(error));
    }
  }, []);

  const removeCar = (id) => {
    const loggedInUser = sessionStorage.getItem("loggedInUser");

    if (!loggedInUser) {
      alert("Please sign in first to delete a car");
      navigate("/signin");
      return;
    }

    const confirmDelete = window.confirm("Are you sure you want to delete this car?");

    if (confirmDelete) {
      const updatedCars = cars.filter((car) => car.id !== id);
      setCars(updatedCars);
      sessionStorage.setItem("cars", JSON.stringify(updatedCars));
      alert("Car removed successfully");
    }
  };

  return (
    <div className="container">
      <h2>All Cars</h2>

      <div className="card-container">
        {cars.map((car) => (
          <div className="card" key={car.id}>
            <div>
              <h3>{car.car}</h3>
              <p><strong>Model:</strong> {car.car_model}</p>
              <p><strong>Color:</strong> {car.car_color}</p>
              <p><strong>Year:</strong> {car.car_model_year}</p>
              <p><strong>Price:</strong> {car.price}</p>
            </div>

            <div className="card-buttons">
              <Link to={`/car-details/${car.id}`}>
                <button className="view-btn">View</button>
              </Link>

              <Link to={`/update-car/${car.id}`}>
                <button className="edit-btn">Edit</button>
              </Link>

              <button
                className="delete-btn"
                onClick={() => removeCar(car.id)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default CarList;