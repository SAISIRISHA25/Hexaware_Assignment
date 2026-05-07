import { useEffect, useState } from "react";

const Effect = () =>
{
    let [users, setUsers] = useState([]);

    useEffect(() =>
    {
        fetch("https://fakestoreapi.com/products")
        .then((res) => res.json())
        .then((res) =>
        {
            setUsers(res);
        })
        .catch((err) => console.log(err));

    }, []);

    return(
        <>
            <h1>Products List</h1>

            {
                users.map((temp) =>
                    <div key={temp.id}>
                        <h2>{temp.title}</h2>
                        <h3>Price: {temp.price}</h3>
                        <img src={temp.image} width="100px" height="100px"/>
                        <hr/>
                    </div>
                )
            }
        </>
    )
}

export default Effect;