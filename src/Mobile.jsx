import "./App.css"

const Mobile = ({company, price, ram, pic}) =>
{
    return(
        <div className="d1">


            <h1>Mobile : {company}</h1>

            <h2>Price : {price}</h2>

            <h2>RAM : {ram}</h2>

            <img src={pic} width="200px" height="200px"/>


        </div>
    )
}

export default Mobile