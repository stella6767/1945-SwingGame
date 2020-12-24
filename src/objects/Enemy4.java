package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Enemy4 extends EnemyUnit {

	private Enemy4 enemy4 = this;
	private static final String TAG = "Enemy4 : ";

	ArrayList<EnemyAttack> enemyAttackkList = new ArrayList<EnemyAttack>();
	private EnemyAttack enemyAttack;

	public Enemy4(PlayerPlane player, int x, int y, int w, int h) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.image = new ImageIcon("images/enemy4.png").getImage();
		this.life = 3;
		this.crushCheck = false;

		this.player.contextAdd(enemy4);

		this.move();
		this.crush();

	}

	public void move() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				count = 0;
				while (true) {
					try {
						Thread.sleep(5);

						movedown();
						y++; // down가속

						if (y < 400) {
							moveright();

						}

						bulletCreate();
						enemyAttack();
						count++;

						if (y > 900) {
							// System.out.println("enemy4 쓰레드 종료");
							break;
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void crush() { // 적비행기-Player 충돌

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (player.getLife() > 0) {

					if (Math.abs((player.getX() + player.getWidth() / 2) - (x + player.getWidth() / 2)) < (width / 2
							+ player.getWidth() / 2)
							&& Math.abs((player.getY() + player.getHeight() / 2) - (y + height / 2)) < (height / 2
									+ player.getHeight() / 2)) {
						collision = true;
					} else {
						collision = false;
					}

					try {
						if (collision) {
							explosePlayer(player, enemy4); // 충돌 폭발 메서드
						}
						Thread.sleep(10);
//						if(playerPlane.getLife() <= 0) {
//							Thread.sleep(100);						//1초후
//							System.exit(1);							//프로그램 종료
//						}

						if (crushCheck) {
							explosePlayer(enemy4);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		}).start();

	}
	
	

	private void bulletCreate() {
		if (count % 100 == 0) {
			enemyAttack = new EnemyAttack(enemy4, player, x + 20, y + 40, 270, 3, 20, 20);
			enemyAttackkList.add(enemyAttack);

		}
	}

	public void enemyUpdate(Graphics g) {
		enemyDraw(g);
	}

	private void enemyAttack() {
		for (int i = 0; i < enemyAttackkList.size(); i++) {
			enemyAttack = enemyAttackkList.get(i);
			if (enemy4.life > 0)
				enemyAttack.fire();

		}
	}

	public void enemyDraw(Graphics g) { // 그림그리기
		g.drawImage(image, x, y, width, height, null);
		for (int i = 0; i < enemyAttackkList.size(); i++) {
			enemyAttack = enemyAttackkList.get(i);
			g.drawImage(enemyAttack.bulletImg1, enemyAttack.getX(), enemyAttack.getY(), enemyAttack.getWidth(),
					enemyAttack.getHeight(), null);


		}
	}

}
