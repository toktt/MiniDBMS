package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.FileWriter;
import dataType.*;
import table.*;

public class Parser {
	
	public static String Create_Table(String information) 
	{
		//ArrayList tables = new ArrayList();
		String answer = "CREATE_TABLE";
		StringTokenizer str = new StringTokenizer(information);
		if (!str.hasMoreTokens())
		{
			System.out.println("NOTHING INPUT0");
			return null;
		}
		String firstword = str.nextToken();
		if(firstword.equalsIgnoreCase("CREATE"))
		{
			if (!str.hasMoreTokens())
			{
				System.out.println("NOTHING INPUT1");
				return null;
			}
			String secondword = str.nextToken();
			if(secondword.equalsIgnoreCase("TABLE"))
			{
				if (!str.hasMoreTokens())
				{
					System.out.println("NOTHING INPUT2");
					return null;
				}
				//tables.add(str.nextToken());//TABLE的名字
				answer = answer+" "+str.nextToken();
				if(str.nextToken().equalsIgnoreCase("("))  //開始存取TABLE裡的東西
				{
					String attribute_name;
					attribute_name = str.nextToken();
					int number_of_attribute = 0;
					while(!attribute_name.equalsIgnoreCase(")")) //定計數器，五次以上GG
					{
						number_of_attribute++;
						if (!str.hasMoreTokens())
						{
							System.out.println("ERROR0");
							return null;
						}
						//tables.add(attribute_name);//ATTRIBUTE類型?
						answer = answer+" "+attribute_name;
						System.out.println(attribute_name);
						String types = str.nextToken();
						if(types.indexOf(",") == types.length()-1) //沒有primary key,這個attribute結束
						{
							if(types.equals("int,"))
							{
								answer = answer+" "+types.substring(0,types.length()-1);
							}
							else if(types.length() >= 11 && types.substring(0, 8).equalsIgnoreCase("varchar(") && types.substring(types.length()-2,types.length()).equals("),"))
							{
								try {
									int y = Integer.parseInt(types.substring(8,types.length()-2));
						        }
						        catch (Exception ex) {
						            System.out.println("something wrong");
						            return null;
						        }
								answer = answer+" "+types.substring(0,types.length()-1);
								
						        
							}
							else
							{
								System.out.println("wrong datatype");
								return null;
							}
							//tables.add(types);//型別?	
							//answer = answer+" "+types.substring(0,types.length()-1);
							//檢查type
						}
						else{ 									 //有primary key,或是語法錯誤
						String primary_or_not = str.nextToken();
						if(primary_or_not.equalsIgnoreCase("PRIMARY"))
						{
							if(str.nextToken().equalsIgnoreCase("KEY,"))
							{
								//把這筆資料訂為PRIMARY KEY
								
								
								
								
								
								answer = answer+" "+types;
								answer = answer+"_PRIMARY_KEY";
								//
								System.out.println("I AM PRIMARY KEY,CORRECT");
							}
							else 
							{
								System.out.println("PRIMARY BUT NOT KEY,or no comma,ERROR");
								return null;
							
							}
						}
						else if(primary_or_not.equalsIgnoreCase(")"))//最後一項，可以沒有逗點
						{
							if(types.equals("int"))
							{
								answer = answer+" "+types;
							}
							else if(types.length() >= 10 && types.substring(0, 8).equalsIgnoreCase("varchar(") && types.substring(types.length()-1,types.length()).equals(")"))
							{
								
								try {
									int y = Integer.parseInt(types.substring(8,types.length()-1));
						        }
						        catch (Exception ex) {
						            System.out.println("something wrong");
						            return null;
						        }
								answer = answer+" "+types;
								
						        
							}
							else
							{
								System.out.println("wrong datatype");
								return null;
							}
							System.out.println("正確，最後一項，且沒有逗點");
							return answer;
						}
						else
						{
							System.out.println("語法錯誤");
							return null;
						}
					
					}
					//決定是否成功CREATE_TABLE
						
						attribute_name = str.nextToken();
						System.out.println(number_of_attribute);
						
						
					}
					if(number_of_attribute == 0)
					{
						System.out.println("沒有attribute");
						return null;
					}
				}
				else 
					{
					System.out.println("沒有左括弧");
					return null;
					}
			
			}
			
			
			
			
		}
		return answer;
		
	}
	
	
	
	
	
	

	
	public static String Data_Insertion(String information)
	{	
		String answer = "Data_Insert";
		StringTokenizer str = new StringTokenizer(information);
		if (!str.hasMoreTokens())
		{
			System.out.println("NOTHING INPUT0");
			return null;
		}
		String firstword = str.nextToken();
		if(firstword.equalsIgnoreCase("INSERT"))
		{
			if (!str.hasMoreTokens())
			{
				System.out.println("NOTHING INPUT1");
				return null;
			}
		}
		if(str.nextToken().equalsIgnoreCase("INTO"))
		{
			if (!str.hasMoreTokens())
			{
				System.out.println("NOTHING INPUT1");
				return null;
			}
			
		}
		String attribure_name = str.nextToken();
		//data.add(attribure_name);
		answer = answer+" "+attribure_name;
		String tmp_word = str.nextToken();
		if(tmp_word.startsWith("("))  //有第一行敘述列
		{
			//data.add(tmp_word.substring(1, tmp_word.length()-1));
			answer = answer+" "+tmp_word.substring(1, tmp_word.length()-1);
			tmp_word = str.nextToken();
			//int tmp = 0;
			while(!tmp_word.substring(tmp_word.length()-1).equals(")"))//還沒結束
			{
				//data.add(tmp_word.substring(0, tmp_word.length()-1));
				answer = answer+" "+tmp_word.substring(0, tmp_word.length()-1);
				tmp_word = str.nextToken();
			}
			answer = answer+" "+tmp_word.substring(0, tmp_word.length()-1);
			tmp_word = str.nextToken();
		}
		
		if(tmp_word.equalsIgnoreCase("VALUES"))
		{
			if (!str.hasMoreTokens())
			{
				System.out.println("NOTHING INPUT1");
				return null;
			}
			String value;//存value
			value = str.nextToken();
			while(!value.substring(value.length()-2, value.length()).equalsIgnoreCase(");"))
			{
				System.out.println(value);
				String temp;
				if (!str.hasMoreTokens())
				{
					System.out.println("NOTHING INPUT");
					return null;
				}
				while(!value.substring(value.length()-1).equalsIgnoreCase(","))
				{
					if (!str.hasMoreTokens())
					{
						System.out.println("NOTHING INPUT");
						return null;
					}
					value = value+" "+str.nextToken();
					System.out.println(value);
				}
				if(value.substring(value.length()-2, value.length()).equalsIgnoreCase(");"))
				{
					break;
				}
				if(value.substring(0,1).equalsIgnoreCase("("))//第一項	
				{
					if(value.length()>=5 && value.substring(1, 2).equals("'") && value.substring(value.length()-2,value.length()).equals("',"))
					{
						//我是字串
						answer = answer+" "+value.substring(2,value.length()-2);
					}
					else
					{
						//int 或是  error
						try {
							int y = Integer.parseInt(value.substring(1,value.length()-1));
				        }
				        catch (Exception ex) {
				            System.out.println("我不是int也不是varchar");
				            return null;
				        }
						answer = answer+" "+value.substring(1,value.length()-1);
					}
					//data.add(value.substring(1,value.length()-1));
					
				}
				else if(!value.substring(0,1).equalsIgnoreCase("'"))//int
				{
					try {
						int y = Integer.parseInt(value.substring(1,value.length()-1));
			        }
			        catch (Exception ex) {
			            System.out.println("我不是int");
			            return null;
			        }
					//data.add(value.substring(0,value.length()-1));
					answer = answer+" "+value.substring(0,value.length()-1);
				}
				else if(value.length()>=4 && value.substring(0,1).equalsIgnoreCase("'") && value.substring(value.length()-2,value.length()).equalsIgnoreCase("',"))//varchar
				{
					//data.add(value.substring(1,value.length()-2));
					answer = answer+" "+value.substring(1,value.length()-2);
				}
				else
				{
					System.out.println("ERROR");
				}
				
				value = str.nextToken();
				
			}
			//最後一項
			//也是第一項
			if(value.substring(0,1).equalsIgnoreCase("("))
			{
				//int
				if(!value.substring(1,2).equals("'"))
				{
				try {
					int y = Integer.parseInt(value.substring(1,value.length()-2));
		        }
		        catch (Exception ex) {
		            System.out.println("我不是int");
		            return null;
		        }
				answer = answer+" "+value.substring(1,value.length()-2);
				}
				else if(value.length()>=6 && value.substring(1,2).equals("'") && value.substring(value.length()-3,value.length()).equals("');"))//varchar
				{
					answer = answer+" "+value.substring(2,value.length()-3);
				}
				else
				{
					System.out.println("我是最後一項，我啥都不是");
				}
				
			}
			//int
			else if(!value.substring(0,1).equals("'"))
			{
			try {
				int y = Integer.parseInt(value.substring(0,value.length()-2));
	        }
	        catch (Exception ex) {
	            System.out.println("我不是int");
	            return null;
	        }
			answer = answer+" "+value.substring(0,value.length()-2);
			}
			//varchar
			else if(value.length()>=5 && value.substring(0,1).equals("'") && value.substring(value.length()-3,value.length()).equals("');"))//varchar
			{
				answer = answer+" "+value.substring(1,value.length()-3);
			}
			//error
			else
			{
				System.out.println("我是最後一項，我有錯");
				return null;
			}
			
		}
		else
		{
			System.out.println("沒有VALUE這個字,error");
		}
		return answer;
		
	}
	
