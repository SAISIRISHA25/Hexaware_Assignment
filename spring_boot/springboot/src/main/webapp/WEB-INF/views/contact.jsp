<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contact Us</title>
</head>
<body>

<h1>Contact Us</h1>

<form action="submitContact" method="post">
    
    Name: <input type="text" name="name" /><br><br>
    
    Email: <input type="email" name="email" /><br><br>
    
    Message: <textarea name="message"></textarea><br><br>
    
    <input type="submit" value="Send" />

</form>

</body>
</html>