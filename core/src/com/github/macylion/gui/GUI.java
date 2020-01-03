package com.github.macylion.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.macylion.Inventory;
import com.github.macylion.Inventory.ItemSlot;
import com.github.macylion.texturebank.TextureBank;
import com.kotcrab.vis.ui.VisUI;

public class GUI {

	private int screenHeight = 0;
	private int screenWidth = 0;
	private SpriteBatch batch;
	private boolean isEqOpen;
	private BitmapFont bf;
	
	public GUI(int width, int height) {
		VisUI.load();
		this.screenHeight = height;
		this.screenWidth = width;
		this.batch = new SpriteBatch();
		this.isEqOpen = false;
		this.bf = new BitmapFont();
	}
	
	public void draw(TextureBank bank) {
		update();
		batch.begin();
		if(this.isEqOpen)
			drawEq();
		batch.end();
	}
	
	public void drawEq() {
		//change this function all af
		float y = 100;
		for(ItemSlot is : Inventory.getItems()) {
			bf.draw(batch, is.getItemName() + ": " + is.getQuantity(), 100, y);
			y+=100;
		}
	}
	
	private void update() {
		if(Gdx.input.isKeyJustPressed(Keys.E) || Gdx.input.isKeyJustPressed(Keys.I))
			this.isEqOpen = !this.isEqOpen;
	}

	public int getWidth() {
		return this.screenWidth;
	}
	
	public int getHeight() {
		return this.screenHeight;
	}
	
	public void dispose() {
		VisUI.dispose();
		bf.dispose();
	}

}
