function initialize() {


}

function id(s) { return document.getElementById(s); }

function findRestaurants() {
    var xhr = new XMLHttpRequest();
    // server = "proxy.php?term=indian+restaurant&location=Arlington+Texas&limit=10"
    searchType = id("searchTerm").value
    city = id("city").value
    limit = id("level").value
    state = "Texas"
    url = "proxy.php?term=" + searchType + "+restaurant&location=" + city + "+" + state + "&limit=" + limit;
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Accept", "application/json");
    var flag = 0;

    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var json = JSON.parse(this.responseText);
            var str = JSON.stringify(json, undefined, 2);

            var list = id("output").appendChild(id("ool"));
            list.innerHTML = "";

            //get JSON data
            if (city && searchType) {
                for (var i = 0; i <= id("level").value; i++) {
                    let t = i + 1;
                    list.innerHTML += "<pre>" +
                        "<li>" +
                        //1 Image
                        '<img src="' + json.businesses[i].image_url + '" width="100" height="100">' + "\n" +
                        //2 Name

                        "<b>Restaurant: </b> " + '<a href="' + json.businesses[i].url + '" target=_blank> ' + json.businesses[i].name + '</a>' + "\n" +
                        //3 Cat

                        "<b>Categories:</b> " + JSON.stringify(json.businesses[i].categories[0].title) +
                        //4 Price
                        "    <b>Price:</b> " + json.businesses[i].price +
                        //5 Rating
                        "    <b>Rating:</b>" + JSON.stringify(json.businesses[i].rating) +
                        //6 Address
                        "    <b>Address:</b> " + json.businesses[i].location.display_address[0] + "\n" +
                        // 7 Phone
                        "<b>Phone:</b> " + json.businesses[i].display_phone +
                        "</li>" +
                        "<hr>" +
                        " </pre > ";

                    console.log(json.businesses.length);
                }

            } else {
                alert("Enter Search Terms and city");
            }




        }
    };
    xhr.send();
}