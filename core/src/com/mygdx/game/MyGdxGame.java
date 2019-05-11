package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Player.Ball;
import com.mygdx.game.Player.EventHandler;
import com.sun.org.apache.xpath.internal.operations.Or;

public class MyGdxGame extends ApplicationAdapter {
	public static int METER_TO_PIX=16;
	public static Vector2 screenSize;
	private World world;
	private SpriteBatch batch;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera cam;
	private OrthographicCamera viewCam;
	private MapParser mapParser;
	Ball ball;
	ShapeRenderer line;

	@Override
	public void create () {
		screenSize=new Vector2(1600,900);
		cam=new OrthographicCamera();
		viewCam=new OrthographicCamera();
		viewCam.setToOrtho(false,100*0.8f,56.25f*0.8f);
		debugRenderer=new Box2DDebugRenderer();
		world=new World(new Vector2(0,-10),true);
		cam.setToOrtho(false,100,56.25f);
		Gdx.graphics.setWindowedMode((int)screenSize.x,(int)screenSize.y);
		batch = new SpriteBatch();
		tiledMap = new TmxMapLoader().load("core/assets/map/map1.tmx");
		mapParser=new MapParser();
		mapParser.parseMapLayers(world, tiledMap);
		mapParser.manager();
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/32f,batch);
		ball = new Ball(world, batch, 50.0f, 50.0f, 0.5f);
		EventHandler eventHandler = new EventHandler(ball,cam);
		Gdx.input.setInputProcessor(eventHandler);
		line = new ShapeRenderer();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1/60f, 6, 2);

		viewCam.position.set(ball.getBody().getWorldCenter().x,ball.getBody().getWorldCenter().y,0);

		viewCam.update();
		cam.update();
		batch.setProjectionMatrix(cam.combined);



		tiledMapRenderer.setView(viewCam);
		tiledMapRenderer.render();
		debugRenderer.render(world,viewCam.combined);


		batch.begin();
		batch.end();
		draw_vec();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
	}

	private void draw_vec() { //not working
		if (EventHandler.mouseDown) {
			float currentX = Gdx.input.getX();
			float currentY = MyGdxGame.screenSize.y - Gdx.input.getY();

//			line.setProjectionMatrix(viewCam.combined);

			line.begin(ShapeRenderer.ShapeType.Line);
			line.setColor(Color.BLACK);
//			line.line(EventHandler.prevX, EventHandler.prevY, currentX, currentY);
			line.line(ball.getBody().getPosition().x*METER_TO_PIX, ball.getBody().getPosition().y*METER_TO_PIX, currentX, currentY);
			line.end();

			System.out.println(currentX + " " + currentY);
		}
	}

}
