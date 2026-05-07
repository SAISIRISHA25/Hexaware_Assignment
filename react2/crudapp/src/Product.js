import "./App.css";
import { useState } from "react";

const Product = ({
    id,
    title,
    price,
    description,
    image,
    category,
    removeProduct,
    updatePrice
}) =>
{
    let [newPrice, setNewPrice] = useState("");

    return(
        <div className="d1">

            <h1>{id}</h1>

            <h2>{title}</h2>

            <h3>Price : {price}</h3>

            <h3>Category : {category}</h3>

            <h4>{description}</h4>

            <img
                src={image}
                alt={title}
                width="200px"
                height="200px"
            />

            <br/><br/>

            <button onClick={()=>removeProduct(id)}>
                Delete
            </button>

            <br/><br/>

            <input
                type="number"
                placeholder="Enter new price"
                onChange={(e)=>setNewPrice(e.target.value)}
            />

            <button onClick={()=>updatePrice(id,newPrice)}>
                Update Price
            </button>

        </div>
    )
}

export default Product;