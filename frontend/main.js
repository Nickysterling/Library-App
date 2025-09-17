import { resources } from './src/Resources.js';
import { GameMap } from "./src/GameMap.js";
import { Sprite } from './src/Sprite.js';
import { Vector2 } from './src/Vector2.js';
import { GameLoop } from './src/GameLoop.js';
import { Input } from './src/Input.js';
import { Character } from './src/Character.js';
import { DynamicLayer } from './src/DynamicLayer.js';
import { Collisions } from './src/Collisions.js';

const canvas = document.getElementById("game-canvas");
const ctx = canvas.getContext("2d");

// Game objects
let gameMap;
let player, librarian;
let input;

document.addEventListener("DOMContentLoaded", async () => {
    // --- Load map ---
    gameMap = new GameMap("assets/map/library.json", "assets/map/tiled/tileset.png", 24);
    await gameMap.load();
    const collisions = new Collisions(gameMap.getCollisionObjects());

    // --- Initialize sprites ---
    const librarianSprite = new Sprite({
        resource: resources.images.librarian,
        frameSize: new Vector2(64, 64),
        hFrames: 1,
        vFrames: 1,
        frame: 1
    });

    const playerSprite = new Sprite({
        resource: resources.images.player,
        frameSize: new Vector2(64, 64),
        hFrames: 4,
        vFrames: 6,
        frame: 0
    });

    librarian = new Character(librarianSprite, new Vector2(365, 265), {xOffset: 16, yOffset: 48, width: 32, height: 16});
    player = new Character(playerSprite, new Vector2(209, 305), {xOffset: 24, yOffset: 48, width: 16, height: 8});

    // --- Initialize input ---
    input = new Input();

     // --- Define update function ---
    const update = () => { 
        player.update(input, collisions);
    };
    
    // --- Get dynamic objects from GameMap ---
    const dynamicData = gameMap.getDynamicData();
    const dynamicLayer = new DynamicLayer(dynamicData, gameMap.tileset, gameMap.tileSize);
    
    dynamicLayer.addCharacter(player);
    dynamicLayer.addCharacter(librarian);

    // --- In draw function ---
    const draw = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        gameMap.drawBackground(ctx);

        dynamicLayer.draw(ctx);
        collisions.drawDebug(ctx);

        gameMap.drawForeground(ctx);
    };

    // --- Start game loop ---
    const gameLoop = new GameLoop(update, draw);
    gameLoop.start();
});