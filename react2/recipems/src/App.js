import { useEffect, useState } from "react";
import Recipe from "./Recipe";
import "./App.css";

const App = () =>
{
    let [recipes, setRecipes] = useState([]);
    let [search, setSearch] = useState("");
    let [cuisineFilter, setCuisineFilter] = useState("");

    let [id, setId] = useState("");
    let [name, setName] = useState("");
    let [cuisine, setCuisine] = useState("");
    let [difficulty, setDifficulty] = useState("");
    let [rating, setRating] = useState("");
    let [image, setImage] = useState("");

    useEffect(() =>
    {
        fetch("https://dummyjson.com/recipes")
        .then((res)=>res.json())
        .then((res)=>setRecipes(res.recipes))
        .catch((err)=>console.log(err));
    }, []);

    const addRecipe = () =>
    {
        let data = {
            id,
            name,
            cuisine,
            difficulty,
            rating,
            image
        };

        setRecipes([data, ...recipes]);

        setId("");
        setName("");
        setCuisine("");
        setDifficulty("");
        setRating("");
        setImage("");
    };

    const deleteRecipe = (id) =>
    {
        let result = recipes.filter((item)=>item.id !== id);
        setRecipes(result);
    };

    const updateRating = (id, newRating) =>
    {
        let result = recipes.map((item)=>

            item.id === id

            ? {...item, rating:newRating}

            : item

        );

        setRecipes(result);
    };

    let filteredRecipes = recipes.filter((item)=>

        item.name.toLowerCase().includes(search.toLowerCase()) &&
        item.cuisine.toLowerCase().includes(cuisineFilter.toLowerCase())

    );

    return(
        <div>

            <h1 className="main-title">Recipe Management System</h1>

            <div className="form-box">

                <h2>Add Recipe</h2>

                <input
                    type="number"
                    placeholder="Enter ID"
                    value={id}
                    onChange={(e)=>setId(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Enter Recipe Name"
                    value={name}
                    onChange={(e)=>setName(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Enter Cuisine"
                    value={cuisine}
                    onChange={(e)=>setCuisine(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Enter Difficulty"
                    value={difficulty}
                    onChange={(e)=>setDifficulty(e.target.value)}
                />

                <input
                    type="number"
                    placeholder="Enter Rating"
                    value={rating}
                    onChange={(e)=>setRating(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Enter Image URL"
                    value={image}
                    onChange={(e)=>setImage(e.target.value)}
                />

                <button onClick={addRecipe}>Add Recipe</button>

            </div>

            <div className="search-box">

                <input
                    type="text"
                    placeholder="Search by recipe name"
                    onChange={(e)=>setSearch(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Filter by cuisine"
                    onChange={(e)=>setCuisineFilter(e.target.value)}
                />

            </div>

            <div className="recipe-container">

                {
                    filteredRecipes.length > 0 ?

                    filteredRecipes.map((r)=>

                        <Recipe
                            key={r.id}
                            id={r.id}
                            name={r.name}
                            cuisine={r.cuisine}
                            difficulty={r.difficulty}
                            rating={r.rating}
                            image={r.image}
                            deleteRecipe={deleteRecipe}
                            updateRating={updateRating}
                        />

                    )

                    :

                    <h2>No Recipes Found</h2>
                }

            </div>

        </div>
    );
};

export default App;