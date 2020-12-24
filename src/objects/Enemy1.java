package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Enemy1 extends EnemyUnit {
	// �떒�닚�엳 諛묒쑝濡쒕쭔 �씠�룞�븯�뒗 �쑀�떅
	private Enemy1 enemy1 = this;
	private static final String TAG = "Enemy1 : ";
	
	static ArrayList<EnemyAttack> enemyAttackkList = new ArrayList<EnemyAttack>();
	private EnemyAttack enemyAttack;

	public Enemy1(PlayerPlane player, int x, int y, int w, int h) {

		this.player = player;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.image = new ImageIcon("images/enemy1.png").getImage();
		this.life =1;
		this.crushCheck = false;
		
		this.player.contextAdd(enemy1); //�룞�쟻�쑝濡� �깮�꽦�븣留덈떎 而⑦뀓�뒪�듃 �뵆�젅�씠�뼱�뿉寃� �꽆湲곌린

		this.move();
		this.crush(); //異⑸룎�깘吏��슜
	}
	

	public void move() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(2);
												
						movedown();

						if (y > 900) {
							//System.out.println("enemy1 �벐�젅�뱶 醫낅즺");
							break;
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t1.start();
		
		t1.setName("enemy1");
	}

	public void crush() { // �쟻鍮꾪뻾湲�-Player 異⑸룎

		new Thread(new Runnable() {
			@Override
			public void run() {

				while (player.getLife() > 0) {

					if (Math.abs((player.getX() + player.getWidth() / 2)
							- (x + player.getWidth() / 2)) < (width / 2
									+ player.getWidth() / 2)
							&& Math.abs((player.getY() + player.getHeight() / 2)
									- (y + height / 2)) < (height / 2
											+ player.getHeight() / 2)) {
						collision = true;
					} else {
						collision = false;
					}

					try {
						if (collision) {
							explosePlayer(player, enemy1); // �뵆�젅�씠�뼱�� �쟻湲� 異⑸룎�떆
						}
						
						if(crushCheck) {
							explosePlayer(enemy1); //�뵆�젅�씠�뼱 珥앹븣�씠 �쟻湲� 異⑸룎�떆
						}
						
						Thread.sleep(10);
//						if(playerPlane.getLife() <= 0) {
//							Thread.sleep(100);						//1珥덊썑
//							System.exit(1);							//�봽濡쒓렇�옩 醫낅즺
//						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		}).start();

	}

	public void enemyUpdate(Graphics g) {
		enemyDraw(g);
	}

	public void enemyDraw(Graphics g) { // 洹몃┝洹몃━湲�
		g.drawImage(image, x, y, width, height, null);

	}
	
	

}
