import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function SearchCar() {
  const [cars, setCars] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    const storedCars = JSON.parse(sessionStorage.getItem("cars")) || [];
    setCars(storedCars);
  }, []);

  const filteredCars = cars.filter((car) =>
    car.car.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="container">
      <h2>Search Car</h2>

      <input
        type="text"
        placeholder="Search by car name"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      <div className="card-container">
        {filteredCars.map((car) => (
          <div className="card" key={car.id}>
            <h3>{car.car}</h3>
            <p>Model: {car.car_model}</p>
            <p>Color: {car.car_color}</p>
            <p>Year: {car.car_model_year}</p>
            <p>Price: {car.price}</p>

            <Link to={`/car-details/${car.id}`}>
              <button>View Details</button>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default SearchCar;