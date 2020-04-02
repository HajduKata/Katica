import Rectangle from "./Rectangle.js";
import Circle from "./Circle.js";

let c = document.getElementById("myCanvas");
let ctx = c.getContext("2d");
let playing = true;

// Initialize objects
let items = [];

function initItems() {
    let index = 0;
    for (index = 0; index < 3; index++) {
        items[index] = new Rectangle();
    }
    for (index = 3; index < 6; index++) {
        items[index] = new Circle();
    }
}

// Draw objects function
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

// Ladybug initialization
var katicaImg = document.getElementById("katicaImg");
katicaImg.src = '../resources/pictures/katica.png';
katicaImg.onload = drawKatica;
var katicaX = 10;
var katicaY = 10;
var katicaWidth = 40;
var katicaHeight = 50;
var katicaRotation = 90;
window.addEventListener('keydown', moveKatica);

var requestAnimationFrame = window.requestAnimationFrame ||
    window.webkitRequestAnimationFrame;

initItems();
animate();

// Animate function, moves the ladybug and draws the objects
function animate() {
    if (playing) {
        requestAnimationFrame(animate);
        ctx.clearRect(0, 0, c.width, c.height);
        drawItems();
        drawKatica();
    }
}

// Draw ladybug function
function drawKatica() {
    ctx.save();
    ctx.translate(katicaX + katicaWidth / 2, katicaY + katicaHeight / 2);
    ctx.rotate((katicaRotation - 90) * Math.PI / 180.0);
    ctx.translate(-katicaX - katicaWidth / 2, -katicaY - katicaHeight / 2);
    ctx.drawImage(katicaImg, katicaX, katicaY, katicaWidth, katicaHeight);
    ctx.restore();
}

// Moving the ladybug via keypressed event
function moveKatica(event) {
    let key = event.key;
    // Up
    if ((key === 'ArrowUp' || key === 'w' || key === 'W') && katicaY > 0) {
        katicaX -= 5 * Math.cos(katicaRotation * Math.PI / 180);
        katicaY -= 5 * Math.sin(katicaRotation * Math.PI / 180);
        if (collision()) {
            katicaX += 15 * Math.cos(katicaRotation * Math.PI / 180);
            katicaY += 15 * Math.sin(katicaRotation * Math.PI / 180);
        }
    }// Down
    else if ((key === 'ArrowDown' || key === 's' || key === 'S') && katicaY < c.height - katicaHeight) {
        katicaX += 5 * Math.cos(katicaRotation * Math.PI / 180);
        katicaY += 5 * Math.sin(katicaRotation * Math.PI / 180);
        if (collision()) {
            katicaX -= 15 * Math.cos(katicaRotation * Math.PI / 180);
            katicaY -= 15 * Math.sin(katicaRotation * Math.PI / 180);
        }
    }// Left
    else if (key === 'ArrowLeft' || key === 'a' || key === 'A') {
        katicaRotation -= 15;
    }// Right
    else if (key === 'ArrowRight' || key === 'd' || key === 'D') {
        katicaRotation += 15;
    }
}

// Checks if there's collision between the ladybug and the objects
function collision() {
    var collision = false;
    for (let i = 0; i < 6; i++) {
        // Rectangle
        if (0 <= i && i < 3) {
            // Get ladybug vertexes
            katicaVertexes[0] = {x: katicaX, y: katicaY};
            katicaVertexes[1] = {x: katicaX + katicaWidth, y: katicaY};
            katicaVertexes[2] = {x: katicaX + katicaWidth, y: katicaY + katicaHeight};
            katicaVertexes[3] = {x: katicaX, y: katicaY + katicaHeight};
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
            var angleOfRad = degToRad(-katicaRotation);
            var rectCenterX = katicaX + katicaWidth / 2;
            var rectCenterY = katicaY + katicaHeight / 2;
            var rotateCircleX = Math.cos(angleOfRad) * (items[i].x - rectCenterX) - Math.sin(angleOfRad) * (items[i].y - rectCenterY) + rectCenterX;
            var rotateCircleY = Math.sin(angleOfRad) * (items[i].x - rectCenterX) + Math.cos(angleOfRad) * (items[i].y - rectCenterY) + rectCenterY;
            if (rotateCircleX < katicaX) {
                cx = katicaX;
            } else if (rotateCircleX > katicaX + katicaWidth) {
                cx = katicaX + katicaWidth;
            } else {
                cx = rotateCircleX;
            }
            if (rotateCircleY < katicaY) {
                cy = katicaY;
            } else if (rotateCircleY > katicaY + katicaHeight) {
                cy = katicaY + katicaHeight;
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
                console.log("polygons don't intersect!");
                return false;
            }
        }
    }
    console.log("polygons intersect!");
    return true;
}