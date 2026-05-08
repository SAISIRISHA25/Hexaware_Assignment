import { Link, Route, Routes } from "react-router-dom";
import Home from "./Home";
import About from "./About";
import Faq from "./Faq";
import Welcome from "./Welcome";
import Login from "./Login";
import MarkEntry from './MarkEntry';
import Result from './Result';
import Products from './Products'; 
import Product from './Product';
 

 
const App=()=>
{
 
 
 
  return(<>


  <ol>
    <Link to="/"> <li> Home</li></Link>
    <Link to="/about"> <li> about</li></Link>
     <Link to="/Faq"> <li> Faq</li></Link>
     <Link to ="/Login">   <li> Login </li>  </Link>
      <Link to ="/entry">   <li> entry </li>  </Link>
      <Link to="/Products"> <li> View Products </li> </Link>



  </ol>
 
 
   <Routes>
 
   <Route path="/" element={<Home/>}/>
   <Route path="/about" element={<About/>}/>
   <Route path="/Faq" element={<Faq/>}/>
   <Route path="/Login" element={<Login/>}/>
   <Route path="/welcome" element={<Welcome/>}/>
   <Route path="/entry" element={<MarkEntry />} />
   <Route path="/result" element={<Result />} />
   <Route path="/Products" element={<Products />} />
<Route path="/Product/:id/:price/:qty" element={<Product />} /> 
   </Routes>
 
 
 
 
 
  </>)
}
 
export default App;
 