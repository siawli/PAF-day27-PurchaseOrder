package PO.day27.purchaseOrder.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PO.day27.purchaseOrder.exception.PriceExceeded;
import PO.day27.purchaseOrder.model.LineItems;
import PO.day27.purchaseOrder.model.PurchaseOrder;
import PO.day27.purchaseOrder.repository.LineItemsRepo;
import PO.day27.purchaseOrder.repository.PurchaseOrderRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class PurchaseOrderService {

    @Autowired
    private LineItemsRepo lineItemsRepo;

    @Autowired
    private PurchaseOrderRepo poRepo;

    @Transactional(rollbackFor = PriceExceeded.class)
    public Integer createPOandLineItems(String jsonString) throws PriceExceeded {

        JsonObject newOrder = jsonStringToObject(jsonString);
        PurchaseOrder newPo = createPO(newOrder);

        Integer createdPo = poRepo.createPurchaseOrder(newPo);

        Double totalPrice = 0.0;
        for (LineItems li : newPo.getListLineItems()) {
            totalPrice += li.getUnitPrice() * li.getQuantity();
        }

        if (totalPrice > 20000) {
            throw new PriceExceeded("Price exceeded SGD 20000. Your total price: %.2f".formatted(totalPrice));
        }

        for (LineItems li : newPo.getListLineItems()) {
            boolean added = lineItemsRepo.createLineItems(li, createdPo);
        }
        
        return createdPo;
    }

    public JsonObject jsonStringToObject(String jsonString) {
        JsonObject po = null;
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            po = reader.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return po;
    }

    public List<LineItems> createLineItems(JsonObject po) {
        JsonArray jsonArrLineItems = po.getJsonArray("lineitems");
        List<LineItems> listLineItems = new LinkedList<>();

        for (JsonValue lineItem : jsonArrLineItems) {
            LineItems item = new LineItems();
            item.setDescription(lineItem.asJsonObject().getString("description"));
            item.setQuantity(lineItem.asJsonObject().getInt("quantity"));
            JsonValue unitPrice = lineItem.asJsonObject().get("unitPrice");
            item.setUnitPrice(Double.parseDouble(unitPrice.toString()));
            listLineItems.add(item);
        }

        return listLineItems;
    }

    public PurchaseOrder createPO(JsonObject jsonObj) {
        PurchaseOrder po = new PurchaseOrder();
        po.setEmail(jsonObj.getString("email"));
        po.setName(jsonObj.getString("name"));
        po.setListLineItems(createLineItems(jsonObj));

        return po;
    }

    
}
