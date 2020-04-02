export default class Katica {
    x;
    y;
    width;
    height;
    rotation;
    katicaImg;

    constructor() {
        this.katicaImg = document.getElementById("katicaImg");
        this.katicaImg.src = '../resources/pictures/katica.png';
        this.x = 10;
        this.y = 10;
        this.width = 40;
        this.height = 50;
        this.rotation = 90;
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

}