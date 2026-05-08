import { useState } from "react";
import { useNavigate } from "react-router-dom";

function AddCar() {
  const navigate = useNavigate();

  const [car, setCar] = useState("");
  const [carModel, setCarModel] = useState("");
  const [carColor, setCarColor] = useState("");
  const [carYear, setCarYear] = useState("");
  const [price, setPrice] = useState("");

  const addCar = (e) => {
    e.preventDefault();

    const cars = JSON.parse(sessionStorage.getItem("cars")) || [];

    const newCar = {
      id: cars.length + 1,
      car: car,
      car_model: carModel,
      car_color: carColor,
      car_model_year: carYear,
      price: price,
    };

    const updatedCars = [...cars, newCar];

    sessionStorage.setItem("cars", JSON.stringify(updatedCars));

    alert("New car added successfully");
    navigate("/cars");
  };

  return (
    <div className="container">
      <h2>Add New Car</h2>

      <form onSubmit={addCar}>
        <input
          type="text"
          placeholder="Car name"
          value={car}
          onChange={(e) => setCar(e.target.value)}
        />

        <input
          type="text"
          placeholder="Car model"
          value={carModel}
          onChange={(e) => setCarModel(e.target.value)}
        />

        <input
          type="text"
          placeholder="Car color"
          value={carColor}
          onChange={(e) => setCarColor(e.target.value)}
        />

        <input
          type="number"
          placeholder="Car year"
          value={carYear}
          onChange={(e) => setCarYear(e.target.value)}
        />

        <input
          type="text"
          placeholder="Price"
          value={price}
          onChange={(e) => setPrice(e.target.value)}
        />

        <button type="submit">Add Car</button>
      </form>
    </div>
  );
}

export default AddCar;