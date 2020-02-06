
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<form id="tiedot">
<table>
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
			<th>Hinta</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="text" name="tunnus" id="tunnus"></td>
			<td><input type="text" name="nimi" id="nimi"></td>
			<td><input type="text" name="merkkimalli" id="merkkimalli"></td>
			<td><input type="text" name="pituus" id="pituus"></td>
			<td><input type="text" name="leveys" id="leveys"></td> 
			<td><input type="text" name="hinta" id="hinta"></td>
			<td><input type="submit" id="tallenna" value="Lisää"></td>
		</tr>
	</tbody>
</table>
</form>
<span id="ilmo"></span>
<script>
$(document).ready(function(){
		
	$("#takaisin").click(function(){
		document.location="listaaveneet.jsp";
	});
		
	$("#tiedot").validate({						
		rules: {
			tunnus:  {
				required: true,
				minlength: 1,
				number: true
			},	
			nimi:  {
				required: true,
				minlength: 2				
			},
			merkkimalli:  {
				required: true,
				minlength: 3
			},	
			pituus:  {
				required: true,
				minlength: 2
			},	
			leveys:  {
				required: true,
				minlength: 2,
				number: true
			},	
			hinta:  {
				required: true,
				minlength: 2,
				number: true
			}
		},
		messages: {
			tunnus: {     
				required: "Puuttuu",
				minlength: "Liian lyhyt",
				number: "Ei kelpaa"
			},
			nimi: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			merkkimalli: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			pituus: {
				required: "Puuttuu",
				minlength: "Liian lyhyt",
				number: "Ei kelpaa"
			},
			leveys: {
				required: "Puuttuu",
				minlength: "Liian lyhyt",
				number: "Ei kelpaa"
			},
			hinta: {
				required: "Puuttuu",
				minlength: "Liian lyhyt",
				number: "Ei kelpaa"
			}
		},			
		submitHandler: function(form) {	
			lisaaTiedot();
		}		
	});   
	$("#tunnus").focus();
});
function lisaaTiedot(){
	$.post({url:"LisaaVene", data:$("#tiedot").serialize(), success:function(result) {  //Tiedot kulkevat palvelimelle POST-metodilla
        if(result==0){
        	$("#ilmo").html("Veneen lisääminen epäonnistui.");
        }else if(result==1){
        	$("#ilmo").html("Veneen lisääminen onnistui.");
        	$("#tunnus, #nimi, #merkkimalli, #pituus, #leveys, #hinta").val("");
		}else if (result==2){
			$("#ilmo").html("Tunnus on jo käytössä."); 
		}
    }});	
}
</script>
</body>
</html>