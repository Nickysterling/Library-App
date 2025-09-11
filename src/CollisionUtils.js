export function rectIntersect(a, b) {
    return !(
        a.x + a.width <= b.x ||
        a.x >= b.x + b.width ||
        a.y + a.height <= b.y ||
        a.y >= b.y + b.height
    );
}

export function pointInPolygon(point, polygon, offsetX, offsetY) {
    let inside = false;
    const x = point.x, y = point.y;

    for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
        const xi = polygon[i].x + offsetX, yi = polygon[i].y + offsetY;
        const xj = polygon[j].x + offsetX, yj = polygon[j].y + offsetY;

        const intersect = ((yi > y) !== (yj > y)) &&
                          (x < (xj - xi) * (y - yi) / (yj - yi) + xi);

        if (intersect) inside = !inside;
    }

    return inside;
}

export function lineIntersect(p1, p2, p3, p4) {
    function ccw(a, b, c) {
        return (c.y - a.y) * (b.x - a.x) > (b.y - a.y) * (c.x - a.x);
    }
    return (ccw(p1, p3, p4) !== ccw(p2, p3, p4)) &&
           (ccw(p1, p2, p3) !== ccw(p1, p2, p4));
}
