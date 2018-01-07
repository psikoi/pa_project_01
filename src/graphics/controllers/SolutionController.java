package graphics.controllers;

import graphics.Controller;
import java.util.HashSet;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import tads.graph.model.Joint;

public class SolutionController implements Controller {

    private HashSet<Joint> joints;

    public SolutionController(HashSet<Joint> joints) {
        this.joints = joints;
    }

    @Override
    public void render(Pane pane) {

        if (joints == null || joints.isEmpty()) {
            return;
        }

        Polygon polygon = new Polygon();
        
        for(Joint joint : joints){
            polygon.getPoints().addAll(new Double[]{(double) joint.getX(),(double) joint.getY()});
        }
        
        polygon.setFill(new Color(0, 0, 0, .15));
        pane.getChildren().add(polygon);
        

    }

}
