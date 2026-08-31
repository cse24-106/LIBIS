The include directive in JSP (<%@ include %>) is performed at JSP translation time. 
Since the included file is translated along with the JSP, variables that are declared prior to the directive (like pageTitle) are accessible inside the included file.

As opposed to the < jsp:include > tag, which is an include that takes place at request time, the included file gets executed separately, not included in the translation process, hence its inappropriateness in this case, where the included file uses the local variable pageTitle.

Thus, the include directive is the right choice for the included file header.jspf and footer.jspf.