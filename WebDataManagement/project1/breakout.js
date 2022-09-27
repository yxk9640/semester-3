var dx, dy; /* displacement at every dt */
var x, y; /* ball location */
var score = 0; /* # of walls you have cleaned */
var tries = 0; /* # of tries to clean the wall */
var started = false; /* false means ready to kick the ball */
var ball, court, paddle, brick, msg;
var court_height, court_width, paddle_left;
var animated, paddle_position, updateMsg = 0,
    levelDiff;

var bricks = new Array(4); // rows of bricks
var colors = ["red", "blue", "yellow", "green"];

/* get an element by id */
function id(s) { return document.getElementById(s); }

/* convert a string with px to an integer, eg "30px" -> 30 */
function pixels(pix) {
    pix = pix.replace("px", "");
    num = Number(pix);
    return num;
}

/* place the ball on top of the paddle */
function readyToKick() {
    x = pixels(paddle.style.left) + paddle.width / 2.0 - ball.width / 2.0;
    y = pixels(paddle.style.top) - 2 * ball.height;
    ball.style.left = x + "px";
    ball.style.top = y + "px";
}

/* paddle follows the mouse movement left-right */
function movePaddle(e) {
    var ox = e.pageX - court.getBoundingClientRect().left;
    paddle.style.left = (ox < 0) ? "0px" :
        ((ox > court_width - paddle.width) ?
            court_width - paddle.width + "px" :
            ox + "px");
    paddle_position = Math.abs(pixels(paddle.style.left));
    if (!started) {
        readyToKick();
    }
}

function initialize() {
    court = id("court");
    ball = id("ball");
    paddle = id("paddle");
    wall = id("wall");
    msg = id("messages");
    brick = id("red");
    court_height = pixels(court.style.height);
    court_width = pixels(court.style.width);
    for (i = 0; i < 4; i++) {
        // each row has 20 bricks
        bricks[i] = new Array(20);
        var b = id(colors[i]);
        for (j = 0; j < 20; j++) {
            var x = b.cloneNode(true);
            bricks[i][j] = x;
            wall.appendChild(x);
        }
        b.style.visibility = "hidden";
    }
    started = false;
}

/* true if the ball at (x,y) hits the brick[i][j] */
function hits_a_brick(x, y, i, j) {
    var top = i * brick.height - 450;
    var left = j * brick.width;
    return (x >= left && x <= left + brick.width &&
        y >= top && y <= top + brick.height);
}



function startGame() {
    if (!started) {
        started = true;
        levelDiff = (document.getElementById("level").value == 1) ?
            0.5 : (document.getElementById("level").value == 2) ?
            1 : (document.getElementById("level").value == 3) ?
            1.5 : (document.getElementById("level").value == 4) ?
            2 : alert("select level");
        dy = -levelDiff;
        dx = levelDiff;
        console.log(msg.innerText + " :score");
        console.log((bricks[3][19].style.visibility == "hidden") ? ": bricks start" : "not");
        animated = setInterval(move2, 1);
    }


}

function resetGame() {
    //tries=0, score=0, ball on paddle, bricks reset, messages=0, 
    clearInterval(animated);
    tries = 0;
    updateMsg = 0;
    id("tries").innerText = tries;
    id("messages").innerText = updateMsg;
    for (i = 0; i < 4; i++) {
        for (j = 0; j < 20; j++) {
            bricks[i][j].style.visibility = "visible";
        }
    }
    readyToKick();
}


// ---- Extra Self functions -----

function collision() {
    if (Math.abs(pixels(ball.style.top)) >= court_height - 10) {
        dy = -dy;
        console.log(y);
    }
    if (Math.abs(pixels(ball.style.top)) < 1) { //bottom
        //brick=same, ball on paddle, tries++ score=0 msgs++
        clearInterval(animated);
        started = false;
        readyToKick();
        tries++;
        // score = 0;
        id("tries").innerText = tries;
        // id("score").innerText = score;
        id("messages").innerText = updateMsg;

        console.log((bricks[3][19].style.visibility == "hidden") ? ": bricks bottom" : "not");
    }
    if (Math.abs(pixels(ball.style.left)) >= court_width || Math.abs(pixels(ball.style.left)) <= 1) {
        dx = -dx;
    }
    if ((Math.abs(pixels(ball.style.left)) >= paddle_position && Math.abs(pixels(ball.style.left)) <= paddle_position + pixels("97.183px")) && Math.abs(pixels(ball.style.top)) == pixels(paddle.style.top)) {
        dy = -dy;

    }

    for (let l = 0; l < bricks.length; l++) {
        for (let k = 0; k < 20; k++) {
            if (hits_a_brick(x, y, l, k) && bricks[l][k].style.visibility != "hidden") {
                bricks[l][k].style.visibility = "hidden";
                dy = -dy;
                updateMsg++;
                id("messages").innerText = updateMsg;
            }
        }
    }



}

function move2() {
    collision();

    y += dy;
    x += dx;
    ball.style.top = y + "px";
    ball.style.left = x + "px";
    if (updateMsg == 80) {
        score++;
        updateMsg = 0;
        id("score").innerText = score;
        // id("score").innerText = score;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 20; j++) {
                bricks[i][j].style.visibility = "visible";
            }
        }
        clearInterval(animated);
        readyToKick();
    }

}


// ----
// check if pixel can retrive brick indexes?