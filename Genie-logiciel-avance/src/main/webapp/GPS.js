window.onload = function() {    //Toutes les fonctions ci-dessous s'execute à chaque refresh
   let gps = L.map("GPSMap").setView([48.852969, 2.349903], 13)
    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1,
        accessToken: 'pk.eyJ1IjoiYWt1cmF3biIsImEiOiJjazhpdzRldncwOHAwM2RvNWNkMjJyejFnIn0.bDaWCw4cIn2OL91W1nu6Wg'
    }).addTo(gps);

    L.Control.geocoder().addTo(gps);

    var findMe = L.control.locate({
        position: 'topright',
        strings: {
            title: "Show me where I am, yo!"
        }
    }).addTo(gps);
    findMe.start();
    var myRoute = myGPS();
    myRoute.addTo(gps);
    var onMyWay = true;
   $(".my-itinerary-button").click(function(){
        $("#markerFrom").remove();
        $("#fromItinerary").append('<select class="form-control" id="markerFrom"></select>');
        $("#markerTo").remove();
        $("#toItinerary").append('<select class="form-control" id="markerTo"></select>');
        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
       //$$$$$$$$$$ Un GET pour marker, normalement on ne veut get que les marker appartenant à notre utilisateur
        getServerData("ws/impl/markerimpl/getmarker", result => {
            var tabName = [];
            var tabLat = [];
            var tabLon = [];
            for (var i = 0; i < Object.keys(result).length; i++) {
                tabName[i] = result[i].name;     //récupération de tous les noms de map
                tabLat[i] = result[i].longitude;    //récupération de toutes les latitudes associées
                tabLon[i] = result[i].latitude;   //Idem pour les longitudes
            }
            //Pour chaque nom, on crée un bouton dans la droplist
            for (var j = 0; j < Object.keys(result).length; j++) {
                // Au clic, on appel changeMyCurrentMap avec la position : la map appelée devient la current map
                $("#markerFrom").append('<option value='+tabLat[j]+','+tabLon[j]+'>'+tabName[j]+'</option>');
                $("#markerTo").append('<option value='+tabLat[j]+','+tabLon[j]+'>'+tabName[j]+'</option>');
            }
        });
        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        $('#modalMarkerRoute').modal('show');
        document.getElementById("fromMyPosition").checked = false;
        var startFromCurrent = false;
        $("input[name='fromMyPosition']").click(function() {
            if ($(this).prop("checked") == true) {
                console.log("checked !!!");
                startFromCurrent = true;
            } else {
                startFromCurrent = false;
            }
        });
            document.getElementById("finisherRoute").onclick = function() {
                console.log(onMyWay);
                if(onMyWay){
                    gps.removeControl(myRoute);
                    onMyWay = false;
                }
                var to = document.getElementById("markerTo").value.split(",");
                if(startFromCurrent){
                    myRoute = myRouting(findMe._map._lastCenter.lat, findMe._map._lastCenter.lng, to[0], to[1]);
                    console.log(findMe._map._lastCenter);
                }else{
                    var from = document.getElementById("markerFrom").value.split(",");
                    myRoute = myRouting(from[0], from[1], to[0], to[1]);
                }
                myRoute.addTo(gps);
                onMyWay = true;
            }
    });
    function myRouting(latStart,lonStart,latEnd,lonEnd){
        var findMyItinerary = L.Routing.control({
            waypoints: [
                L.latLng(latStart, lonStart),     //start
                L.latLng(latEnd, lonEnd)   //end
            ],
            routeWhileDragging: true,
            geocoder: L.Control.Geocoder.nominatim()
        })
        return findMyItinerary;
    }
//Gestion d'itinéraire
    function myGPS(){
        var GPS = L.Routing.control({
            geocoder: L.Control.Geocoder.nominatim(),
            lineOptions: {
                styles: [{
                    color: "#AD5E39",
                    opacity: 1,
                    weight: 7
                }]
            },

        })
        return GPS;
    }
};









