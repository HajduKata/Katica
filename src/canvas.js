import Rectangle from "./Rectangle.js";
import Circle from "./Circle.js";
import Katica from "./Katica.js";
import Flashlight from "./Flashlight.js";

let canvas = document.getElementById("myCanvas");
let ctx = canvas.getContext("2d");
let playing = true;

// Initialize Objects
let items = [];

// Initialize Flashlight
let flashlight = new Flashlight();
flashlight.image.onload = drawFlashlight();

// Initialize Katica
let katica = new Katica();
katica.image.onload = drawKatica();
window.addEventListener('keydown', moveKatica);
window.addEventListener('keydown', flashlightSwitching);
canvas.addEventListener('click', e => waitForClickOnKatica(e));

// Functions that run
let requestAnimationFrame = window.requestAnimationFrame || window.webkitRequestAnimationFrame;
initItems();
animate();

function initItems() {
    let index;
    for (index = 0; index < 3; index++) {
        items[index] = new Rectangle();
    }
    for (index = 3; index < 6; index++) {
        items[index] = new Circle();
    }
}

// Animate function, redraws the ladybug and flashlight and draws the objects
function animate() {
    if (playing) {
        requestAnimationFrame(animate);
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        drawItems();
        drawFlashlight();
        drawKatica();
    }
}

function drawItems() {
    for (let i = 0; i < 6; i++) {
        let item = items[i];
        if (0 <= i && i < 3) {
            ctx.fillStyle = item.color;
            ctx.beginPath();
            ctx.fillRect(item.x, item.y, item.width, item.height);
        } else {
            ctx.fillStyle = item.color;
            ctx.beginPath();
            ctx.arc(item.x, item.y, item.radius, 0, 2 * Math.PI);
            ctx.fill();
        }
    }
}

function drawFlashlight() {
    flashlight.updateImage();
    ctx.save();
    ctx.translate(flashlight.x + flashlight.width / 2, flashlight.y + flashlight.height / 2);
    ctx.rotate((flashlight.rotation - 90) * Math.PI / 180.0);
    ctx.translate(-flashlight.x - flashlight.width / 2, -flashlight.y - flashlight.height / 2);
    ctx.drawImage(flashlight.image, flashlight.x, flashlight.y, flashlight.width, flashlight.height);
    ctx.restore();
}

function drawKatica() {
    katica.updateImage();
    ctx.save();
    ctx.translate(katica.x + katica.width / 2, katica.y + katica.height / 2);
    ctx.rotate((katica.rotation - 90) * Math.PI / 180.0);
    ctx.translate(-katica.x - katica.width / 2, -katica.y - katica.height / 2);
    ctx.drawImage(katica.image, katica.x, katica.y, katica.width, katica.height);
    ctx.restore();
}

// Event operator for clicking (on katica)
function waitForClickOnKatica(e) {
    if (katica.isResentful) {
        if (e.clientX - 9 > katica.x && e.clientX - 9 < katica.x + katica.width &&
            e.clientY - 26 > katica.y && e.clientY - 26 < katica.y + katica.height) {
            katica.isResentful = false;
            window.addEventListener('keydown', moveKatica);
        }
    }
}

// Interacting with the flashlight
function flashlightSwitching(event) {
    let key = event.key;
    // Switching the flashlight on or off by pressing 'F'
    if(key === 'f' || key === 'F') {
        flashlight.isSwitchedOn = !flashlight.isSwitchedOn;
    } // Rotating the flashlight counterclockwise
    else if(key === 'q' || key === 'Q') {
        flashlight.rotation -= 15;
    } // Rotating the flashlight clockwise
    else if(key === 'e' || key === 'E') {
        flashlight.rotation += 15;
    }
}

// Moving the ladybug via keypressed event
function moveKatica(event) {
    let key = event.key;
    // Up
    if ((key === 'ArrowUp' || key === 'w' || key === 'W')) {
        katica.x -= 5 * Math.cos(katica.rotation * Math.PI / 180);
        katica.y -= 5 * Math.sin(katica.rotation * Math.PI / 180);
        if (collision() || hitEdgeOfCanvas()) {
            katica.x += 15 * Math.cos(katica.rotation * Math.PI / 180);
            katica.y += 15 * Math.sin(katica.rotation * Math.PI / 180);
            katicaCollided();
        }
    }// Down
    else if ((key === 'ArrowDown' || key === 's' || key === 'S') && katica.y < canvas.height - katica.height) {
        katica.x += 5 * Math.cos(katica.rotation * Math.PI / 180);
        katica.y += 5 * Math.sin(katica.rotation * Math.PI / 180);
        if (collision() || hitEdgeOfCanvas()) {
            katica.x -= 15 * Math.cos(katica.rotation * Math.PI / 180);
            katica.y -= 15 * Math.sin(katica.rotation * Math.PI / 180);
            katicaCollided();
        }
    }// Left
    else if (key === 'ArrowLeft' || key === 'a' || key === 'A') {
        katica.rotation -= 15;
    }// Right
    else if (key === 'ArrowRight' || key === 'd' || key === 'D') {
        katica.rotation += 15;
    }
}

