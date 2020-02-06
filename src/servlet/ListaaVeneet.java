package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.Dao;
import model.Vene;


@WebServlet("/ListaaVeneet")
public class ListaaVeneet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ListaaVeneet() {
        super();
        System.out.println("ListaaVeneet.ListaaVeneet()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ListaaVeneet.doGet()");
		String hakusana = request.getParameter("hakusana");
		Dao dao = new Dao();
		ArrayList<Vene> veneet = dao.listaaKaikki(hakusana);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String strJSON = new JSONObject().put("veneet", veneet).toString();
		out.println(strJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ListaaVeneet.doPost()");
	}

}
