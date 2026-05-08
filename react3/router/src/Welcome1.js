const Welcome1 = () => {
  const currentUser = sessionStorage.getItem("user");

  return (
    <div>
      <h1>Welcome, {currentUser}!</h1>
      <p>You have successfully logged in using session storage.</p>
    </div>
  );
};