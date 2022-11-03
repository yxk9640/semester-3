let map, content, infoWindow, infoOpen, mark;
searchType = id("searchTerm").value
var options = {
    center: {
        lat: 32.75,
        lng: -97.13
    },
    zoom: 16
        // mapTypeId: 'satellite'
};

function initialize() {}

//initial map
function initMap() {
    createMap("map", options);
    console.log(map);
    mark = new google.maps.Marker({
        position: {
            lat: 32.75,
            lng: -97.13
        },
        label: "i",
        map: map
    });
    // content = "Initial Pin point";
    // infoWindow = new google.maps.InfoWindow({ content: content });
    // mark.addListener("click", () => {
    //     infoWindow.open({
    //         anchor: mark,
    //         map,
    //     })
    // });
}



function id(str) { return document.getElementById(str); }

async function findRestaurants() {
    var xhr = new XMLHttpRequest();

    city = "Arlington"
    limit = 10
    url = "proxy.php?term=" + searchType + "+restaurant&location=" + city + "&limit=" + limit;

    xhr.open("GET", url, true);
    xhr.setRequestHeader("Accept", "application/json");

    xhr.onreadystatechange = function() {
        if (this.readyState == 4) {
            var json = JSON.parse(this.responseText);
            var str = JSON.stringify(json, undefined, 2);
            var list = id("output").appendChild(id("ool"));
            list.innerHTML = "";
            mark.setMap(null);
            console.log(json.businesses);

            //get JSON data
            if (json.businesses && searchType) {
                for (var i = 0; i <= 10; i++) {
                    if (json.businesses[i] &&
                        map.getBounds().ab.hi >= restLat(json.businesses[i]) &&
                        map.getBounds().Fa.hi >= restLng(json.businesses[i])
                    ) {
                        content = "<pre>" +
                            //1 Name
                            '<img src="' + json.businesses[i].image_url + '" width="100" height="100">' + "\n" +
                            //2 Name
                            "<b>Restaurant: </b> " + '<a href="' + json.businesses[i].url + '" target=_blank> ' +
                            json.businesses[i].name + '</a>' + "\n" +
                            //3 Rating
                            "    <b>Rating:</b>" + JSON.stringify(json.businesses[i].rating) +
                            "</pre>";
                        //create markers for each restaurant
                        mark = new google.maps.Marker({
                            position: {
                                lat: json.businesses[i].coordinates.latitude,
                                lng: json.businesses[i].coordinates.longitude
                            },
                            label: (i + 1).toString(),
                            map: map,
                        });
                        // create info window open and close info
                        infoWindow = new google.maps.InfoWindow();
                        console.log("Two");
                        google.maps.event.addListener(mark, 'click', (function(mark, content, infoWindow) {
                            return function() {
                                if (infoOpen != null) {
                                    infoOpen.close();
                                    // infoWindow.close();
                                }
                                infoWindow.setContent(content);
                                infoWindow.open(map, mark);
                                infoOpen = infoWindow;
                            };
                        })(mark, content, infoWindow));
                    }
                }
            } else {
                alert("Enter a valid Search Term");
            }
        }
    };
    xhr.send();
}


function createMap(m_id, options) {
    map = new google.maps.Map(id(m_id), options);
    window.clearInterval();
}

function centerChanged() {

    map.addListener("center_changed", () => {
        window.setTimeout(() => {
            // console.log("center changed");
            createMap("map", {
                center: {
                    lat: getNewLat,
                    lng: getNewLng
                },
                zoom: 16
            })
        }, 3000);
        findRestaurants();
    });
}

function getNewLat(map) {
    return map.getCenter().lat();
}

function getNewLng(map) {
    return map.getCenter().lng();
}

function restLat(x) {
    return x.coordinates.latitude;
}

function restLng(x) {
    return x.coordinates.longitude;
}