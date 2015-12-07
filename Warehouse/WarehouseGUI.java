import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;

import javafx.event.*;

import javafx.geometry.*;

import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.beans.value.*;

import javafx.collections.*;
import javafx.scene.control.cell.*;
import java.io.*;
import java.util.*;


import java.io.IOException;
import java.net.URL;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;

import javax.xml.*;

public class WarehouseGUI extends Application 
{
    /**Runs application*/
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    Button mainMenu, orderMenu, picklistMgmtMenu, invMgmtMenu, returnB1, returnB2;
    Label mainLabel, orderLabel, picklistLabel, invLabel;
    VBox mainPane, orderPane, picklistPane, invPane;
    Scene mainScene, orderScene, picklistScene, invScene;
    Stage theStage;
    
    @Override
    public void start(Stage primaryStage) 
    {
        //Chris - db.getOrder(orderNumber) returns an order
        cloudDatabaseManager db = new cloudDatabaseManager();
        
        theStage = primaryStage;
        theStage.getIcons().add(new Image("box.png"));
        
        //can now use the stage in other methods
        
        //make things to put on panes
        
        mainMenu =new Button("Return");
        returnB1 = new Button("Return");
            returnB1.setOnAction(e-> ButtonClicked(e));
        returnB2 = new Button("Return");
            returnB2.setOnAction( e-> ButtonClicked(e));
        
        

        
        orderMenu = new Button("Orders");
        picklistMgmtMenu = new Button("Picklists");
        invMgmtMenu = new Button("Inventory Management");
        
        
        mainMenu.setOnAction(e-> ButtonClicked(e));
        orderMenu.setOnAction(e-> ButtonClicked(e));
        picklistMgmtMenu.setOnAction(e-> ButtonClicked(e));
        invMgmtMenu.setOnAction(e-> ButtonClicked(e));
        
        mainLabel = new Label("Main");
        orderLabel = new Label("Orders");
        picklistLabel = new Label("Picklists");
        invLabel = new Label("Inventory Management");

        /**orderLabel = new Label("Scene 2");*/
        
        //Panes
        mainPane = new VBox();
        orderPane = new VBox();
        picklistPane = new VBox();
        invPane = new VBox();

            /**        mainPane.setVgap(10);
        orderPane.setVgap(10);
        picklistPane.setVgap(10);
        invPane.setVgap(10); */
        
        //set background color of each Pane
        mainPane.setStyle("-fx-background-color: #2B547E; -fx-font-family: 'Century Gothic'; -fx-padding: 10px; -fx-font-color: #FFFFFF;");
        orderPane.setStyle("-fx-background-color: #2B547E; -fx-font-family: 'Century Gothic'; -fx-padding: 10px; -fx-font-color: #FFFFFF;");
        picklistPane.setStyle("-fx-background-color: #2B547E; -fx-font-family: 'Century Gothic'; -fx-padding: 10px; -fx-font-color: #FFFFFF;");
        invPane.setStyle("-fx-background-color: #2B547E; -fx-font-family: 'Century Gothic'; -fx-padding: 10px; -fx-font-color: #FFFFFF;");
        
        
       
        
        /**Test order lines*********/
        /**https://www.youtube.com/watch?v=tVj0ZTS4WF4&t=40*/
        String p1 = new String("Vidya");
        OrderLine line1 = new OrderLine(p1, 012, 34);
        
        String p2 = new String("Record");
        OrderLine line2 = new OrderLine(p2, 113, 7);
        
        String p3 = new String("Soda");
        OrderLine line3 = new OrderLine(p3, 731, 8);
        
        OrderLine[] order1 = {line1, line3};
        OrderLine[] order2 = {line3, line1, line2};
        
        Picklist list1 = new Picklist(order1);
        Picklist list2 = new Picklist(order2);
        
        //Order o1 = new Order("Processing", 10375/*, list1*/);
        //Order o2 = new Order("Shipped", 11440/*, list2*/);
        
        /***************************/
        //XML Table for orders
        TableView orderTable = new TableView();
        orderTable.setEditable(true);

        TableColumn orderNumC = new TableColumn("Order Number");
        TableColumn orderStatusC = new TableColumn("Order Status");

        orderTable.getColumns().addAll(orderNumC, orderStatusC);

        // implements the List interface, Observable design pattern // initializes it with the orders from the database
        final ObservableList<Order> oList = FXCollections.observableArrayList(db.getAllOrders());

        //oList.add( new Order("Processing", 10375/*, list1 */));
        //oList.add( new Order("Shipped", 11440/*, list2 */));
       

        orderTable.setItems(oList);
        orderNumC.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
        orderStatusC.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        
        Random rand = new Random();
        int custId = rand.nextInt(1000)+1;
        int prodId = rand.nextInt(1000)+1;
        int quant = rand.nextInt(3000)+1;
        
        //Add/remove orders
        
        final TextField orderNumText = new TextField();
        orderNumText.setPromptText("Enter new order # (increment from highest)");
        
        final TextField orderStatusText = new TextField();
        orderStatusText.setPromptText("Enter new order status");

        Button addButton = ButtonBuilder.create()
            .text("Add Order")
            .onAction(new EventHandler<ActionEvent>()
              {
                    public void handle(ActionEvent ae)
                    {
                        int newOrderNum = Integer.parseInt( orderNumText.getText() );

                        String newOrderStatus = orderStatusText.getText();

                        Order neworder = new Order(newOrderStatus,newOrderNum );
                        oList.add( neworder );
                        db.addOrder(custId, prodId, quant, newOrderStatus);
                        
                        final ObservableList<Order> oListUpdate = FXCollections.observableArrayList(db.getAllOrders());
                        orderTable.getColumns().clear();
                        orderTable.setItems(oListUpdate);
                        orderTable.getColumns().addAll(orderNumC, orderStatusC);
                    }
                }
                )
            .build();

        
        final TextField removeText = new TextField();
        removeText.setPromptText("Enter order # to remove");

        Button removeButton = ButtonBuilder.create()
            .text("Remove")
            .onAction(  new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent ae)
                    {
                        int n = Integer.parseInt( removeText.getText() );
                        db.remove(n);
                        
                        final ObservableList<Order> oListUpdate = FXCollections.observableArrayList(db.getAllOrders());
                        orderTable.getColumns().clear();
                        orderTable.setItems(oListUpdate);
                        orderTable.getColumns().addAll(orderNumC, orderStatusC);
                        //orderTable.getColumns().clear();
                        //orderTable.getColumns().addAll(orderNumC, orderStatusC);
                        
                        //oList.remove();
                        /**
                        String toremove = orderNumC.getCellObservableValue(n).toString();
                        int comp = Integer.parseInt(toremove);
                        for (int i = 0; i < oList.size(); i++)
                        {
                            if (comp == n)
                            {
                                oList.remove(i);
                            }
                        }
                        */
                    }
                }
                )
            .build();

        /****************************/
        //XML Table for picklists???
        TableView picklistTable = new TableView();
        picklistTable.setEditable(true);

        TableColumn prodC = new TableColumn("Product");
        TableColumn skuC = new TableColumn("SKU");
        TableColumn quantC = new TableColumn("Quantity");

        picklistTable.getColumns().addAll(prodC, skuC, quantC);

        // implements the List interface, Observable design pattern
        final ObservableList<Picklist> pList = FXCollections.observableArrayList();

        pList.add(list1);
        pList.add(list2);

        picklistTable.setItems(pList);
        prodC.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("prodName"));
        skuC.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("skusku"));
        quantC.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("quant"));
        
        /**********************/
        
        
        //add everything to panes
        mainPane.getChildren().addAll( orderMenu, picklistMgmtMenu, invMgmtMenu);
        orderPane.getChildren().addAll( orderTable, orderNumText, orderStatusText,addButton, removeText, removeButton, returnB1);
        picklistPane.getChildren().addAll( picklistTable, returnB2 );
        invPane.getChildren().addAll(invLabel, mainMenu);
        
        
        mainScene = new Scene(mainPane, 600, 750);
        orderScene = new Scene(orderPane, 600, 750);
        picklistScene = new Scene(picklistPane, 600, 750);
        invScene = new Scene(invPane, 600, 750);
        
        primaryStage.setTitle("Warehouse Management");
        primaryStage.setScene(mainScene);
        primaryStage.show();
        

    }
    
    public void ButtonClicked(ActionEvent e)
    {
        if (e.getSource()==orderMenu)
        {
            theStage.setScene(orderScene);
        }
        else if (e.getSource()==picklistMgmtMenu)
        {
            theStage.setScene(picklistScene);
        }
        else if (e.getSource()==invMgmtMenu)
        {
            theStage.setScene(invScene);
        }
        else
        {
            theStage.setScene(mainScene);
        }
    }

    
}
