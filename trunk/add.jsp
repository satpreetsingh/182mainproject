<!-- notes/messageboard/add.jsp -->

<html>
   <head><title>WPJ Add Message</title></head>

   <body bgcolor="3399cc" onLoad="document.form.message.focus()">
     <font color="white">
     <h2>You are signed in as <%= session.getAttribute("name") %>.</h2>
     <h2>Type your message in the area below and press
         the submit button.</h2>
     </font>

    <form method="post" name="form"
          action="/apache-tomcat-6.0.20/MessageServlet">

     <p>
        <textarea name="message" rows="25" cols="80"></textarea>
     </p>
     <p>
        <input type="submit" name="AddMessage" value="Add Message"/>
     </p>
     </form>

    <form action="/apache-tomcat-6.0.20/MessageServlet">
         <input type="submit" name="Cancel" value="Cancel"/>
    </form>
</body>
</html>

