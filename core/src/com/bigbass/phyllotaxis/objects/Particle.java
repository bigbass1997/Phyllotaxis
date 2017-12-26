package com.bigbass.phyllotaxis.objects;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bigbass.phyllotaxis.SimOptions;

public class Particle {
	
	private static float h = 0;
	private static float s = 0;
	private static float v = 1;
	
	private static int bw = 0;
	
	public final Color color;
	
	private Vector2 pos;
	private Vector2 polar;
	private final Vector2 offset;
	private int n;
	
	private float radius;
	
	private static Random rand = new Random();
	
	public Particle(float theta, float r, float xOffset, float yOffset, int n){
		radius = SimOptions.getInstance().particleSize;

		SimOptions opt = SimOptions.getInstance();
		
		switch(opt.colorGen){
		case BLACKWHITE:
			color = setBlackWhiteColor();
			break;
		default:
		case HSV:
			color = setHSVColor();
			break;
		case RANDOM:
			color = setRandomColor();
			break;
		}
		
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
		SimOptions opt = SimOptions.getInstance();
		
		polar.x = newTheta;
		
		if(opt.isInverse){
			polar.y += opt.inverseRate;
		}
		
		pos = toCart(polar.x, polar.y);
		pos.add(offset);
		
		if(opt.isChangingSize){
			radius += opt.changingSizeRate;
			
			float limit = Math.abs(opt.changingSizeLimit);
			
			if(opt.changingSizeRate > 0 && radius > limit){
				radius = -limit;
			} else if(opt.changingSizeRate < 0 && radius < -limit){
				radius = limit;
			}
		}
	}
	
	public void render(ShapeRenderer sr){
		sr.setColor(color);
		sr.rect(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
	}
	
	private Color setHSVColor(){
		Color c = new Color();
		
		c.a = 1.0f;
		c.fromHsv(h, s, v);
		
		s += 0.05f;
		if(s >= 1){
			s = 0;
			h += 1;
			if(h >= 360){
				h = 0;
			}
		}
		
		c.a = SimOptions.getInstance().colorAlpha;
		
		return c;
	}
	
	private Color setBlackWhiteColor(){
		if(bw == 0){
			bw = 1;
			return new Color(0x000000FF);
		} else if(bw == 1){
			bw = 0;
			return new Color(0xFFFFFFFF);
		} else {
			return new Color(0xAAAAAAFF); // shouldn't ever happen
		}
	}
	
	private Color setRandomColor(){
		return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
	}
}
