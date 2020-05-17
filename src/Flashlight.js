export default class Flashlight {
    x;
    y;
    width;
    height;
    rotation;
    image;
    isSwitchedOn;

    constructor() {
        let flashlight = document.getElementById("flashlightImg");
        this.image = flashlight;
        flashlight.src = '../resources/pictures/flashlightOff.png';
        this.x = 900;
        flashlight.style.left = this.x + 'px';
        this.y = 10;
        flashlight.style.top = this.y + 'px';
        this.width = 35;
        flashlight.width = this.width;
        this.height = 70;
        flashlight.height = this.height;
        this.rotation = 0;
        flashlight.style.transform = 'rotate(' + this.rotation + 'deg)';
        this.isSwitchedOn = false;
    }

    mouseListenerOnFlashlight() {
        let flashlight = this.image;
        // (1) start the process
        flashlight.onmousedown = function (event) {
            // (2) prepare to moving: make absolute and on top by z-index
            // for better positioning
            let shiftX = event.clientX - flashlight.getBoundingClientRect().left;
            let shiftY = event.clientY - flashlight.getBoundingClientRect().top;
            flashlight.style.position = 'absolute';
            flashlight.style.zIndex = 1000 + 'px';
            // move it out of any current parents directly into body to make it positioned relative to the body
            document.body.append(flashlight);
            // ...and put that absolutely positioned ball under the pointer
            moveAt(event.pageX, event.pageY);
            // centers the ball at (pageX, pageY) coordinates
            function moveAt(pageX, pageY) {
                flashlight.style.left = pageX - shiftX + 'px';
                flashlight.style.top = pageY - shiftY + 'px';
            }
            function onMouseMove(event) {
                moveAt(event.pageX, event.pageY);
            }
            // (3) move the ball on mousemove
            document.addEventListener('mousemove', onMouseMove);
            // (4) drop the ball, remove unneeded handlers
            flashlight.onmouseup = function () {
                document.removeEventListener('mousemove', onMouseMove);
                flashlight.onmouseup = null;
            };
        };
        flashlight.ondragstart = function () {
            return false;
        };
    }

    get rotation() {
        return this.rotation;
    }

    set rotation(value) {
        this.rotation = value;
        // this.image.style.transform = 'rotate(' + this.rotation + 'deg)';
    }

    updateImage() {
        if (this.isSwitchedOn) {
            this.image.src = '../resources/pictures/flashlightOn.png';
        } else {
            this.image.src = '../resources/pictures/flashlightOff.png';
        }
        this.image.style.transform = 'rotate(' + this.rotation + 'deg)';
    }
}