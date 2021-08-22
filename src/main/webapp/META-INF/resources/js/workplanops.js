function workloadSum(dict,values,language){
	var sep = ".";
	if(language=="es")
		sep = ",";

	var hours = 0;
	var mins = 0;
	for(i=0;i<values.length;i++){
		indice = values[i];
		var splited = dict[indice].workload.toString().split("\.");
		var h = splited[0];
		var m = splited[1];
		if(m == undefined)
			m = '00';
		else if(m.length==1)
			m = m + '0';
		hours += parseInt(h);
		mins += parseInt(m);
	}
	hours = hours + Math.floor(mins/60);
	mins = mins % 60;
	var stHours = hours.toString();
	var stMins = mins.toString();
	if(stMins.length==1)
		stMins = '0' + stMins;
	
	document.getElementById("workload").value=stHours+sep+stMins;

}

function taskCount(dict,values){
	var public = 0;
	var private = 0;
	
	for(i=0;i<values.length;i++){
		var indice = values[i];
		if(dict[indice].isPublic == true)
			public+=1;
		else
			private+=1;
	}
	document.getElementById("publica").textContent = public;
	document.getElementById("privada").textContent = private;
}

function dateSuggestions(dict,values,language){
	var pattern = "";
	var sugerencia = "";
	if(language=="es"){
		pattern = "DD/MM/YYYY HH:mm";
		sugerencia = "sugerencia";
		}
	else{
		pattern = "YYYY/MM/DD HH:mm";
		sugerencia = "suggestion";
		}
	

	if(values.length == 0){
		document.getElementById("startSuggestion").textContent = "";
		document.getElementById("endSuggestion").textContent = "";
	}
	else{
		var startSuggestion = null;
		var endSuggestion = null;
		
		for(i=0;i<values.length;i++){
			indice = values[i];
			if(startSuggestion == null || dict[indice].executionStart.diff(startSuggestion)<0)
				startSuggestion = dict[indice].executionStart.clone();
			if(endSuggestion == null || dict[indice].executionEnd.diff(endSuggestion)>0)
				endSuggestion = dict[indice].executionEnd.clone();	
		}
		startSuggestion = startSuggestion.subtract(1,'days').hour(8).minute(0);
		endSuggestion = endSuggestion.add(1,'days').hour(17).minute(0);
		document.getElementById("startSuggestion").textContent = "("+sugerencia+": "+startSuggestion.format(pattern)+")";
		document.getElementById("endSuggestion").textContent = "("+sugerencia+": "+endSuggestion.format(pattern)+")";
	}


}