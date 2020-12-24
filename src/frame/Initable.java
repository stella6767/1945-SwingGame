package frame;

public interface Initable {
	
	public static final int SCREEN_WIDTH = 600; // 창의 크기를 절대값으로 고정
	public static final int SCREEN_HEIGHT = 820;

	
	void init(); 
	void setting();
	//void batch(); 
	void listener(); 
}
