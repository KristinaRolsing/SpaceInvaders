package Model;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Enemies {

	private ArrayList<Enemy> enemyList = new ArrayList<>();
	private ArrayList<Bomb> bombList = new ArrayList<>();
	private boolean direction = true;
	

	public Enemies(int rows, int columns) {

		int r = 2;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {

				enemyList.add(new Enemy(3 + col * 2, 0 + row * 2, r));
			}
			r--;
		}
	}

	public ArrayList<Enemy> getEnemmyList() {
		return enemyList;
	}

	public void removeDestroiedEnemies() {
		@SuppressWarnings("unchecked")
		ArrayList<Enemy> enemmyCheckList = (ArrayList<Enemy>) enemyList.clone();
		for (Enemy enemy : enemmyCheckList) {
			if (enemy.getHitpoints() == 0) {
				enemyList.remove(enemy);
			}
		}
	}

	public boolean areAllDead() {
		return enemyList.size() == 0;
	}

	public void move(int row) {
		checkDirection();

		for (Enemy enemy : enemyList) {
			if (enemy.getRow() == row) {
				if (direction) {
					enemy.setX(enemy.getX() - 1);
				} else {
					enemy.setX(enemy.getX() + 1);
				}
			}
		}
	}

	private void checkDirection() {
		for (Enemy enemy : enemyList) {
			if (enemy.getRow() == 2) {
				if (direction) {
					if (enemy.getX() == 0) {
						direction = false;
						lowerByOne();
					}
				}
				else {
					if (enemy.getX() == Spaceship.BASE_WIDTH - 1) {
						direction = true;
						lowerByOne();
					}
				}
			}
		}
	}
	
	
	private void lowerByOne() {
		for (Enemy enemy : enemyList) {
			enemy.setY(enemy.getY() + 1);
		}
	}
	
	public boolean killPlayer(Spaceship player) {
		
		for (Enemy enemy : enemyList) {
			if (enemy.getX() == player.getX() && enemy.getY() == player.getY()) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	public void removeInvisbleBombs() {
		@SuppressWarnings("unchecked")
		ArrayList<Bomb> bombCheckList = (ArrayList<Bomb>) bombList.clone();
		for(Bomb bomb : bombCheckList) {
			if (!bomb.isVisible()) {
				bombList.remove(bomb);
			}
		}
	}

	public void moveBombs() {
		for(Bomb bomb : bombList) {
			bomb.move();
		}
	}

	public void hitsPlayer(Spaceship player) {
		if (killPlayer(player)) {
			player.setLife(0);
			return;
		}
		
		for(Bomb bomb : bombList) {
			if (bomb.getX() == player.getX() && bomb.getY() == player.getY()) {
				player.setLife(player.getLife() - 1);
				player.explosionList.add(new Explosion(bomb.getX(),bomb.getY()));
				play("bomb.wav");
			}
		}
	}
	
	private void play(String filename) {
		try {
			// Open an audio input stream.
			URL url = this.getClass().getClassLoader().getResource(filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void createBomb() {
		int rand = (int) (Math.random() * 12);
		
		if (rand == 0) {
			rand = (int) (Math.random() * enemyList.size());
			if (enemyList.size() > 0) {
				Bomb bomb = new Bomb(enemyList.get(rand).getX() , enemyList.get(rand).getY());
				bombList.add(bomb);				
			}
		}
	}
	
	
	public ArrayList<Bomb> getBombs() {
		return bombList;
	}
	

}