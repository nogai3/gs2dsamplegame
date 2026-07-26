package com.lighsync.gs2d.world.entity;

import com.lighsync.gs2d.platform.InputManager;
import com.lighsync.gs2d.renderer.RenderSystem;
import com.lighsync.gs2d.world.Tilemap;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> toAdd = new ArrayList<>();

    public void addEntity(Entity e) {
        toAdd.add(e);
    }

    public void update(float deltaTime, InputManager inputManager, Tilemap tilemap) {
        if (!toAdd.isEmpty()) {
            entities.addAll(toAdd);
            toAdd.clear();
        }

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (!e.isActive()) {
                entities.remove(i);
                i--;
                continue;
            }
            e.update(deltaTime, inputManager, tilemap);
        }
    }

    public void render(RenderSystem renderSystem) {
        for (Entity e : entities) {
            e.render(renderSystem);
        }
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void clear() {
        entities.clear();
        toAdd.clear();
    }
}