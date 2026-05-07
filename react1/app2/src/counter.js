import { useState } from "react";
import "./App.css"
 
const Counter=()=>
{
 
//let c;
   let [c,setC]=useState(0)
 
 
   const add=()=>
{
      setC(c+1)
   
    console.log(c);
}
 
    //  sub
    // 0     10
return(<div>
 
  <h1> {c}</h1>
 
   <button onClick={add}> ADD</button>  
 
</div>)
 
 
}
 
export default Counter