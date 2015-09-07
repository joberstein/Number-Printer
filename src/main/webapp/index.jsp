<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
	<div class='top-space'>
		<div align="center">
			<h1>My Number Printer</h1>
			
			<form class='number-form' action="validate" method="GET">
				<h3 align="left">Enter a numeric value:</h3>
				<div class='center bottom-space'>
					<c:if test="${not empty requestScope.error}">
						<div class='tooltip'>
							<p class='tooltip-text'>${requestScope.error}</p>
						</div>
					</c:if>
					<input class='half-width text-right' type="text" name="integer" placeholder="Integer value" maxlength="15">
					<span class='decimal-width'>.</span>
					<input class='half-width' type="text" name="decimal" placeholder="Decimal value"  maxlength="6">
				</div>
				<button class='center convert-button'>Convert to English</button>
			</form>
		</div>
	</div>
</body>
</html>
