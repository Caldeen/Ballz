package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Player.Ball;
import com.mygdx.game.Player.EventHandler;

public class MyGdxGame extends ApplicationAdapter {
	public static int METER_TO_PIX=16;
	World world;
	SpriteBatch batch;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	OrthographicCamera cam;

	Ball ball;

	@Override
	public void create () {
		cam=new OrthographicCamera();
		world=new World(new Vector2(0,0),true);
		cam.setToOrtho(false,1200,1200);
		batch = new SpriteBatch();
		tiledMap = new TmxMapLoader().load("core/assets/testMapa.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		MapParser.parseMapLayers(world, tiledMap);

		ball = new Ball(world, batch, 100.0f, 100.0f, 50.0f);
		EventHandler eventHandler = new EventHandler(ball);
		Gdx.input.setInputProcessor(eventHandler);
	}

	@Override
	public void render () {
		tiledMapRenderer.setView(cam);
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1/60f, 6, 2);

		tiledMapRenderer.render();
		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
