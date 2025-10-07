import { rectIntersect, pointInPolygon, lineIntersect } from "../utils/CollisionUtils.js";

export class Collisions {
    constructor(objects) {
        this.objects = objects;
    }

    isBlocked(box) {
        return this.objects.some(obj => {
            if (obj.type === "rect") {
                return rectIntersect(box, obj);
            }
            if (obj.type === "polygon") {
                const corners = [
                    { x: box.x, y: box.y },
                    { x: box.x + box.width, y: box.y },
                    { x: box.x + box.width, y: box.y + box.height },
                    { x: box.x, y: box.y + box.height }
                ];

                // 1. Any corner inside poly?
                if (corners.some(corner =>
                    pointInPolygon(corner, obj.polygon, obj.x, obj.y)
                )) return true;

                // 2. Any edge intersection?
                const polyPoints = obj.polygon.map(p => ({
                    x: obj.x + p.x,
                    y: obj.y + p.y
                }));
                const boxEdges = [
                    [corners[0], corners[1]],
                    [corners[1], corners[2]],
                    [corners[2], corners[3]],
                    [corners[3], corners[0]]
                ];

                for (let i = 0; i < polyPoints.length; i++) {
                    const a = polyPoints[i];
                    const b = polyPoints[(i + 1) % polyPoints.length];
                    for (const [p1, p2] of boxEdges) {
                        if (lineIntersect(p1, p2, a, b)) return true;
                    }
                }

                return false;
            }
            return false;
        });
    }

    drawDebug(ctx) {
        ctx.save();
        ctx.strokeStyle = "red";
        ctx.lineWidth = 1;

        this.objects.forEach(obj => {
            if (obj.type === "rect") {
                ctx.strokeRect(obj.x, obj.y, obj.width, obj.height);
            }
            if (obj.type === "polygon") {
                ctx.beginPath();
                const pts = obj.polygon;
                ctx.moveTo(pts[0].x + obj.x, pts[0].y + obj.y);
                for (let i = 1; i < pts.length; i++) {
                    ctx.lineTo(pts[i].x + obj.x, pts[i].y + obj.y);
                }
                ctx.closePath();
                ctx.stroke();
            }
        });

        ctx.restore();
    }
}
