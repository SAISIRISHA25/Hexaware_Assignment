import { useState } from "react";
import "./App.css";

const Shooping = () =>
{
    let [qty, setQty] = useState("");
    let [price, setPrice] = useState("");
    let [discount, setDiscount] = useState(5);

    let [total, setTotal] = useState(0);
    let [disAmount, setDisAmount] = useState(0);
    let [gst, setGst] = useState(0);
    let [finalBill, setFinalBill] = useState(0);

    const calculateBill = () =>
    {
        let amount = qty * price;

        let discountValue = (amount * discount) / 100;

        let afterDiscount = amount - discountValue;

        let gstAmount = (afterDiscount * 10) / 100;

        let finalAmount = afterDiscount + gstAmount;

        setTotal(amount);
        setDisAmount(discountValue);
        setGst(gstAmount);
        setFinalBill(finalAmount);
    }

    return (
        <div className="container">

            <h2>Shopping Bill Calculator</h2>

            <div className="form-group">

                <label>Enter Quantity:</label>

                <input
                    type="number"
                    placeholder="Enter quantity"
                    onChange={(e)=>setQty(e.target.value)}
                />

            </div>

            <div className="form-group">

                <label>Enter Price:</label>

                <input
                    type="number"
                    placeholder="Enter price"
                    onChange={(e)=>setPrice(e.target.value)}
                />

            </div>

            <div className="form-group">

                <label>Select Discount:</label>

                <select onChange={(e)=>setDiscount(e.target.value)}>

                    <option value="5">5%</option>

                    <option value="10">10%</option>

                </select>

            </div>

            <button onClick={calculateBill}>Calculate</button>

            <div className="result">

                <p>Total Amount: {total}</p>

                <p>Discount: {disAmount}</p>

                <p>GST (5% + 5%): {gst}</p>

                <h3>Final Bill: {finalBill}</h3>

            </div>

        </div>
    );
};

export default Shooping;