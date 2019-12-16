package com.github.macylion.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.overworld.Block;
import com.github.macylion.texturebank.TextureBank;
import com.github.macylion.thirdparty.GifDecoder;

public class Player extends Entity{
	
	private Animation<TextureRegion>[] anims;
	private int currAnim = 2;
	private float stateTime;
	private Rectangle foot;
	private boolean isJump = false;
	private float jumpTime = 0;
	private float jumpTimeMax = 0.15f;
	
	public Player(Vector2 position, World world, TextureBank bank) {
		super(position, world, 19, 30);
		
		this.anims = new Animation[5];
		
		this.anims[0] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/run.gif").read());
		this.anims[1] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/jump.gif").read());
		this.anims[2] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/idle.gif").read());
		this.anims[3] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/run_r.gif").read());
		this.anims[4] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/idle_r.gif").read());
		
		this.stateTime = 0;
		this.groundBody.setGravityScale(4);
		
		this.foot = new Rectangle(position.x+1, position.y-2, this.width-2, 4);
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion currentFrame = this.anims[this.currAnim].getKeyFrame(stateTime, true);
		batch.draw(currentFrame, this.x, this.y);
	}
	
	public void update(ArrayList<Block> blocks) {
		this.fixedUpdate();
		this.foot.x = this.x;
		this.foot.y = this.y-2;
		this.movement(blocks);
		this.stateTime += Gdx.graphics.getDeltaTime();
	}
	
	private void movement(ArrayList<Block> blocks) {
		
		boolean isInAir = true;
		
		for(Block b : blocks)
			if(b.isOnScreen() && b.overlaps(this.foot)) {
				isInAir = false;
				break;
			}
		
		boolean isUp = (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.SPACE));
		boolean isRight = (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT));
		boolean isLeft = (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT));
		boolean isSprinting = (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT));
		
		float speed = 60;
		float sprintMultiplier = 1;
		
		if(isSprinting) {
			sprintMultiplier = 2f;
			this.stateTime += Gdx.graphics.getDeltaTime();
		}
		if(isUp && !isInAir) 
			this.isJump = true;
		
		if(this.isJump) {
			this.jumpTime += Gdx.graphics.getDeltaTime();
			this.groundBody.setLinearVelocity(this.groundBody.getLinearVelocity().x, speed*4);
			if(this.jumpTime >= this.jumpTimeMax) {
				this.isJump = false;
				this.jumpTime = 0;
			}
		}
		
		if(isRight) {
			this.groundBody.setLinearVelocity(speed*sprintMultiplier, this.groundBody.getLinearVelocity().y);
			this.currAnim = 0;
		}
		else if(isLeft) {
			this.groundBody.setLinearVelocity(-speed*sprintMultiplier, this.groundBody.getLinearVelocity().y);
			this.currAnim = 3;
		}
		else {
			if(this.currAnim == 3) this.currAnim = 4;
			if(this.currAnim == 0) this.currAnim = 2;
			this.groundBody.setLinearVelocity(0, this.groundBody.getLinearVelocity().y);
		}
	}
	
	public boolean isJump() {
		return this.isJump;
	}
	
	public int getCurrentAnim() {
		return this.currAnim;
	}
	
}
