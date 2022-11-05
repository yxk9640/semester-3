<pre>
<?php
session_start();
$_SESSION["display"]="";
$_SESSION["userfile"]="";
// put your generated access token here
$auth_token = 'sl.BSchwdTwwjc51l-FaaMrzp19nCE3yRv8Y3hE_NRQFPgNfcxf5WGdk_oK9Bx-MMmRTU_ywhfiHvwsTOFzkxj-Ns_85vVBe4E-_vuEPXFHqyVoBnDppFt90wmDup61Ti6EIrl2ppaAbxk1';
// import the Dropbox library
include "dropbox.php";

// set it to true to display debugging info
// $debug = true;

// display all errors on the browser
// error_reporting(E_ALL);
// ini_set('display_errors','On');


// create a new Dropbox folder called images
// createFolder("images");
function createFile(String $fileName){
   createFolder("images");
}


// upload a local file into the Dropbox folder images
// upload("leonidas.jpg","/images");
function ImgUpload(String $imageName){
   upload($imageName,"/images");
   // echo 'Upload Successfull <br/>';
   // print_r($imageName);
}

// download a file from the Dropbox folder images into the local directory tmp
// download("/images/leonidas.jpg","tmp/tmp.jpg");
function ImgDownload(String $imagePath){
   download($imagePath,"tmp/tmp.jpg");
}

// delete a Dropbox file
// delete("/images/leonidas.jpg");
function ImgDelete(String $imagePath){
   delete($imagePath);
}

?>
<!DOCTYPE html>
<html lang=".en.">
  
  <head>
      <title>ALBUM</title>
      <meta charset=".utf-8." />
  </head>
  
        <body>
         <div>
            <form enctype="multipart/form-data"  action="" method="POST">
                <h1> Select image to upload: </h1>
                    <div>
                        <input type="hidden" name="fileName" value="" />
                        <input type="file" name="imageInput" > 
                        <input type="submit" name="upload" value="Upload">
                        <!-- code to upload to dropbox- use form submit and php call -->
                        <?php
                              if(isset($_POST["upload"]) ){
                                 if( !empty($_FILES["imageInput"]['name']) ){
                                 header("location:album.php?userfile=".$_FILES["imageInput"]['name']);                           
                                 ImgUpload($_FILES["imageInput"]['name']);
                                 print_r($_FILES["imageInput"]['name']);
                              }
                              else echo'No file';
                           }
                        ?>
                    </div>
                <hr/>    
            </form>
         </div>
            <div style="width: 100%; display: table;">

                    <div style="display: table-row; height: 100px;">
                        <div id="DisplayWindow" style="width: 50%; display: table-cell;">
                            <h1> Display Window </h1>
                            <!-- code to display names of images in dropbox -->
                            <?php  
                             // print the files in the Dropbox folder images
                              $result = listFolder("/images");
                              foreach ($result['entries'] as $x) {
                                 // echo ' <a href="'. $x["path_display"].'">'
                                 echo'<a href="album.php?display='.$x["path_display"].'">'.$x["name"].'<a/> 
                                  <hr/>';
                              }
                              ?>
                        </div>
                        <div id="ImageSection" style="width: 50%; display: table-cell;">
                            <h1> Image Section </h1>
                            <!-- code to display images if clicked -->
                            <?php
                                 if(isset($_GET["display"])){
                                    download($_GET["display"],"tmp/tmp.jpg");                                    
                                    // download("/images/leonidas.jpg","tmp/tmp.jpg");                                 
                                    echo '<div> 
                                    <img src="tmp/tmp.jpg" style="width="100%" height="200px"" /> </br>                     
                                    <form action="album.php?delete='.$_GET["display"].'" method="POST" >
                                    <input type=submit name="delete" value="Delete" )> 
                                    </form>
                                    </div>
                                    ';
                                    $_SESSION["display"]=$_GET["display"];
                                    // print_r($_GET["display"]);
                                 }

                                 if(isset($_GET["delete"]) ){
                                    ImgDelete($_GET["delete"]);                           
                                    echo 'Delete Successful';

                                    // header("Refresh:0");
                                 }
                                 
                            ?>
                        </div>
                    </div>
                </div>
            
        </body>

</html>
</pre>
<!-- <img src="tmp/tmp.jpg"/> -->
