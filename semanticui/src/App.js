// // // // import "./App.css";


// // // // import React, { useState } from "react";
// // // // import { Button, Flex ,message} from "antd";

// // // // function App() {
// // // // //   const [status, setStatus] = useState("start");

// // // // //   const start = () => {
// // // // //     setStatus("stop");
// // // // //   };

// // // // //   const stop = () => {
// // // // //     setStatus("resume");
// // // // //   };

// // // // //   const resume = () => {
// // // // //     setStatus("stop");
// // // // //   };

// // // // //   return (
// // // // //     <div className="d1">
// // // // //       <Flex gap="small" wrap>
// // // // //         {status === "start" && (
// // // // //           <Button onClick={start} type="primary">
// // // // //             Start
// // // // //           </Button>
// // // // //         )}

// // // // //         {status === "stop" && (
// // // // //           <Button onClick={stop} danger>
// // // // //             Stop
// // // // //           </Button>
// // // // //         )}

// // // // //         {status === "resume" && (
// // // // //           <Button onClick={resume} type="default">
// // // // //             Resume
// // // // //           </Button>
// // // // //         )}
// // // // //       </Flex>
// // // // //     </div>
// // // // //   );
// // // // // }

// // // // const [messageApi, contextHolder] = message.useMessage();
// // // //   const info = () => {
// // // //     messageApi.info('Hello, Welcome you are login ');
// // // //   };
// // // //   return (
// // // //     <>
// // // //       {contextHolder}
// // // //       <Button type="primary" onClick={info}>
// // // //         show
// // // //       </Button>
// // // //     </>
// // // //   );
// // // // };

// // // // export default App;



// // // import React, { useState } from 'react';
// // // import { Button, Modal } from 'antd';
 
// // // const App = () => {
 
// // //   const [isModalOpen, setIsModalOpen] = useState(false);
 
// // //   const [bookname, setBookname] = useState("");
// // //   const [code, setCode] = useState("");
// // //   const [price, setPrice] = useState("");
// // //   const [qty, setQty] = useState("");
 
// // //   const [books, setBooks] = useState([]);
 
// // //   const showModal = () => {
// // //     setIsModalOpen(true);
// // //   };
 
// // //   const handleOk = () => {
 
// // //     const obj = {
// // //       bookname,
// // //       code,
// // //       price,
// // //       qty
// // //     };
 
// // //     setBooks([...books, obj]);
 
// // //     setIsModalOpen(false);
 
// // //     setBookname("");
// // //     setCode("");
// // //     setPrice("");
// // //     setQty("");
// // //   };
 
// // //   const handleCancel = () => {
// // //     setIsModalOpen(false);
// // //   };
 
// // //   return (
// // //     <div style={{ padding: "20px" }}>
 
// // //       <Button type="primary" onClick={showModal}>
// // //         Add Book
// // //       </Button>
 
// // //       <Modal
// // //         title="Book Form"
// // //         open={isModalOpen}
// // //         onOk={handleOk}
// // //         onCancel={handleCancel}
// // //       >
 
// // //         <input
// // //           type="text"
// // //           placeholder="Enter Book Name"
// // //           value={bookname}
// // //           onChange={(e) => setBookname(e.target.value)}
// // //         />
 
// // //         <br /><br />
 
// // //         <input
// // //           type="text"
// // //           placeholder="Enter Code"
// // //           value={code}
// // //           onChange={(e) => setCode(e.target.value)}
// // //         />
 
// // //         <br /><br />
 
// // //         <input
// // //           type="number"
// // //           placeholder="Enter Price"
// // //           value={price}
// // //           onChange={(e) => setPrice(e.target.value)}
// // //         />
 
// // //         <br /><br />
 
// // //         <input
// // //           type="number"
// // //           placeholder="Enter Qty"
// // //           value={qty}
// // //           onChange={(e) => setQty(e.target.value)}
// // //         />
 
// // //       </Modal>
 
// // //       <hr />
 
// // //       <h2>Book List</h2>
 
// // //       {
// // //         books.map((b, index) => (
// // //           <div key={index}>
// // //             <h3>{b.bookname}</h3>
// // //             <p>Code : {b.code}</p>
// // //             <p>Price : {b.price}</p>
// // //             <p>Qty : {b.qty}</p>
// // //             <hr />
// // //           </div>
// // //         ))
// // //       }
 
// // //     </div>
// // //   );
// // // };
 
// // // export default App;
 


// // import React, { useEffect, useState } from "react";
// // import { Card } from "antd";
// // import Loder from "./Load";

// // const App = () => {
// //   const [users, setUsers] = useState([]);

// //   useEffect(() => {
// //     fetch("https://jsonplaceholder.typicode.com/users")
// //       .then((response) => response.json())
// //       .then((data) => {
// //         setUsers(data);
// //       })
// //       .catch((error) => {
// //         console.log(error);
// //       });
// //   }, []);

// //   return (
// //     <>
// //       {users.length > 0 ? (
// //         users.map((user) => (
// //           <Card
// //             key={user.id}
// //             title={user.name}
// //             style={{ width: 300, margin: 20 }}
// //           >
// //             <p>Email: {user.email}</p>
// //             <p>Phone: {user.phone}</p>
// //             <p>City: {user.address.city}</p>
// //           </Card>
// //         ))
// //       ) : (
// //         <Loder />
// //       )}
// //     </>
// //   );
// // };

// // export default App;



// import React, { useState } from 'react';
// import { Button, Input, notification } from 'antd';
// import 'antd/dist/reset.css';
 
// const App = () => {
 
//   const [name, setName] = useState("");
//   const [email, setEmail] = useState("");
 
//   const [api, contextHolder] = notification.useNotification();
 
//   const saveData = () => {
 
//     // validation
//     if (name === "" || email === "") {
 
//       api.error({
//         message: 'Error',
//         description: 'Please fill all fields',
//         duration: 3,
//       });
 
//       return;
//     }
 
//     // create object
//     const user = {
//       name: name,
//       email: email
//     };
 
//     // store in local storage
//     localStorage.setItem("userinfo", JSON.stringify(user));
 
//     // success notification
//     api.success({
//       message: 'Success',
//       description: 'Data stored in local storage successfully',
//       duration: 3,
//     });
 
//     // clear fields
//     setName("");
//     setEmail("");
//   };
 
//   return (
//     <div style={{ width: "300px", margin: "50px auto" }}>
 
//       {contextHolder}
 
//       <h2>User Form</h2>
 
//       <Input
//         placeholder="Enter Name"
//         value={name}
//         onChange={(e) => setName(e.target.value)}
//         style={{ marginBottom: "10px" }}
//       />
 
//       <Input
//         placeholder="Enter Email"
//         value={email}
//         onChange={(e) => setEmail(e.target.value)}
//         style={{ marginBottom: "10px" }}
//       />
 
//       <Button type="primary" onClick={saveData}>
//         Save Data
//       </Button>
 
//     </div>
//   );
// };
 
// export default App;



import Navbar from "./Navbar";
import AppRoutes from "./AppRoutes";

function App() {
  return (
    <div>
      <Navbar />
      <AppRoutes />
    </div>
  );
}

export default App;
 