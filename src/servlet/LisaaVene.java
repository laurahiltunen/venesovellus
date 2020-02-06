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

@WebServlet("/LisaaVene")
public class LisaaVene extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LisaaVene() {
        super();
        System.out.println("LisaaVene.LisaaVene()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LisaaVene.doGet()");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LisaaVene.doPost()");
		Vene vene = new Vene();
		
		vene.setTunnus(Integer.parseInt(request.getParameter("tunnus")));
		vene.setNimi(request.getParameter("nimi"));
		vene.setMerkkimalli(request.getParameter("merkkimalli"));
		vene.setPituus(Double.parseDouble(request.getParameter("pituus")));
		vene.setLeveys(Double.parseDouble(request.getParameter("leveys")));
		vene.setHinta(Integer.parseInt(request.getParameter("hinta")));
		
		PrintWriter out = response.getWriter();
		response.setContentType("Text/html");	
		Dao dao = new Dao();
		if(dao.onkoTunnusta(vene.getTunnus())) { //Testataan onko tunnus käytössä ennen lisäämistä
			if(dao.lisaaVene(vene)) {
				out.println(1);
			} else {
				out.println(0);
			}
		} else {
			out.println(2); // Veneen lisääminen epäonnistui, sillä tunnus on jo käytössä
		}
		
	}

}
