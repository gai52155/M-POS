<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Map</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

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

            label
            {
                font-size: 15px;
                color: blue;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <legend>GOOGLE MAP</legend>
                <div class="col-md-3">
                    <label for="Country">Select Country</label>
                    <div class="form-group">
                        <s:select size="3" class="form-control" name="Country" 
                                  onchange="selectCountry()" list="{'Thailand','Laos','Myanmar'}" />
                    </div>
                    <label for="place">Select place</label>
                    <div class="form-group">
                        <s:select class="form-control" name="place" 
                                  onchange="myFunction()" list="{}" />
                    </div>
                </div>
                <label for="map">GOOGLE MAP</label>
                <div id="map" class="col-md-9"></div>
            </div>
        </div>
        <script type="text/javascript">
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 5,
                center: {lat: 13.75633, lng: 100.50177},
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });

            function selectCountry()
            {
                document.getElementById('place').options.length = 0; // PREPARE FOR MOVE TO clearAll();

                var e = document.getElementById("Country");
                var place = e.options[e.selectedIndex].value;

                $(document).ready(function () {
                    $.post("Location", {
                        location: place
                    }).done(function (data) {
                        obj = JSON.parse(data.replace(/&quot;/g, '"'));
                        console.log(obj);

                        var row = Object.keys(obj).length;
                        var x = document.getElementById("place");

                        for (var i = 0; i < row; i++)
                        {
                            var option = document.createElement("option");
                            option.text = obj[i].name;
                            x.add(option);
                        }

                    });
                });

                //geocodePlaceId(geocoder, map, infowindow, place);
            }

            function clearAll()
            {
                document.getElementById('place').options.length = 0;

                //Loop through all the markers and remove
                for (var i = 0; i < markers.length; i++) {
                    markers[i].setMap(null);
                }
                markers = [];
            }
        </script>
    </body>
</html>
