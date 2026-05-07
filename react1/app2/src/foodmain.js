import Food from "./Food"

const App = () =>
{
    let foods = [

    {
        name: "Pizza",
        category: "Fast Food",
        price: 250,
        quantity: 10,
        city: "Delhi",
        pic: "/pizza.jpg"
    },

    {
        name: "Burger",
        category: "Fast Food",
        price: 120,
        quantity: 15,
        city: "Mumbai",
        pic: "/burger.jpg"
    },

    {
        name: "Biryani",
        category: "Main Course",
        price: 200,
        quantity: 8,
        city: "Hyderabad",
        pic: "/biryani.jpg"
    },

    {
        name: "Dosa",
        category: "South Indian",
        price: 80,
        quantity: 20,
        city: "Bangalore",
        pic: "/dosa.jpg"
    },

    {
        name: "Pasta",
        category: "Italian",
        price: 180,
        quantity: 12,
        city: "Pune",
        pic: "../pasta.jpg"
    },

    {
        name: "Chole Bhature",
        category: "North Indian",
        price: 100,
        quantity: 14,
        city: "Delhi",
        pic: "../chole.jpg"
    },

    {
        name: "Paneer Tikka",
        category: "Starter",
        price: 220,
        quantity: 9,
        city: "Chandigarh",
        pic: "../paneer.jpg"
    },

    {
        name: "Fried Rice",
        category: "Chinese",
        price: 150,
        quantity: 11,
        city: "Kolkata",
        pic: "../friedrice.jpg"
    },

    {
        name: "Ice Cream",
        category: "Dessert",
        price: 90,
        quantity: 25,
        city: "Jaipur",
        pic: "../icecream.jpg"
    },

    {
        name: "Samosa",
        category: "Snack",
        price: 20,
        quantity: 30,
        city: "Lucknow",
        pic: "../samosa.jpg"
    }

];

    return(
        <>
            {
                foods.map((temp)=>

                    <Food
                        name={temp.name}
                        category={temp.category}
                        price={temp.price}
                        quantity={temp.quantity}
                        city={temp.city}
                        pic={temp.pic}
                    />

                )
            }
        </>
    )
}

export default App