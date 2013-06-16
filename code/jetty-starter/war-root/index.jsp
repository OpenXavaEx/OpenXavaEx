<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%
	String appCtx = System.getenv("CTX_PATH");
	if (null==appCtx) appCtx = "TestApp";
%><HTML>
    <HEAD>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="2; url=<%=appCtx%>/index.html">
        <TITLE>OpenXava <%=appCtx%></TITLE>
    </HEAD>
    <BODY style="font-family: Courier New">
        <h1>Working ...</h1>
        <hr style="border-top:1px dotted gray; height:0;"/>
        <i style="font-size:0.8em">Redirecting, <a href="<%=appCtx%>/index.html">press here</a> ...</i>
    </BODY>
</HTML>