package maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    
    private boolean[][] tiles;
    private int width;
    private int height;
    private int count;
    private int area;
    
    private static final boolean FLOOR = false;
    private static final boolean WALL = true;
    
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
                    tiles[i][j] = FLOOR;
                    List<Point> l = new ArrayList<Point>();
                    l.add(new Point(i,j));
                    sets.add(l);
                } else {
                    tiles[i][j] = WALL;
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
            } while (tiles[px][py] == FLOOR);
            
            Point p = new Point(px,py);
            
            List<List<Point>> adjacentLists = new ArrayList<List<Point>>();
            
            for (int i=0;i<4;i++) {
                Point pa = p.getAdjacent(i);
                for (List<Point> l : sets) {
                    if (l.contains(pa)) {
                        adjacentLists.add(l);
                        break;
                    }
                }
            }
            
            if (adjacentLists.size() == 0)
                continue;
            
            /*
            System.out.println("-" + p + "-");
            for(List<Point> l : adjacentLists) {
                for (Point po : l)
                    System.out.println(po);
            }
            System.out.println();
            */
            
            boolean god = true;
            
            System.out.println(this.toString(px,py));
            
            outside:
            for (int i=0;i<adjacentLists.size();i++) {
                for (int j=0;j<adjacentLists.size();j++)  {
                    if (i==j)
                        continue;
                    for (Point p1 : adjacentLists.get(i)) {
                        for (Point p2 : adjacentLists.get(j)) {
                            if (p1.equals(p2)) {
                                god = false;
                                break outside;
                            }
                        }
                    }
                }
            }
            
            if (!god)
                continue;
            
            for (int i=1;i<adjacentLists.size();i++) {
                adjacentLists.get(0).addAll(adjacentLists.get(i));
                sets.remove(adjacentLists.get(i));
            }
            
            tiles[px][py] = FLOOR;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (boolean[] inner : tiles) {
            for (boolean b : inner) {
                s.append(b==FLOOR?'.':'#');
            }
            s.append('\n');
        }
        return s.toString();
    }
    
    public String toString(int x, int y) {
        StringBuilder s = new StringBuilder();
        for (int i=0;i<tiles.length;i++) {
            for (int j=0;j<tiles[0].length;j++) {
                if (i==x && j==y)
                    s.append('X');
                else
                    s.append(tiles[i][j]==FLOOR?'.':'#');
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
            if (o == null || !(o instanceof Point))
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
