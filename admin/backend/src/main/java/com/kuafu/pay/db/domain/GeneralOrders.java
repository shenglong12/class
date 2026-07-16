package com.kuafu.pay.db.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kuafu.pay.db.LocalDateTimeTypeHandler;
import com.kuafu.pay.enums.PayStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName general_orders
 */
@TableName(value = "general_orders")
@Data
public class GeneralOrders implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String orderNo;


    private String opId;

    /**
     *
     */
    private Integer userId;


    /**
     *
     */
    private String orderType;

    /**
     *
     */

    private PayStatus orderStatus;

    /**
     *
     */
    private PayStatus payStatus;

    /**
     *
     */
    private String deliveryStatus;

    /**
     *
     */
    private Integer productsId;

    /**
     *
     */
    private Integer quality;

    /**
     *
     */
    private BigDecimal totalAmount;

    /**
     *
     */
    private BigDecimal discountAmount;

    /**
     *
     */
    private BigDecimal shippingFee;

    /**
     *
     */
    private BigDecimal actualAmount;

    /**
     *
     */
    private String paymentChannel;

    /**
     *
     */
    private String paymentOrderId;
    /**
     *
     */

    @TableField(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime paymentTime;

    /**
     *
     */
    private BigDecimal paymentAmount;

    /**
     *
     */
    private String shippingAddress;

    /**
     *
     */
    private String shippingCompany;

    /**
     *
     */
    private String trackingNumber;


    /**
     *
     */
    @TableField(typeHandler = LocalDateTimeTypeHandler.class)

    private LocalDateTime createTime;
    @TableField(typeHandler = LocalDateTimeTypeHandler.class)


    private LocalDateTime updateTime;

    /**
     *
     */


    @TableField(typeHandler = LocalDateTimeTypeHandler.class)    /**
     *
     */
    private LocalDateTime cancelTime;
    @TableField(typeHandler = LocalDateTimeTypeHandler.class)

    /**
     *
     */
    private LocalDateTime completeTime;

    /**
     *
     */
    private String refundId;


    private String refundNo;

    /**
     *
     */
    private String refundType;

    /**
     *
     */
    private BigDecimal refundAmount;

    /**
     *
     */
    private String refundReason;

    /**
     *
     */
    private PayStatus refundStatus;

    /**
     *
     */
    private String remark;


    /**
     *
     */
    private Integer isDeleted;

    /**
     * 物流方式
     */
    private String shippingMethod;

    /**
     * 收件人手机号
     */
    private String tel;

    /**
     * 收件人姓名
     */

    private String recipient;

    @TableField(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime deliverTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    private String tableName;
    private String fieldName;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        GeneralOrders other = (GeneralOrders) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
                && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
                && (this.getPayStatus() == null ? other.getPayStatus() == null : this.getPayStatus().equals(other.getPayStatus()))
                && (this.getDeliveryStatus() == null ? other.getDeliveryStatus() == null : this.getDeliveryStatus().equals(other.getDeliveryStatus()))
                && (this.getProductsId() == null ? other.getProductsId() == null : this.getProductsId().equals(other.getProductsId()))
                && (this.getQuality() == null ? other.getQuality() == null : this.getQuality().equals(other.getQuality()))
                && (this.getTotalAmount() == null ? other.getTotalAmount() == null : this.getTotalAmount().equals(other.getTotalAmount()))
                && (this.getDiscountAmount() == null ? other.getDiscountAmount() == null : this.getDiscountAmount().equals(other.getDiscountAmount()))
                && (this.getShippingFee() == null ? other.getShippingFee() == null : this.getShippingFee().equals(other.getShippingFee()))
                && (this.getActualAmount() == null ? other.getActualAmount() == null : this.getActualAmount().equals(other.getActualAmount()))
                && (this.getPaymentChannel() == null ? other.getPaymentChannel() == null : this.getPaymentChannel().equals(other.getPaymentChannel()))
                && (this.getPaymentOrderId() == null ? other.getPaymentOrderId() == null : this.getPaymentOrderId().equals(other.getPaymentOrderId()))
                && (this.getPaymentTime() == null ? other.getPaymentTime() == null : this.getPaymentTime().equals(other.getPaymentTime()))
                && (this.getPaymentAmount() == null ? other.getPaymentAmount() == null : this.getPaymentAmount().equals(other.getPaymentAmount()))
                && (this.getShippingAddress() == null ? other.getShippingAddress() == null : this.getShippingAddress().equals(other.getShippingAddress()))
                && (this.getShippingCompany() == null ? other.getShippingCompany() == null : this.getShippingCompany().equals(other.getShippingCompany()))
                && (this.getTrackingNumber() == null ? other.getTrackingNumber() == null : this.getTrackingNumber().equals(other.getTrackingNumber()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
//                && (this.getUpLocalDateTimeTime() == null ? other.getUpLocalDateTimeTime() == null : this.getUpLocalDateTimeTime().equals(other.getUpLocalDateTimeTime()))
                && (this.getCancelTime() == null ? other.getCancelTime() == null : this.getCancelTime().equals(other.getCancelTime()))
                && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
                && (this.getRefundId() == null ? other.getRefundId() == null : this.getRefundId().equals(other.getRefundId()))
                && (this.getRefundType() == null ? other.getRefundType() == null : this.getRefundType().equals(other.getRefundType()))
                && (this.getRefundAmount() == null ? other.getRefundAmount() == null : this.getRefundAmount().equals(other.getRefundAmount()))
                && (this.getRefundReason() == null ? other.getRefundReason() == null : this.getRefundReason().equals(other.getRefundReason()))
                && (this.getRefundStatus() == null ? other.getRefundStatus() == null : this.getRefundStatus().equals(other.getRefundStatus()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))

                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getPayStatus() == null) ? 0 : getPayStatus().hashCode());
        result = prime * result + ((getDeliveryStatus() == null) ? 0 : getDeliveryStatus().hashCode());
        result = prime * result + ((getProductsId() == null) ? 0 : getProductsId().hashCode());
        result = prime * result + ((getQuality() == null) ? 0 : getQuality().hashCode());
        result = prime * result + ((getTotalAmount() == null) ? 0 : getTotalAmount().hashCode());
        result = prime * result + ((getDiscountAmount() == null) ? 0 : getDiscountAmount().hashCode());
        result = prime * result + ((getShippingFee() == null) ? 0 : getShippingFee().hashCode());
        result = prime * result + ((getActualAmount() == null) ? 0 : getActualAmount().hashCode());
        result = prime * result + ((getPaymentChannel() == null) ? 0 : getPaymentChannel().hashCode());
        result = prime * result + ((getPaymentOrderId() == null) ? 0 : getPaymentOrderId().hashCode());
        result = prime * result + ((getPaymentTime() == null) ? 0 : getPaymentTime().hashCode());
        result = prime * result + ((getPaymentAmount() == null) ? 0 : getPaymentAmount().hashCode());
        result = prime * result + ((getShippingAddress() == null) ? 0 : getShippingAddress().hashCode());
        result = prime * result + ((getShippingCompany() == null) ? 0 : getShippingCompany().hashCode());
        result = prime * result + ((getTrackingNumber() == null) ? 0 : getTrackingNumber().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
//        result = prime * result + ((getUpLocalDateTimeTime() == null) ? 0 : getUpLocalDateTimeTime().hashCode());
        result = prime * result + ((getCancelTime() == null) ? 0 : getCancelTime().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        result = prime * result + ((getRefundId() == null) ? 0 : getRefundId().hashCode());
        result = prime * result + ((getRefundType() == null) ? 0 : getRefundType().hashCode());
        result = prime * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
        result = prime * result + ((getRefundReason() == null) ? 0 : getRefundReason().hashCode());
        result = prime * result + ((getRefundStatus() == null) ? 0 : getRefundStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", userId=").append(userId);
        sb.append(", orderType=").append(orderType);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", deliveryStatus=").append(deliveryStatus);
        sb.append(", productsId=").append(productsId);
        sb.append(", quality=").append(quality);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", discountAmount=").append(discountAmount);
        sb.append(", shippingFee=").append(shippingFee);
        sb.append(", actualAmount=").append(actualAmount);
        sb.append(", paymentChannel=").append(paymentChannel);
        sb.append(", paymentOrderId=").append(paymentOrderId);
        sb.append(", paymentTime=").append(paymentTime);
        sb.append(", paymentAmount=").append(paymentAmount);
        sb.append(", shippingAddress=").append(shippingAddress);
        sb.append(", shippingCompany=").append(shippingCompany);
        sb.append(", trackingNumber=").append(trackingNumber);
        sb.append(", createTime=").append(createTime);
//        sb.append(", upLocalDateTimeTime=").append(upLocalDateTimeTime);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", refundId=").append(refundId);
        sb.append(", refundType=").append(refundType);
        sb.append(", refundAmount=").append(refundAmount);
        sb.append(", refundReason=").append(refundReason);
        sb.append(", refundStatus=").append(refundStatus);
        sb.append(", remark=").append(remark);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}