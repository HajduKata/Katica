export default class Katica {
    x;
    y;
    width;
    height;
    rotation;
    image;
    isResentful;

    constructor() {
        this.image = document.getElementById("katicaImg");
        this.image.src = '../resources/pictures/katica.png';
        this.x = 10;
        this.y = 10;
        this.width = 40;
        this.height = 50;
        this.rotation = 90;
        this.isResentful = false;
    }

    get x() {
        return this.x;
    }
    get y() {
        return this.y;
    }
    get width() {
        return this.width;
    }
    get height() {
        return this.height;
    }
    get rotation() {
        return this.rotation;
    }

    updateImage() {
        if(this.isResentful) {
            this.image.src = '../resources/pictures/katicaResentful.png';
        } else {
            this.image.src = '../resources/pictures/katica.png';
        }
    }
}