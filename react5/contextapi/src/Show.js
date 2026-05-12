import { useContext } from "react"
import { Mycontext } from "./Mycontext"

export const Show=()=>{

    let {age,name}=useContext(Mycontext)

    return(<>

    <h1>  AGE{age}</h1>
    <h1>  NAME{name}</h1>
    
    </>)

}