import processing.core.*;

public class App extends PApplet {
    // Triangles initial positions
    float triangleX1 = 300;
    float triangleX2 = 450;
    float triangleX3 = 575;
    float triangleX4 = 700;
    float triangleX5 = 825;

    double moveSpeed = 1.2; // Speed of movement to the left
    float resetPosition = width; // Reset position to the right side of the screen

    float circleY = 375; // Y position of the circle
    float circleRadius = 20; // Sets radius of the circle

    float jumpHeight = 60;// How high it jumps
    boolean isJumping = false; // Checking if it is already jumping so that you can jump twice
    int lives = 3; // Amount of lives you have before you die
    boolean gameOver = false;// Checking the state of the game
    boolean hittable = true; // so that it only counts it being hit once
    int score = 0; // Tracks how well the player is doing
    int frameCountWhenHit = 0; // Records the frame when the ball is it
    boolean collided = false; // Flag for collision detection

    int gamescreen = 0; // Sets which screen we are on

    float triangleY = 400; // Base Y position of the triangle
    float triangleBaseY = 375; // Peak Y position of the triang

    public static void main(String[] args) {
        PApplet.main("App");

    }

    public void settings() {
        size(600, 400); // Size width, Size height
    }

    public void setup() {
        background(5, 16, 84); // Blue Color

    }

    public void draw() {
        if (gamescreen == 0) {
            intro();

        } else if (gamescreen == 1) {
            textAlign(LEFT, BASELINE); // puts text align back to default

            background(5, 16, 84);
            fill(240, 207, 60);

            HitWriting();

            if (gameOver) {
                gameOver();
                return;

            }

            if (isJumping) {
                Jump();

            }

            // Draw circle
            stroke(235, 130, 49);
            strokeWeight(5);
            ellipse(150, circleY, circleRadius * 2, circleRadius * 2);

            DrawTriangles();

            MoveTrianglesandResetThem();

            // Check for collisions with each triangle
            if (hittable) {
                CheckiftheBallCollided();
            }

            if (collided) {
                CollidingResults();

            }

            // Writing on the screen
            fill(255);
            textSize(40);
            text("Lives: " + lives, 450, 30);
            fill(255);
            textSize(40);
            text("Score: " + score, 450, 60);
        }
    }

    boolean checkCollision(float triangleX) {
        // Check distances to the triangle's base points
        float distance1 = dist(150, circleY, triangleX, triangleY);
        float distance2 = dist(150, circleY, triangleX + 10, triangleY);
        float distance3 = dist(150, circleY, triangleX + 5, triangleBaseY);

        return (distance1 < circleRadius || distance2 < circleRadius || distance3 < circleRadius); // if it touches any point
    }

    public void keyPressed() {
        if (key == ' ' && circleY >= 375 && gamescreen == 1) { // If space bar is pressed
            if (!isJumping) { // Jump only if not already jumping
                isJumping = true; // Start jumping
            }

        }
        if (key == ' ' && gamescreen == 0) {
            gamescreen = 1;
        }

    }

    public void mousePressed() {
        if (gameOver == true && mouseX >= 175 && mouseX <= 425 && mouseY >= 240 && mouseY <= 340)// coordinates of the
                                                                                                 // play box
        {
            reset();
        }

    }

    // Making it only count the hit for one frame
    public void HitWriting() {
        if (frameCountWhenHit < frameCount - (60 * (moveSpeed))) {
            hittable = true;
        }

        if (hittable == false && frameCountWhenHit < frameCount - 1 && lives != 0) {
            fill(255, 0, 0);
            textSize(32);
            text("HIT!", 130, 320);

        }
        fill(240, 207, 60); //resets ball fill to yellow

    }
//All the effects of getting hit like decreasing the lives and reseting the frame count
    public void CollidingResults() {
        System.out.println("collided");

        lives--;

        if (lives <= 0) {
            gameOver = true;
        }
        collided = false;
        circleY = 375;
        hittable = false;
        frameCountWhenHit = frameCount;
    }

    public void Jump() {
        circleY -= 1.5 * moveSpeed; // speed of moving up
        if (circleY < 375 - jumpHeight) { // Check if reached jump height
            isJumping = false; // Start falling
        } else if (circleY < 375) { // If falling, reset to ground level
            circleY += 1.5 * moveSpeed; // Move down

        }

    }

    public void DrawTriangles() {
        fill(150); // Fill color for the triangle
        noStroke(); // No outline
        triangle(triangleX1, 400, triangleX1 + 10, 400, triangleX1 + 5, 375);
        triangle(triangleX2, 400, triangleX2 + 10, 400, triangleX2 + 5, 375);
        triangle(triangleX3, 400, triangleX3 + 10, 400, triangleX3 + 5, 375);
        triangle(triangleX4, 400, triangleX4 + 10, 400, triangleX4 + 5, 375);
        triangle(triangleX5, 400, triangleX5 + 10, 400, triangleX5 + 5, 375);
    }

    public void MoveTrianglesandResetThem() {
        //Moves triangles based on movespeed
        triangleX1 -= moveSpeed;
        triangleX2 -= moveSpeed;
        triangleX3 -= moveSpeed;
        triangleX4 -= moveSpeed;
        triangleX5 -= moveSpeed;
//Reseting triangles when they reach the edge
        if (triangleX1 < -10) {
            triangleX1 = width;
            score++;

        }

        if (triangleX2 < -10) {
            triangleX2 = width;
            score++;
        }

        if (triangleX3 < -10) {
            triangleX3 = width;
            score++;
        }

        if (triangleX4 < -10) {
            triangleX4 = width;
            score++;
        }

        if (triangleX5 < -10) {
            triangleX5 = width;
            score++;
        }
        if (triangleX5 == width)
            moveSpeed = moveSpeed + (0.3); //increasing movespeed when the last triangle hits the edge
    }
    //Checking collision on ball for each triangle to mark that they touched
    public void CheckiftheBallCollided() {
        if (checkCollision(triangleX1) || checkCollision(triangleX2) ||
                checkCollision(triangleX3) || checkCollision(triangleX4) ||
                checkCollision(triangleX5)) {
            collided = true;

        }
    }
    //Game over screen
    public void gameOver() {
        fill(235, 130, 49);
        textSize(50);
        textAlign(CENTER, CENTER);
        text("Game Over!\n Final Score: " + score, width / 2, height / 2 - 50);

        strokeWeight(6);
        stroke(250, 196, 35);
        fill(150);
        rect(175, 240, 250, 100);// X, Y size width, size height,
        fill(100);
        text("Play again", 300, 275);

    }
    //Intro screen
    public void intro() {
        background(255);
        fill(0);
        // title of the game in the middle
        textSize(30);
        textAlign(CENTER, CENTER);
        text("This is the dinosaur game!", width / 2, 135);

        // instructions for the game
        text("Press space to jump in the game", width / 2, 165);
        text("Try and avoid the obstacles", width / 2, 200);
        text("You get 3 lives", width / 2,
                235);

        // How to Start
        text("Press space to start", width / 2, 280);

    }
    //Resetting after play again button
    public void reset() {
        triangleX1 = 300;
        triangleX2 = 450;
        triangleX3 = 575;
        triangleX4 = 700;
        triangleX5 = 825;
        moveSpeed = 1.2;
        isJumping = false;
        lives = 3;
        gameOver = false;
        hittable = true;
        score = 0;
        frameCountWhenHit = 0;

    }

}