	public static String Handle_Table(String answer)
	{
		String correct_or_not = "wrong";//預設錯誤
		
		
		
		return correct_or_not;
	}
	
	public static void main(String[] args) throws IOException 
	{
		
		FileReader fr = new FileReader("input.txt");
		//FileWriter fw = new FileWriter("output.txt");
		BufferedReader br = new BufferedReader(fr);
		String information = br.readLine();
		while (br.ready()) {
			//System.out.println("x");
			information = information+"\r\n"+br.readLine();
		}
		//System.out.println(information);
		fr.close();
		String answer;
		StringTokenizer check = new StringTokenizer(information);
		String check_type;
		String check_type_second_word;
		check_type = check.nextToken();
		check_type_second_word = check.nextToken();
		if(	check_type.equals("CREATE") )
		{
			if(	check_type_second_word.equals("TABLE"))
			{
				answer = Create_Table(information);
				if(answer == null)
				{
					System.out.println("CREATE語法有誤");
				}
				else
				{
				System.out.println(answer);
				//call table
				StringTokenizer parse_answer = new StringTokenizer(answer);
				parse_answer.nextToken();
				
				
				Table table = new Table(parse_answer.nextToken());
				table.newTable();
				int i = 0;
				while(parse_answer.hasMoreTokens())
				{
					String name = parse_answer.nextToken();
					String dataType = parse_answer.nextToken();
					//tmp = parse_answer.nextToken();
					String keyType = "default";
					int length = 0;
					if(dataType.length()>=15 && dataType.substring(dataType.length()-11, dataType.length()).equals("PRIMARY_KEY"))
					{
						keyType = dataType.substring(0, dataType.length()-12);
						dataType = dataType.substring(0, dataType.length()-12);
						
					}
					else
					{
						keyType = "default";
					}
					if(dataType.length()>=10 && dataType.substring(0,8).equals("varchar(")  &&  dataType.substring(dataType.length()-1, dataType.length()).equals(""))
					{
								length = Integer.parseInt(dataType.substring(8,dataType.length()-1));
					}
					else
					{
						length = -1;
					}
					//System.out.println(i+ name+ dataType+ keyType+ length);
					table.addAttr(i, name, dataType, keyType, length);
					i++;
					
				}

				//fw.write(answer);
		        //fw.flush();
		        //fw.close();
				}
			}
			else 
			{
				System.out.println("not CREATE TABLE");
			}
		}
		else if(check_type.equals("INSERT"))
		{
			if(	check_type_second_word.equals("INTO"))
			{
				answer = Data_Insertion(information);
				if(answer == null)
				{
					System.out.println("INSERT語法有誤");
				}
				else
				{
				System.out.println(answer);
				//fw.write(answer);
		        //fw.flush();
		        //fw.close();
				}
			}
			else
			{
				System.out.println("not CREATE TABLE");
			}
		}
		else
		{
			System.out.println("both not,SQL ERROR");
		}
		
		
	}
	
	
	
	
	
	
	
}



