package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUI extends JFrame {
    
    private static final int width = 16;
    private static final int height = 16;
    
    private Screen frame;
    
    public GUI(int w, int h) {
        super();
        
        setLocation(200,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setMaximumSize(new Dimension(640,480));
        setVisible(true);
        setTitle("Maze");
        
        frame = new Screen(w,h);
        frame.setMaze(Maze.genMaze(w,h));
        
        setPreferredSize(new Dimension(Math.min(w*16,640),Math.min(h*16,480)));
        
        if (w*16 > 640 || h*16 > 480) {
            JScrollPane scrollPane = new JScrollPane(frame);
            add(scrollPane);
        } else {
            add(frame);
        }
        pack();
    }
    
    public static void main(String[] args) {
        GUI window = new GUI(width,height);
        window.repaint();
    }
        
    @Override
    public void repaint() {
        super.repaint();
    }
}

class Screen extends JPanel {
    
    private Maze maze;
    
    public Screen(int w, int h) {
        //setPreferredSize(new Dimension(Math.min(w*16,640),Math.min(h*16,480)));
        setPreferredSize(new Dimension(w*16,h*16));
    }
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (maze == null)
            return;
        
        boolean[][] tiles = maze.getTiles();
        
        for (int i=0;i<tiles.length;i++) {
            for (int j=0;j<tiles[0].length;j++) {
                g.setColor(tiles[i][j] ? Color.black : Color.white);
                g.fillRect(i*16,j*16,16,16);
            }
        }
    }
    
    public void setMaze(Maze m) { maze = m; }
}