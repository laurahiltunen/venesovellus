package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import model.Vene;


@WebServlet("/MuutaVene")
public class MuutaVene extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MuutaVene() {
        super();
        System.out.println("MuutaVene.MuutaVene()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MuutaVene.doGet()");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MuutaVene.doPost()");
		
		//int uusitunnus = Integer.parseInt(request.getParameter("uusitunnus"));	
		String uusinimi = request.getParameter("uusinimi");
		String uusimerkkimalli = request.getParameter("uusimerkkimalli");
		double uusipituus = Double.parseDouble(request.getParameter("uusipituus"));
		double uusileveys = Double.parseDouble(request.getParameter("uusileveys"));
		int uusihinta = Integer.parseInt(request.getParameter("uusihinta"));
		int tunnus = Integer.parseInt(request.getParameter("tunnus"));
		PrintWriter out = response.getWriter();
	    response.setContentType("text/html"); 
	    
		try {
			Vene vene = new Vene(tunnus, uusinimi, uusimerkkimalli, uusipituus, uusileveys, uusihinta);
			Dao dao = new Dao();	
			if(dao.muutaVene(vene, tunnus)) {
				 out.println(1);  //Tietojen päivitys onnistui	
			}else {
				 out.println(0);  //Tietojen päivitys epäonnistui	
			}   
		} catch (Exception e) {
			out.println(0);		  //Tietojen päivitys epäonnistui, koska vuosi ei ollut luku
		}
		
	}
}
