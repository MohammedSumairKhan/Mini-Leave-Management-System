<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee Login</title>
<link rel="stylesheet" type="text/css" href="css/adminlogin.css">
</head>
<body>
	<div class="login-container">
		<h1>Employee Login</h1>
		<form action="EmployeeLogin" method="post">
			<label for="username">Username:</label> 
			<input type="text" id="username" name="username" required> 
			<label for="password">Password:</label> 
			<input type="password" id="password" name="password" required>
			<button type="submit">Login</button>
		</form>
	</div>
</body>
</html>
