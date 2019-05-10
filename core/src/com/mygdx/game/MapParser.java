package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MapParser {
    private static final String MAP_LAYER_NAME_GROUND = "Ground";
    private static final String MAP_LAYER_NAME_BOUNDS = "Bounds";
    private static final String MAP_LAYER_NAME_DANGERS = "DeathG";
    public static void parseMapLayers(World world, TiledMap tiledMap) {
        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                Shape shape;
                if (object instanceof PolylineMapObject) {
                    shape = createPolyline((PolylineMapObject) object);
                } else {
                    continue;
                }
                if (layer.getName().equals(MAP_LAYER_NAME_GROUND))
                    new Ground(world, shape);
                if (layer.getName().equals(MAP_LAYER_NAME_BOUNDS))
                    new Bounds(world, shape);
                if (layer.getName().equals(MAP_LAYER_NAME_DANGERS))
                    new DangerZone(world, shape);
            }
        }
    }
    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVerticies = new Vector2[vertices.length / 2];
        for (int i = 0; i < worldVerticies.length; i++) {
            worldVerticies[i] = new Vector2(vertices[i * 2] / MyGdxGame.METER_TO_PIX,
                    vertices[i * 2 + 1] / MyGdxGame.METER_TO_PIX);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVerticies);
        return cs;
    }
}
class Bounds {
    private static final float DENSITY = 1.0f;
    public Bounds(World world, Shape shape) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.createFixture(shape, DENSITY).setUserData(this);
        shape.dispose();
    }
}
class Ground {
    private static final float DENSITY = 1.0f;
    public Ground(World world, Shape shape) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.createFixture(shape, DENSITY).setUserData(this);
        shape.dispose();
    }
}
class DangerZone {
    private static final float DENSITY = 1.0f;
    public DangerZone(World world, Shape shape) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.createFixture(shape, DENSITY).setUserData(this);
        shape.dispose();
    }
}
