<%@ taglib prefix="s" uri="/struts-tags" %>
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
                <div class="col-md-3 col-sm-3 col-xs-5">
                    <label for="Country">Select Country</label>
                    <div class="form-group">
                        <s:select size="3" class="form-control" name="Country" onchange="myFunction()"
                                  list="{'Thailand','Laos','Myanmar'}" />
                    </div>
                </div>
                <label for="map">GOOGLE MAP</label>
                <div id="map" class="col-md-9 col-sm-9 col-xs-7"></div>
            </div>
        </div>
        <script type="text/javascript">
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 2,
                center: {lat: 13.75633, lng: 100.50177},
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });
            
            function myFunction()
            {
                var e = document.getElementById("Country");
                var place = e.options[e.selectedIndex].value;

                alert("Searching : " + place);
            }
        </script>
    </body>
</html>
