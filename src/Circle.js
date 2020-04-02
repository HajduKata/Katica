export default class Circle {
    x;
    y;
    radius;

    constructor() {
        let colors = ["Tomato", "SandyBrown", "Orange", "Gold", "YellowGreen", "SeaGreen", "Turquoise", "Teal", "SkyBlue", "SteelBlue", "SlateBlue", "Plum",];
        // Randomize colors
        let randomColor = Math.floor(Math.random() * 12);
        this.color = colors[randomColor];
        // Randomize positions
        this.x = Math.floor(Math.random() * 890) + 70;
        this.y = Math.floor(Math.random() * 450) + 70;
        // Randomize size
        this.radius = Math.floor(Math.random() * 20) + 20;
    }

    get x() {
        return this.x;
    }
    get y() {
        return this.y;
    }
    get radius() {
        return this.radius;
    }
}