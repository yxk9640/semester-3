<?php
session_start();
if(empty($_SESSION["city"])){
    $search_City="";
}
else{
    $search_City = $_SESSION["city"];
}
?>
<!DOCTYPE html>
<html lang=".en.">
  
  <head>
      <title>Find Restaurants on Yelp</title>
      <meta charset=".utf-8." />
  </head>
  
        <body>
            <div>
                <div>
                    <form action="" method="GET">
                        <label>City: <input type="text" name="city"  value="<?php 
                        if(isset($_GET["reset"])){
                            echo $search_City="";
                            $_SESSION["favourites"]=array();
                            $_SESSION["favDupli"]=array();
                        }
                        else{echo $search_City;}
                         ?>"/></label>
                        <label>Search Terms: <input type="text"   name="searchTerm"/></label>
                        </br>
                        </br>
                        <input type="submit" name="find" value="Find"/>
                        <a href="yelp.php?reset">
                            <input type="button" name="reset" value="Reset"/>
                        </a>

                    </form>
                    <hr/>
                    
                </div>
                <div style="width: 100%; display: table;">
                    <div style="display: table-row; height: 100px;">
                        <div id="Rest_left" style="width: 50%; display: table-cell;">
                            <h1> Restaurant </h1>
                            <?php     
                            // put your Yelp API key here:
                            $API_KEY = '7R_MdF-TPHmvKdVwPQcsisKjKiVf1kilf-OdKcWxy10XoBC_Gn4Wy-Ra49izE27y7O4s1TuBUybtmz3fKYqpBnfWT0Jv9RmgOMCkgysYtmEghknD4eNM5F-lFnM0Y3Yx';
                            if(isset($_GET["city"]) && isset($_GET["searchTerm"]))
                            {
                               echo( sessionCitySearch($_GET["city"],$_GET["searchTerm"]));
                            }
                                    
                            
                            ?>
                        </div>

                        <div id="Fav_right" style="width: 50%; display: table-cell;">
                            <h1> Favourite </h1>
                            <?php
                                   if(isset($_GET["store"]) ){
                                    $fav_store = $_GET["store"];
                                    array_push($_SESSION["favDupli"],$fav_store);
                                    $tempId = array();
                                    $tempId = getTopRestListID($_SESSION["search"]);
                                    array_push($_SESSION["favourites"],getK($fav_store,$tempId));
                                    

                                    echo'<hr/>';
                                    $result="";
                                    // if(in_array($fav_store,$_SESSION["favDupli"])){
                                    //     print_r($_SESSION["favDupli"]);
                                    // }
                                    foreach($_SESSION["favourites"] as $outKey=>$outValue)
                                        { 
                                            
                                            foreach($outValue as $inKey=>$inVal){
                                                // $result =$result.
                                                echo ' <img src="' .$inVal['image_url'] . '"width=100 height=100 name="store" value="store"></a></form> </br> 
                                                Restaurant: '. $inVal['name'] . '</br>
                                                Rating: '. $inVal['rating']. ' <hr/> ' ;
                                            } 
                                        }
                                    
                                }

                            ?>
                        </div>
                    </div>
                </div>
            </div>
        </body>

</html>


<!-- PHP functions -->
<?php

function getK(String $fav, array $tempID){
    //return values of fav that are selected
    $result = array();
    foreach($tempID as $key=>$value){
        if(strcmp(key($value),$fav)===0){
            $result=($value);
            }
        }
    return $result;
}

function getTopRestListID(array $JsonArray){
    $resultArray = array();
    for($i=0; $i<sizeof($JsonArray['businesses']);$i++){
        array_push($resultArray,array($JsonArray['businesses'][$i]['id']=>$JsonArray['businesses'][$i]));
    }
    return $resultArray;
}

function sessionCitySearch(String $search_City, String $search_Term){
    $search_City = $_GET["city"];
    $search_Term = $_GET["searchTerm"];
    $arrBusiness = array(); 
    $response = apiCall($search_City,$search_Term);
        
    $json = json_decode($response);
    $arrBusiness = json_decode($response,true);
    
    $_SESSION["search"] = $arrBusiness;
    $_SESSION["city"] = $search_City;
    $result = "";
    if($search_City && $search_Term){
        for($i = 0; $i < 10; $i++)
        { 
            $j=$i+1;
            // echo "</br>"; 
            // echo ''.$i.'<form action="" method="GET"> <a href="yelp.php?store='.$_SESSION["search"]['businesses'][$i]['id'] .'"> 
           $result =$result.''.$j.'<form action="" method="GET"> <a href="yelp.php?store='.$_SESSION["search"]['businesses'][$i]['id'] .'"> 
            <img src="' .$_SESSION["search"]['businesses'][$i]['image_url'] . '"width=100 height=100 name="store" value="store"></a></form> </br> 
                Restaurant: ' .$_SESSION["search"]['businesses'][$i]['name'] . '</br>
                Rating: '.$_SESSION["search"]['businesses'][$i]['rating']. ' <hr/>' ; 
        }
    }
    return $result;
}



function apiCall(String $search_City,String $search_Term){
    $API_HOST = "https://api.yelp.com";
    $SEARCH_PATH = "/v3/businesses/search";
    $BUSINESS_PATH = "/v3/businesses/";
    $curl = curl_init();
    if (FALSE === $curl)
       throw new Exception('Failed to initialize');
  //   $url = $API_HOST . $SEARCH_PATH . "?" . $_SERVER['QUERY_STRING']; // connects to .js file.
    $query = "term=$search_Term+restaurant&location=$search_City&limit=10";
    $url = $API_HOST . $SEARCH_PATH . "?" . $query;
    curl_setopt_array($curl, array(
              CURLOPT_URL => $url,
              CURLOPT_RETURNTRANSFER => true,  // Capture response.
              CURLOPT_ENCODING => "",  // Accept gzip/deflate/whatever.
              CURLOPT_MAXREDIRS => 10,
              CURLOPT_TIMEOUT => 30,
              CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
              CURLOPT_CUSTOMREQUEST => "GET",
              CURLOPT_HTTPHEADER => array(
                  "authorization: Bearer " . $GLOBALS['API_KEY'],
                  "cache-control: no-cache",
              ),
          ));
    $responsefun = curl_exec($curl);
    curl_close($curl);
    return $responsefun;
}
?>