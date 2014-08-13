package rpc.topic.message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.Gson;

public class JsonToJava {
	MsgDirectory msgDirectory;
	
	public JsonToJava() {
		msgDirectory = new MsgDirectory();
	}

	public MsgDirectory getMsgDirectory() {
		return msgDirectory;
	}

	public MsgDirectory jsonToJava(){   
		 try {   
			 
			 // obtained a file object from json file  
			 File file = new File("json/message.json");  
			 
			 // get json as buffer  
			 BufferedReader br = new BufferedReader(new FileReader(file));  
			 
		     // obtained Gson object  
		     Gson gson = new Gson();  
		  
		     // called fromJson() method and passed incoming buffer from json file  
		     // passed student class reference to convert converted result as Student object  
		     Message message = gson.fromJson(br, Message.class); 
		     msgDirectory.addMesg(message);
		     
		     // printed student data on console  
		     System.out.println("JSON message: " + "Name: " + message.getName()+" Content:" + message.getContent()  
		     + " RoutingKey: " + message.getRoutingKey() + " Address: " + message.getAddress()); 
		     
		     
 
		 } catch (FileNotFoundException e) { 
			 System.err.println("can't find the file");
		   e.printStackTrace();  
		 } 
		 return msgDirectory;
	 }
	 
	 public static void main(String[] args) {
		 JsonToJava json = new JsonToJava();
		 json.jsonToJava();
		 for (Message msg: json.getMsgDirectory().getMsgDirectory()) {
			 System.out.println(msg.getContent());
		 }
	 }
}
