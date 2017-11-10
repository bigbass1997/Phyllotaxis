package com.bigbass.phyllotaxis.objects;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Particle {
	
	private static final Random rand = new Random();
	
	public final Color color;
	
	private Vector2 pos;
	private Vector2 polar;
	private final Vector2 offset;
	private int n;
	
	public Particle(float theta, float r, float xOffset, float yOffset, int n){
		//color = new Color(0xFFFFFFFF);
		color = setRandomColor();
		
		offset = new Vector2(xOffset, yOffset);
		
		polar = new Vector2(theta, r);
		pos = toCart(theta, r);
		pos.add(offset);
		
		this.n = n;
	}
	
	public Vector2 toCart(float theta, float r){
		return new Vector2(r * MathUtils.cosDeg(theta), r * MathUtils.sinDeg(theta));
	}
	
	public void changeTheta(float delta){
		float newTheta = polar.x + (delta * n);
		
		polar.x = newTheta;
		//pos = toCart(polar.x, pos.y);
		pos = toCart(polar.x, polar.y);
		pos.add(offset);
	}
	
	public void render(ShapeRenderer sr){
		sr.setColor(color);
		final float r = 1.5f;
		sr.rect(pos.x - r, pos.y - r, r * 2, r * 2);
	}
	
	private Color setRandomColor(){
		Color c = new Color();
		
		c.a = 1.0f;
		c.r = rand.nextFloat();
		c.g = rand.nextFloat();
		c.b = rand.nextFloat();
		
		return c;
	}
}
