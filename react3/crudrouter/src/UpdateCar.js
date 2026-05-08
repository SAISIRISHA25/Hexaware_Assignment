import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

function UpdateCar() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [car, setCar] = useState("");
  const [carModel, setCarModel] = useState("");
  const [carColor, setCarColor] = useState("");
  const [carYear, setCarYear] = useState("");
  const [price, setPrice] = useState("");

  useEffect(() => {
    const cars = JSON.parse(sessionStorage.getItem("cars")) || [];

    const selectedCar = cars.find((c) => c.id === Number(id));

    if (selectedCar) {
      setCar(selectedCar.car);
      setCarModel(selectedCar.car_model);
      setCarColor(selectedCar.car_color);
      setCarYear(selectedCar.car_model_year);
      setPrice(selectedCar.price);
    }
  }, [id]);

  const updateCar = (e) => {
    e.preventDefault();

    const cars = JSON.parse(sessionStorage.getItem("cars")) || [];

    const updatedCars = cars.map((c) => {
      if (c.id === Number(id)) {
        return {
          ...c,
          car: car,
          car_model: carModel,
          car_color: carColor,
          car_model_year: carYear,
          price: price,
        };
      }
      return c;
    });

    sessionStorage.setItem("cars", JSON.stringify(updatedCars));

    alert("Car updated successfully");
    navigate("/cars");
  };

  return (
    <div className="container">
      <h2>Update Car</h2>

      <form onSubmit={updateCar}>
        <input
          type="text"
          value={car}
          onChange={(e) => setCar(e.target.value)}
        />

        <input
          type="text"
          value={carModel}
          onChange={(e) => setCarModel(e.target.value)}
        />

        <input
          type="text"
          value={carColor}
          onChange={(e) => setCarColor(e.target.value)}
        />

        <input
          type="number"
          value={carYear}
          onChange={(e) => setCarYear(e.target.value)}
        />

        <input
          type="text"
          value={price}
          onChange={(e) => setPrice(e.target.value)}
        />

        <button type="submit">Update Car</button>
      </form>
    </div>
  );
}

export default UpdateCar;