<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Map</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

        <!-- CUSTOM -->
        <!-- LOADING ICON -->
        <link href="css/loadingicon.css" rel="stylesheet" type="text/css"/> 
        <script src="customjs/loadingicon.js" type="text/javascript"></script>

        <!-- dropdown CSS -->
        <link href="css/dropdown.css" rel="stylesheet" type="text/css"/>
        <script src="customjs/dropdown.js" type="text/javascript"></script>




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
                font-size: 15px;
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
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Setting <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Add</a></li>
                            <li><a href="#">Edit</a></li>
                            <li><a href="#">Delete</a></li>
                        </ul>
                    </li>
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
                    <div id="countrySection" class="countrySection">
                        <label for="Country">Select Country</label>
                        <div class="form-group">
                            <s:select name="Country" cssClass="form-control"
                                      onchange="selectCountry()" list="{'--- เลือกประเทศ ---', 'Thailand', 'Laos', 'Myanmar'}" />
                        </div>
                    </div>
                    <div id="placeSection" class="placeSection">
                        <label for="place">Select place</label>
                        <div class="form-group">
                            <s:select cssClass="form-control" name="place" 
                                      onchange="place()" list="{'--- เลือกพื้นที่ ---'}" />
                        </div>
                    </div>
                    <div>
                        <div class="printbutton">

                        </div>
                        <s:submit name="create" cssClass="btn btn-primary btn-md dropdown-toggle"
                                  data-toggle="dropdown"  onclick="createpdf()" value="สร้างรายงานของประเทศนี้"/>
                        <ul class="dropdown-menu">
                            <li><a href="#">Add</a></li>
                            <li><a href="#">Edit</a></li>
                            <li><a href="#">Delete</a></li>
                        </ul>
                    </div>
                    <div class="loader" id="loader"></div>
                </div>
                <label for="map">GOOGLE MAP</label>
                <div id="map" class="col-md-9 col-sm-12"></div>
            </div>
        </div>

        <script type="text/javascript">
            var map;
            var country;
            var countrydata;
            var start = {lat: 13.75633, lng: 100.50177};
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: 4,
                center: start
            });

            $("#placeSection").hide();
            $("#create").hide();
            function selectCountry()
            {
                loading();
                setDefault(); // PREPARE FOR MOVE TO clearAll();

                var e = document.getElementById("Country");
                country = e.options[e.selectedIndex].value;
                if (country !== "--- เลือกประเทศ ---")
                {
                    $(document).ready(function () {
                        $.post("Location", {
                            location: country
                        }).done(function (data) {
                            countrydata = data.replace(/&quot;/g, '"');
                            obj = JSON.parse(countrydata);
                            
                            var row = Object.keys(obj).length;
                            var x = document.getElementById("place");

                            for (var i = 0; i < row; i++)
                            {
                                var option = document.createElement("option");
                                option.text = obj[i].name;
                                x.add(option);
                            }
                            loadComplete();
                            $("#placeSection").show();
                            $("#create").show();
                        });
                    });
                }
                else
                {
                    clearMarker();
                    //SET MAP START
                    map.setZoom(4);
                    map.panTo(start);

                    loadComplete();

                    //HIDE PLACE SELECT & PRINT BUTTON
                    $("#placeSection").hide();
                    $("#create").hide();
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
                            map.setZoom(15);
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
                $("#placeSection").hide();
                $(".doc").remove();
                document.getElementById('place').options.length = 1;
            }

            function clearMarker()
            {
                for (var i = 0; i < markers.length; i++) {
                    markers[i].setMap(null);
                }
                markers = [];
            }

            //PDF PRINT
            function createpdf()
            {
                $(document).ready(function () {
                    $.post("printPDF", {
                        country: country,
                        countrydata: countrydata
                    }).done(function (data) {
                        $("#create").hide();
                        loading();
                        setTimeout(function () {
                            openPDF();
                        }, 5000);
                    });
                });
            }
            function openPDF()
            {
                loadComplete();
                var $formrow = "<a href='PDF/" + country + ".pdf' class='btn btn-primary btn-md doc' role='button' download>โหลดเลย!</a>";
                $('.printbutton').append($formrow);
            }
        </script>
    </body>
</html>
