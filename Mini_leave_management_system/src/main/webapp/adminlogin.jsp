<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Login</title>
<link rel="stylesheet" type="text/css" href="css/adminlogin.css">
</head>
<body>
	<div class="login-container">
		<h1>Admin Login</h1>
		<form action="AdminLogin" method="post">
		<!-- Show error above form -->
			<c:if test="${not empty errorMessage}">
				<p style="color: red;">${errorMessage}</p>
			</c:if>

			<label for="username">Username:</label> <input type="text"
				id="username" name="username" required> <label
				for="password">Password:</label> <input type="password"
				id="password" name="password" required>

			<button type="submit">Login</button>
		</form>
	</div>
</body>
</html>
