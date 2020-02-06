package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.Dao;
import model.Vene;

@WebServlet("/HaeVene")
public class HaeVene extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public HaeVene() {
        super();  
        System.out.println("HaeVene.HaeVene()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HaeVene.doGet()");
		//Muutettavan veneen tietojen haku
		int tunnus = Integer.parseInt(request.getParameter("tunnus"));	
		Dao dao = new Dao();	
		Vene vene = dao.etsiVene(tunnus);		
		JSONObject JSON = new JSONObject();
		JSON.put("tunnus", vene.getTunnus());
		JSON.put("nimi", vene.getNimi());
		JSON.put("merkkimalli", vene.getMerkkimalli());
		JSON.put("pituus", vene.getPituus());
		JSON.put("leveys", vene.getLeveys());
		JSON.put("hinta", vene.getHinta());
		String strJSON = JSON.toString();		
		PrintWriter out = response.getWriter(  );
	    response.setContentType("text/html"); 
	    out.println(strJSON);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HaeVene.doPost()");		
	}
}
