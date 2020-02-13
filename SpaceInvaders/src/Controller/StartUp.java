package Controller;

import java.awt.EventQueue;

import acm.program.GraphicsProgram;

public class StartUp extends GraphicsProgram {
	
	public static void main(String[] args) {
		new StartUp().start();
	}
	
	
	public void init() {

        add(new Board());

        setTitle("Space Invaders");
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    @Override
    public void run() {
    	EventQueue.invokeLater(() -> {

            var ex = new StartUp();
            ex.setVisible(true);
        });
    	super.run();
    }

}