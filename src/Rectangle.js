export default class Rectangle {
    x;
    y;
    width;
    height;

    constructor(color) {
        let colors = ["Tomato", "SandyBrown", "Orange", "Gold", "YellowGreen", "SeaGreen", "Turquoise", "Teal", "SkyBlue", "SteelBlue", "SlateBlue", "Plum",];
        // Randomize colors
        let randomColor = Math.floor(Math.random() * 12);
        this.color = colors[randomColor];
        this.color = color;
        // Randomize positions
        this.x = Math.floor(Math.random() * 880) + 50;
        this.y = Math.floor(Math.random() * 480) + 50;
        // Randomize size
        this.width = Math.floor(Math.random() * 50) + 20;
        this.height = Math.floor(Math.random() * 50) + 20;
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
}