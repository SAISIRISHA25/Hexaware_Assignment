import { useState } from "react";
import "./App.css"
 
 
const Sum=()=>
{
 
 
 
 
  let [A,setA]=useState();
 
  let [B,setB]=useState();
 
    let [C,setC]=useState();
 
 
  const handleA=(e)=>
  {
     setA(e.target.value)
  }
 
 
   const handleB=(e)=>
  {
     setB(e.target.value)
  }
 
 
  const sum=()=>
  {
    setC(parseInt(A)+parseInt(B))
  }
return(<div>
 
    <div className="d1">
       <input type="text" placeholder="Enter A number " onChange={handleA}/>  <br/>
        <input type="text" placeholder="Enter A number " onChange={handleB}/><br/>
   
        <h1>Result {C} </h1>
         <button onClick={sum}> Add </button><br/>
    </div>
     
 
</div>)
 
 
}
 
export default Sum;
 