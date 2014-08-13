package rpc.topic.message;

import com.google.gson.Gson;

public class JavaToJson {
	 public void convertJavaToJson(){  
		    
		  // simple object from student domain class  
		  Message message = new Message();  
		    
		  message.setName("toquan");
		  message.setContent("help me");
		  message.setRoutingKey("q.middle");
		  message.setAddress("192.168.1.1");

		    
		  // obtained Gson object   
		  Gson gson = new Gson();  
		    
		  // called toJson() method and passed student object as parameter  
		  // print generated json to console  
		  System.out.println(gson.toJson(message));  
		    
		  }  
	 
	 public static void main(String[] args) {
		 JavaToJson java = new JavaToJson();
		 java.convertJavaToJson();
	 }
}
