<!-- notes/messageboard/login.jsp -->

<html>
   <head><title>Message Board Login</title></head>

<body bgcolor="ff9999" onLoad="document.form.name.focus()">
    <form method="post" name="form"
          action="/apache-tomcat-6.0.20/MessageServlet">
        <h2>Enter your name and password.</h2>

        <table cellpadding="6">
           <tr>
             <td>Name</td>
             <td> <input type="text" name="name" 
                                     size="40"/> </td>
           </tr>
           <tr>
             <td>Password</td>
             <td> <input type="password" name="password" 
                                         size="40"/> </td>
           </tr>
        </table>
        <p><input type="submit" name="Login" value="Login" /></p>
    </form>

    <form  action="/apache-tomcat-6.0.20/notes/mboard.jsp">
         <input type="submit" value="Welcome Page" />
    </form>
</body>
</html>

