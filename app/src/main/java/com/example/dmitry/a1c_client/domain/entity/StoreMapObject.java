package com.example.dmitry.a1c_client.domain.entity;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 29.11.2016.
 */
public class StoreMapObject {

    private Place[][] map;
    private int width, height;
    private Point start, finish;
    private List<Point> path;

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
        path = null;
        this.finish = finish;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        path = null;
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

    public List<Point> getPath(){
        if (path != null){
            return path;
        }else {
            AStarPathing aStarPathing = new AStarPathing(this);
            path = aStarPathing.pathing();
            return path;
        }
    }

    public class AStarPathing {
        private StoreMapObject storeMapObject;
        private AStarPathing.AStarValue[][] values;
        private Point start, finish;

        private List<AStarValue> uncheckedList;
        private List<AStarValue> checkedList;

        public static final int HEURISTIC_VALUE = 10;
        public static final int STEP_COST = 10;
        public static final int DIAGONAL_STEP_COST = 14;

        public AStarPathing(StoreMapObject storeMapObject) {
            this.storeMapObject = storeMapObject;
            values = new AStarPathing.AStarValue[storeMapObject.getHeight()][storeMapObject.getWidth()];
        }

        public List<Point> pathing() {
            return pathing(storeMapObject.getStart(), storeMapObject.getFinish());
        }

        public List<Point> pathing(Point start, Point finish) {
            this.start = start;
            this.finish = finish;
            calcHeuristicCost();
            uncheckedList = new ArrayList<>();
            checkedList = new ArrayList<>();
            AStarPathing.AStarValue current = values[start.y][start.x];
            current.movementCost = 0;
            checkedList.add(values[start.y][start.x]);
            boolean stop = false;
            while (!stop) {
                addPointToUncheckedList(current, uncheckedList);
                current = findBest(uncheckedList);
                checkedList.add(current);
                if (current.position.equals(finish)) {
                    stop = true;
                }
            }
            System.out.println(current.movementCost);
            return generatePath(current);
        }

        private List<Point> generatePath(AStarPathing.AStarValue current) {
            List<Point> result = new LinkedList<>();
            boolean stop = false;
            do {
                result.add(0, current.position);
                current = values[current.previous.y][current.previous.x];
            } while (!current.position.equals(start));
            result.add(0, current.position);
            return result;
        }


        private AStarPathing.AStarValue findBest(List<AStarPathing.AStarValue> uncheckedList) {
            AStarPathing.AStarValue result = uncheckedList.get(0);
            int cost = uncheckedList.get(0).movementCost + uncheckedList.get(0).heuristicCost;
            for (AStarPathing.AStarValue value : uncheckedList) {
                if ((value.movementCost + value.heuristicCost) < cost) {
                    cost = value.movementCost + value.heuristicCost;
                    result = value;
                }
            }
            uncheckedList.remove(result);
            return result;
        }

        private void addPointToUncheckedList(AStarPathing.AStarValue current, List<AStarPathing.AStarValue> uncheckedList) {
            addPointIfExists(current.position.x - 1, current.position.y, uncheckedList, current, STEP_COST);
            addPointIfExists(current.position.x + 1, current.position.y, uncheckedList, current, STEP_COST);
            addPointIfExists(current.position.x, current.position.y - 1, uncheckedList, current, STEP_COST);
            addPointIfExists(current.position.x, current.position.y + 1, uncheckedList, current, STEP_COST);
            addPointIfExists(current.position.x + 1, current.position.y + 1, uncheckedList, current, DIAGONAL_STEP_COST);
            addPointIfExists(current.position.x + 1, current.position.y - 1, uncheckedList, current, DIAGONAL_STEP_COST);
            addPointIfExists(current.position.x - 1, current.position.y + 1, uncheckedList, current, DIAGONAL_STEP_COST);
            addPointIfExists(current.position.x - 1, current.position.y - 1, uncheckedList, current, DIAGONAL_STEP_COST);
        }

        private void addPointIfExists(int x, int y, List<AStarPathing.AStarValue> uncheckedList, AStarPathing.AStarValue previous, int cost) {
            if ((x >= 0) && (x < storeMapObject.getWidth()) && (y >= 0) &&
                    (y < storeMapObject.getHeight()) && (!checkedList.contains(values[y][x])) &&
                    (isPointReachable(x, y)) && (isPointReachable(x, previous.position.y) &&
                    (isPointReachable(previous.position.x, y)))) {
                if (!uncheckedList.contains(values[y][x])) {
                    values[y][x].movementCost = previous.movementCost + cost;
                    values[y][x].previous = new Point(previous.position.x, previous.position.y);
                    uncheckedList.add(values[y][x]);
                } else {
                    if (values[y][x].movementCost > previous.movementCost + cost) {
                        uncheckedList.remove(values[y][x]);
                    }
                }
            }
        }

        private boolean isPointReachable(int x, int y) {
            return storeMapObject.getType(x, y) == 0;
        }

        private void addNeighborPointIfExists(int x, int y, List<AStarPathing.AStarValue> uncheckedList, AStarPathing.AStarValue previous) {
            if ((x >= 0) && (x < storeMapObject.getWidth()) && (y >= 0) &&
                    (y < storeMapObject.getHeight()) && (!checkedList.contains(values[y][x])) &&
                    (storeMapObject.getType(x, y) == 0)) {
                values[y][x].movementCost = previous.movementCost + STEP_COST;
                values[y][x].previous = new Point(previous.position.x, previous.position.y);
                uncheckedList.add(values[y][x]);
            }
        }


        private void calcHeuristicCost() {
            int movementCost = HEURISTIC_VALUE * (storeMapObject.getWidth() + storeMapObject.getHeight());
            for (int y = 0; y < storeMapObject.getHeight(); y++) {
                for (int x = 0; x < storeMapObject.getWidth(); x++) {
                    int heuristicCost = (int) Math.sqrt(Math.pow((x - finish.x) * STEP_COST, 2.0f) +
                            Math.pow((y - finish.y) * STEP_COST, 2.0f));
                    values[y][x] = new AStarPathing.AStarValue(new Point(x, y), heuristicCost, movementCost);
                }
            }
        }

        private class AStarValue implements Comparable {
            int heuristicCost;
            int movementCost;
            Point position;
            Point previous;

            public AStarValue(Point position, int heuristicCost, int movementCost) {
                this.position = position;
                this.heuristicCost = heuristicCost;
                this.movementCost = movementCost;
            }

            public int getF() {
                return heuristicCost + movementCost;
            }

            @Override
            public String toString() {
                return "position (" + position.x + "," + position.y + ") cost = " + (movementCost + heuristicCost);
            }

            @Override
            public int compareTo(Object o) {
                if (o instanceof AStarPathing.AStarValue) {
                    return (movementCost + heuristicCost - ((AStarPathing.AStarValue) o).movementCost - ((AStarPathing.AStarValue) o).heuristicCost);
                } else {
                    return 0;
                }
            }
        }
    }
}
