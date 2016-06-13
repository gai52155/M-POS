<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Map</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link href="css/loadingicon.css" rel="stylesheet" type="text/css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="customjs/loadingicon.js" type="text/javascript"></script>
        <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>

        <style>
            /* Set the responsive map size here */
            #map 
            {
                min-height:500px; 
                max-height:500px; 
            }

            select option
            {
                font-size: 18px;
            }

            select option:nth-child(odd)
            {
                background-color: yellow;
            }

            select option:nth-child(even)
            {
                background-color: scrollbar;
            }

            select option:nth-child(1)
            {
                background-color: white;
            }

            label
            {
                font-size: 15px;
                color: blue;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">M-POS</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#">Page 1</a></li>
                    <li><a href="#">Page 2</a></li> 
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <legend>GOOGLE MAP</legend>
                <div class="col-md-3">
                    <label for="Country">Select Country</label>
                    <div class="form-group">
                        <s:select name="Country" cssClass="form-control"
                                  onchange="selectCountry()" list="{'---Choose country---', 'Thailand', 'Laos', 'Myanmar'}" />
                    </div>
                    <label for="place">Select place</label>
                    <div class="form-group">
                        <s:select cssClass="form-control" name="place" 
                                  onchange="place()" list="{'---Choose location---'}" />
                        <div class="loader" id="loader"></div>
                    </div>
                </div>
                <label for="map">GOOGLE MAP</label>
                <div id="map" class="col-md-9 col-sm-12"></div>
            </div>
        </div>

        <script type="text/javascript">

            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 4,
                center: {lat: 13.75633, lng: 100.50177},
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });

            function selectCountry()
            {
                loading();
                setDefault(); // PREPARE FOR MOVE TO clearAll();

                var e = document.getElementById("Country");
                var country = e.options[e.selectedIndex].value;
                if (country !== "choose country")
                {
                    $(document).ready(function () {
                        $.post("Location", {
                            location: country
                        }).done(function (data) {
                            obj = JSON.parse(data.replace(/&quot;/g, '"'));

                            var row = Object.keys(obj).length;
                            var x = document.getElementById("place");

                            for (var i = 0; i < row; i++)
                            {
                                var option = document.createElement("option");
                                option.text = obj[i].name;
                                x.add(option);
                            }
                            loadComplete();
                        });
                    });
                }
            }

            function place()
            {
                clearMarker();

                var e = document.getElementById("place");
                var place = e.selectedIndex - 1;

                var geocoder = new google.maps.Geocoder;
                var infowindow = new google.maps.InfoWindow;

                geocodeLatLng(geocoder, infowindow, place);
            }

            var markers = [];
            function geocodeLatLng(geocoder, infowindow, place)
            {
                var latlng = {lat: parseFloat(obj[place].lat), lng: parseFloat(obj[place].lng)};
                geocoder.geocode({'location': latlng}, function (results, status) {
                    if (status === google.maps.GeocoderStatus.OK) {
                        if (results[1]) {
                            map.setZoom(11);
                            var marker = new google.maps.Marker({
                                position: latlng,
                                map: map
                            });
                            markers.push(marker);
                            map.panTo(latlng);
                            google.maps.event.addListener(marker, 'click', (function (marker)
                            {
                                return function ()
                                {
                                    infowindow.setContent('<div>' +
                                            '<h1>' + obj[place].name + '</h1>' +
                                            '<h4>' + 'LATITDUDE : ' + obj[place].lat + '</h4>' +
                                            '<h4>' + 'LONGITUDE : ' + obj[place].lng + '</h4>' +
                                            '<h4>' + 'ISO : ' + obj[place].iso + '</h4>' +
                                            '<h4>' + 'PROVINCE : ' + obj[place].province + '</h4>' +
                                            '</div>');
                                    infowindow.open(map, marker);
                                };
                            })(marker));
                        } else {
                            window.alert('No results found');
                        }
                    } else {
                        window.alert('Geocoder failed due to: ' + status);
                    }
                });
            }

            function setDefault()
            {
                document.getElementById('place').options.length = 1;
            }

            function clearMarker()
            {
                for (var i = 0; i < markers.length; i++) {
                    markers[i].setMap(null);
                }
                markers = [];
            }
        </script>
    </body>
</html>
