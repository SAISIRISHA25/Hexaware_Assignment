import { useParams } from "react-router-dom"


const Product=()=>{
    const p=useParams()

    return(<>
    <h1>Product Details:</h1>
    <h1>{p.id}</h1>
    <h1>Price: ₹{p.price}</h1>
      <h1>Quantity: {p.qty}</h1>
    
    </>)

}

export default Product