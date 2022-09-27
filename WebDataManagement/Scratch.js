() => {
    while (true) {
        ball.style.left = dx + "px";
        ball.style.top = dy - "px";
        sum += 0.1;
        started = true;
        i++;
        console.log(i);
        // if (i > court_height) {
        //     // ball.style.left = 2 + "px";
        //     // ball.style.top = 2 + "px";
        //     break;
        // }   
    }

}
// ---------- For Ball Movement ---------

function startGame() {
    if (!started) {
        started = true;
        dy = -1;
        dx = 1;
        console.log(id(bricks[0][0]) + " Brick");

        animated = setInterval(move2, 1);
    }
}

function collision() {
    if (Math.abs(pixels(ball.style.top)) >= court_height - 10) {
        dy = -dy;
        console.log(y);
    }
    // if (Math.abs(pixels(ball.style.top)) < 21) {
    //     dy = -dy;
    // }
    if (Math.abs(pixels(ball.style.left)) >= court_width || Math.abs(pixels(ball.style.left)) <= 1) {
        dx = -dx;
    }
    if ((Math.abs(pixels(ball.style.left)) >= paddle_position && Math.abs(pixels(ball.style.left)) <= paddle_position + pixels("97.183px")) && Math.abs(pixels(ball.style.top)) == pixels(paddle.style.top)) {
        dy = -dy;

    }

}

function move2() {
    collision();
    y += dy;
    x += dx;
    ball.style.top = y + "px";
    ball.style.left = x + "px";
}