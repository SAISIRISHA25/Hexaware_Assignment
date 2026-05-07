import { useState } from "react";
import "./App.css";

const Recipe = ({
    id,
    name,
    cuisine,
    difficulty,
    rating,
    image,
    deleteRecipe,
    updateRating
}) =>
{
    let [newRating, setNewRating] = useState("");

    return(
        <div className="recipe-card">

            <img src={image} alt={name} />

            <h2>{name}</h2>

            <h3>Cuisine : {cuisine}</h3>

            <h3>Difficulty : {difficulty}</h3>

            <h3>Rating : {rating}</h3>

            <button className="delete-btn" onClick={()=>deleteRecipe(id)}>
                Delete
            </button>

            <br/><br/>

            <input
                type="number"
                placeholder="Enter new rating"
                onChange={(e)=>setNewRating(e.target.value)}
            />

            <button className="update-btn" onClick={()=>updateRating(id, newRating)}>
                Update Rating
            </button>

        </div>
    );
};

export default Recipe;