export class GameMap {
    constructor(jsonPath, tilesetPath, tileSize = 24) {
        this.jsonPath = jsonPath;
        this.tilesetPath = tilesetPath;
        this.tileSize = tileSize;

        this.mapData = null;
        this.tileset = new Image();

        // Buffers
        this.backgroundBuffer = document.createElement("canvas");
        this.backgroundCtx = this.backgroundBuffer.getContext("2d");

        this.foregroundBuffer = document.createElement("canvas");
        this.foregroundCtx = this.foregroundBuffer.getContext("2d");
    }

    async load() {
        // Load JSON
        const res = await fetch(this.jsonPath);
        this.mapData = await res.json();

        // Load tileset
        await new Promise(resolve => {
            this.tileset.src = this.tilesetPath;
            this.tileset.onload = resolve;
        });

        // Set buffer sizes
        this.backgroundBuffer.width = this.mapData.width * this.tileSize;
        this.backgroundBuffer.height = this.mapData.height * this.tileSize;

        this.foregroundBuffer.width = this.mapData.width * this.tileSize;
        this.foregroundBuffer.height = this.mapData.height * this.tileSize;

        // Render layers
        this.renderLayer("Background", this.backgroundCtx);
        this.renderLayer("Foreground", this.foregroundCtx);
    }

    renderLayer(layerType, ctx) {
        // Find all visible layers matching the type (background or foreground)
        const layers = this.mapData.layers.filter(
            layer => layer.type === "tilelayer" && layer.visible && layer.name === layerType
        );

        const tilesPerRow = this.tileset.width / this.tileSize;

        layers.forEach(layer => {
            layer.data.forEach((tileIndex, i) => {
                if (tileIndex === 0) return; // skip empty tiles

                const tileId = tileIndex - 1;
                const sx = (tileId % tilesPerRow) * this.tileSize;
                const sy = Math.floor(tileId / tilesPerRow) * this.tileSize;

                const dx = (i % layer.width) * this.tileSize;
                const dy = Math.floor(i / layer.width) * this.tileSize;

                ctx.drawImage(
                    this.tileset,
                    sx, sy, this.tileSize, this.tileSize,
                    dx, dy, this.tileSize, this.tileSize
                );
            });
        });
    }

    // Draw the pre-rendered background buffer
    drawBackground(ctx) {
        ctx.drawImage(this.backgroundBuffer, 0, 0);
    }

    // Draw the pre-rendered foreground buffer
    drawForeground(ctx) {
        ctx.drawImage(this.foregroundBuffer, 0, 0);
    }

    // getDynamicData() {
    //     const dynamicTiles = [];
        
    //     // Find all tile layers with a dynamic property
    //     const dynamicTileLayers = this.mapData.layers.filter(layer =>
    //         layer.type === "tilelayer" &&
    //         layer.properties?.some(p => p.name === "Object Name")
    //     );

    //     // Find all object layers with a dynamic property
    //     const objectLayers = this.mapData.layers.filter(layer =>
    //         layer.type === "objectgroup" &&
    //         layer.properties?.some(p => p.name === "Object Name")
    //     );

    //     dynamicTileLayers.forEach(tileLayer => {
    //         const objectName = tileLayer.properties.find(p => p.name === "Object Name").value;

    //         // Find the matching bounding box layer
    //         const boxLayer = objectLayers.find(objLayer => 
    //             objLayer.properties.find(p => p.name === "Object Name")?.value === objectName
    //         );

    //         dynamicTiles.push({
    //             layerName: tileLayer.name,
    //             objectName,
    //             data: tileLayer.data,
    //             width: tileLayer.width,
    //             height: tileLayer.height,
    //             boxes: boxLayer?.objects || []
    //         });
    //     });

    //     return dynamicTiles;
    // }  

    getDynamicData() {
        const dynamicLayers = [];

        // 1. Find all dynamic tile layers
        const tileLayers = this.mapData.layers.filter(
            l => l.type === "tilelayer" && l.visible && l.name === "Dynamic"
        );

        tileLayers.forEach(layer => {
            // Find matching object layer for bounding boxes
            const boxLayer = this.mapData.layers.find(
                l => l.type === "objectgroup" && l.visible && 
                    l.properties?.some(p => p.name === "Object Name" && p.value === layer.properties?.find(pp => pp.name === "Object Name")?.value)
            );

            dynamicLayers.push({
                data: layer.data,
                width: layer.width,
                height: layer.height,
                boxes: boxLayer ? boxLayer.objects : [],
                objectName: layer.properties?.find(p => p.name === "Object Name")?.value || null
            });
        });

        return dynamicLayers;
    }
}