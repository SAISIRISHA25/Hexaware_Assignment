import { useState } from "react";
import { Mycontext } from "./Mycontext";
import { Show } from "./Show";
 
const App=()=>
{
 
 
let [age,setAge]=useState(21);
let [name,setName]=useState("ajay");
 
  return(<>
 
 
 
<Mycontext.Provider value={{age,name}}>
 
 <Show/>
 
</Mycontext.Provider>
 
 
 
 
  </>)
}
export default App;
 
 