package table;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataType.*;

public class Table {
	private String name;
	private Attribute[] attrs;
	private String dbpath;
	private String attrpath;
	private String directory = "/MiniDBMS";
	private HashMap<String,JSONObject> dbMap;
	private File dbfile;
	private File attrfile;
	private JSONObject jobject;
	private JSONArray jarray;
	static private boolean dirty = false;
	
	//use for first time create
	public Table(String name){
		this.name = name;
		Timer timer = new Timer();
		timer.schedule(new AutoSave(), 0, 2*60*1000);
		dbpath = name + ".txt";
		attrpath = name + "_attr.txt";
		dbfile = new File(directory+dbpath);
		attrfile = new File(directory+attrpath);
	}
	
	public void retreiveAll(){
		retreiveAttr();
		retreiveTable();
	}
	
	public boolean newTable(){
		if(dbfile.exists() && attrfile.exists()){
			System.out.println("Table already exist");
			return false;
		}
		else{
			try {
				dbfile.createNewFile();
				attrfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbMap = new HashMap();
			return true;
		}
	}
	
	public void addAttr(int i, String name, String dataType, String keyType, int length){
		//if datatype = int , lenght = -1
		dirty = true;
		attrs[i] = new Attribute(name, dataType, keyType, length);
	}
	
	public void retreiveAttr(){
		try {
			BufferedReader in = new BufferedReader(new FileReader(attrpath));
			int num;
				num = Integer.parseUnsignedInt(in.readLine());
			for(int i=0; i<num; i++){
				String tmp;
				tmp = in.readLine();
				String[] attrtmp = tmp.split(" ");
				attrs[i] = new Attribute(attrtmp[0], attrtmp[1], attrtmp[2], Integer.parseInt(attrtmp[3]));
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void retreiveTable(){
		
	}
	
	public boolean insert(String infor){
		dirty = true;
		String tmp[] = infor.split(",");
		jobject = new JSONObject();
		for(int i=0; i<attrs.length; i++){
			for(int j=0; j<tmp.length; j+=2){
				if(attrs[i].getKeyType().equals("PK") && tmp[j+1] == null){
					System.out.println("PK is null");
					return false;
				}
				if(!attrs[i].getDateType().equals(tmp[j])){
					System.out.println("Wrong data type");
					return false;
				}
				if(attrs[i].getDateType().equals("String") && tmp[j+1].length() > attrs[i].getLength()){
					System.out.println("String Overflow");
					return false;
				}
				if(attrs[i].getDateType().equals("Int") && 
						(Integer.getInteger(tmp[j+1]) > Integer.MAX_VALUE || 
							Integer.getInteger(tmp[j+1]) < Integer.MIN_VALUE) ){
					System.out.println("Int Overflow");
					return false;
				}
				try {
					jobject.put(attrs[i].getName(), tmp[j+1]);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		jarray.put(jobject);
		return true;
	}
	
	public void writeAttr(){
		FileWriter out;
		try {
			out = new FileWriter(attrfile.getAbsolutePath());
			out.write(String.valueOf(attrs.length));
			for(int i=0; i<attrs.length; i++){
				out.write(attrs[i].getName() + " " + attrs[i].getDateType() + " " + attrs[i].getKeyType());
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeTuple(){
		FileWriter out;
		try {
			out = new FileWriter(dbfile.getAbsolutePath());
			for(int i=0; i<jarray.length(); i++)
					out.write(jarray.getString(i).toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void close(){
		if(dirty){
			writeAttr();
			writeTuple();
			dirty = false;
		}
	}
	
	private class AutoSave extends TimerTask{
		public void run(){
			if(dirty){
				writeAttr();
				writeTuple();
				dirty = false;
			}
		}
	}
	
}
