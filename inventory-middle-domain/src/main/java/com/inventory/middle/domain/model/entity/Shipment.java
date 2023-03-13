package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.ShipmentId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交运单领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class Shipment implements Entity<Shipment> {

		private ShipmentId id;
		    		
	/**
	 * 交运单号
	 */
	private String shipmentNo;
	    		
	/**
	 * 交运单类型
	 */
	private String shipmentType;
	    		
	/**
	 * 交运单状态
	 */
	private String shipmentStatus;
	    		
	/**
	 * wms指令类型
	 */
	private String instructionType;
	    		
	/**
	 * 拣货日期
	 */
	private LocalDateTime pickDate;
	    		
	/**
	 * 拣配状态
	 */
	private String pickStatus;
	    		
	/**
	 * 运输线路
	 */
	private String transportRoute;
	    		
	/**
	 * 运输单据号
	 */
	private String transportNo;
	    		
	/**
	 * 运输主体
	 */
	private String transportCarrier;
	    		
	/**
	 * 装运点
	 */
	private String shipmentPoint;
	    		
	/**
	 * 运输方式
	 */
	private String transportMode;
	    		
	/**
	 * 发货方名称
	 */
	private String deliveryName;
	    		
	/**
	 * 发货方手机
	 */
	private String deliveryPhone;
	    		
	/**
	 * 发货地址（省）
	 */
	private String deliveryProvince;
	    		
	/**
	 * 发货地址（市）
	 */
	private String deliveryCity;
	    		
	/**
	 * 发货地址（区）
	 */
	private String deliveryCounty;
	    		
	/**
	 * 发货地址（详细地址）
	 */
	private String deliveryAddressLine;
	    		
	/**
	 * 收货放名称
	 */
	private String receivingName;
	    		
	/**
	 * 收货方手机
	 */
	private String receivingPhone;
	    		
	/**
	 * 收货地址（省）
	 */
	private String receivingProvince;
	    		
	/**
	 * 收货地址（市）
	 */
	private String receivingCity;
	    		
	/**
	 * 收货地址（区）
	 */
	private String receivingCounty;
	    		
	/**
	 * 收货地址（详细地址）
	 */
	private String receivingAddressLine;
	    		
	/**
	 * 计划发货日期
	 */
	private LocalDateTime plannedDeliveryDate;
	    		
	/**
	 * 计划收货日期
	 */
	private LocalDateTime plannedReceivingDate;
	    		
	/**
	 * 实际发货日期
	 */
	private LocalDateTime actualDeliveryDate;
	    		
	/**
	 * 实际收货日期
	 */
	private LocalDateTime actualReceivingDate;
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 创建人id
	 */
	private String creatorId;
	    		
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	    		
	/**
	 * 修改人id
	 */
	private String updatorId;
	    		
	/**
	 * 删除标识
	 */
	private Integer deleted;
	    		
	/**
	 * 租户id
	 */
	private String tenantId;
	    
    @Override
    public boolean sameIdentityAs(Shipment other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
