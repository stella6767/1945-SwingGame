package frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Boss;
import objects.Enemy1;
import objects.Enemy2;
import objects.Enemy3;
import objects.Enemy4;
import objects.Enemy5;
import objects.Enemy6;
import objects.EnemyUnit;
import objects.PlayerPlane;

public class GameFrame extends JFrame implements Initable {

	public GameFrame gameFrame = this;

	public boolean isgame; // 게임실행 여부
	private GamePanel gamePanel; // 인게임 패널 이거 잘 봐야된다. 오류 !!
	private GameTitle gameTitle; // 타이틀 인트로 패널
	private SelectAPI selectAPI; //선택 패널

	public GameFrame() {
		init();
		setting();
		listener();

		setVisible(true);
	}

	public void init() {
		change("gameTitle"); // 초기 타이틀 화면
		isgame = false; // 게임 중 이지 않은 상태
	}

	public void setting() {
		setTitle("Strikers 1945");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	


	// 패널 바꾸기 함수
	public void change(String panelName) {
		if (panelName.equals("gameTitle")) {
			gameTitle = new GameTitle(gameFrame);
			getContentPane().removeAll();
			getContentPane().add(gameTitle);
			revalidate();
			repaint();
		} else if (panelName.equals("selectAPL")) {
			selectAPI = new SelectAPI(gameFrame);
			getContentPane().removeAll();
			getContentPane().add(selectAPI);
			revalidate();
			repaint();
		} else if (panelName.equals("gameMap")) {
			gamePanel = new GamePanel();
			getContentPane().removeAll();
			getContentPane().add(gamePanel);
			revalidate();
			repaint();
		} else {
			gameTitle = null;
			selectAPI = null;
			gamePanel = null;
			isgame = false;
			getContentPane().removeAll();
			revalidate();
			repaint();
		}
	}









}
