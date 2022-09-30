function initialize() {}

function id(s) { return document.getElementById(s); }

function findRestaurants() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "proxy.php?term=indian+restaurant&location=Arlington+Texas&limit=10", true);
    xhr.setRequestHeader("Accept", "application/json");
    xhr.onreadystatechange = function() {
        if (this.readyState == 4) {
            var json = JSON.parse(this.responseText);
            var str = JSON.stringify(json, undefined, 2);
            // var image = json.businesses[0].image_url; //method to access the details in yelp webserver
            // var name = json.businesses[0].name; //method to access the details in yelp webserver
            // var yelpURL = json.businesses[0].url; // URL for name
            // var categories = json.businesses[0].categories; //method to access the details in yelp webserver
            // var price = json.businesses[0].price; //method to access the details in yelp webserver
            // var rating = json.businesses[0].rating; //method to access the details in yelp webserver
            // var location = json.businesses[0].location.display_address; //method to access the details in yelp webserver
            // var contact = json.businesses[0].phone; //method to access the details in yelp webserver
            // console.log(str.length);


            //1 Name redirect to YELP page. 

            // for (var i = 0; i < id("level").value; i++) {
            for (var i = 0; i < 11; i++) {
                if ((id("city").value != "undefined" && id("city").value === (json.businesses[i].location.city)) && (id("searchTerm").value === json.businesses[i].categories[0]["title"] && json.businesses[i].categories[0]["title"] != "undefined")) {
                    id("output").appendChild(document.createElement("div")).innerHTML = "<pre>" +
                        "Restaurant: " + '<a href="' + json.businesses[i].url + '"> ' + json.businesses[i].name + '</a>' + "\n" +
                        //2 Image
                        '<img src="' + json.businesses[i].image_url + '" width:"100px height="100px"/>' + "\n" +
                        //3 Cat
                        "Categories: " + JSON.stringify(json.businesses[i].categories[i]) + "\n" +
                        //4 Price
                        "Price: " + JSON.stringify(json.businesses[i].price) + "\n" +
                        //5 Rating
                        "Rating:" + JSON.stringify(json.businesses[i].rating) + "\n" +
                        //6 Address
                        "Address: " + JSON.stringify(json.businesses[i].location.display_address) + "\n" +
                        // 7 Phone
                        "Phone: " + JSON.stringify(json.businesses[i].phone) +
                        " </pre > ";


                } else {
                    // for (var x: json.businesses) {
                    id("output").innerHTML = "<pre>" + str + "</pre>";
                    // }

                }


            }




        }
    };
    xhr.send(null);
}