package com.github.macylion.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.macylion.texturebank.TextureBank;
import com.kotcrab.vis.ui.VisUI;

public class GUI {

	private int screenHeight = 0;
	private int screenWidth = 0;
	private SpriteBatch batch;
	
	public GUI(int width, int height) {
		VisUI.load();
		this.screenHeight = height;
		this.screenWidth = width;
		this.batch = new SpriteBatch();
	}
	
	public void draw(TextureBank bank) {
		batch.begin();
		
		batch.end();
	}
	
	public int getWidth() {
		return this.screenWidth;
	}
	
	public int getHeight() {
		return this.screenHeight;
	}
	
	public void dispose() {
		VisUI.dispose();
	}

}
