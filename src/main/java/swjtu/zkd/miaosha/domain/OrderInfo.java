package swjtu.zkd.miaosha.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfo {

	private Long id;
	private Long userId;
	private Long goodsId;
	private Long  deliveryAddrId;
	private String goodsName;
	private Integer goodsCount;
	private Double goodsPrice;
	private Integer orderChannel;
	private Integer status;
	private Date createDate;
	private Date payDate;

	//订单状态：0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
	public enum OrderStatus {

		NOT_PAY(0), PAY(1), SEND(2), RECEIVE(3), REFUND(4), COMPLETE(5);

		OrderStatus(int status) {
			this.status = status;
		}

		private int status;

		public int getStatus() {
			return this.status;
		}
	}
}
