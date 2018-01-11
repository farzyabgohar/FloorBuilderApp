//Farzyab Gohar
// 101021301
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.geometry.Insets;




public class FloorBuilderView extends GridPane {
    private Label floorlayoutlabel, selecteditlabel, floorsummarylabel;
    private RadioButton wallbutton, exitbutton, roomtilebutton, selectroombutton;
    private Button buildingoverviewbutton, tilebutton;
    private TextField summaryfield;
    private Button[][] gridbuttons = new Button[20][20];
    private GridPane gridbuttonpane;
    private GridPane radiobuttonpane;
    public static final String[] ROOM_COLORS =
            {"ORANGE", "YELLOW", "LIGHTGREEN", "DARKGREEN",
                    "LIGHTBLUE", "BLUE", "CYAN", "DARKCYAN",
                    "PINK", "DARKRED", "PURPLE", "GRAY"};


    public Label getFloorlayoutlabel() {
        return floorlayoutlabel;
    }

    public Label getSelecteditlabel() {
        return selecteditlabel;
    }

    public Label getFloorsummarylabel() {
        return floorsummarylabel;
    }

    public RadioButton getWallbutton() {
        return wallbutton;
    }

    public RadioButton getExitbutton() {
        return exitbutton;
    }

    public RadioButton getRoomtilebutton() {
        return roomtilebutton;
    }

    public RadioButton getSelectroombutton() {
        return selectroombutton;
    }

    public Button getBuildingoverviewbutton() {
        return buildingoverviewbutton;
    }

    public Button getTilebutton() {
        return tilebutton;
    }

    public TextField getSummaryfield() {
        return summaryfield;
    }

    public Button getGridbuttons(int i, int j) {
        return gridbuttons[i][j];
    }


    public GridPane getRadiobuttonpane() {
        return radiobuttonpane;
    }

    private Building model;

    public FloorBuilderView(Building m){
        model = m;
        setPadding(new Insets(10, 10, 10, 10));

        gridbuttons = new Button[model.getFloorPlan(0).size()][model.getFloorPlan(0).size()];
        gridbuttonpane = new GridPane();
        radiobuttonpane = new GridPane();
        floorlayoutlabel = new Label("FLOOR LAYOUT");
        selecteditlabel = new Label("SELECT/EDIT");
        floorsummarylabel = new Label("FLOOR SUMMARY:");
        buildingoverviewbutton = new Button("Building Overview");
        tilebutton = new Button();
        wallbutton = new RadioButton("Walls");
        exitbutton = new RadioButton("Exits");
        roomtilebutton = new RadioButton("Room Tiles");
        selectroombutton = new RadioButton("Select Room");
        summaryfield = new TextField();
        floorlayoutlabel.setMinSize(100, 10);
        selecteditlabel.setMinSize(100, 20);
        roomtilebutton.setMinSize(100, 0);
        buildingoverviewbutton.setMinSize(120, 0);
        summaryfield.setPrefWidth(Integer.MAX_VALUE);
        tilebutton.setMinSize(30, 30);
        tilebutton.setStyle("-fx-base: Orange");
        floorsummarylabel.setMinHeight(30);

        for (int i =0;i<20; i++) {
            for (int j =0;j<20;j++){
                gridbuttons[i][j] = new Button();
                gridbuttons[i][j].setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                gridbuttonpane.add(gridbuttons[i][j], j*1, i*1);
                if (model.getFloorPlan(0).wallAt(i, j)){
                    if (!(model.exitAt(i, j) == null)) {
                        gridbuttons[i][j].setStyle("-fx-base: RED");
                        gridbuttons[i][j].setText("EXIT");
                    }else {
                        gridbuttons[i][j].setStyle("-fx-base: BLACK");
                    }
                }else {
                    gridbuttons[i][j].setStyle("-fx-base: WHITE");
                }

            }
        }


        add(gridbuttonpane, 1,1);
        add(floorlayoutlabel, 1, 0);
        add(selecteditlabel, 2,0);
        radiobuttonpane.add(wallbutton, 0, 0);
        radiobuttonpane.add(exitbutton,0, 1);
        radiobuttonpane.add(roomtilebutton, 0, 2);
        radiobuttonpane.add(selectroombutton, 0, 3);
        radiobuttonpane.add(buildingoverviewbutton, 0, 5);
        radiobuttonpane.add(tilebutton, 1, 2);
        radiobuttonpane.setMargin(wallbutton, new Insets(10, 10, 10, 10));
        radiobuttonpane.setMargin(exitbutton, new Insets(0, 10, 10, 10));
        radiobuttonpane.setMargin(roomtilebutton, new Insets(0, 10, 10, 10));
        radiobuttonpane.setMargin(selectroombutton, new Insets(0, 10, 10, 10));
        radiobuttonpane.setMargin(buildingoverviewbutton, new Insets(5, 10, 0, 5));
        add(radiobuttonpane, 2, 1);
        add(floorsummarylabel, 1, 2);
        add(summaryfield, 1, 3, 2, 1);

    }
    public void update(){
        for (int i =0;i<model.getFloorPlan(0).size(); i++) {
            for (int j =0;j<model.getFloorPlan(0).size();j++){
                if (!(model.exitAt(i, j) == null)) {
                    gridbuttons[i][j].setStyle("-fx-base: RED");
                    gridbuttons[i][j].setText("EXIT");
                } else if (model.getFloorPlan(0).wallAt(i, j)) {
                    gridbuttons[i][j].setStyle("-fx-base: BLACK");
                    gridbuttons[i][j].setText("");
                } else if (!(model.getFloorPlan(0).roomAt(i, j) == null)){
                    gridbuttons[i][j].setStyle("-fx-base: " + ROOM_COLORS[model.getFloorPlan(0).roomAt(i, j).getColorIndex()]);
                    gridbuttons[i][j].setText("");

                }else {
                    gridbuttons[i][j].setStyle("-fx-base: WHITE");
                    gridbuttons[i][j].setText("");
                }


            }
        }
        tilebutton.setDisable(!roomtilebutton.isSelected());
        summaryfield.setText(model.getFloorPlan(0).getName() + " with " + model.getFloorPlan(0).getNumberOfRooms() + " rooms.");
    }
}
