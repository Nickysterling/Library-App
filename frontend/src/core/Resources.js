/**
 * Handles loading and managing game resources (sprites).
 * Each resource tracks whether it has finished loading.
 */
class Resources {
  constructor() {
    this.toLoad = {
      librarian: "assets/models/librarian.png",
      player: "assets/models/player.png"
    };

    this.images = {};

    Object.keys(this.toLoad).forEach(key => {
      const img = new Image();
      img.src = this.toLoad[key];
      this.images[key] = { 
        image: img, 
        isLoaded: false
      }
      img.onload = () => {
        this.images[key].isLoaded = true;
      }
    })
  }
}

export const resources = new Resources();
