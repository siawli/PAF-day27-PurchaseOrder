package PO.day27.purchaseOrder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import PO.day27.purchaseOrder.exception.PriceExceeded;
import PO.day27.purchaseOrder.service.PurchaseOrderService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
public class PurchaseOrderRestController {

    @Autowired
    private PurchaseOrderService poSvc;

    @GetMapping(path="/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newOrder(@RequestBody String jsonString) {

        System.out.println(">>>> jsonString: " + jsonString);

        JsonObjectBuilder jsonObjB = Json.createObjectBuilder();

        try {
            Integer orderId = poSvc.createPOandLineItems(jsonString);
            JsonObject message = jsonObjB.add("order_id", orderId).build();
            return ResponseEntity.ok().body(message.toString());
        } catch (PriceExceeded ex) {
            ex.printStackTrace();
            JsonObject message =jsonObjB.add("error", ex.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message.toString());
        }

    }    
}
