<!-- notes/messageboard/todays.jsp -->

<jsp:useBean id="msbean" class="messages.MessageStoreBean" 
                         scope="session"/>

<html>
<head><title>WPJ Message Board</title></head>

<body bgcolor="ffff66">
    <h2>You are signed in as <%= session.getAttribute("name") %>.</h2>

    <h3>Today's Messages:</h3>

    <jsp:setProperty name="msbean" property="userName"
                     value='<%= session.getAttribute("name")%>'/>

    <form action="/apache-tomcat-6.0.20/MessageServlet">

       <jsp:getProperty name="msbean" property="todaysMessages"/>

      <p>
        <input type="submit" name="vaReturn" value="Return" />
      </p>
    </form>
</body>
</html>
