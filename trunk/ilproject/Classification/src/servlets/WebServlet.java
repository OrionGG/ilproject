package servlets;

public @interface WebServlet {

	String[] urlPatterns();

	String name();

}
