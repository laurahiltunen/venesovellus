<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Veneen tietojen muutos</title>
</head>
<body>
<form id="tiedot">
	<table class="table">
		<thead>
			<tr>
				<th colspan="7" class="oikealle"><span id="takaisin">Takaisin listaukseen</span></th>
			</tr>
			<tr>
				<th>Tunnus</th>
				<th>Nimi</th>
				<th>Merkki / Malli</th>
				<th>Pituus</th>	
				<th>Leveys</th>		
				<th colspan = "2">Hinta</th>
			</tr>
		</thead>
			<tr>
				<!--  <td><input type="text" name="uusitunnus" id="uusitunnus"></td> -->
				<td><input type="text" name="tunnus" id="tunnus" readonly></td>
				<td><input type="text" name="uusinimi" id="uusinimi"></td>
				<td><input type="text" name="uusimerkkimalli" id="uusimerkkimalli"></td>
				<td><input type="text" name="uusipituus" id="uusipituus"></td>
				<td><input type="text" name="uusileveys" id="uusileveys"></td>
				<td><input type="text" name="uusihinta" id="uusihinta"></td>			
				<td><input type="submit" value="Tallenna" id="tallenna"></td>
			</tr>
		<tbody>
		</tbody>
	</table>
	<!--  <input type="hidden" name="tunnus" id="tunnus"> -->
</form>
<span id="ilmo"></span>
<script>
$(document).ready(function(){
	
	$("#tunnus").focus();
	
	//Haetaan muutettavan auton tiedot
	var id = requestURLParam("tunnus"); //Funktio löytyy scripts/main.js 	    
	$.getJSON({url:"HaeVene", data:{tunnus: id}, success:function(result){	//Tiedot kulkevat palvelimelta GET-metodilla JSON-muodossa	
		//$("#uusitunnus").val(result.tunnus);	
		$("#uusinimi").val(result.nimi);
		$("#uusimerkkimalli").val(result.merkkimalli);
		$("#uusipituus").val(result.pituus);		
		$("#uusileveys").val(result.leveys);	
		$("#uusihinta").val(result.hinta);
		$("#tunnus").val(result.tunnus);
    }});
		
	$("#takaisin").click(function(){
		document.location="listaaveneet.jsp";
	});
		
	$("#tiedot").validate({						
		rules: {
			
			//uusitunnus:  {
				//required: true,
				//minlength: 1				
			//},	
			uusinimi:  {
				required: true,
				minlength: 2				
			},
			uusimerkkimalli:  {
				required: true,
				minlength: 5
			},	
			uusipituus:  {
				required: true,
				minlength: 2
			},	
			uusileveys:  {
				required: true,
				minlength: 2
			},
			uusihinta:  {
				required: true,
				minlength: 4
			}	
		},
		messages: {
			//uusitunnus: {     
				//required: "Puuttuu",
				//minlength: "Liian lyhyt"			
			//},
			uusinimi: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			uusimerkkimalli: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			uusipituus: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			uusileveys: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			uusihinta: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			}
		},			
		submitHandler: function (form) {	
			vieTiedot();
		}		
	});   
});
function vieTiedot(){
	$.post({url:"MuutaVene", data:$("#tiedot").serialize(), success:function(result) {  //Tiedot kulkevat palvelimelle POST-metodilla
        if(result==0){
        	$("#ilmo").html("Tietojen päivitys epäonnistui.");
        }else if(result==1){
        	$("#ilmo").html("Tietojen päivitys onnistui.");
		}
    }});
}
</script>
</body>
</html>