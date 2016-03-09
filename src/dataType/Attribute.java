package dataType;

public class Attribute {
	private String name;
	private String dataType;
	private String keyType;
	private int length;
	
	public Attribute(String name, String dataType, String keyType, int length){
		this.name = name;
		this.dataType = dataType;
		this.keyType = keyType;
		this.length = length;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDateType(){
		return dataType;
	}
	
	public String getKeyType(){
		return keyType;
	}
	
	public int getLength(){
		return length;
	}
}
