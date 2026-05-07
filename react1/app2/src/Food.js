import "./App.css"

const Food = ({name, category, price, quantity, city, pic}) =>
{
    return(
        <div className="d1">

            <img src={pic} width="200px" height="200px"/>

            <h1>Food : {name}</h1>

            <h2>Category : {category}</h2>

            <h2>Price : {price}</h2>

            <h2>Quantity : {quantity}</h2>

            <h2>City : {city}</h2>

        </div>
    )
}

export default Food