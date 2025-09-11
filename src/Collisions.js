// Collisions.js
export class Collisions {
    constructor(rawObjects) {
        // parse raw objects into a standard format
        this.objects = rawObjects
            .map(obj => {
                if (obj.width && obj.height) {
                    return { type: 'rect', x: obj.x, y: obj.y, width: obj.width, height: obj.height };
                } else if (obj.polygon) {
                    const points = obj.polygon.map(p => ({ x: obj.x + p.x, y: obj.y + p.y }));
                    return { type: 'polygon', points };
                }
                return null;
            })
            .filter(obj => obj !== null);
    }

    debugLog() {
        console.log("Loaded collisions:", this.objects);
    }

    debugDraw(ctx) {
        ctx.save();
        ctx.strokeStyle = "red";
        ctx.lineWidth = 2;

        this.objects.forEach(obj => {
            if (obj.type === 'rect') {
                ctx.strokeRect(obj.x, obj.y, obj.width, obj.height);
            } else if (obj.type === 'polygon') {
                const points = obj.points;
                ctx.beginPath();
                ctx.moveTo(points[0].x, points[0].y);
                for (let i = 1; i < points.length; i++) ctx.lineTo(points[i].x, points[i].y);
                ctx.closePath();
                ctx.stroke();
            }
        });

        ctx.restore();
    }
}




// export class Collisions {
//     constructor(objects) {
//         this.rawObjects = rawObjects;
//         this.collisionObjects = [];
//     }

    
//     debugLog() {
//         console.log("Loaded collisions:", this.rawObjects);
//     }

//     debugDraw(ctx) {
//         ctx.save();
//         ctx.strokeStyle = "red";
//         ctx.lineWidth = 2;

//         this.rawObjects.forEach(obj => {
//             if (obj.width && obj.height) {
//                 ctx.strokeRect(obj.x, obj.y, obj.width, obj.height);
//             } else if (obj.polygon) {
//                 ctx.beginPath();
//                 const points = obj.polygon.map(p => ({x: obj.x + p.x, y: obj.y + p.y}));
//                 ctx.moveTo(points[0].x, points[0].y);
//                 for (let i = 1; i < points.length; i++) ctx.lineTo(points[i].x, points[i].y);
//                 ctx.closePath();
//                 ctx.stroke();
//             }
//         });

//         ctx.restore();
//     }
// }



    // parseObject(obj) {
    //     if (obj.polygon) {
    //         return {
    //             type: "polygon",
    //             points: obj.polygon.map(p => ({
    //                 x: obj.x + p.x,
    //                 y: obj.y + p.y
    //             }))
    //         };
    //     }
    //     if (obj.width && obj.height) {
    //         return {
    //             type: "rect",
    //             x: obj.x,
    //             y: obj.y,
    //             width: obj.width,
    //             height: obj.height
    //         };
    //     }
    //     return null;
    // }

    // collidesWith(player) {
    //     for (const obj of this.collisionObjects) {
    //         if (obj.type === "rect" && rectIntersect(player, obj)) return true;
    //         if (obj.type === "polygon" && pointInPolygon(player, obj.points)) return true;
    //     }
    //     return false;
    // }

    // debugDraw(ctx) {
    //     ctx.save();
    //     ctx.strokeStyle = "red";
    //     ctx.lineWidth = 2;
    //     for (const obj of this.collisionObjects) {
    //         if (obj.type === "rect") {
    //             ctx.strokeRect(obj.x, obj.y, obj.width, obj.height);
    //         } else if (obj.type === "polygon") {
    //             ctx.beginPath();
    //             ctx.moveTo(obj.points[0].x, obj.points[0].y);
    //             for (let i = 1; i < obj.points.length; i++) {
    //                 ctx.lineTo(obj.points[i].x, obj.points[i].y);
    //             }
    //             ctx.closePath();
    //             ctx.stroke();
    //         }
    //     }
    //     ctx.restore();
    // }
//}
