package com.bigbass.phyllotaxis.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass.phyllotaxis.Main;
import com.bigbass.phyllotaxis.SimOptions;
import com.bigbass.phyllotaxis.objects.Sunflower;
import com.bigbass.phyllotaxis.skins.SkinManager;

public class PrimaryPanel extends Panel {
	
	private Camera cam;
	private Stage stage;
	private ShapeRenderer sr;
	
	private Label infoLabel;
	
	private Sunflower sun;
	
	private float scalar = 1;
	
	public PrimaryPanel() {
		super();
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		
		stage = new Stage();
		Main.inputMultiplexer.addProcessor(stage);
		Main.inputMultiplexer.addProcessor(new ScrollwheelInputAdapter(){
			@Override
			public boolean scrolled(int amount) {
				if(amount == 1){
					changeCameraViewport(0.1f);
				} else if(amount == -1){
					changeCameraViewport(-0.1f);
				}
				return true;
			}
		});
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		stage.addActor(infoLabel);
		
		sr = new ShapeRenderer(500000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		sun = new Sunflower();
	}
	
	public void render() {
		sr.begin(ShapeType.Filled);
		sr.setColor(SimOptions.getInstance().backgroundColor);
		sr.rect(-(cam.viewportWidth * 0.5f), -(cam.viewportHeight * 0.5f), Gdx.graphics.getWidth() * scalar * 2, Gdx.graphics.getHeight() * scalar * 2);
		sr.end();
		
		sun.render(sr);
		
		panelGroup.render();
		
		stage.draw();
		
		/*sr.begin(ShapeType.Filled);
		sr.setColor(Color.FIREBRICK);
		renderDebug(sr);
		sr.end();*/
	}
	
	public void update(float delta) {
		sun.update(delta);
		
		stage.act(delta);
		
		panelGroup.update(delta);
		
		String info = String.format("Data:%n  FPS: %s%n  Particles: %s",
				Gdx.graphics.getFramesPerSecond(),
				sun.particles.size()
			);
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		infoLabel.setText("");
	}
	
	public boolean isActive() {
		return true; // Always active
	}
	
	public void dispose(){
		stage.dispose();
		sr.dispose();
		panelGroup.dispose();
	}
	
	private void changeCameraViewport(float dscalar){
		scalar += dscalar;
		
		cam.viewportWidth = Gdx.graphics.getWidth() * scalar;
		cam.viewportHeight = Gdx.graphics.getHeight() * scalar;
		cam.update();

		sr.setProjectionMatrix(cam.combined);
	}
}