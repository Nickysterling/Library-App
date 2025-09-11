    import { Vector2 } from "./Vector2.js";
    import { UP, DOWN, LEFT, RIGHT } from "./Input.js";

    export class Character {
        constructor(sprite, position, bbox = { xOffset: 16, yOffset: 48, width: 32, height: 16 }, speed = 2) {
            this.sprite = sprite;
            this.position = position ?? new Vector2(0, 0);
            this.bbox = bbox;
            this.speed = speed;
            this.showDebugBox = false;
        }

        getBoundingBox() {
            return {
                x: this.position.x + this.bbox.xOffset,
                y: this.position.y + this.bbox.yOffset,
                width: this.bbox.width,
                height: this.bbox.height
            };
        }

        getFootY() {
            return this.position.y + this.bbox.yOffset + this.bbox.height;
        }

        update(input, collisions) {
            if (!input.direction) return;

            let dx = 0, dy = 0;
            if (input.direction === DOWN)  { dy = this.speed; this.sprite.frame = 0; }
            if (input.direction === UP)    { dy = -this.speed; this.sprite.frame = 1; }
            if (input.direction === LEFT)  { dx = -this.speed; this.sprite.frame = 2; }
            if (input.direction === RIGHT) { dx = this.speed; this.sprite.frame = 3; }

            const nextBox = this.getNextBox(dx, dy);

            // Case 1: Free move
            if (!collisions.isBlocked(nextBox)) {
                this.position.x += dx;
                this.position.y += dy;
                return;
            }

            // Case 2: Try X only
            const nextBoxX = this.getNextBox(dx, 0);
            if (!collisions.isBlocked(nextBoxX)) {
                this.position.x += dx;
                return;
            }

            // Case 3: Try Y only
            const nextBoxY = this.getNextBox(0, dy);
            if (!collisions.isBlocked(nextBoxY)) {
                this.position.y += dy;
                return;
            }

            // Case 4: Both blocked → don’t move
        }

        getNextBox(dx, dy) {
            const box = this.getBoundingBox();
            return {
                x: box.x + dx,
                y: box.y + dy,
                width: box.width,
                height: box.height
            };
        }

        draw(ctx) {
            this.sprite.drawImage(ctx, this.position.x, this.position.y);

            if (this.showDebugBox) {
                const box = this.getBoundingBox();
                ctx.strokeStyle = "red";
                ctx.strokeRect(box.x, box.y, box.width, box.height);
            }
        }
    }
