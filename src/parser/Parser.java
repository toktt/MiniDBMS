package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.FileWriter;

import dataType.*;
import table.*;
import view.*;



public class Parser {
	int parse_times = 0;
	public static String Create_Table(String information) 
	{
		//ArrayList tables = new ArrayList();
		String answer = "CREATE_TABLE";
		StringTokenizer str = new StringTokenizer(information);
		if (!str.hasMoreTokens())
		{
			System.out.println("In Create_Table:NOTHING INPUT");
			return null;
		}
		String firstword = str.nextToken();
		if(firstword.equalsIgnoreCase("CREATE"))
		{
			if (!str.hasMoreTokens())
			{
				System.out.println("In Create_Table:NO CREATE");
				return null;
			}
			String secondword = str.nextToken();
			if(secondword.equalsIgnoreCase("TABLE"))
			{
				if (!str.hasMoreTokens())
				{
					System.out.println("In Create_Table:NO TABLE");
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
							System.out.println("HAVE CREATE TABLE,BUT NO ATTRIBUTE");
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
						            System.out.println("IN VARCHAR IS NOT INTEGER");
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
								System.out.println("PRIMARY KEY:NOT CORRECTLY SPELLED");
								return null;
							
							}
						}
						else if(primary_or_not.equalsIgnoreCase(")"))//最後一項，可以沒有逗點
						{
							if(types.equalsIgnoreCase("int"))
							{
								answer = answer+" "+types;
							}
							else if(types.length() >= 10 && types.substring(0, 8).equalsIgnoreCase("varchar(") && types.substring(types.length()-1,types.length()).equals(")"))
							{
								
								try {
									int y = Integer.parseInt(types.substring(8,types.length()-1));
						        }
						        catch (Exception ex) {
						            System.out.println("IN VARCHAR IS NOT INTEGER");
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
						else if(primary_or_not.equalsIgnoreCase(");"))//最後一項，);的案例
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
						            System.out.println("IN VARCHAR IS NOT INTEGER");
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
							System.out.println("CREATE TABLE,語法錯誤");
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
	
	
	
	
	
	

	
	public static ArrayList Data_Insertion(String information)
	{	
		ArrayList answer = new ArrayList();
		//String answer = "Data_Insert";
		StringTokenizer str = new StringTokenizer(information);
		if (!str.hasMoreTokens())
		{
			System.out.println("Data_Insertion:NOTHING INPUT");
			return null;
		}
		String firstword = str.nextToken();
		if(firstword.equalsIgnoreCase("INSERT"))
		{
			if (!str.hasMoreTokens())
			{
				System.out.println("Data_Insertion:NO INSERT");
				return null;
			}
		}
		if(str.nextToken().equalsIgnoreCase("INTO"))
		{
			if (!str.hasMoreTokens())
			{
				System.out.println("Data_Insertion:NO INTO");
				return null;
			}
			
		}
		String attribure_name = str.nextToken();
		answer.add(attribure_name);
		//answer = answer+" "+attribure_name;
		String tmp_word = str.nextToken();
		if(tmp_word.startsWith("("))  //有第一行敘述列
		{
			answer.add("order_told");
			if(!tmp_word.substring(tmp_word.length()-1, tmp_word.length()).equals(","))
			{
				System.out.println("DATA_INSERTION:NO COMMA BEHIND ATTRIBUTE");
				return null;
			}
			answer.add(tmp_word.substring(1, tmp_word.length()-1));
			//answer = answer+" "+tmp_word.substring(1, tmp_word.length()-1);
			tmp_word = str.nextToken();
			//int tmp = 0;
			while(!tmp_word.substring(tmp_word.length()-1).equals(")"))//還沒結束
			{
				if(tmp_word.equalsIgnoreCase("VALUES"))
				{
					System.out.println("DATA_INSERTION:haven't meet )yet,but already meet VALUES");
					return null;
				}
				if(!tmp_word.substring(tmp_word.length()-1, tmp_word.length()).equals(","))
				{
					System.out.println("DATA_INSERTION:NO COMMA OR ) BEHIND ATTRIBUTE");
					return null;
				}
				
				answer.add(tmp_word.substring(0, tmp_word.length()-1));
				//answer = answer+" "+tmp_word.substring(0, tmp_word.length()-1);
				tmp_word = str.nextToken();
			}
			answer.add(tmp_word.substring(0, tmp_word.length()-1));
			
			//answer = answer+" "+tmp_word.substring(0, tmp_word.length()-1);
			tmp_word = str.nextToken();
		}
		else
		{
			answer.add("origin_order");
		}
		
		if(tmp_word.equalsIgnoreCase("VALUES"))
		{
			
			if (!str.hasMoreTokens())
			{
				System.out.println("Data_Insertion:NO INPUT BEHING VALUES");
				return null;
			}
			String value;//存value
			value = str.nextToken();
			while(!value.substring(value.length()-2, value.length()).equalsIgnoreCase(");") && (!value.substring(value.length()-1, value.length()).equalsIgnoreCase(")")))
			{
				//System.out.println(value.substring(value.length()-1, value.length())+"    yy");
				String temp;
				if (!str.hasMoreTokens())
				{
					System.out.println("Data_Insertion:NO INPUT BEHING VALUES");
					return null;
				}
				while(!value.substring(value.length()-1).equalsIgnoreCase(",") && (!value.substring(value.length()-2,value.length()).equalsIgnoreCase(");") && !value.substring(value.length()-1,value.length()).equalsIgnoreCase(")")))
				{
					
					value = value+" "+str.nextToken();
					System.out.println(value);
				}
				if(value.substring(value.length()-2, value.length()).equalsIgnoreCase(");") || value.substring(value.length()-1, value.length()).equalsIgnoreCase(")"))
				{
					break;
				}
				if(value.substring(0,1).equalsIgnoreCase("("))//第一項	
				{
					if(value.length()>=5 && value.substring(1, 2).equals("'") && value.substring(value.length()-2,value.length()).equals("',"))
					{
						//我是字串
						//answer = answer+" "+value.substring(2,value.length()-2);
						answer.add(value.substring(2,value.length()-2));
						answer.add("String");
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
						//answer = answer+" "+value.substring(1,value.length()-1);
						answer.add(value.substring(1,value.length()-1));
						answer.add("int");
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
					//answer = answer+" "+value.substring(0,value.length()-1);
					answer.add(value.substring(0,value.length()-1));
					answer.add("int");
				}
				else if(value.length()>=4 && value.substring(0,1).equalsIgnoreCase("'") && value.substring(value.length()-2,value.length()).equalsIgnoreCase("',"))//varchar
				{
					//data.add(value.substring(1,value.length()-2));
					//answer = answer+" "+value.substring(1,value.length()-2);
					answer.add(value.substring(1,value.length()-2));
					answer.add("String");
				}
				else
				{
					System.out.println("ERRORx");
				}
				
				value = str.nextToken();
				
			}
			//最後一項
			//也是第一項
			if(value.substring(value.length()-2, value.length()).equalsIgnoreCase(");"))
			{
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
				//answer = answer+" "+value.substring(1,value.length()-2);
				answer.add(value.substring(1,value.length()-2));
				answer.add("int");
				}
				else if(value.length()>=5 && value.substring(1,2).equals("'") && value.substring(value.length()-3,value.length()).equals("');"))//varchar
				{
					//answer = answer+" "+value.substring(2,value.length()-3);
					answer.add(value.substring(2,value.length()-3));
					answer.add("String");
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
			//answer = answer+" "+value.substring(0,value.length()-2);
			answer.add(value.substring(0,value.length()-2));
			answer.add("int");
			}
			//varchar
			else if(value.length()>=4 && value.substring(0,1).equals("'") && value.substring(value.length()-3,value.length()).equals("');"))//varchar
			{
				//answer = answer+" "+value.substring(1,value.length()-3);
				answer.add(value.substring(1,value.length()-3));
				answer.add("String");
			}
			//error
			else
			{
				System.out.println("我是最後一項 and ERROR");
				return null;
			}
			}
			else if(value.substring(value.length()-1, value.length()).equalsIgnoreCase(")"))
			{
				if(value.substring(0,1).equalsIgnoreCase("("))
				{
					//int
					if(!value.substring(1,2).equals("'"))
					{
					try {
						int y = Integer.parseInt(value.substring(1,value.length()-1));
			        }
			        catch (Exception ex) {
			            System.out.println("我不是int");
			            return null;
			        }
					//answer = answer+" "+value.substring(1,value.length()-2);
					answer.add(value.substring(1,value.length()-1));
					answer.add("int");
					}
					else if(value.length()>=4 && value.substring(1,2).equals("'") && value.substring(value.length()-2,value.length()).equals("')"))//varchar
					{
						//answer = answer+" "+value.substring(2,value.length()-3);
						answer.add(value.substring(2,value.length()-2));
						answer.add("String");
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
					int y = Integer.parseInt(value.substring(0,value.length()-1));
		        }
		        catch (Exception ex) {
		            System.out.println("我不是int");
		            return null;
		        }
				//answer = answer+" "+value.substring(0,value.length()-2);
				answer.add(value.substring(0,value.length()-1));
				answer.add("int");
				}
				//varchar
				else if(value.length()>=3 && value.substring(0,1).equals("'") && value.substring(value.length()-2,value.length()).equals("')"))//varchar
				{
					//answer = answer+" "+value.substring(1,value.length()-3);
					answer.add(value.substring(1,value.length()-2));
					answer.add("String");
				}
				//error
				else
				{
					System.out.println("我是最後一項，我有錯");
					return null;
				}
			}
			
		}
		else
		{
			System.out.println("沒有VALUE這個字,error");
		}
		return answer;
		
	}
	
	public static void main(String[] args)
	{
		new View().mainframe();
		
	}
	
	public void test(String information) throws IOException 
	{
		int i = 0;
		ArrayList<String> answer_insert = new ArrayList();
		
		/*FileReader fr = new FileReader("input.txt");
		
		BufferedReader br = new BufferedReader(fr);
		String information = br.readLine();
		while (br.ready()) {

			information = information+"\r\n"+br.readLine();
		}
		fr.close();*/
		
	
		String answer;
		StringTokenizer check = new StringTokenizer(information);
		String check_type;
		String check_type_second_word;
		String tmp = "";
		while(check.hasMoreTokens())
		{
		String order = "";
		check_type = check.nextToken();
		check_type_second_word = check.nextToken();
		order = order+check_type+" "+check_type_second_word;
		while(check.hasMoreTokens())
		{
			tmp = check.nextToken();
			//System.out.println(tmp+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			order = order+" "+tmp;
			if(tmp.substring(tmp.length()-1, tmp.length()).equalsIgnoreCase(";"))break;
		}
		if(	check_type.equals("CREATE") )
		{
			if(	check_type_second_word.equals("TABLE"))
			{
				answer = Create_Table(order);
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
				
				while(parse_answer.hasMoreTokens())
				{
					String name = parse_answer.nextToken();
					String dataType = parse_answer.nextToken();
					//tmp = parse_answer.nextToken();
					String keyType = "default";
					int length = 0;
					if(dataType.length()>=10 && dataType.substring(0,8).equals("varchar(")  &&  dataType.substring(dataType.length()-1, dataType.length()).equals(")"))
					{
								length = Integer.parseInt(dataType.substring(8,dataType.length()-1));
					}
					else
					{
						length = -1;
					}
					if(dataType.length()>=15 && dataType.substring(dataType.length()-11, dataType.length()).equals("PRIMARY_KEY"))
					{
						keyType = "PK";
						
						if(dataType.substring(0,7).equalsIgnoreCase("varchar"))
						{
							dataType = "String";
						}
						else
						{
							dataType = "int";
						}
						
						
					}
					else
					{
						keyType = "default";
						if(dataType.length()>=10 && dataType.substring(0,7).equalsIgnoreCase("varchar"))
						{
							dataType = "String";
						}
					}
					
					//System.out.println(i+ name+ dataType+ keyType+ length);
					table.addAttr(i, name, dataType, keyType, length);
					i++;
					
				}

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
				
				answer_insert = Data_Insertion(order);
				if(answer_insert == null)
				{
					System.out.println("INSERT語法有誤");
				}	
				else
				{
					File file = new File("./"+answer_insert.get(0)+".txt");
					
					if(file.exists())
					{
						String for_insert = "";
						Table table = new Table(answer_insert.get(0));
						table.retreiveAttr();
						table.retreiveTable();
						//System.out.println(table.attr.get(0).getName()+"xxxxxxxxxxxxxxxxxxxx");
						System.out.println("有這個table");
						for(i = 0;i<answer_insert.size();i++)
						{
							System.out.println(answer_insert.get(i));
						}
						if(answer_insert.get(1).equals("order_told"))
						{
							for(i = 0;i<(answer_insert.size()-2)/3;i++)
							{
								//data:  Attr, type, data
								for_insert = for_insert+answer_insert.get(3+i-1)+","+answer_insert.get(3+((answer_insert.size()-2)/3)+(2*i))+","+answer_insert.get(3+((answer_insert.size()-2)/3)+(2*i)-1);
								if(i != (answer_insert.size()-2)/3)for_insert = for_insert+",";
								
								//table.insert(answer_insert.get(0)+","+answer_insert.get(3+i)+","+answer_insert.get(3+2*i));
							}
							table.insert(for_insert);
						}
						else if(answer_insert.get(1).equals("origin_order"))
						{
							for(i = 0;i<(answer_insert.size()-2)/2;i++)
							{
								//data:  Attr, type, data
								for_insert = for_insert+table.attr.get(i).getName()+","+answer_insert.get(4+(2*i)-1)+","+answer_insert.get(3+(2*i)-1);
								if(i != ((answer_insert.size()-2)/2)-1 )for_insert = for_insert+",";
								
							}
							System.out.println(for_insert);
							table.insert(for_insert);
						}
					}
					else
					{
						System.out.println("SQL格式對，可是沒有這個table");
					}
				

				
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
	
	
	
	
	
	
	
}



