<!-- notes/mboard.jsp -->

<html>
<head><title>WPJ Message Board</title></head>

<body bgcolor="99ff99">
   <h1>Welcome to the WPJ Message Board.</h1>
   <h2>Choose to login or to register as a new user and press Continue.


   <form action="/apache-tomcat-6.0.20/MessageServlet">

     <p>
       <input type="radio" name="choice" value="login"
                                         checked="yes"/>Login
     </p>
     <p>
       <input type="radio" name="choice" value="register"/>Register
     </p>
     <p>
       <input type="submit" value="Continue" />
     </p>
   </form>
   </h2>
</body>
</html>
