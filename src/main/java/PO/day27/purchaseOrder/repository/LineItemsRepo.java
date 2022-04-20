package PO.day27.purchaseOrder.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import PO.day27.purchaseOrder.model.LineItems;

import static PO.day27.purchaseOrder.repository.Queries.*;

@Repository
public class LineItemsRepo {

    @Autowired
    private JdbcTemplate template;

    public boolean createLineItems(LineItems lineitem, Integer orderId) {
        int added = template.update(SQL_INSERT_LINE_ITEMS,
            lineitem.getDescription(),
            lineitem.getQuantity(),
            lineitem.getUnitPrice(),
            orderId);

        return added == 1;
    }
    
}
