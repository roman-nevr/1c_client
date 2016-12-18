package com.example.dmitry.a1c_client.domain.entity;

import android.graphics.Point;

/**
 * Created by Admin on 29.11.2016.
 */
public class StoreMapObject {

    private Place[][] map;
    private int width, height;
    private Point start, finish;

    public void setPlace(int x, int y, int type, String comment){
        width = x;
        height = y;
        map[y][x] = new Place(type, comment);
    }

    public int getWidth(){
        return map[1].length;
    }

    public int getHeight(){
        return map.length;
    }

    public int getType(int x,int y){
        return map[y][x].type;
    }

    public String getComment(int x, int y){
        return map[y][x].comment;
    }

    public StoreMapObject(int width, int height, Point start, Point finish) {
        map = new Place[height][width];
        for (int i = 0; i < (width * height); i++){
            int x = i % width;
            int y = i / width;
            map[y][x] = new Place(0, "");
        }
        this.start = start;
        this.finish = finish;
    }

    public class Place{
        int type;
        public String comment;

        public Place(int type, String comment) {
            this.type = type;
            this.comment = comment;
        }
    }

    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    private static StoreMapObject getFIRST(){
        StoreMapObject storeMapObject = getDummy();
        return storeMapObject;
    }

    private static StoreMapObject getSECOND(){
        StoreMapObject storeMapObject = getDummy();
        storeMapObject.setStart(new Point(4,2));
        storeMapObject.setFinish(new Point(2,8));
        return storeMapObject;
    }

    private static StoreMapObject getDummy(){
        StoreMapObject storeMapObject = new StoreMapObject(7,9, new Point(6,8), new Point(6,2));
        storeMapObject.setPlace(1,4,1,"1");
        storeMapObject.setPlace(2,4,1,"1");
        storeMapObject.setPlace(3,1,2,"2");
        storeMapObject.setPlace(3,2,2,"2");
        storeMapObject.setPlace(4,3,3,"3");
        storeMapObject.setPlace(5,3,3,"3");
        storeMapObject.setPlace(6,3,3,"3");
        storeMapObject.setPlace(1,6,4,"4");
        storeMapObject.setPlace(2,6,4,"4");
        storeMapObject.setPlace(3,6,4,"4");
        storeMapObject.setPlace(4,6,4,"4");
        storeMapObject.setPlace(5,6,4,"4");
        storeMapObject.setPlace(3,7,4,"4");
        storeMapObject.setPlace(3,8,4,"4");
        return storeMapObject;
    }

    public static StoreMapObject EMPTY = new StoreMapObject(0,0, new Point(0,0), new Point(0,0));
    public static StoreMapObject FIRST = getFIRST();
    public static StoreMapObject SECOND = getSECOND();
}