function katicaCollided() {
    window.removeEventListener('keydown', moveKatica);
    katica.isResentful = true;
    katica.rotation += 180;
}

function hitEdgeOfCanvas() {
    return katica.x <= 0 || katica.y <= 0 || katica.x >= canvas.width - katica.width || katica.y >= canvas.height - katica.height;
}

// Checks if there's collision between the ladybug and the objects
function collision() {
    var collision = false;
    for (let i = 0; i < 6; i++) {
        // Rectangle
        if (0 <= i && i < 3) {
            // Get ladybug vertexes
            let katicaVertexes = [];
            katicaVertexes[0] = {x: katica.x, y: katica.y};
            katicaVertexes[1] = {x: katica.x + katica.width, y: katica.y};
            katicaVertexes[2] = {x: katica.x + katica.width, y: katica.y + katica.height};
            katicaVertexes[3] = {x: katica.x, y: katica.y + katica.height};
            // Get rectangle vertexes
            let rectangleVertexes = [];
            rectangleVertexes[0] = {x: items[i].x, y: items[i].y};
            rectangleVertexes[1] = {x: items[i].x + items[i].width, y: items[i].y};
            rectangleVertexes[2] = {x: items[i].x + items[i].width, y: items[i].y + items[i].height};
            rectangleVertexes[3] = {x: items[i].x, y: items[i].y + items[i].height};

            if (doPolygonsIntersect(katicaVertexes, rectangleVertexes)) {
                collision = true;
            }

        } // Circle
        else {
            var cx, cy;
            var angleOfRad = degToRad(-katica.rotation);
            var rectCenterX = katica.x + katica.width / 2;
            var rectCenterY = katica.y + katica.height / 2;
            var rotateCircleX = Math.cos(angleOfRad) * (items[i].x - rectCenterX) - Math.sin(angleOfRad) * (items[i].y - rectCenterY) + rectCenterX;
            var rotateCircleY = Math.sin(angleOfRad) * (items[i].x - rectCenterX) + Math.cos(angleOfRad) * (items[i].y - rectCenterY) + rectCenterY;
            if (rotateCircleX < katica.x) {
                cx = katica.x;
            } else if (rotateCircleX > katica.x + katica.width) {
                cx = katica.x + katica.width;
            } else {
                cx = rotateCircleX;
            }
            if (rotateCircleY < katica.y) {
                cy = katica.y;
            } else if (rotateCircleY > katica.y + katica.height) {
                cy = katica.y + katica.height;
            } else {
                cy = rotateCircleY;
            }
            if (distance(rotateCircleX, rotateCircleY, cx, cy) < items[i].radius) {
                collision = true;
            }

            function degToRad(deg) {
                return deg * Math.PI / 180;
            }

            function distance(x1, y1, x2, y2) {
                return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            }
        }
    }
    return collision;
}

/**
 * Helper function to determine whether there is an intersection between the two polygons described
 * by the lists of vertices. Uses the Separating Axis Theorem
 *
 * @param a an array of connected points [{x:, y:}, {x:, y:},...] that form a closed polygon
 * @param b an array of connected points [{x:, y:}, {x:, y:},...] that form a closed polygon
 * @return true if there is any intersection between the 2 polygons, false otherwise
 */
function doPolygonsIntersect(a, b) {
    var polygons = [a, b];
    var minA, maxA, projected, i, i1, j, minB, maxB;

    for (i = 0; i < polygons.length; i++) {
        // For each polygon, look at each edge of the polygon, and determine if it separates the two shapes
        var polygon = polygons[i];
        for (i1 = 0; i1 < polygon.length; i1++) {

            // Grab 2 vertices to create an edge
            var i2 = (i1 + 1) % polygon.length;
            var p1 = polygon[i1];
            var p2 = polygon[i2];

            // Find the line perpendicular to this edge
            var normal = {x: p2.y - p1.y, y: p1.x - p2.x};

            minA = null;
            maxA = null;
            // For each vertex in the first shape, project it onto the line perpendicular to the edge
            // and keep track of the min and max of these values
            for (j = 0; j < a.length; j++) {
                projected = normal.x * a[j].x + normal.y * a[j].y;
                if (minA == null || projected < minA) {
                    minA = projected;
                }
                if (maxA == null || projected > maxA) {
                    maxA = projected;
                }
            }

            // For each vertex in the second shape, project it onto the line perpendicular to the edge
            // and keep track of the min and max of these values
            minB = null;
            maxB = null;
            for (j = 0; j < b.length; j++) {
                projected = normal.x * b[j].x + normal.y * b[j].y;
                if (minB == null || projected < minB) {
                    minB = projected;
                }
                if (maxB == null || projected > maxB) {
                    maxB = projected;
                }
            }

            // If there is no overlap between the projects, the edge we are looking at separates the two
            // polygons, and we know there is no overlap
            if (maxA < minB || maxB < minA) {
                return false;
            }
        }
    }
    return true;

}