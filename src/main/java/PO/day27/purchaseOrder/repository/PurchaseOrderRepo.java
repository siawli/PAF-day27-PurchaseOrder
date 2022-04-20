package PO.day27.purchaseOrder.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import PO.day27.purchaseOrder.model.PurchaseOrder;

import static PO.day27.purchaseOrder.repository.Queries.*;

@Repository
public class PurchaseOrderRepo {

    @Autowired
    private JdbcTemplate template;

    public Integer createPurchaseOrder(PurchaseOrder po) {
        int added = template.update(SQL_INSERT_PO,
                    po.getName(),
                    po.getEmail());
        
        SqlRowSet result = template.queryForRowSet(SQL_GET_ORDER_ID, po.getName());
        result.next();
        return result.getInt("order_id");
    }
    
}
