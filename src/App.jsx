

import Mobile from "./Mobile";

const App = () =>
{

    let mobiles = [

        { company: "Samsung", price: 70000, ram: "12GB", pic: "../samsung.jpg" },

        { company: "Vivo", price: 25000, ram: "8GB", pic: "../vivo.jpg" },

        { company: "iPhone", price: 120000, ram: "16GB", pic: "../iphone.jpg" },

        { company: "OnePlus", price: 50000, ram: "12GB", pic: "../oneplus.jpg" },

        { company: "Realme", price: 22000, ram: "6GB", pic: "../realme.jpg" },

        { company: "Oppo", price: 30000, ram: "8GB", pic: "../oppo.jpg" }

    ];

    return(
        <>

            {
                mobiles.map((temp) =>

                    <Mobile
                        company={temp.company}
                        price={temp.price}
                        ram={temp.ram}
                        pic={temp.pic}
                    />

                )
            }

        </>
    )
}

export default App;