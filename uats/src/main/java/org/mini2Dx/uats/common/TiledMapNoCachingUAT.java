/**
 * Copyright (c) 2015, mini2Dx Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the mini2Dx nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mini2Dx.uats.common;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.desktop.DesktopMini2DxGame;
import org.mini2Dx.tiled.TiledMap;
import org.mini2Dx.tiled.exception.TiledException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * A {@link GameContainer} that allows visual user acceptance testing of
 * {@link TiledMap} rendering with layer caching disabled
 */
public class TiledMapNoCachingUAT extends GameContainer {
    private TiledMap tiledMap;

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void initialise() {
        try {
            tiledMap = new TiledMap(Gdx.files.classpath("simple.tmx"), true, false);
        } catch (TiledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void interpolate(float alpha) {
    }

    @Override
    public void render(Graphics g) {
        g.setBackgroundColor(Color.WHITE);
        g.setColor(Color.RED);

        renderFullMapInTopLeftCorner(g);
        renderPartOfMapUnderTopLeftMap(g);
        renderFirstTilesetInTopRightCorner(g);
        renderScaledAndRotatedMapInBottomRightCorner(g);
        renderTranslatedFullMapInBottomLeftCorner(g);
    }
    
    private void renderFullMapInTopLeftCorner(Graphics g) {
        tiledMap.draw(g, 0, 0);
    }
    
    private void renderFirstTilesetInTopRightCorner(Graphics g) {
        tiledMap.getTilesets().get(0).drawTileset(g, tiledMap.getWidth() * tiledMap.getTileWidth() + 32, 0);
    }
    
    private void renderScaledAndRotatedMapInBottomRightCorner(Graphics g) {
        g.scale(1.25f, 1.25f);
        g.rotate(5f, 0f, (tiledMap.getHeight() * tiledMap.getTileHeight()) * 1.5f);

        //Render rotated map in bottom right corner
        tiledMap.draw(g, tiledMap.getWidth() * tiledMap.getTileWidth(), MathUtils.round((tiledMap.getHeight() * tiledMap.getTileHeight()) * 1.5f), 1, 1, 4, 8);

        g.rotate(-5f, 0f, (tiledMap.getHeight() * tiledMap.getTileHeight()) * 1.5f);
        g.scale(0.8f, 0.8f);
    }
    
    private void renderPartOfMapUnderTopLeftMap(Graphics g) {
        tiledMap.draw(g, 32, tiledMap.getHeight() * tiledMap.getTileHeight(), 1, 1, 4, 8);
    }
    
    private void renderTranslatedFullMapInBottomLeftCorner(Graphics g) {
        int mapWidthInPixels = tiledMap.getWidth() * tiledMap.getTileWidth();
        g.translate(mapWidthInPixels, 0);
        tiledMap.draw(g, mapWidthInPixels, (tiledMap.getHeight() * tiledMap.getTileHeight()) + (8 * tiledMap.getTileHeight()));
        g.translate(-mapWidthInPixels, 0);
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "mini2Dx - TiledMap No Caching Verification Test";
        cfg.width = 800;
        cfg.height = 800;
        cfg.stencil = 8;
        cfg.vSyncEnabled = true;
        cfg.foregroundFPS = 0;
        cfg.backgroundFPS = 0;
        new LwjglApplication(new DesktopMini2DxGame("org.mini2Dx.uats.common.TiledMapNoCachingUAT", new TiledMapNoCachingUAT()), cfg);
    }
}
