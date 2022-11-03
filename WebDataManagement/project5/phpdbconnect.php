<?php
$servername = "localhost";
$username = "root";
$password = "";
$db = "favourites";


try {
  echo '
  <form action="" method="GET"> 
  <input type=text value="" name="id" placeholder="id"> </input> </br>
  <input type=text value="" name="name" placeholder="name"> </input> </br>
  <input type=text value="" name="image_url" placeholder="image_url"> </input> </br>
  <input type=text value="" name="yelp_page_url" placeholder="yelp_page_url"> </input> </br>
  <input type=text value="" name="categories" placeholder="categories"> </input> </br>
  <input type=text value="" name="price" placeholder="price"> </input> </br>
  <input type=text value="" name="rating" placeholder="rating"> </input> </br>
  <input type=text value="" name="address" placeholder="address"> </input> </br>
  <input type=text value="" name="phone" placeholder="phone"> </input> </br>
  <input type=submit value="fav" name="submit"> </input>

  <input type=submit value="reset" name="reset"> </input> <hr/>
  </form>';
  if(isset($_GET['submit'])){
    $id = $_GET['id'];
    $name = $_GET['name'];
    $image = $_GET['image_url'];
    $yelp = $_GET['yelp_page_url'];
    $categories = $_GET['categories'];
    $price = $_GET['price'];
    $rating = $_GET['rating'];
    $address = $_GET['address'];
    $phone = $_GET['phone'];
    
    $dbhost = ConnectDB($servername,$username,$db,$password);
    
    insertSQL(
      $dbhost,
      $id,
      $name,
      $image,
      $yelp,
      $categories,
      $price,
      $rating,
      $address,
      $phone
    );

    print_r(retriveSQL($dbhost));
    
  }
  if(isset($_GET['reset'])){
    echo'';
  }
 

  }
 catch(PDOException $e)
  {
    echo "Connection failed: " . $e->getMessage();
  }
 
?>

<?php 
// Connection 
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
 echo'Insert Successful ';
} 


?>