//Farzyab Gohar
// 101021301

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;


public class FloorBuilderApp extends Application {
    private Building model;
    private FloorBuilderView view;
    private int cindex = 0;
    public static final String[] ROOM_COLORS =
            {"ORANGE", "YELLOW", "LIGHTGREEN", "DARKGREEN",
                    "LIGHTBLUE", "BLUE", "CYAN", "DARKCYAN",
                    "PINK", "DARKRED", "PURPLE", "GRAY"};


    public void start(Stage primaryStage) {
        model = Building.example();
        view = new FloorBuilderView(model);

        view.getRoomtilebutton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update();
            }
        });
        view.getTilebutton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                tileButtonHandler();
                view.update();
            }
        });
        view.getWallbutton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getExitbutton().setSelected(false);
                view.getRoomtilebutton().setSelected(false);
                view.getSelectroombutton().setSelected(false);
            }
        });
        view.getExitbutton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getWallbutton().setSelected(false);
                view.getRoomtilebutton().setSelected(false);
                view.getSelectroombutton().setSelected(false);
            }
        });
        view.getRoomtilebutton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.update();
                view.getExitbutton().setSelected(false);
                view.getWallbutton().setSelected(false);
                view.getSelectroombutton().setSelected(false);
            }
        });

        for (int i =0;i<model.getFloorPlan(0).size();i++){
            for (int j =0;j<model.getFloorPlan(0).size();j++){
                int fi = i;
                int fj = j;
                view.getGridbuttons(i, j).setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        GridSelectionHandler(fi, fj);
                        view.update();
                    }
                });
            }
        }


        primaryStage.setTitle("Floor Plan Builder");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(view,  630,400));
        primaryStage.show();

        view.update();

    }
    public static void main(String[] args) { launch(args); }
    public void tileButtonHandler(){
        if (cindex == 7){
            cindex = 0;
        }else {
            cindex = cindex +1;
        }
        view.getTilebutton().setStyle("-fx-base: " + ROOM_COLORS[cindex]);

    }
    private void GridSelectionHandler(int i, int j){
        if (view.getWallbutton().isSelected()){
            WallButtonHandler(i, j);
        } else if (view.getExitbutton().isSelected()){
            ExitButtonHandler(i, j);
        } else if (view.getRoomtilebutton().isSelected()){
            RoomTileButtonHandler(i, j);
        } else {
        }
    }
    private void WallButtonHandler(int i, int j){
        if (model.getFloorPlan(0).wallAt(i, j)) {
            model.getFloorPlan(0).setWallAt(i, j, false);
        } else if (!model.getFloorPlan(0).wallAt(i, j)){
            model.getFloorPlan(0).setWallAt(i, j, true);
            if (!(model.getFloorPlan(0).roomAt(i, j) == null)){
                model.getFloorPlan(0).roomAt(i, j).removeTile(i, j);
            }
        }
    }
    private void ExitButtonHandler(int i, int j){
        if (model.hasExitAt(i, j)){
            model.removeExit(i, j);
        } else if (!model.hasExitAt(i, j)){
            model.addExit(i, j);
            if (!(model.getFloorPlan(0).roomAt(i, j) == null)){
                model.getFloorPlan(0).roomAt(i, j).removeTile(i, j);
            }
        }
    }
    private void RoomTileButtonHandler(int i, int j){
        if (!(model.getFloorPlan(0).wallAt(i, j) | model.hasExitAt(i, j))){
            if (!(model.getFloorPlan(0).roomAt(i, j) == null)) {
                if (model.getFloorPlan(0).roomAt(i, j).getColorIndex() == cindex){
                    model.getFloorPlan(0).roomAt(i, j).removeTile(i, j);
                } else {
                    if (model.getFloorPlan(0).roomWithColor(cindex) == null){
                        model.getFloorPlan(0).addRoomAt(i, j);
                        model.getFloorPlan(0).roomAt(i, j).setColorIndex(cindex);

                    } else {
                        model.getFloorPlan(0).roomAt(i, j).removeTile(i, j);
                        model.getFloorPlan(0).roomWithColor(cindex).addTile(i, j);
                    }

                }

            } else if (model.getFloorPlan(0).roomWithColor(cindex) == null){
                model.getFloorPlan(0).addRoomAt(i, j);
                model.getFloorPlan(0).roomAt(i, j).setColorIndex(cindex);
            } else {
                model.getFloorPlan(0).roomWithColor(cindex).addTile(i, j);
            }

        }

    }

}
