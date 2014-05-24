package maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    
    private boolean[][] tiles;
    private int width;
    private int height;
    private int count;
    private int area;
    
    private List<List<Point>> sets;
    
    public Maze(int w, int h) {
        tiles = new boolean[w][h];
        width = w;
        height = h;
        count = 0;
        area = w*h;
        
        sets = new ArrayList<>();
        
        for (int i=0;i<w;i++) {
            for (int j=0;j<h;j++) {
                if (i%2==1 && j%2==1) {
                    tiles[i][j] = true;
                    List<Point> l = new ArrayList<Point>();
                    l.add(new Point(i,j));
                    sets.add(l);
                }
            }
        }
    }
    
    public void generateMaze() {
        int px;
        int py;
        
        while(sets.size() > 1) {
            do {
                px = (int)(width*Math.random());
                py = (int)(height*Math.random());
            } while (tiles[px][py]);
            
            Point p = new Point(px,py);
            
            List<List<Point>> list = new ArrayList<List<Point>>();
            
            for (List<Point> l : sets) {
                for (int i=0;i<4;i++) {
                    Point pa = p.getAdjacent(i);
                    if (l.contains(pa)) {
                        list.add(l);
                    }
                }
            }
            
            System.out.println("-" + p + "-");
            for(List<Point> l : list) {
                for (Point po : l)
                    System.out.println(po);
            }
            System.out.println();
            
            boolean god = true;
            
            for (int i=0;i<list.size();i++) {
                for (int j=0;j<list.size();j++)  {
                    if (i==j)
                        continue;
                    if (list.get(i) == list.get(j)) {
                        System.out.println("Asd");
                        god = false;
                    }
                }
            }
            
            if (!god)
                continue;
            
            for (int i=1;i<list.size();i++) {
                list.get(0).addAll(list.get(i));
                sets.remove(list.get(i));
            }
            
            tiles[px][py] = true;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (boolean[] inner : tiles) {
            for (boolean b : inner) {
                s.append(b?'.':'#');
            }
            s.append('\n');
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
        Maze maze = new Maze(15,15);
        System.out.println(maze);
        maze.generateMaze();
        System.out.println(maze);
    }
    
    class Point {
        
        public final int x;
        public final int y;
                
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Point getAdjacent(int dir) {
            return new Point(x+(dir%2==1?0:1-dir),y+(dir%2==0?0:dir-2));
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (!(o instanceof Point))
                return false;
            Point p = (Point) o;
            return p.x==x && p.y==y;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 17 * hash + this.x;
            hash = 17 * hash + this.y;
            return hash;
        }
        
        @Override
        public String toString() {
            return x + " " + y;
        }
    }
}
