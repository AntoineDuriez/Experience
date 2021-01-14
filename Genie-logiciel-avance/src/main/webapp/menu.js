window.onload = function() {    //Toutes les fonctions ci-dessous s'execute à chaque refresh
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++Affichage la première map personnelle sauvegardée dans la bdd, sinon pas de map,
    //++++++++++informe l'utilisateur avec un console log
    //$$$$$$$$$$ Il va falloir vérifier que l'implémentation bdd tient la route$$$$$$$$$$
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   getServerData("ws/impl/mapimpl/getmap", result => {
       //Variables
        var tabName;
        var tabLat;
        var tabLon;
        var tabMarkerList = new Array();
       var map;
       //===============================================================================================================
       //==========ELEMENT BOUCHON DE TEST POUR IMPLEMENTATION MARKER DANS MAP==========
       // C'est un test d'affichage pour savoir si les fonctions de créations de markers et d'event fonctionnent bien avec le markerList de chaque
       //map. Les tests ci-dessous ne vont fonctionner que pour la première map du menu déroulant et se situe à Paris
       //===============================================================================================================
       //========== Un marker
       var c1 = {
           "name": "Café de Boony",
           "description": "Un café tenu pour Boony",
           "category": "Loisir",
           "picture": [],
           "latitude": 48.8395413,
           "longitude": 2.336299,
           "messageList":["J'y ai bu un café",
               "Très joli"],
       };
       var dateControl1 = {"start": "",
           "end": ""};
       //========== Un event
       var c2 = {
           "name": "Concert de Boony",
           "description": "Boony chante à Bercy",
           "category": "Concert",
           "picture": [],
           "latitude": 48.8351101,
           "longitude": 2.3824298,
           "messageList":["Seulement 5 euros la place !"]
       };
       var dateControl2 = {"start": "2020-05-06",
           "end": "2020-05-26"};
       var geo1 = {
           "contentJson": c1,
           "date": dateControl1
       };
       var geo2 = {
           "contentJson": c2,
           "date": dateControl2
       };
       //========== Ajout manuel
       tabMarkerList.push(geo1);
       tabMarkerList.push(geo2);
       //===============================================================================================================
       //===============================================================================================================
       if(Object.keys(result).length != 0){
            tabName = result[0].name;
            tabLat = result[0].latitudeDeparture;
            tabLon = result[0].longitudeDeparture;
            //tabMarkerList = result[0].markerList; //========== A remettre dès que le bouchon sera enlevé ==========
            map = changeMyCurrentMap(tabName, tabLat, tabLon, tabMarkerList); //On définit la current map du load
        }else{
            console.log("Pas de map, Veuillez en créer une"); //Indique qu'il n'y a pas de map dans la bdd
                                                             // On pourrait le remplacer par un alert ou un conteneur bootstrap
        }
    });
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    allMyMaps(); // récupère toutes les maps enregistrées dans la bdd et les affiches dans le drop-down menu de la navbar
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++ On démarre ici la gestion par bouton des maps++++++++++
    //$$$$$$$$$$ Certains vont avoir besoin d'implémentation bdd, on va détailler les besoins au cas par cas des boutons
    //++++++++++ Bouton de la suppression de map ++++++++++
    $(".my-delete-map-button").click(function(){ //Suppression de map au clic
        //$$$$$$$$$$ Un GET ici, il faut vérifier le fonctionnement
        //$$$$$$$$$$ c'est un getmap qu'on utilise ici rien que pour avoir les noms des maps
        getServerData("ws/impl/mapimpl/getmap", result => {
            $('#modalMapDeleter').modal('show');    //Affichage de la modal de suppression
            var tabName = new Array();
            var tabMap = new Array();
            var mapToDelete;
            var availableToDelete = false;
            document.getElementById("finisherMapDeleter").onclick = function() { //au clic
                for(var i = 0; i < Object.keys(result).length; i++) {
                    //On compare le nom de la map à delete avec le nom de toutes les maps de l'utilisateur
                    if(document.getElementById("mapToDelete").value == result[i].name){
                        mapToDelete = result[i].name;   //La map à delete existe
                        availableToDelete = true;   //active le droit d'appel à deleteServerData
                    }
                }
                if(availableToDelete){
                    //delete de la map sélectionnée
                    //$$$$$$$$$$ un DELETE ici
                    //$$$$$$$$$$ on se sert du nom de la map pour la delete, si 2 maps ont le même nom ça va être
                    //$$$$$$$$$$ un problème, il faut le résoudre dans les DAO
                    deleteServerData("ws/impl/mapimpl/deletemap/"+mapToDelete, result=>{
                        console.log(mapToDelete);
                    });
                }else{
                    // Ne correspond à aucunes map de la bdd, pas possible de delete
                    // On pourrait ici aussi renvoyer une alert ou un conteneur Bootstrap
                    console.log("Delete is impossible : no data with this name");
                }
            }
        });
    });
    //++++++++++ Fin du code du bouton de suppression de map ++++++++++
    //++++++++++ Création des nouvelles maps ++++++++++
    $(".my-new-map-button").click(function(){ //Bouton pour appeler la modal de création de nouvelles map
        $('#modalMapBuilder').modal('show');    //Affichage de la modal
    });
    document.getElementById("finisherMap").onclick = function(){ //Quand on a finit de créer la map, on l'envoie vers le serveur
        var name = document.getElementById("nameMap").value; //Nom de la map à stocker sur la base de données
        var physicalAddress = document.getElementById("nameCityRef").value; //Nom de la ville d'ancrage de la map
        //console.log(physicalAddress); //test de vérification
        geocoder = new L.Control.Geocoder.Nominatim();  //On géolocalise la ville d'ancrage pour s'afficher dessus
        geocoder.geocode(physicalAddress, function(results) {
            latLng= new L.LatLng(results[0].center.lat, results[0].center.lng); //Position d'ancrage par latitude/longitude
            //$$$$$$$$$$ un PUT, vérifier le fonctionnement
            //$$$$$$$$$$ Une map est d'abord créée sans markers dessus, il faut ajouter les markers au fur et à mesure
            //$$$$$$$$$$ dans le markerList de la map
            //$$$$$$$$$$ Encore une fois, 2 maps peuvent avoir le même nom : problème ?
            putServerData("ws/impl/mapimpl/addmap/"+name+"/"+latLng.lat+"/"+latLng.lng, result =>{ //Appel à PUT pour aller vers vers le addMap de MapRessourceImplStub.java
                console.log(result); //test de vérification
            });
        });
    };
};  //!!!!!!! SORTIE DU ONLOAD !!!!!!!
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//++++++++++ Fonctions hors onload
//========== Gestion de l'affichage des différentes maps de l'utilisateur
//========== Il s'agit de la fonction-coeur de l'appli puisqu'elle gère la current map et tous les markers qui y sont
//========== associés + Elle est extrèmement velue
function changeMyCurrentMap(name, lat, lon, markerList){
    $("#mainMap").remove(); //On remove le conteneur de map existant (sinon on ne peut pas le remplir)
    $("#mapGenerator").append('<div id="mainMap" style="height: 96.5%;\n' + //On remet un conteneur vide
        '\twidth: 99.5vw;\n' +
        '\tz-index: 5;"></div>');
    //On crée la map avec les coordonnées voulues
    var map = L.map("mainMap").setView([lat, lon], 13);
    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1,
        accessToken: 'pk.eyJ1IjoiYWt1cmF3biIsImEiOiJjazhpdzRldncwOHAwM2RvNWNkMjJyejFnIn0.bDaWCw4cIn2OL91W1nu6Wg'
    }).addTo(map); //on l'ajoute dans le conteneur
    //barre de recherche d'adresse
    console.log('SALUTATIONS');
    L.Control.geocoder().addTo(map);
    console.log(lat);
    console.log(lon);
    console.log(markerList);
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++ Gestion des markers de markerList
    //++++++++++ Ne fonctione pour l'instant qu'avec les bouchons puisque les markerList de la bdd sont vides
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    if(markerList == null){
        markerList = new Array();   //si null, on va en faire un Array et le remplir, il faudra le sauver dans la bdd plus tard
    }else{
        //si non null, on affichera chaque markers de markerList sur la current map
        var marker;
        for(var i = 0 ; i < Object.keys(markerList).length ; i++){
            //console.log(markerList[i]);
            var geojsonFeatureCreator = {
                "type": "Feature",
                "properties":{
                    "contentJson": markerList[i].contentJson,
                    "date": markerList[i].date
                },
                "geometry": {
                    "type": "Point",
                    "coordinates": [markerList[i].contentJson.latitude, markerList[i].contentJson.longitude]
                }
            };
            L.geoJson(geojsonFeatureCreator, {
                pointToLayer: function(feature, latlng){
                    if(markerList[i].date.start != ""){
                        marker = createEvent(markerList[i].contentJson, marker, markerList[i].date);
                    }else{
                        marker = createMarker(markerList[i].contentJson, marker);
                    }
                    console.log(marker);
                    marker = marker.on("popupopen", onPopupOpen);   //action à réaliser sur le current marker
                    return marker;
                }
            }).addTo(map);  //ajout à la current map
        }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //==================================================================================================================
    //========== Bouton de modification de la map
    //$$$$$$$$$$ Contient DELETE et PUT pour simuler POST, il faudra le modifier
    $(".my-modify-map-button").click(function(){    //Bouton pour modifier le nom d'une map, doit garder tous les markers
        console.log(name);
        document.getElementById("nameMapModif").setAttribute('value',name); //ancien nom
        $('#modalMapModifier').modal('show');
        document.getElementById("finisherMapModif").onclick = function() {
            var newName = document.getElementById("nameMapModif").value;    //nouveau nom
            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            //$$$$$$$$$$ Un DELETE, comme expliqué plus haut, le POST est plus approprié
            deleteServerData("ws/impl/mapimpl/deletemap/"+name, result=>{   //Retrait de la map avec l'ancien nom
                console.log(name);
            });
            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            //$$$$$$$$$$ Le PUT
            //$$$$$$$$$$ Même quand on aura remplacé par le POST il va quand même falloir faire attention au cas des 2 maps du même nom
            putServerData("ws/impl/mapimpl/addmap/"+newName+"/"+lat+"/"+lon, result =>{ //Nouvel ajout avec le nouveau nom, conserve ville d'ancrage et markers
                changeMyCurrentMap(newName, lat, lon, markerList); // On modifie la current map, il faut donc changer son nom
            });
            //==========================================================================================================
            //==========================================================================================================
        }
    });
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++ Mise en place de la gestion marker
    //Création de marker : les marqueurs sont crées pour la current map, celle qui occupe le conteneur à l'instant où ils sont créés
    var popup = L.popup();  //création d'un popup
    //==================================================================================================================
    //========== Gestion du clic sur les markers pour détecter le current marker
    //========== 9a va être le siège des interactions entre l'utilisateur et les markers
    function onMapClick(e) {
        var container = L.DomUtil.create('div');
        var btn = L.DomUtil.create('button', '', container);
        btn.setAttribute('type', 'button');
        btn.classList.add('btn', 'marker_btn');
        btn.innerHTML = "Create marker here";   //Popup avec un bouton à l'endroit où on clique
        popup
            .setLatLng(e.latlng)
            .setContent(btn)
            .openOn(map);
        //Conteneur Json des attributs caractéristiques du marqueur
        var c = {
            "name": "",
            "description": "",
            "category": "",
            "picture": [],
            "latitude": 0,
            "longitude": 0,
            "messageList":[]
        };
        var dateControl = {"start": "",
            "end": ""};
        //Conteneur geoJson, pour faciliter l'implémentation géographique, contient le Json ci-dessus
        var geojsonFeature = {
            "type": "Feature",
            "properties":{
                "contentJson": c,    //les attributs Json du marqueur
                "date": dateControl
            },
            "geometry": {
                "type": "Point",
                "coordinates": [e.latlng.lat, e.latlng.lng]
            }
        };
        L.DomEvent.on(btn, 'click', () => { //lorsqu'on clique sur le bouton pour créer un marqueur
            $('#modalMarker').modal('show');    //affichage de la modal de création
            document.getElementById("finisher").onclick = function() {
                //Attribution des valeurs dans le Json
                c.name = document.getElementById("name").value;
                c.description = document.getElementById("descrip").value;
                c.category = document.getElementById("category").value;
                c.latitude = e.latlng.lat;
                c.longitude = e.latlng.lng;
                //on regarde si le marker est un event, si oui on traite les dates
                if(document.getElementById("event").checked){
                    dateControl.start = document.getElementById("start").value;
                    dateControl.end = document.getElementById("end").value;
                }
                console.log(geojsonFeature.properties.dateStart);
                var marker;     //création du marqueur
                //implantation du marqueur grâce à geoJson
                L.geoJson(geojsonFeature, {
                    pointToLayer: function(feature, latlng){
                        if(document.getElementById("event").checked){
                            marker = createEvent(c, marker, dateControl);
                            //$$$$$$$$$$ Ici un PUT, pour simplifier, j'ai fais comme si le marker était un event
                            //$$$$$$$$$$ sans date et inversement, addmarker gère donc event ET marker
                            //$$$$$$$$$$ faut-il build un addevent spécifique ?
                            putServerData("ws/impl/markerimpl/addmarker/"+c.name+"/"+c.description+"/"+c.longitude+"/"+c.latitude+"/"+c.category+"/"
                                +dateControl.start+"/"+dateControl.end, result =>{
                                markerList.push(marker);
                                console.log(markerList);
                            });
                        }else{
                            //si c'est un marker
                            marker = createMarker(c, marker);
                            //$$$$$$$$$$ ici un PUT
                            //$$$$$$$$$$ même questionnement que ci-dessus
                            //$$$$$$$$$$ on note aussi que 2 markers peuvent avoir le même nom, c'est moins un problème que
                            //$$$$$$$$$$ pour les maps car pour supprimer un marker on utilise geoJson et on cible le marker en question
                            putServerData("ws/impl/markerimpl/addmarker/"+c.name+"/"+c.description+"/"+c.longitude+"/"+c.latitude+"/"+c.category+"/"
                                +null+"/"+null, result =>{
                                markerList.push(marker);
                                console.log(markerList);
                            });
                        }
                        console.log(geojsonFeature);
                        marker = marker.on("popupopen", onPopupOpen);   //action à réaliser sur le current marker
                        return marker;
                    }
                }).addTo(map);  //ajout à la current map
            };
            map.closePopup(); //tous les popups se ferment
        });
    }
    map.on('click', onMapClick); //execution de la fonction au clic

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++ Fonction de gestion des markers par boutons
    function onPopupOpen() {
        var tempMarker = this;  //current marker
        //==============================================================================================================
        //========== Suppression d'un marker
        $(".marker-delete-button").click(function () {  //Fonction de destruction du current marker
            //$$$$$$$$$$ un DELETE
            //$$$$$$$$$$ normalement fonctionnel, à vérifier
            deleteServerData("ws/impl/markerimpl/deletemarker/"+tempMarker.feature.properties.contentJson.name, result =>{
                map.removeLayer(tempMarker);
                markerList.splice(markerList.indexOf(tempMarker), 1);
                console.log(markerList);
            });
            console.log(markerList);
        });
        //==============================================================================================================
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++ Affichage des messages contenus sur un marker
        //$$$$$$$$$$ on doit normalement récupérer la liste de message depuis la bdd dans le marker
        $(".message-shower-button").click(function(){   //Fonction d'affichages des messages du current marker
            console.log(tempMarker.feature.properties.contentJson.messageList); //vérification
            console.log((tempMarker.feature.properties.contentJson.messageList).length);    //vérification
            //On crée le carousel d'affichage à chaque clic du bouton, il est delete à chaque fermeture : Permet d'éviter chevauchements et doublons
            $("#carouselMessageControls").append('<div class="carousel-inner" id="listOfMessage"></div>\n' +
                '                    <a class="carousel-control-prev" id="previous" href="#carouselMessageControls" role="button" data-slide="prev" style="color: #FA9A6C;">\n' +
                '                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>\n' +
                '                        <span class="sr-only">Previous</span>\n' +
                '                    </a>\n' +
                '                    <a class="carousel-control-next" id="next" href="#carouselMessageControls" role="button" data-slide="next" style="color: #FA9A6C;">\n' +
                '                        <span class="carousel-control-next-icon" aria-hidden="true"></span>\n' +
                '                        <span class="sr-only">Next</span>\n' +
                '                    </a>');
            //Premier message de la liste
            $("#listOfMessage").append('<div class="carousel-item active">'+ tempMarker.feature.properties.contentJson.messageList[0] +'</div>');
            for(var i = 1 ; i < Object.keys(tempMarker.feature.properties.contentJson.messageList).length ; i++){
                //tous les autres messages de la liste
                $("#listOfMessage").append('<div class="carousel-item">'+ tempMarker.feature.properties.contentJson.messageList[i] +'</div>');
            }
            //on delete tout le carousel lorsqu'on clique sur le bouton de fermeture
            document.getElementById("messageCloser").onclick = function(){
                $("#listOfMessage").remove();
                $("#previous").remove();
                $("#next").remove();
            }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //==============================================================================================================
        //========== Pareil pour les pictures
        $(".picture-shower-button").click(function(){   //Fonction d'affichages des pictures du current marker
            //On crée le carousel d'affichage à chaque clic du bouton, il est delete à chaque fermeture : Permet d'éviter chevauchements et doublons
            $("#carouselPictureControls").append('<div class="carousel-inner" id="listOfPicture"></div>\n' +
                '                    <a class="carousel-control-prev" id="previous" href="#carouselPictureControls" role="button" data-slide="prev" style="color: #FA9A6C;">\n' +
                '                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>\n' +
                '                        <span class="sr-only">Previous</span>\n' +
                '                    </a>\n' +
                '                    <a class="carousel-control-next" id="next" href="#carouselPictureControls" role="button" data-slide="next" style="color: #FA9A6C;">\n' +
                '                        <span class="carousel-control-next-icon" aria-hidden="true"></span>\n' +
                '                        <span class="sr-only">Next</span>\n' +
                '                    </a>');
            //Première picture de la liste
            $("#listOfPicture").append('<div class="carousel-item active">' +
                    '<img src="'+tempMarker.feature.properties.contentJson.picture[0]+'" class="d-block w-100">' +
                '</div>');
            for(var i = 1 ; i < Object.keys(tempMarker.feature.properties.contentJson.picture).length ; i++){
                //toutes les autres pictures de la liste
                $("#listOfPicture").append('<div class="carousel-item">' +
                    '<img src="'+tempMarker.feature.properties.contentJson.picture[i]+'" class="d-block w-100">' +
                    '</div>');
            }
            //on delete tout le carousel lorsqu'on clique sur le bouton de fermeture
            document.getElementById("pictureCloser").onclick = function(){
                $("#listOfPicture").remove();
                $("#previous").remove();
                $("#next").remove();
            }
        });
        //==============================================================================================================
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++ Modification de marker
        $(".marker-modify-button").click(function(){    //Pour la modification de marker uniquement
            document.getElementById("nameModif").setAttribute('value',tempMarker.feature.properties.contentJson.name);
            $('#descripModif').val(tempMarker.feature.properties.contentJson.description);
            document.getElementById("categoryModif").setAttribute('value',tempMarker.feature.properties.contentJson.category);
            $('#modalModificationMarker').modal('show');    //affichage de la modal de modification
            //on supprime l'ancien marker et on en recrée un avec les nouvelles infos au même endroit
            var geojsonFeatureModifier = {
                "type": "Feature",
                "properties":{
                    "contentJson": tempMarker.feature.properties.contentJson,
                    "date": tempMarker.feature.properties.date
                },
                "geometry": {
                    "type": "Point",
                    "coordinates": [tempMarker.feature.properties.contentJson.latitude, tempMarker.feature.properties.contentJson.longitude]
                }
            };
            document.getElementById("finisherModif").onclick = function() { //bouton de fin de modification
                map.removeLayer(tempMarker);
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ DELETE suivi d'un PUT : à remplacer par un POST
                deleteServerData("ws/impl/markerimpl/deletemarker/"+tempMarker.feature.properties.contentJson.name, result =>{
                    markerList.splice(markerList.indexOf(tempMarker), 1);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //Attribution des nouvelles valeurs
                tempMarker.feature.properties.contentJson.name = document.getElementById("nameModif").value;
                tempMarker.feature.properties.contentJson.description = document.getElementById("descripModif").value;
                tempMarker.feature.properties.contentJson.category = document.getElementById("categoryModif").value;
                //création du marker modifié
                L.geoJson(geojsonFeatureModifier, {
                    pointToLayer: function(feature, latlng){
                        tempMarker = createMarker(tempMarker.feature.properties.contentJson, tempMarker);
                        tempMarker = tempMarker.on("popupopen", onPopupOpen);
                        return tempMarker;
                    }
                }).addTo(map);  //Ajout à la map courante
                console.log(tempMarker);
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ le PUT en question
                putServerData("ws/impl/markerimpl/addmarker/"+tempMarker.feature.properties.contentJson.name+"/"
                    +tempMarker.feature.properties.contentJson.description+"/"
                    +tempMarker.feature.properties.contentJson.longitude+"/"
                    +tempMarker.feature.properties.contentJson.latitude+"/"
                    +tempMarker.feature.properties.contentJson.category+"/"
                    + null +"/"+ null, result=>{    //on PUT un marker donc pas de date
                    markerList.push(tempMarker);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //==============================================================================================================
        //========== Modification d'un event
        $(".event-modify-button").click(function(){    //modification des events
            document.getElementById("nameModifEvent").setAttribute('value',tempMarker.feature.properties.contentJson.name);
            $('#descripModifEvent').val(tempMarker.feature.properties.contentJson.description);
            document.getElementById("categoryModifEvent").setAttribute('value',tempMarker.feature.properties.contentJson.category);
            document.getElementById("startModifEvent").setAttribute('value',tempMarker.feature.properties.date.start);
            document.getElementById("endModifEvent").setAttribute('value',tempMarker.feature.properties.date.end);
            $('#modalModificationEvent').modal('show');    //affichage de la modal de modification
            //on supprime l'ancien marker et on en recrée un avec les nouvelles infos au même endroit
            var geojsonFeatureModifier = {
                "type": "Feature",
                "properties":{
                    "contentJson": tempMarker.feature.properties.contentJson,
                    "date": tempMarker.feature.properties.date
                },
                "geometry": {
                    "type": "Point",
                    "coordinates": [tempMarker.feature.properties.contentJson.latitude, tempMarker.feature.properties.contentJson.longitude]
                }
            };
            document.getElementById("finisherModifEvent").onclick = function() { //bouton de fin de modification
                map.removeLayer(tempMarker);
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ un DELETE suivi d'un PUT : à remplacer par un POST
                deleteServerData("ws/impl/markerimpl/deletemarker/"+tempMarker.feature.properties.contentJson.name, result =>{
                    markerList.splice(markerList.indexOf(tempMarker), 1);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //Attribution des nouvelles valeurs
                tempMarker.feature.properties.contentJson.name = document.getElementById("nameModifEvent").value;
                tempMarker.feature.properties.contentJson.description = document.getElementById("descripModifEvent").value;
                tempMarker.feature.properties.contentJson.category = document.getElementById("categoryModifEvent").value;
                tempMarker.feature.properties.date.start = document.getElementById("startModifEvent").value;
                tempMarker.feature.properties.date.end = document.getElementById("endModifEvent").value;
                //création du marker modifié
                L.geoJson(geojsonFeatureModifier, {
                    pointToLayer: function(feature, latlng){
                        tempMarker = createEvent(tempMarker.feature.properties.contentJson, tempMarker,tempMarker.feature.properties.date);
                        tempMarker = tempMarker.on("popupopen", onPopupOpen);
                        return tempMarker;
                    }
                }).addTo(map);  //Ajout à la map courante
                console.log(tempMarker);
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ le PUT
                putServerData("ws/impl/markerimpl/addmarker/"+tempMarker.feature.properties.contentJson.name+"/"
                    +tempMarker.feature.properties.contentJson.description+"/"
                    +tempMarker.feature.properties.contentJson.longitude+"/"
                    +tempMarker.feature.properties.contentJson.latitude+"/"
                    +tempMarker.feature.properties.contentJson.category+"/"
                    +tempMarker.feature.properties.date.start+"/"       //ici c'est un event, donc il y a des dates
                    +tempMarker.feature.properties.date.end, result=>{
                    markerList.push(tempMarker);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            }
        });
        //==============================================================================================================
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++ Ajout d'un message
        $(".message-adder-button").click(function () {  //Ajout de message sur un marker/event
            $('#modalMessageWriter').modal('show'); //Affichage de la modal de création de message
            //Même système qu'en haut, on considère l'ajout d'un message comme la modification du marker
            map.removeLayer(tempMarker);
            var geojsonFeatureMessager = {
                "type": "Feature",
                "properties":{
                    "contentJson": tempMarker.feature.properties.contentJson,
                    "date": tempMarker.feature.properties.date
                },
                "geometry": {
                    "type": "Point",
                    "coordinates": [tempMarker.feature.properties.contentJson.latitude, tempMarker.feature.properties.contentJson.longitude]
                }
            };
            document.getElementById("finisherMessage").onclick = function(){
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ un DELETE suivi d'un PUT : à remplacer par un POST
                deleteServerData("ws/impl/markerimpl/deletemarker/"+tempMarker.feature.properties.contentJson.name, result=>{
                    markerList.splice(markerList.indexOf(tempMarker), 1);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                tempMarker.feature.properties.contentJson.messageList.push(document.getElementById("messager").value);  //Ajout du nouveau message à la liste
                //création du marker modifié
                L.geoJson(geojsonFeatureMessager, {
                    pointToLayer: function(feature, latlng){
                        if(document.getElementById("event").checked){
                            tempMarker = createEvent(tempMarker.feature.properties.contentJson, tempMarker, tempMarker.feature.properties.date);
                        }else{
                            //console.log(tempMarker);
                            tempMarker.feature.properties.date.start = null;
                            tempMarker.feature.properties.date.end = null;
                            tempMarker = createMarker(tempMarker.feature.properties.contentJson, tempMarker);
                        }
                        tempMarker = tempMarker.on("popupopen", onPopupOpen);
                        return tempMarker;
                    }
                }).addTo(map);  //Ajout à la map courante
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ le PUT
                putServerData("ws/impl/markerimpl/addmarker/"+tempMarker.feature.properties.contentJson.name+"/"
                    +tempMarker.feature.properties.contentJson.description+"/"
                    +tempMarker.feature.properties.contentJson.longitude+"/"
                    +tempMarker.feature.properties.contentJson.latitude+"/"
                    +tempMarker.feature.properties.contentJson.category+"/"
                    +tempMarker.feature.properties.date.start+"/"
                    +tempMarker.feature.properties.date.end, result=>{
                    markerList.push(tempMarker);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //==============================================================================================================
        //========== Ajout d'une picture sur le marker
        $(".picture-adder-button").click(function () {  //Ajout de picture sur un marker/event
            $('#modalPictureImplementer').modal('show'); //Affichage de la modal de création de picture
            map.removeLayer(tempMarker);
            var geojsonFeatureMessager = {
                "type": "Feature",
                "properties":{
                    "contentJson": tempMarker.feature.properties.contentJson,
                    "date": tempMarker.feature.properties.date
                },
                "geometry": {
                    "type": "Point",
                    "coordinates": [tempMarker.feature.properties.contentJson.latitude, tempMarker.feature.properties.contentJson.longitude]
                }
            };
            //++++++++++Récupération + blob picture++++++++++
            document.getElementById("finisherPicture").onclick = function() {
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ un DELETE suivi d'un PUT : à remplacer par un POST
                deleteServerData("ws/impl/markerimpl/deletemarker/" + tempMarker.feature.properties.contentJson.name, result => {
                    markerList.splice(markerList.indexOf(tempMarker), 1);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                var preview = document.querySelector('img');
                var file = document.querySelector('input[type=file]').files[0];
                var reader = new FileReader();
                var U = URL.createObjectURL(file);
                //++++++++++++++++++++++++++++++++++++++++++++++
                console.log(tempMarker.feature.properties.contentJson.picture);
                tempMarker.feature.properties.contentJson.picture.push(U);  //Ajout du nouveau message à la liste
                //création du marker modifié
                L.geoJson(geojsonFeatureMessager, {
                    pointToLayer: function (feature, latlng) {
                        if (document.getElementById("event").checked) {
                            tempMarker = createEvent(tempMarker.feature.properties.contentJson, tempMarker, tempMarker.feature.properties.date);
                        } else {
                            tempMarker.feature.properties.date.start = null;
                            tempMarker.feature.properties.date.start = null;
                            tempMarker = createMarker(tempMarker.feature.properties.contentJson, tempMarker);
                        }
                        tempMarker = tempMarker.on("popupopen", onPopupOpen);
                        return tempMarker;
                    }
                }).addTo(map);  //Ajout à la map courante
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                //$$$$$$$$$$ le PUT
                putServerData("ws/impl/markerimpl/addmarker/" + tempMarker.feature.properties.contentJson.name + "/"
                    + tempMarker.feature.properties.contentJson.description + "/"
                    + tempMarker.feature.properties.contentJson.longitude + "/"
                    + tempMarker.feature.properties.contentJson.latitude + "/"
                    + tempMarker.feature.properties.contentJson.category + "/"
                    + tempMarker.feature.properties.date.start + "/"
                    + tempMarker.feature.properties.date.end, result => {
                    markerList.push(tempMarker);
                    console.log(markerList);
                });
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            }
            //==========================================================================================================
        });
    }
    return map; //On retourne la current map : Pas forcément utile au vu de la forme du code actuel ?
}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//======================================================================================================================
//======== récupération de toutes les maps de la bdd
function allMyMaps() {
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //$$$$$$$$ le GET de map, normalement il doit aussi pouvoir retourner une liste de marker associée à la map
    getServerData("ws/impl/mapimpl/getmap", result => {
        var tabName = [];
        var tabLat = [];
        var tabLon = [];
        var tabMarkerList = [];
        for (var i = 0; i < Object.keys(result).length; i++) {
            tabName[i] = result[i].name;     //récupération de tous les noms de map
            tabLat[i] = result[i].latitudeDeparture;    //récupération de toutes les latitudes associées
            tabLon[i] = result[i].longitudeDeparture;   //Idem pour les longitudes
            tabMarkerList[i] = result[i].markerList;
        }
        //==============================================================================================================
        //========== BOUCHON DE TEST POUR L'AFFICHAGE DES MARKERS
        //Un marker
        var c1 = {
            "name": "Café de Boony",
            "description": "Un café tenu pour Boony",
            "category": "Loisir",
            "picture": [],
            "latitude": 48.8395413,
            "longitude": 2.336299,
            "messageList":["J'y ai bu un café",
                "Très joli"],
        };
        var dateControl1 = {"start": "",
            "end": ""};
        //Un event
        var c2 = {
            "name": "Concert de Boony",
            "description": "Boony chante à Bercy",
            "category": "Concert",
            "picture": [],
            "latitude": 48.8351101,
            "longitude": 2.3824298,
            "messageList":["Seulement 5 euros la place !"]
        };
        var dateControl2 = {"start": "2020-05-06",
            "end": "2020-05-26"};
        var geo1 = {
            "contentJson": c1,
            "date": dateControl1
        };
        var geo2 = {
            "contentJson": c2,
            "date": dateControl2
        };
        console.log(tabMarkerList);
        //Ajout manuel
        var markerBouchon = [];
        markerBouchon.push(geo1);
        markerBouchon.push(geo2);
        tabMarkerList.splice(0, 1, markerBouchon);
        //vérification de l'ajout
        //console.log(JSON.stringify(tabMarkerList));
        //==============================================================================================================
        //Pour chaque nom, on crée un bouton dans la droplist
        for (var j = 0; j < Object.keys(result).length; j++) {
        //    console.log(JSON.stringify(tabMarkerList[j]));
            // Au clic, on appel changeMyCurrentMap avec la position : la map appelée devient la current map
            var tester = JSON.stringify(tabMarkerList[j]);
            console.log(tester);
            var testerEncode = escape(tester);
            console.log(testerEncode);
            $("#navbar-item-1").append(`<a class="dropdown-item" type="button" onclick="changeMyCurrentMap_('${tabName[j]}','${tabLat[j]}','${tabLon[j]}', '${testerEncode}')">${tabName[j]}</a>`);
        }
    });
}
//======================================================================================================================
function changeMyCurrentMap_(a, b, c, d){
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(d);
    var decoder = JSON.parse(unescape(d));
    console.log(decoder);
    changeMyCurrentMap(a, b, c, decoder);
}
function tester(tab){
    console.log(tab);
}
//Fonction de création d'évènements
function createEvent(c, marker, d){
    var choicer;
    //Cas avec images et messages
    if(c.picture != "" && c.messageList.length != 0) {
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>" + c.name + "</b><br>" + c.description + "<br><b>Category: </b>" + c.category + "<br><b>from </b>"+ d.start +"<b> to </b>"+ d.end +
            "<br>" +
            "<div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn picture-shower-button\" data-toggle=\"modal\" data-target=\"#modalPicture\">Show pictures</button>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn message-shower-button\" data-toggle=\"modal\" data-target=\"#modalMessage\">Show messages</button>" +
            "</div><div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" id=\"showMeMessage\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn event-modify-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
        //Cas sans image et avec messages
    }else if(c.picture == "" && c.messageList.length != 0) {
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>" + c.name + "</b><br>" + c.description + "<br><b>Category: </b>" + c.category + "<br><b>from </b>"+ d.start +"<b> to </b>"+ d.end +
            "<br>" +
            "<div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn message-shower-button\" data-toggle=\"modal\" data-target=\"#modalMessage\">Show messages</button>" +
            "</div><div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn event-modify-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
        //Cas avec image et sans messages
    }else if(c.picture != "" && c.messageList.length == 0){
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>" + c.name + "</b><br>" + c.description + "<br><b>Category: </b>" + c.category + "<br><b>from </b>"+ d.start +"<b> to </b>"+ d.end +
            "<br>" +
            "<div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn picture-shower-button\" data-toggle=\"modal\" data-target=\"#modalPicture\">Show pictures</button>" +
            "</div><div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn event-modify-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
        //Cas sans images ni messages
    }else{
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>"+ c.name +"</b><br>"+ c.description +"<br>Category: "+ c.category + "<br><b>from </b>"+ d.start +"<b> to </b>"+ d.end +
            "<br>" +
            "<div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn event-modify-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
    }
    return choicer; //On retourne la forme choisie
}
//Fonction de création de marker
function createMarker(c, marker){
    var choicer;
    //Cas avec images et messages
    if(c.picture != "" && c.messageList.length != 0) {
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>" + c.name + "</b><br>" + c.description + "<br><b>Category: </b>" + c.category +
            "<br>" +
            "<div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn picture-shower-button\" data-toggle=\"modal\" data-target=\"#modalPicture\">Show pictures</button>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn message-shower-button\" data-toggle=\"modal\" data-target=\"#modalMessage\">Show messages</button>" +
            "</div><div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-modify-button\" data-toggle=\"modal tooltip\" data-target=\"#modalModificationMarker\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
        //Cas sans images et avec messages
    }else if(c.picture == "" && c.messageList.length != 0) {
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>" + c.name + "</b><br>" + c.description + "<br><b>Category: </b>" + c.category +
            "<br>" +
            "<div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn message-shower-button\" data-toggle=\"modal\" data-target=\"#modalMessage\">Show messages</button>" +
            "</div><div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-modify-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
        //Cas avec images et sans messages
    }else if(c.picture != "" && c.messageList.length == 0){
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>" + c.name + "</b><br>" + c.description + "<br><b>Category: </b>" + c.category +
            "<br>" +
            "<div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn-sm marker_btn picture-shower-button\" data-toggle=\"modal\" data-target=\"#modalPicture\">Show pictures</button>" +
            "</div><div class='d-flex justify-content-between'>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-modify-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
        //Cas sans images ni messages
    }else{
        choicer = L.marker([c.latitude, c.longitude]).bindPopup("<b>"+ c.name +"</b><br>"+ c.description +"<br>Category: "+ c.category +
            "<br>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn message-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalMessage\" data-placement='bottom' title=\"Add\"><i class=\"fa fa-plus pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-modify-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Modify\"><i class=\"fa fa-pen pr-2\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn picture-adder-button\" data-toggle=\"modal tooltip\" data-target=\"#modalPictureImplementer\" data-placement='bottom' title=\"Picture\"><i class=\"fa fa-images\" aria-hidden=\"true\"></i></button>" +
            "<button type=\"button\" class=\"btn btn_link btn-sm mt-1 changer_btn marker-delete-button\" data-toggle=\"modal tooltip\" data-placement='bottom' title=\"Delete\"><i class=\"fa fa-trash pr-2\"></i></button>" +
            "</div>")
            .openPopup();
    }
    return choicer;
}
// checkbox evenement dans menu creation d'évènement
$("input[type='checkbox']").click(function(){//quand on clicue sur la check box
    if($(this).prop("checked") == true){ // quand on clique sur la checkbox si elle n'existe pas alors on la crée
        var date = new Date();
        var annee = date.getFullYear()+'-';
        var mois ="";
        var jour ="";
        var heure ="";
        if(date.getMonth()<9){
            var oui = date.getMonth()+1;
            mois+= '0' + oui;
        }else{
            mois+= date.getMonth()+1;
        }
        if(date.getDate()<9){
            jour+='-'+'0' + date.getDate()+'T';
        }else {
            jour+= '-'+ date.getDate()+'T';
        }
        if(date.getHours()<9){
            heure+='0' + date.Hours()+':';
        }else {
            heure+=date.getHours()+':';
        }
        if(date.getMinutes()<9){
            heure+='0' + date.getMinutes();
        }else {
            heure+= date.getMinutes();
        }
        //var heure = date.getHours() + ':' + date.getMinutes();
        var today = annee+mois+jour+heure;
        //console.log(today);

        $("#eventOk").remove(); //On remove le conteneur de map existant (sinon on ne peut pas le remplir)
        $("#dateGenerator").append('<div id="eventOk"></div>');
        $("#eventOk").append('<div id="debut"><label for="debut">Date de début :</label><input type="date" class="form-control" id="start" name="start" value="'+today+'" min="'+today+'">' +
            '</div><div id="fin"><label for="fin">Date de fin :</label><input type="date" class="form-control" id="end" name="end"value="'+today+'"min="'+today+'"></div>');
    }else if($(this).prop("checked") == false){ //si la div existe
        $("#eventOk").remove();
    }
});
function myfunction(){
    document.getElementById("friend-list").innerHTML="";
    $('#modalamis').modal('show');
    //document.getElementById('#friend-list').innerHTML="";
    getServerData("ws/impl/userimpl/getfriendlist", result =>{
        var tab = [];
        $("#friend-list").append("<div class=\"col-md-12\">");
        for(var i = 0; i < Object.keys(result).length ; i++){
            tab[i] = result[i].name
        }
        for(var i = 0 ; i < Object.keys(result).length ; i++){
            $("#friend-list").append("<button type=\"button\" class=\"btn btn-primary px-3\"><i class=\"fa fa-plus\" style=\"display:block;\" aria-hidden=\"true\"></i>" + tab[i] + "</button>");
        }
        $("#friend-list").append("</div>");
    })
}
// modal de liste d'amis
function searchFriend(){
    document.getElementById("addFriend").innerHTML="";
    $('#modal-ajout-amis').modal('show');
    postServerData("ws/impl/userimpl/searchfriend",result =>{
        var tab = [];
        if(Object.keys(result).length >0){
            for(var i = 0; i < Object.keys(result).length ; i++){
                tab[i] = result[i].name;
            }
            for(var i = 0 ; i < Object.keys(result).length ; i++){
                $("#addFriend").append('<a id="newFriend'+i+'">'+ tab[i]+'</a>');
                $("#addFriend").append('<input type="checkbox" id="futureFriend'+i+'" name="event" value="0"><br>');
            }
        }
    });
}

// ajout d'amis
function addFriend(){
    for(var i = 0;document.getElementById('futureFriend'+i);i++){
        var checkbox = document.getElementById('futureFriend'+i);
        if(checkbox.checked == true){
            var name = document.getElementById('newFriend'+i).value;
            //$$$$$$$$$$$$$$$ POST : vérifier la concordance avec le java $$$$$$$$$$$$$$$$$$$$
            postServerData('ws/impl/userimpl/addfriend/'+name, result=>{});
            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        }
    }
    $('#modal-ajout-amis').modal('toggle');
    myfunction();
}

function shareMap(){
    document.getElementById("friend-list-share").innerHTML="";
    $('#modalShareMap').modal('show');
    //document.getElementById('#friend-list').innerHTML="";
    //getServerData("ws/impl/userimpl/getfriendlist", result =>{
    var tab = [];
    $("#friend-list-share").append("<div class=\"col-md-12\">");
    //for(var i = 0; i < Object.keys(result).length ; i++){
    // tab[i] = result[i].name
    //}
    tab[0]= "titi";
    tab[1]= "toto";

    //for(var i = 0 ; i < Object.keys(result).length ; i++){
    for(var i = 0 ; i < Object.keys(tab).length ; i++){
        $("#friend-list-share").append('<a id="friend'+i+'">'+ tab[i]+'</a>');
        $("#friend-list-share").append('<input type="checkbox" id="shareFriend'+i+'" name="event" value="0"><br />');
    }
    $("#friend-list-list").append("</div>");
    $("#friend-list-list").append("");
    // })
}

function shareMapToFriend(){
    for(var i = 0;document.getElementById('shareFriend'+i);i++){
        var checkbox = document.getElementById('shareFriend'+i);
        if(checkbox.checked == true){
            var name = document.getElementById('friend'+i).value;
            postServerData('ws/impl/mapimpl/sharemap'+name, result=>{}); //Il faut définir le chemin de comment on partage
        }
    }
    $('#modalShareMap').modal('toggle');
}