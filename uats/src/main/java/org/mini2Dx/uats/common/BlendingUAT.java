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

import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.desktop.DesktopMini2DxGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * UAT for alpha blending
 */
public class BlendingUAT extends BasicGame {
	private Sprite sprite;

	@Override
	public void initialise() {
		sprite = new Sprite(new Texture(Gdx.files.internal("unsealed.png")));
		sprite.flip(false, true);
	}

	@Override
	public void update(float delta) {
	}
	
	@Override
	public void interpolate(float alpha) {
	}

	@Override
	public void render(Graphics g) {
		g.setBackgroundColor(Color.BLACK);
		//g.drawTexture(texture, 0, 0);
		
		/* Render shadow over everything */
		g.setBlendFunction(GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Color shadow = new Color(1, 1, 1, 0.9f);
		g.setColor(shadow);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/* Render lights to alpha mask */
		Gdx.gl.glColorMask(false, false, false, true);
		g.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
		g.setColor(new Color(1f, 1f, 1f, 1f));
		g.fillCircle(sprite.getWidth() / 2f, sprite.getHeight() / 2f, MathUtils.round(sprite.getWidth() / 6f));
		g.flush();
		
		/* Render the scene */
		Gdx.gl.glColorMask(true, true, true, true);   
		g.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_DST_ALPHA);
		g.drawSprite(sprite);
	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "mini2Dx - Graphics Verification Test";
		cfg.width = 800;
		cfg.height = 600;
		cfg.vSyncEnabled = true;
		cfg.foregroundFPS = 0;
		cfg.backgroundFPS = 0;
		new LwjglApplication(new DesktopMini2DxGame("org.mini2Dx.uats.common.BlendingUAT", new BlendingUAT()), cfg);
	}
}