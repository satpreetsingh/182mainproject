<!-- notes/messageboard/register.jsp -->

<html>
   <head><title>Message Board Registration</title></head>

<body bgcolor="aqua" onLoad="document.form.name.focus()">
    <form method="post" name="form"
          action="/apache-tomcat-6.0.20/MessageServlet">
        <h2>Enter your name and a password.</h2>

        <h3>
        <table cellpadding="6">
           <tr>
             <td>Name:</td>
             <td> <input type="text" name="name" 
                                     size="40"/> </td>
           </tr>
           <tr>
             <td>Password:</td>
             <td> <input type="password" name="password" 
                                         size="40"/> </td>
           </tr>
        </table>
        </h3>
        <p><input type="submit" name="Register" value="Register" /></p>
    </form>

    <form  action="/apache-tomcat-6.0.20/notes/mboard.jsp">
         <input type="submit" value="Welcome Page" />
    </form>
</body>
</html>
