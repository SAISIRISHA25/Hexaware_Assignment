import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

function CarDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [car, setCar] = useState(null);

  useEffect(() => {
    const cars = JSON.parse(sessionStorage.getItem("cars")) || [];

    const selectedCar = cars.find((c) => c.id === Number(id));

    setCar(selectedCar);
  }, [id]);

  if (!car) {
    return <h2 className="container">Car not found</h2>;
  }

  return (
    <div className="container">
      <h2>Car Details</h2>

      <div className="details-card">
        <h3>{car.car}</h3>
        <p>Model: {car.car_model}</p>
        <p>Color: {car.car_color}</p>
        <p>Year: {car.car_model_year}</p>
        <p>Price: {car.price}</p>
      </div>

      <button onClick={() => navigate("/cars")}>Back</button>
    </div>
  );
}

export default CarDetails;