import processing.core.*;

public class App extends PApplet {
    float triangleX1 = 300;
    float triangleX2 = 450;
    float triangleX3 = 575;
    float triangleX4 = 700;
    float triangleX5 = 825;
    double moveSpeed = 1.2; // Speed of movement to the left
    float resetPosition = width; // Reset position to the right side of the screen

    float circleY = 375; // Initial Y position of the circle
    float circleRadius = 20;
    float jumpHeight = 60;
    boolean isJumping = false;
    int lives = 3;
    boolean gameOver = false;
    boolean hittable = true;
    int score = 0;
    int frameCountWhenHit = 0;

    boolean checkCollision(float triangleX) {
        // Get the triangle vertices
        float triangleY = 400; // Base Y position of the triangle
        float triangleBaseY = 375; // Peak Y position of the triangle

        // Check distances to the triangle's base points
        float distance1 = dist(150, circleY, triangleX, triangleY);
        float distance2 = dist(150, circleY, triangleX + 10, triangleY);
        float distance3 = dist(150, circleY, triangleX + 5, triangleBaseY);

        return (distance1 < circleRadius || distance2 < circleRadius || distance3 < circleRadius);
    }

    public static void main(String[] args) {
        PApplet.main("App");

    }

    public void settings() {
        size(600, 400); // Size width, Size height
    }

    public void setup() {
        background(5, 16, 84);

    }

    public void draw() {
        background(5, 16, 84);
        if (frameCountWhenHit < frameCount - (60 * (moveSpeed))) {
            hittable = true;
        }

        if (hittable == false && frameCountWhenHit < frameCount - 5 && lives != 0) {
            fill(255, 0, 0);
            textSize(32);
            text("HIT!", 130, 320);

        }

        if (gameOver) {
            fill(235, 130, 49);
            textSize(50);
            textAlign(CENTER, CENTER );
            text("Game Over!", width / 2, height / 2 - 50);

            strokeWeight(6);
            stroke(250, 196, 35);
            fill(150);
            rect(200, 250, 100, 50);// size width, size height, X, Y
            fill(100);
            text("Play again", 320, 270);

            fill(255);
            textSize(30);
            textAlign(RIGHT, TOP);
            text("Final Score: " + score, 385, 40);

            return;

        }

        fill(240, 207, 60);
        if (isJumping) {
            circleY -= 1.5 * moveSpeed; // speed of moving up
            if (circleY < 375 - jumpHeight) { // Check if reached jump height
                isJumping = false; // Start falling
            }
        } else {
            if (circleY < 375) { // If falling, reset to ground level
                circleY += 1.5 * moveSpeed; // Move down
            }
        }
        stroke(235, 130, 49);
        strokeWeight(5);
        ellipse(150, circleY, circleRadius * 2, circleRadius * 2); // Draw circle
        triangleX1 -= moveSpeed;
        triangleX2 -= moveSpeed;
        triangleX3 -= moveSpeed;
        triangleX4 -= moveSpeed;
        triangleX5 -= moveSpeed;

        if (triangleX1 < -10){
            triangleX1 = width;
            score++;
            
        }
           
        if (triangleX2 < -10){
            triangleX2 = width;
            score++;
        }
            

        if (triangleX3 < -10){
            triangleX3 = width;
            score++;
        }

        if (triangleX4 < -10){
            triangleX4 = width;
            score++;
        }

        if (triangleX5 < -10){
            triangleX5 = width;
                score++;
            }
        if (triangleX5 == width)
            moveSpeed = moveSpeed + (0.3);

        fill(150); // Fill color for the triangle
        noStroke(); // No outline
        triangle(triangleX1, 400, triangleX1 + 10, 400, triangleX1 + 5, 375);
        triangle(triangleX2, 400, triangleX2 + 10, 400, triangleX2 + 5, 375);
        triangle(triangleX3, 400, triangleX3 + 10, 400, triangleX3 + 5, 375);
        triangle(triangleX4, 400, triangleX4 + 10, 400, triangleX4 + 5, 375);
        triangle(triangleX5, 400, triangleX5 + 10, 400, triangleX5 + 5, 375);

        // Check for collisions with each triangle
        boolean collided = false; // Flag for collision detection
        if (hittable) {
            if (checkCollision(triangleX1) || checkCollision(triangleX2) ||
                    checkCollision(triangleX3) || checkCollision(triangleX4) ||
                    checkCollision(triangleX5)) {
                collided = true;

            }
        }

        if (collided) {
            // fill(255, 0, 0);
            // textSize(32);
            System.out.println("hit");

            lives--;

            if (lives <= 0) {
                gameOver = true;
            }
            collided = false;
            circleY = 375;
            hittable = false;
            frameCountWhenHit = frameCount;
            // text("HIT!", 130, 320);
        }
        fill(255);
        textSize(40);
        text("Lives: " + lives, 390, 30);
        fill(255);
        textSize(40);
        text("Score: " + score , 390, 60);

    }

    public void keyPressed() {
        if (key == ' ' && circleY >= 375) { // If space bar is pressed
            if (!isJumping) { // Jump only if not already jumping
                isJumping = true; // Start jumping
            }

        }
    }

    public void playAgain() {
        if (mousePressed && mouseX >= 250 && mouseX <= 450 && mouseY >= 250 && mouseY <= 350) {
            reset();
        }
    }

    public void reset() {

    }

}
