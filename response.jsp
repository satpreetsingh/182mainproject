<%-- 
    Document   : response
    Created on : Oct 30, 2009, 6:17:17 PM
    Author     : Mark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello, !</h1>
    <jsp:useBean id="mybean" scope="session" class="org.mypackage.hello.NameHandler" />
    <jsp:setProperty name="mybean" property="name" />
    <h2>Hello, <jsp:getProperty name="mybean" property="name" />!</h2>

    </body>
</html>
