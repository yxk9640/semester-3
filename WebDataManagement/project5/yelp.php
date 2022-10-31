<?php
session_start();
if(!isset($_SESSION["search"]) && !isset($_SESSION["favourites"]) ){
    $_SESSION["search"] = array();
    $_SESSION["favourites"] = array();
    $_SESSION["favDupli"] = array();
    $_SESSION["city"] = "";
}
if(empty($_SESSION["city"])||!isset($_SESSION["city"])){
    $search_City="";
}
if(!empty($_GET['city'])){
    $_SESSION['city'] = $_GET['city'];
}

if(isset($_GET["reset"])){
session_unset();
session_destroy();
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
                        <?php 
                        $search_City = isset($_SESSION["city"])? $_SESSION["city"]: "";
                        echo "<label>City: <input type='text' name='city' value='$search_City'/></label>";
                        ?>
                        
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
                            if(isset($_GET["city"]) )
                            {
                                $_SESSION["city"]=$_GET["city"];
                               echo( sessionCitySearch($_GET["city"],!empty($_GET["searchTerm"])?$_GET["searchTerm"]:""));
                            }
                            else{
                                if(!empty($_SESSION["search"])){
                                   echo(returnHTML());
                                }
                            }
                                    
                            
                            ?>
                        </div>

                        <div id="Fav_right" style="width: 50%; display: table-cell;">
                            <h1> Favourite </h1>
                            <form action="" method="GET"><hr/>
                            <!-- <input type=submit value="Fav DB" name="FavDB"> </input> <hr/> -->
                            </form>
                            <?php
                                   if(isset($_GET["store"]) ){
                                    $fav_store = $_GET["store"];
                                    $tempId = array();
                                    $tempId = getTopRestListID($_SESSION["search"]);
                                    
                                    //Initiate DB and insert in DB
                                    $dbconnect = DBInstance();
                                    // print_r($dbconnect); //
                                    // insertSQL($dbconnect);


                                    if(!in_array($fav_store,$_SESSION["favDupli"]))
                                    {   
                                        array_push($_SESSION["favDupli"],$fav_store);
                                        $arrayFav = getK($fav_store,$tempId); // gives key value pair and required feilds to insert in table
                                        array_push($_SESSION["favourites"],getK($fav_store,$tempId));
                                        $categories = $arrayFav[$fav_store]["categories"][0]["title"];
                                        $price = ( !empty($arrayFav[$fav_store]["price"])? $arrayFav[$fav_store]["price"]:"N/A");
                                        insertSQL($dbconnect,
                                        $arrayFav[$fav_store]["id"],
                                        $arrayFav[$fav_store]["name"],
                                        $arrayFav[$fav_store]["image_url"],
                                        $arrayFav[$fav_store]["url"],
                                        $categories,
                                        $price,
                                        $arrayFav[$fav_store]["rating"],
                                        $arrayFav[$fav_store]["location"]["address1"],
                                        $arrayFav[$fav_store]["phone"]
                                    );

                                    }
                                    // echo( returnfavHTML());
                                    $dbconnect = DBInstance();
                                        $getDB = retriveSQL($dbconnect);
                                        print_r(getfavDetails($getDB));
                                    
                                }else{
                                    
                                        if(!empty($_SESSION["favourites"])){
                                            // echo(returnfavHTML());
                                            $dbconnect = DBInstance();
                                            $getDB = retriveSQL($dbconnect);
                                            print_r(getfavDetails($getDB));
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
function getfavDetails(array $store){
    $result="";
    foreach($store as $inKey=>$inVal)
        { 
            
            //    $result =$result.
                $price = (!empty($inVal['price']))? $inVal['price']:"N/A";
                $result = $result . ' <img src="' .$inVal['image_url'] . '"width=100 height=100 name="store" value="store"></a></form> </br> 
                Restaurant: '. $inVal['name'] . '</br>
                Rating: '. $inVal['rating']. ' </br>
                Caregory: '. $inVal['categories'] . '</br>
                Price: '. $price . '</br><hr/> ' ;
                //  = $outValue["rating"];
            // } 
        }

    return $result;
}

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
    // $_SESSION["city"] = $search_City;
    return returnHTML();
    
}

function returnfavHTML(){
    $result="";
    // if(in_array($fav_store,$_SESSION["favDupli"])){
    //     print_r($_SESSION["favDupli"]);
    // }
    foreach($_SESSION["favourites"] as $outKey=>$outValue)
        { 
            
            foreach($outValue as $inKey=>$inVal){
                // $result =$result.
                $price = (!empty($inVal['price']))? $inVal['price']:"N/A";
                $result = $result . ' <img src="' .$inVal['image_url'] . '"width=100 height=100 name="store" value="store"></a></form> </br> 
                Restaurant: '. $inVal['name'] . '</br>
                Rating: '. $inVal['rating']. ' </br>
                Caregory: '. $inVal['categories'][0]['title'] . '</br>
                Price: '. $price . '</br><hr/> ' ;
            } 
        }

    return $result;
}

function returnHTML(){
    $result = "";$i=0;
    if(!empty($_SESSION["search"])){
        // for($i = 0; $i < count($_SESSION["search"]["businesses"]); $i++)
        foreach($_SESSION["search"]["businesses"] as $iter)
        {

            $i=$i+1;
            $price = (!empty($iter['price'])? $iter['price']:"N/A") ;
            $result =$result.''.$i.'<form action="" method="GET"> <a href="yelp.php?store='.$iter['id'] .'"> 
                <img src="' .$iter['image_url'] . '"width=100 height=100 name="store" value="store"></a></form> </br> 
                Restaurant: ' .$iter['name'] . '</br>
                Rating: '.$iter['rating']. ' </br>
                Category: ' .$iter['categories'][0]['title'] . '</br>
                Price: ' . $price. '</br><hr/>' ; 
        }
    }
    return $result;
}



function apiCall(String $search_City,String $search_Term){
    $search_City = preg_replace('/\s+/', '+', $search_City);
    $search_Term = preg_replace('/\s+/', '+', $search_Term);
    $API_HOST = "https://api.yelp.com";
    $SEARCH_PATH = "/v3/businesses/search";
    $BUSINESS_PATH = "/v3/businesses/";
    $curl = curl_init();
    if (FALSE === $curl)
       throw new Exception('Failed to initialize');
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

//--------------- Database connection --------------------
function DBInstance(){
    $servername = "localhost";
    $username = "root";
    $password = "";
    $db = "favourites";
    $result="";
    try {
        $dbhost = ConnectDB($servername,$username,$db,$password);       
        // print_r(retriveSQL($dbhost));
        $result = $dbhost;
    }
    catch(PDOException $e)
    {
        $result = "Connection failed:  </br>" . $e->getMessage();
    }

    return $result;
}


function ConnectDB(String $servername,String $username,
String $db,String $password){
  //PDO constructor=> new PDO(databaseDriver,username,pass,options){}
  $dbhost = new PDO("mysql:host=$servername;dbname=yelp", $username, $password);
  // set the PDO error mode to exception
  $dbhost->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

  return $dbhost;
}

//query to retrive from database
function retriveSQL(Object $dbhost){
$result=array();
$retrive_query = $dbhost->prepare('select * from favorites');
$retrive_query->execute();
// $retrive_result = $retrive_query->fetch();
while($retrive_result=$retrive_query->fetch()){
  array_push($result,$retrive_result);
}
return $result;
}
// insert into database 
function insertSQL(Object $dbhost,String $id,String $name, String $image,String $yelp, String $categories,String $price, String $rating, String $address, String $phone){
$insert_qurey = $dbhost->prepare('insert into favorites (id,name,image_url,yelp_page_url,categories,price,rating,address,phone)
values(?,?,?,?,?,?,?,?,?)');

$insert_qurey->execute([
$id,
$name,
$image,
$yelp,
$categories,
$price,
$rating,
$address,
$phone
]);

// values ("10","India 101","www.google.com","www.youtube.com","[12]","$$","4.4","1111 s oak","123-123-1234")');
// $insert_qurey->execute();

echo'Insert Successful </br> <hr/>';
} 

?>