// JavaScript Document


var photos=new Array()
var photoslink=new Array()
var which=0

//define images. You can have as many as you want:
photos[0]="http://www.uta.edu/oie/images/photoGallery/pic0.jpg"
photos[1]="http://www.uta.edu/oie/images/photoGallery/pic1.jpg"
photos[2]="http://www.uta.edu/oie/images/photoGallery/pic2.jpg"
photos[3]="http://www.uta.edu/oie/images/photoGallery/pic3.jpg"
photos[4]="http://www.uta.edu/oie/images/photoGallery/pic4.jpg"
photos[5]="http://www.uta.edu/oie/images/photoGallery/pic5.jpg"
photos[6]="http://www.uta.edu/oie/images/photoGallery/pic6.jpg"
photos[7]="http://www.uta.edu/oie/images/photoGallery/pic7.jpg"
photos[8]="http://www.uta.edu/oie/images/photoGallery/pic8.jpg"

//Specify whether images should be linked or not (1=linked)
var linkornot=0

//Set corresponding URLs for above images. Define ONLY if variable linkornot equals "1"
photoslink[0]=""
photoslink[1]=""
photoslink[2]=""

//do NOT edit pass this line

var preloadedimages=new Array()
for (i=0;i<photos.length;i++){
preloadedimages[i]=new Image()
preloadedimages[i].src=photos[i]
}


function applyeffect(){
if (document.all && photoslider.filters){
photoslider.filters.revealTrans.Transition=Math.floor(Math.random()*23)
photoslider.filters.revealTrans.stop()
photoslider.filters.revealTrans.apply()
}
}



function playeffect(){
if (document.all && photoslider.filters)
photoslider.filters.revealTrans.play()
}

function keeptrack(){
window.status="Image "+(which+1)+" of "+photos.length
}


function backward(){
if (which>0){
which--
applyeffect()
document.images.photoslider.src=photos[which]
playeffect()
keeptrack()
}
}

function forward(){
if (which<photos.length-1){
which++
applyeffect()
document.images.photoslider.src=photos[which]
playeffect()
keeptrack()
}
}

function transport(){
window.location=photoslink[which]
}

