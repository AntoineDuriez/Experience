//js function for GET : get data from the server
function getServerData(url, success){  
    $.ajax({
		type: "GET",
        dataType: "json",
        url: url
    }).done(success);
}

//Recherche d'implémentation pour les ws
//js function for POST : modify data which are already known
function postServerData(url, success){
	$.ajax({
		type: "POST",
		dataType: "json",
		url: url,
	}).done(success);
}

//js function for PUT : add new data
function putServerData(url, success){
	$.ajax({
		type: "PUT",
		dataType: "json",
		url: url,
	}).done(success);
}

//js function for DELETE : delete data
function deleteServerData(url, success){
	$.ajax({
		type: "DELETE",
		dataType: "json",
		url: url
	}).done(success);
}
//Fonction de test des appels webservices
$(function(){
	//Boutons pour WS de map
//**********************************************************************************************************************
	$("#buttonGetMap").click(function(){
		getServerData("ws/impl/mapimpl/getmap", result =>{
			for(var i = 0 ; i < Object.keys(result).length ; i++){	//get map
				var templateExample = _.template($('#templateGet').html());
				var html = templateExample({
					"attribute": JSON.stringify(result[i]),	//éléments de chaques map
				});
				$("#result").append(html);
			}
		});
	});
	$("#buttonAddMap").click(function(){
		putServerData("ws/impl/mapimpl/addmap", result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": result
			});
			$("#result").append(html);
		});
	});
	$("#buttonModifyMap").click(function(){
		postServerData("ws/impl/mapimpl/modifymap",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": result
			});
			$("#result").append(html);
		});
	});
	$("#buttonDeleteMap").click(function(){
		deleteServerData("ws/impl/mapimpl/deletemap",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": result
			});
			$("#result").append(html);
		});
	});
	$("#buttonShareMap").click(function(){
		postServerData("ws/impl/mapimpl/sharemap",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": result
			});
			$("#result").append(html);
		});
	});
	$("#buttonGetMyMap").click(function(){
		postServerData("ws/impl/mapimpl/getmymap",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": JSON.stringify(result)
			});
			$("#result").append(html);
		});
	});
//**********************************************************************************************************************
	//Bouton pour WS de user
//**********************************************************************************************************************
	$("#buttonGetUser").click(function(){
		getServerData("ws/impl/userimpl/getuser",result =>{
			for(var i = 0 ; i < Object.keys(result).length ; i++){
				var templateExample = _.template($('#templateGet').html());
				var html = templateExample({
					"attribute": JSON.stringify(result[i])
				});
				$("#result").append(html);
			}
		});
	});
	$("#buttonSearchFriend").click(function(){
		postServerData("ws/impl/userimpl/searchfriend",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": result
			});
			$("#result").append(html);
		});
	});
	$("#buttonAddFriend").click(function(){
		putServerData("ws/impl/userimpl/addfriend",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": JSON.stringify(result)
			});
			$("#result").append(html);
		});
	});
	$("#buttonDeleteFriend").click(function(){
		deleteServerData("ws/impl/userimpl/deletefriend",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": JSON.stringify(result)
			});
			$("#result").append(html);
		});
	});
//**********************************************************************************************************************
	//Bouton pour WS marker
//**********************************************************************************************************************
	$("#buttonGetMarker").click(function(){
		getServerData("ws/impl/markerimpl/getmarker",result =>{
			for(var i = 0 ; i < Object.keys(result).length ; i++){
				var templateExample = _.template($('#templateGet').html());
				var html = templateExample({
					"attribute": JSON.stringify(result[i])
				});
				$("#result").append(html);
			}
		});
	});
	$("#buttonAddMarker").click(function(){
		putServerData("ws/impl/markerimpl/addmarker",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": JSON.stringify(result)
			});
			$("#result").append(html);
		});
	});
	$("#buttonModifyMarker").click(function(){
		postServerData("ws/impl/markerimpl/modifymarker",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": JSON.stringify(result)
			});
			$("#result").append(html);
		});
	});
	$("#buttonDeleteMarker").click(function(){
		deleteServerData("ws/impl/markerimpl/deletemarker",result =>{
			var templateExample = _.template($('#templateExample').html());
			var html = templateExample({
				"attribute": JSON.stringify(result)
			});
			$("#result").append(html);
		});
	});
//**********************************************************************************************************************
	//Bouton pour WS category
//**********************************************************************************************************************
	$("#buttonGetCategory").click(function(){
		getServerData("ws/impl/categoryimpl/getcategory",result =>{
			for(var i = 0 ; i < Object.keys(result).length ; i++){
				var templateExample = _.template($('#templateGet').html());
				var html = templateExample({
					"attribute": JSON.stringify(result[i])
				});
				$("#result").append(html);
			}
		});
	});
//**********************************************************************************************************************
	//Bouton pour WS message
//**********************************************************************************************************************
	$("#buttonGetMessage").click(function(){
		getServerData("ws/impl/messageimpl/getmessage",result =>{
			for(var i = 0 ; i < Object.keys(result).length ; i++){
				var templateExample = _.template($('#templateGet').html());
				var html = templateExample({
					"attribute": JSON.stringify(result[i])
				});
				$("#result").append(html);
			}
		});
	});
//**********************************************************************************************************************
	//Bouton pour WS picture
//**********************************************************************************************************************
	$("#buttonGetPicture").click(function(){
		getServerData("ws/impl/pictureimpl/getpicture",result =>{
			for(var i = 0 ; i < Object.keys(result).length ; i++){
				var templateExample = _.template($('#templateGet').html());
				var html = templateExample({
					"attribute": JSON.stringify(result[i])
				});
				$("#result").append(html);
			}
		});
	});
//**********************************************************************************************************************
});
