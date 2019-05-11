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
	public static float zoomFactor=0.25f;
	public static Vector2 screenSize;
	private World world;
	private SpriteBatch batch;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera cam;
	private OrthographicCamera viewCam;
	private MapParser mapParser;
    private Vector2 worldSize;
	Ball ball;
	ShapeRenderer line;

	@Override
	public void create () {
        batch = new SpriteBatch();
        world=new World(new Vector2(0,-10),true);
        debugRenderer=new Box2DDebugRenderer();
        screenSize=new Vector2(1600,900);
        Gdx.graphics.setWindowedMode((int)screenSize.x,(int)screenSize.y);


		tiledMap = new TmxMapLoader().load("core/assets/map/map1.tmx");
		mapParser=new MapParser();
		mapParser.parseMapLayers(world, tiledMap);
		mapParser.manager();
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1f/METER_TO_PIX,batch);
		worldSize=new Vector2(tiledMap.getProperties().get("width",Integer.class),
                0);
		worldSize.y=worldSize.x*9/16;
        viewCam=new OrthographicCamera();
        viewCam.setToOrtho(false,worldSize.x*zoomFactor,worldSize.y*zoomFactor);
        cam=new OrthographicCamera();
        cam.setToOrtho(false,worldSize.x,worldSize.y);
		ball = new Ball(world, batch, 20.0f, 20.0f, 0.5f);
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
		batch.setProjectionMatrix(viewCam.combined);

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

	private void draw_vec() {
		if (EventHandler.mouseDown) {
			float currentX = Gdx.input.getX();
			float currentY = MyGdxGame.screenSize.y - Gdx.input.getY();
			Gdx.gl.glLineWidth(10);
			line.begin(ShapeRenderer.ShapeType.Line);
			line.setColor(Color.CYAN);
			line.line(EventHandler.prevX, EventHandler.prevY, currentX, currentY);
			line.end();
			Gdx.gl.glLineWidth(1);
			line.begin(ShapeRenderer.ShapeType.Filled);
			line.circle(EventHandler.prevX, EventHandler.prevY, 10);
			line.end();
		}
	}

}
