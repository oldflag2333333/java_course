package com.example.course13.dao;

import com.example.course13.anno.Load;
import com.example.course13.anno.SQL;
import com.example.course13.db.DBConstants;
import org.springframework.stereotype.Component;

/**
 * @author oldFlag
 * @since 2022/4/25
 */
@Component
public class OrderMapper {

    @Load(DBConstants.SLAVE)
    @SQL(OrderDO.class)
    public Object getOrderFromSlave() {
        return "select * from `order` limit 1";
    }

    @Load(DBConstants.MASTER)
    @SQL(OrderDO.class)
    public Object getOrderFromMaster() {
        return "select * from `order` limit 1";
    }

    @SQL(OrderDO.class)
    public Object getOrder() {
        return "select * from `order` limit 1";
    }

}
