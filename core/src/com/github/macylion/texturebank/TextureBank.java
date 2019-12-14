package com.github.macylion.texturebank;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class TextureBank {
	
	private class TextureInstance {
		private Texture texture;
		private String key;
		public TextureInstance(String txtPath, String key) {
			this.texture = new Texture(txtPath);
			this.key = key;
		}
		public Texture getTexture() {
			return texture;
		}
		public String getKey() {
			return key;
		}
	}
	
	ArrayList<TextureInstance> textures;
	
	public TextureBank() {
		this.textures = new ArrayList<TextureInstance>();
		
		System.out.println("[SYSTEM] TextureBank created.");
	}
	
	public void addTexture(String path, String key) {
		this.textures.add(new TextureInstance(path, key));
	}
	
	public Texture getTexture(String key) {
		for(TextureInstance ti : this.textures) 
			if(ti.getKey().equals(key))
				return ti.getTexture();
		return null;
	}
	
	public void dispose() {
		for(TextureInstance ti : this.textures)
			ti.getTexture().dispose();
	}

}
