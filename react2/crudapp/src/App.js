import { useEffect, useState } from "react";
import Product from "./Product";
import "./App.css";

const App = () =>
{
    let [products, setProducts] = useState([]);
    let [Fproducts, setFProducts] = useState([]);
    let [search, setsearch] = useState("");
    let [newPrice, setNewPrice] = useState("");

    // Form States
    let [id, setId] = useState("");
    let [title, setTitle] = useState("");
    let [price, setPrice] = useState("");
    let [description, setDescription] = useState("");
    let [image, setImage] = useState("");
    let [category, setCategory] = useState("");

    // Search Handler
    const handlesearch = (e) =>
    {
        setsearch(e.target.value);
    }

    // Delete Function
    const removeProduct = (id) =>
    {
        let result = products.filter((item) => item.id !== id);

        setProducts(result);
    }

    const updatePrice = (id,newPrice) =>
{
    let result = products.map((item) =>

        item.id === id

        ? {...item, price:newPrice}

        : item

    );

    setProducts(result);

    //setNewPrice("");
}

    // Fetch API
    useEffect(() =>
    {
        fetch("https://fakestoreapi.com/products")
        .then((res) => res.json())
        .then((res) => setProducts(res))
        .catch((e) => console.log(e))

    }, [])

    // Search Filter
    useEffect(() =>
    {
        setFProducts(
            products.filter((item) =>
                item.category.toLowerCase().includes(search.toLowerCase())
            )
        )

    }, [search, products])

    // Add Product
    const add = () =>
    {
        let data =
        {
            id,
            title,
            price,
            description,
            image,
            category
        }

        setProducts([data, ...products]);

        // Clear Fields
        setId("");
        setTitle("");
        setPrice("");
        setDescription("");
        setImage("");
        setCategory("");
    }

    return(
        <>

            <div className="d1">

                <h2>Product Form</h2>

                <label>ID</label><br/>

                <input
                    type="number"
                    placeholder="Enter id"
                    value={id}
                    onChange={(e)=>setId(e.target.value)}
                />

                <br/><br/>

                <label>Title</label><br/>

                <input
                    type="text"
                    placeholder="Enter title"
                    value={title}
                    onChange={(e)=>setTitle(e.target.value)}
                />

                <br/><br/>

                <label>Price</label><br/>

                <input
                    type="number"
                    placeholder="Enter price"
                    value={price}
                    onChange={(e)=>setPrice(e.target.value)}
                />

                <br/><br/>

                <label>Description</label><br/>

                <textarea
                    placeholder="Enter description"
                    value={description}
                    onChange={(e)=>setDescription(e.target.value)}
                ></textarea>

                <br/><br/>

                <label>Image</label><br/>

                <input
                    type="text"
                    placeholder="Enter image url"
                    value={image}
                    onChange={(e)=>setImage(e.target.value)}
                />

                <br/><br/>

                <label>Category</label><br/>

                <input
                    type="text"
                    placeholder="Enter category"
                    value={category}
                    onChange={(e)=>setCategory(e.target.value)}
                />

                <br/><br/>

                <button onClick={add}>
                    Add Product
                </button>

            </div>

            <br/>

            

            <input
                type="text"
                placeholder="Search by category"
                onChange={handlesearch}
            />

            <h2>{search}</h2>

            {
                search.length > 0 ?

                Fproducts.map((p) =>

                    <Product
                        key={p.id}
                        id={p.id}
                        title={p.title}
                        price={p.price}
                        description={p.description}
                        image={p.image}
                        category={p.category}
                        removeProduct={removeProduct}
                        updatePrice={updatePrice}
                    />

                )

                :

                products.map((p) =>

                    <Product
                        key={p.id}
                        id={p.id}
                        title={p.title}
                        price={p.price}
                        description={p.description}
                        image={p.image}
                        category={p.category}
                        removeProduct={removeProduct}
                        updatePrice={updatePrice}
                    />

                )
            }

        </>
    )
}

export default App;