window.onload = function() {    //Toutes les fonctions ci-dessous s'execute à chaque refresh
    getServerData("ws/impl/userimpl/getuser",result =>{
        var friendMap = new Array();    //destiné à contenir les maps des amis
        var friendName = new Array();   //destiné à contenir les noms des amis
        //on va simuler que result[0] est le current user
        for(var i = 0 ; i < Object.keys(result[0].friendList).length ; i++){
            friendName[i] = result[0].friendList[i].name  //Récupération du nom des amis
            for(var j = 0 ; j < Object.keys(result[0].friendList[i].personalMapList).length ; j++) {
                friendMap[j] = result[0].friendList[i].personalMapList[j].name //récupération des maps partagées
            }
        }
        var templateExample = _.template($('#templateAmi').html());
        var html = templateExample({
            "att1": friendName
        });
        for(var i = 0 ; i < Object.keys(result).length ; i++) {
            /*Mise en place d'une card avec bouton selon les infos obtenues ci-dessus
            * Pour l'instant non fonctionnel*/
            for (var j = 0; j < Object.keys(result[0].friendList[i].personalMapList).length; j++) {
                $("#friend-card").append('<div class="card" style="width: 18rem;"> <div class="card-body"> <h5 class="card-title">' + friendMap[j] + '</h5> <p class="card-text">par ' + friendName[i] + '</p> <a href="#" class="btn btn-primary">Zieuter</a></div> </div>');
            }
        }
    });
};
