package rpc.topic.message;

public class Message {
	private String name;
	private String content;
	private String routingKey;
	private String address;
	
	public Message() {
	}
	
	public Message(String name, String content, String routingKey, String address) {
		this.name = name;
		this.content = content;
		this.routingKey = routingKey;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}
	
	public String getRoutingKey() {
		return routingKey;
	}

	public String getAddress() {
		return address;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	

}
