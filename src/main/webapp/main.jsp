<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src='box-controller.js'></script>
</head>
<body>
	<div class='top-space'>
		<div align='center'>
			<h1>My Number Printer</h1>
		</div>
		
		<div class='boxes center'>
			<div class='box'>
				<div class='box-frame'>
					<p class='box-frame-text'>Input</p>
					<p class='collapse'>&ndash;</p>
				</div>
				<div class='result-body'>
					<h3 class='result-text'>${requestScope.input}</h3>
				</div>
			</div>
			
			<c:choose>
	    		<c:when test="${not empty requestScope.error}">
					<div class='box'>
						<div class='box-frame'>
							<p class='box-frame-text'>Full Number</p>
							<p class='collapse'>&ndash;</p>
						</div>
						<div class='result-body'>
							<h3 class='result-text'>${requestScope.error}</h3>
						</div>
					</div>
				</c:when>
				
				<c:otherwise>
					<div class='box'>
						<div class='box-frame'>
							<p class='box-frame-text'>Full Number</p>
							<p class='collapse'>&ndash;</p>
						</div>
						<div class='result-body'>
							<h3 class='result-text'>${requestScope.result}</h3>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			
			<div class='box'>
				<div class='box-frame'>
					<p class='box-frame-text'>Integer Value</p>
					<p class='expand'>+</p>
				</div>
				<div class='result-body collapsed'>
					<h3 class='result-text'>${requestScope.integer}</h3>
				</div>
			</div>
			<div class='box'>
				<div class='box-frame'>
					<p class='box-frame-text'>Decimal Value</p>
					<p class='expand'>+</p>
				</div>
				<div class='result-body collapsed'>
					<h3 class='result-text'>${requestScope.decimal}</h3>
				</div>
			</div>
		</div>
	</div>
</body>
</html>