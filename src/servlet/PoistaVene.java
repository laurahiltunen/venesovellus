package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;

@WebServlet("/PoistaVene")
public class PoistaVene extends HttpServlet {
	private static final long serialVersionUID = 1L;
          
    public PoistaVene() {
        super();
        System.out.println("PoistaVene.PoistaVene()");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PoistaVene.doGet()");		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PoistaVene.doPost()");
		int tunnus = Integer.parseInt(request.getParameter("tunnus"));//(muutetaan integeriksi, request tuottaa stringejä)
		Dao dao = new Dao();
		PrintWriter out = response.getWriter();
	    response.setContentType("text/html"); 
	    	if(dao.poistaVene(tunnus)){
	    		out.println(1); //Veneen poisto onnistui
	    	}else {
	    		out.println(0); //Veneen poisto epäonnistui
		
	  }
	}
}

