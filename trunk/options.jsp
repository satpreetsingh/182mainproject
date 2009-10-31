<!-- notes/messageboard/options.jsp -->

<html>
<head><title>WPJ Message Board</title></head>

<body bgcolor="ff9966">
    <h2>Welcome</h2>
    <h3>You are signed into the WPJ Message Board as 
         <%= session.getAttribute("name") %>.
    <p>
        Choose to read messages or to add a mesage 
        to the board and press Submit.
    </p>
    <hr size="5"/>

    <form action="/apache-tomcat-6.0.20/MessageServlet">
    <p>
      <input type="radio" name="option" 
                          checked="yes" value="viewtodays"/>View Today's Messages
    </p>
    <p>
      <input type="radio" name="option" value="viewall"/>View All Messages
    </p>
    <p>
      <input type="radio" name="option" value="add"/>Add a Message
    </p>
    <p>
      <input type="radio" name="option" value="list"/>List Registered Users
    </p>
    <p>
      <input type="radio" name="option" value="changepw"/>Change Password
    </p>
    <p>
      <input type="radio" name="option" value="unregister"/>Unregister from Message Board
    </p>
    <hr size="5"/>
    <p>
      <input type="submit" value="Submit" />
      &nbsp;&nbsp;    
      <input type="submit" name="Logout" value="Logout" />
    </p>
    </form>
    </h3>
</body>
</html>
