// DynamicLayer.js
export class DynamicLayer {
    constructor(dynamicData, tileset, tileSize) {
        this.dynamicData = dynamicData; // Array from GameMap.getDynamicData()
        this.tileset = tileset;
        this.tileSize = tileSize;
        this.characters = []; // Characters to be drawn
    }

    // Add a character to the layer
    addCharacter(character) {
        this.characters.push(character);
    }

    // Main draw function
    draw(ctx) {
        const drawables = [];

        const tilesPerRow = this.tileset.width / this.tileSize;

        // Add all dynamic tiles to drawables
        this.dynamicData.forEach(layer => {
            layer.data.forEach((gid, i) => {
                if (gid === 0) return;

                const x = (i % layer.width) * this.tileSize;
                const y = Math.floor(i / layer.width) * this.tileSize;

                // Assume one bounding box per tile
                const box = layer.boxes[0]; 
                const bottomY = box ? (box.y + (box.height || 0)) : (y + this.tileSize);

                drawables.push({
                    type: "tile",
                    gid,
                    x,
                    y,
                    bottomY,
                    box
                });
            });
        });

        // Add all characters to drawables
        this.characters.forEach(c => {
            drawables.push({
                type: "character",
                character: c,
                bottomY: c.getFootY()
            });
        });

        // Sort by bottomY for Y-sorting
        drawables.sort((a, b) => a.bottomY - b.bottomY);

        // Draw everything in order
        drawables.forEach(item => {
            if (item.type === "tile") {
                const tileId = item.gid - 1;
                const sx = (tileId % tilesPerRow) * this.tileSize;
                const sy = Math.floor(tileId / tilesPerRow) * this.tileSize;

                ctx.drawImage(
                    this.tileset,
                    sx, sy,
                    this.tileSize, this.tileSize,
                    item.x, item.y,
                    this.tileSize, this.tileSize
                );

                // Optional: draw bounding box for debugging
                // if (item.box) {
                //     ctx.strokeStyle = "blue";
                //     ctx.lineWidth = 2;
                //     if (item.box.polygon) {
                //         ctx.beginPath();
                //         ctx.moveTo(item.box.x + item.box.polygon[0].x, item.box.y + item.box.polygon[0].y);
                //         for (let j = 1; j < item.box.polygon.length; j++) {
                //             ctx.lineTo(item.box.x + item.box.polygon[j].x, item.box.y + item.box.polygon[j].y);
                //         }
                //         ctx.closePath();
                //         ctx.stroke();
                //     } else {
                //         ctx.strokeRect(item.box.x, item.box.y, item.box.width, item.box.height);
                //     }
                // }

            } else if (item.type === "character") {
                item.character.draw(ctx);
            }
        });
    }
}
