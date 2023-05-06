package com.gdyzy.rind.dto;

import com.gdyzy.rind.entity.OrderDetail;
import com.gdyzy.rind.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
