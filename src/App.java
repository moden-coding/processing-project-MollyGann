import processing.core.*;

public class App extends PApplet {
    float triangleX1 = 400; // X-position for the first triangle
    float triangleX2 = 525; // X-position for the second triangle
    float triangleX3 = 675; // X-position for the third triangle
    float triangleX4 = 800; // X-position for the fourth triangle
    float triangleX5 = 925; // X-position for the fifth triangle
    double moveSpeed = 1; // Speed of movement to the left
    float resetPosition = width; // Reset position to the right side of the screen

    float circleY = 373; // Initial Y position of the circle
    float circleRadius = 20; // Radius of the circle
    float jumpHeight = 50; // Height of the jump
    boolean isJumping = false; // Track if the circle is jumping
    int lives = 3;
    boolean gameOver = false; // Track game over state
    boolean hittable = true;

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
        size(600, 400);
    }

    public void setup() {
        background(5, 16, 84);

    }

    public void draw() {
        if(frameCountWhenHit > frameCount - 1000){
            hittable = true;
        }

        background(5, 16, 84);
        if (gameOver) {
            fill(255, 0, 0);
            textSize(32);
            text("Game Over!", width / 2 - 80, height / 2);
            return; // Stop the draw loop
        }

        fill(240, 207, 60);
        if (isJumping) {
            circleY -= 5; // Move up
            if (circleY < 373 - jumpHeight) { // Check if reached jump height
                isJumping = false; // Start falling
            }
            // } else {
            // if (circleY < 373) { // If falling, reset to ground level
            // circleY += 5; // Move down
            // }
        }
        stroke(235, 130, 49);
        strokeWeight(7);
        ellipse(150, circleY, circleRadius * 2, circleRadius * 2); // Draw circle
        triangleX1 -= moveSpeed;
        triangleX2 -= moveSpeed;
        triangleX3 -= moveSpeed;
        triangleX4 -= moveSpeed;
        triangleX5 -= moveSpeed;

        if (triangleX1 < -10)
            triangleX1 = width;
        if (triangleX2 < -10)
            triangleX2 = width;
        if (triangleX3 < -10)
            triangleX3 = width;
        if (triangleX4 < -10)
            triangleX4 = width;
        if (triangleX5 < -10)
            triangleX5 = width;
        if (triangleX5 == width) {
            moveSpeed = moveSpeed + (0.3);
        }

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
            lives--;
            if (lives <= 0) {
                gameOver = true;
            }
            collided = false;
            circleY = 373;
            hittable = false;
            frameCountWhenHit = frameCount;
        }
        fill(255);
        textSize(16);
        text("Lives: " + lives, 390, 20);
        // Move each triangle to the left
        triangleX1 -= moveSpeed;
        triangleX2 -= moveSpeed;
        triangleX3 -= moveSpeed;
        triangleX4 -= moveSpeed;
        triangleX5 -= moveSpeed;

        if (triangleX1 < -10)
            triangleX1 = width;
        if (triangleX2 < -10)
            triangleX2 = width;
        if (triangleX3 < -10)
            triangleX3 = width;
        if (triangleX4 < -10)
            triangleX4 = width;
        if (triangleX5 < -10)
            triangleX5 = width;
    }

    public void keyPressed() {
        if (key == ' ') { // If space bar is pressed
            if (!isJumping) { // Jump only if not already jumping
                isJumping = true; // Start jumping
            }

        }
    }
}
