<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Home</title>
    <script src="${pageContext.request.contextPath}/resources/static/jquery-3.7.1.js"></script>
    <script src="${pageContext.request.contextPath}/resources/static/websocket.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@5.0.0/bundles/stomp.umd.min.js"></script>
</head>
<body>
<div id="payment-filter">
    <button onclick="callWebSocket();">Get Payments</button>
</div>
</body>

</html>

<script>
    const userName = '${pageContext.request.userPrincipal.name}';


</script>
