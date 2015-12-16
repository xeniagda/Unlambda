package com.loovjo.unlambdaint.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.utils.Vector;
import com.loovjo.unlambdaint.UnlambdaInterpreter;
/*
 * Basic gui interface.
 */
public class UnlambdaInterfaceGui implements Scene {

	public UnlambdaInterpreter interpreter;

	public boolean pause = true;

	public int codeScroll = 0;
	public int outScroll = 0;

	public Vector mousePos = new Vector(0, 0);

	public UnlambdaInterfaceGui(UnlambdaInterpreter unlambdaInterpreter) {
		interpreter = unlambdaInterpreter;
		new Window(this);
	}

	@Override
	public void update() {
		if (!pause)
			interpreter.cycle();
	}

	public int width;

	@Override
	public void render(Graphics g, int width, int height) {
		this.width = width;

		BufferedImage codeSection = new BufferedImage(getSplit() - 3, height, BufferedImage.TYPE_INT_ARGB);
		drawCodeSection(codeSection);

		BufferedImage outSection = new BufferedImage(width - getSplit(), height, BufferedImage.TYPE_INT_ARGB);
		drawOutSection(outSection);
		g.drawImage(codeSection, 0, 0, null);
		g.drawImage(outSection, getSplit(), 0, null);
		g.drawLine(getSplit(), 0, getSplit(), height);
	}

	private void drawOutSection(BufferedImage image) {

		Graphics g = image.getGraphics();
		String out = interpreter.output;
		g.setFont(new Font("Helvetica", Font.PLAIN, 12));
		g.setColor(Color.black);
		int x = 0;
		int y = g.getFontMetrics().getHeight();
		for (int i = 0; i < out.length(); i++) {
			char c = out.charAt(i);
			x += g.getFontMetrics().stringWidth("" + c);
			if (x > image.getWidth() || c == '\n') {
				x = 0;
				y += g.getFontMetrics().getHeight();
			}

			if (y + outScroll > 0 && y + outScroll < width)
				g.drawString(c + "", x - g.getFontMetrics().stringWidth("" + c), y + outScroll);

		}
	}

	public void drawCodeSection(BufferedImage image) {
		Graphics g = image.getGraphics();
		String code = interpreter.root.getUnlCode(false);
		g.setFont(new Font("Helvetica", Font.PLAIN, 12));
		int x = 0;
		int y = g.getFontMetrics().getHeight();
		int targetStart = interpreter.root.getTargetStartChar();
		int targetEnd = interpreter.root.getTargetEnd();
		for (int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);

			boolean evaluating = i >= targetStart && i < targetEnd;

			if (evaluating)
				g.setColor(Color.red);
			else
				g.setColor(Color.black);

			x += g.getFontMetrics().stringWidth("" + c);
			if (x > image.getWidth()) {
				x = 0;
				y += g.getFontMetrics().getHeight();
			}
			if (y + codeScroll > 0 && y + codeScroll < width)
				g.drawString(c + "", x - g.getFontMetrics().stringWidth("" + c), y + codeScroll);
		}
	}

	public int getSplit() {
		return 2 * width / 3;
	}

	@Override
	public void mousePressed(Vector pos, int button) {

	}

	@Override
	public void mouseReleased(Vector pos, int button) {

	}

	@Override
	public void mouseMoved(Vector pos) {
		mousePos = pos;
	}

	@Override
	public void keyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_SPACE)
			pause ^= true;
		if (keyCode == KeyEvent.VK_C)
			interpreter.cycle();

		if (keyCode == KeyEvent.VK_DOWN) {
			if (mousePos.getX() > getSplit()) {
				outScroll += 12;
			} else {
				codeScroll += 12;
			}
		}

		if (keyCode == KeyEvent.VK_UP) {
			if (mousePos.getX() > getSplit()) {
				outScroll -= 12;
			} else {
				codeScroll -= 12;
			}
		}
	}

	@Override
	public void keyReleased(int keyCode) {

	}

	@Override
	public void keyTyped(char key) {

	}

	@Override
	public void mouseWheal(MouseWheelEvent e) {
	}

	public class Window extends MainWindow {
		public Window(Scene scene) {
			super("Unlambda Interpreter", scene, new Vector(500, 600), true);
		}
	}

}
