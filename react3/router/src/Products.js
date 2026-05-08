import { Link } from "react-router-dom"
const Products=()=>
{
 
   let items=[{"code":101,"price":200,qty:20},
    {"code":102,"price":100,qty:10},
    {"code":103,"price":900,qty:90},
    {"code":104,"price":200,qty:20}
   ]
 
    return(<>
   
     <h1> Products</h1>
{
    items.map((i)=>
   
<Link key={i.code} to={`/Product/${i.code}/${i.price}/${i.qty}`}>
    <h1> {i.code} </h1>
  </Link> 
 
    )
}
   
    </>)
}
export default Products;