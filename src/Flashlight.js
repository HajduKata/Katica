export default class Flashlight {
    x;
    y;
    width;
    height;
    rotation;
    image;
    isSwitchedOn;

    constructor() {
        this.image = document.getElementById("flashlightImg");
        this.image.src = '../resources/pictures/flashlightOff.png';
        this.x = 900;
        this.y = 10;
        this.width = 35;
        this.height = 70;
        this.rotation = 90;
        this.isSwitchedOn = false;
    }

    updateImage() {
        if(this.isSwitchedOn) {
            this.image.src = '../resources/pictures/flashlightOn.png';
        } else {
            this.image.src = '../resources/pictures/flashlightOff.png';
        }
    }
}