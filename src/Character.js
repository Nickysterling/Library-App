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

        update(input) {
            const moves = {
                [DOWN]:  () => { this.position.y += this.speed; this.sprite.frame = 0; },
                [UP]:    () => { this.position.y -= this.speed; this.sprite.frame = 1; },
                [LEFT]:  () => { this.position.x -= this.speed; this.sprite.frame = 2; },
                [RIGHT]: () => { this.position.x += this.speed; this.sprite.frame = 3; }
            };

            if (moves[input.direction]) moves[input.direction]();
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
