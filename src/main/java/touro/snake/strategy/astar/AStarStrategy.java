package touro.snake.strategy.astar;

import touro.snake.*;
import touro.snake.strategy.Node;
import touro.snake.strategy.SnakeStrategy;

import java.util.ArrayList;

/**
 * Implement the AStar Algorithm
 * https://www.youtube.com/watch?v=-L-WgKMFuhE
 * https://medium.com/@nicholas.w.swift/easy-a-star-pathfinding-7e6689c7f7b2
 */

public class AStarStrategy implements SnakeStrategy {
    @Override
    public void turnSnake(Snake snake, Garden garden) {

        //initialize open list -- has the possibilities
        ArrayList<Node> open = new ArrayList<>();
        //initialize closed list -- has the path among other nodes visited that had low numbers
        ArrayList<Node> closed = new ArrayList<>();

        Node headNode = new Node(snake.getHead());  //headNode = start
        Food food = garden.getFood();
        //protect against null pointer exception
        if (food == null) {
            return;
        }
        Node endNode = new Node(food.getX(), food.getY());   //endNode = target
        System.out.println("food:    x: " + endNode.getX() + "       y:  " + endNode.getY());

        //put starting node on open list
        open.add(headNode);

        Node currentNode;
        //loop until found the end
        while (!open.isEmpty()) {

            //let the currentNode equal the node with the least f value
            currentNode = getLeastCostNode(open);

            open.remove(currentNode);
            closed.add(currentNode);

            //Found the goal
            if (currentNode.equals(endNode)) {
/*                //List with food and all it's parents i.e. the path
                ArrayList<Node> path = new ArrayList<>();
                path.add(currentNode);
                while (currentNode.getParent() != null) {
                    path.add(currentNode.getParent());
                    currentNode = currentNode.getParent();
                }*/
                //Collections.reverse(path);

                /*for (int ix = 0; ix-1 < path.size(); ix++) {
                    snake.turnTo(path.get(ix).directionTo(path.get(ix + 1)));
                    garden.advance();
                    System.out.println(path.toArray().toString() + "__________________________found______________________");
                }*/

                //snake moves according to closed list
                makeSnakeMove(snake, garden, currentNode);
                System.out.println("I'm here");
                return;
            }


            //Generate children
            ArrayList<Node> children = new ArrayList<>();

            Node down = new Node(currentNode.getX(), currentNode.getY() - 1);
            Node up = new Node(currentNode.getX(), currentNode.getY() + 1);
            Node left = new Node(currentNode.getX() - 1, currentNode.getY());
            Node right = new Node(currentNode.getX() + 1, currentNode.getY());
            children.add(down);
            children.add(up);
            children.add(left);
            children.add(right);

            for (Node child : children) {  // Adjacent squares
                //adjacent square on closed list
                if (closed.contains(child)) {
                    continue;
                }
                //adjacent square not walkable
                if(!child.inBounds()) {
                    continue;
                }

                //adjacent square not on open list
                if(!open.contains(child)) {
                    // Add the child to the openList
                    open.add(child);
                    // Make adjacent square the parent of current node
                    currentNode.setParent(child);
                }

                //adjacent square on open list
                if(open.contains(child)||child.getCost() < currentNode.getCost()) {
                    currentNode.setParent(child);
                        //System.out.println(" child: ------------     x is" + child.getX() + "and y is" + child.getY() + "--------------");
                        //System.out.println(" currentNode: ------------     x is" + currentNode.getX() + "and y is" + currentNode.getY() + "--------------");

                }

            }
        }

    }

    private void makeSnakeMove(Snake snake, Garden garden, Node currentNode) {
        /*for (int ix = 0; ix-1 < closed.size(); ix++) {
            snake.turnTo(closed.get(ix).directionTo(closed.get(ix + 1)));
            garden.advance();
            }*/
        ArrayList<Node> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        if (path.size()>1) {
            for (int ix = 0; ix - 1 < path.size(); ix++) {
                snake.turnTo(path.get(ix).directionTo(path.get(ix + 1)));
                //snake.move();
                garden.advance();
            }
        }
   }

    private Node getLeastCostNode(ArrayList<Node> open) {
        Node smallest = open.get(0);
        for (int ix = 1; ix < open.size(); ix++) {
            if (smallest.getCost() > open.get(ix).getCost()) {
                smallest = open.get(ix);
            }
        }
        return smallest;
    }
}



