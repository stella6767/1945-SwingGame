package objects;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class PlayerAttack implements Runnable { // 시간없으니까 지금말고, 나중에 스레드로 여기에 총알 충돌 구현
	private PlayerAttack playerAttack = this;
	private EnemyUnit enemyUnit; // 지금은 쓸데없지만 나중에
	private Boss boss;
	ArrayList<EnemyUnit> enemyUnitList = new ArrayList<EnemyUnit>(); // 총알피격시 객체를 담을 벡터


	Image playerBulletImg1 = new ImageIcon("images/playerBullet1.png").getImage();
	Image playerBulletImg2 = new ImageIcon("images/bullet1.png").getImage();
	Image playerBulletImg3 = new ImageIcon("images/bullet3.png").getImage();

	private boolean collision;
	private int x;
	private int y;
	private double angle; // 총알 각도
	private double speed; // 총알 속도
	private int width;
	private int height;
	private boolean islife; //Thread를 삭제시키기 위한 구문

	public PlayerAttack() {
		// TODO Auto-generated constructor stub
	}

	public PlayerAttack(Boss boss, int x, int y, double bulletAngle, double bulletSpeed) {

		if (boss != null) {
			this.boss = boss;
		}

		this.x = x;
		this.y = y;
		this.angle = bulletAngle;
		this.speed = bulletSpeed;

		collision = false;
		
		Thread bulletthread = new Thread(this); // 총알 충돌 thread 생성, 실행
		bulletthread.setName("PlayerBullet");
		bulletthread.start();

	}
	
	public PlayerAttack(Boss boss, ArrayList<EnemyUnit> enemyUnitList,int x, int y, double bulletAngle, double bulletSpeed) {

		if (boss != null) {
			this.boss = boss;
		}
		
		
		this.enemyUnitList = enemyUnitList;
		this.x = x;
		this.y = y;
		this.angle = bulletAngle;
		this.speed = bulletSpeed;
		this.islife = true;

		collision = false;
		
		Thread bulletthread = new Thread(this); // 총알 충돌 thread 생성, 실행
		bulletthread.setName("PlayerBullet");
		bulletthread.start();
		//this.bulletCrash();

	}

	

	public PlayerAttack getPlayerAttack() {
		return playerAttack;
	}

	public void setPlayerAttack(PlayerAttack playerAttack) {
		this.playerAttack = playerAttack;
	}

	public EnemyUnit getEnemyUnit() {
		return enemyUnit;
	}

	public void setEnemyUnit(EnemyUnit enemyUnit) {
		this.enemyUnit = enemyUnit;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void Fire() {
		x -= Math.cos(Math.toRadians(angle)) * speed;
		y -= Math.sin(Math.toRadians(angle)) * speed;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (boss != null  && boss.getLife() > 0 ) { // 생명이 0보다 크면

			crash();

			try {
				if (collision) {
					y = -100;
					boss.setLife(boss.getLife() - 1);

				}

				if (boss.getLife() == 0) {

					explosePlayer(boss); // 충돌 폭발 메서드
				}
				
		
				Thread.sleep(10);
				
				
				// if (playerPlane.getLife() <= 0) {
				// Thread.sleep(100); // 1초후
				// System.exit(1); // 프로그램 종료
				// }

				if (x > 1000 || x < -500 || y < -100 || y > 1000) {
					// System.out.println("bullet thread terminate");
					return; // Thread 종료구문
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public void bulletCrash() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(islife) {
					PlayerBullet();
				}
			}
		}).start();
		
		
	}
	
	

	public void crash() { // 플레이어 총알이 보스에 부딪쳤을 시 충돌연산
		if (Math.abs(((boss.getX() ) + boss.getWidth() / 2) - (x + width / 3)) < (width / 3 + boss.getWidth() / 3)
				&& Math.abs(((boss.getY() ) + boss.getHeight() / 2) - (y + height / 3)) < (height / 3
						+ boss.getHeight() / 3)) {
			collision = true;
		} else {
			collision = false;
		}
	}

	public void explosePlayer(Boss boss) { // 충돌후 이미지 변경 및 목숨카운트

		try {
			ImageIcon explosionIcon = new ImageIcon("images/explosion.gif");
			boss.imgBoss = explosionIcon.getImage();
			Thread.sleep(3000);

			System.out.println("보스 처치!!");
			System.exit(1); // 프로그램 종료

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	// 플레이어가 쏜 총알에 대한 처리.
	public void PlayerBullet() {


			// 플레이어 총알이 일반 적 비행기에 맞을때 처리.
			for (int j = 0; j < this.enemyUnitList.size(); j++) {

				if (Crash((int) x, (int) y, enemyUnitList.get(j).x,
						enemyUnitList.get(j).y, width, height,
						enemyUnitList.get(j).width, enemyUnitList.get(j).height)) {

					System.out.println("생명이 다하기 전: " + enemyUnitList.get(j).crushCheck);

					y=1000; // 충돌판정이 맞으면, 총알 사라지고 적의 체력이 1 깍임 
					islife = false;
					enemyUnitList.get(j).setLife(enemyUnitList.get(j).getLife() - 1);

					if (enemyUnitList.get(j).life == 0) { //적의 체력이 다 깍이면 리스트에서 제거 후 폭파 연산				
						enemyUnitList.get(j).crushCheck = true;
						enemyUnitList.remove(j);		
					}
				}

		}
	}


	// 플레이어 총알이 적의 비행기에 닿았는지 탐지하는 연산
	static boolean Crash(int x1, int y1, int x2, int y2, int w1, int h1, int w2, int h2) {
		// x,y : 위치값 , w,h : 이미지의 높이와 길이.
		boolean result = false;
		if (Math.abs((x1 + w1 / 2) - (x2 + w2 / 2)) < (w2 / 2 + w1 / 2)
				&& Math.abs((y1 + h1 / 2) - (y2 + h2 / 2)) < (h2 / 2 + h1 / 2))
			result = true;
		else
			result = false;

		return result;
	}

}
