package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JFrame {
    
    private static final int defaultWidth = 64;
    private static final int defaultHeight = 64;
    
    private static final int windowWidth = 640;
    private static final int windowHeight = 480;
    
    private Screen frame;
    
    public GUI(int w, int h) {
        super();
        
        Maze maze = Maze.genMaze(w,h);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setTitle("Maze");
        
        frame = new Screen(w,h);
        frame.setMaze(maze);
        
        Dimension size = new Dimension(Math.min(w*16,windowWidth),Math.min(h*16,windowHeight));
        
        if (w*16>windowWidth || h*16>windowHeight) {
            setPreferredSize(size);
            JScrollPane scrollPane = new JScrollPane(frame);
            scrollPane.getVerticalScrollBar().setUnitIncrement(4);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(4);
            add(scrollPane);
        } else {
            setSize(size);
            add(frame);
        }
        pack();
    }
    
    public static void main(String[] args) {
        int width = args.length==2 ? Integer.valueOf(args[0]) : defaultWidth;
        int height = args.length==2 ? Integer.valueOf(args[1]) : defaultHeight;
        
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException| UnsupportedLookAndFeelException e) {
            System.out.println("Error loading look and feel: " + e);
        }
        
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