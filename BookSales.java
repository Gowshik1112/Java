import java.io.*;
import java.util.*;

public class BookSales {
	
	
	//To store top_customers details
	 Map<String, Float> top_Customers=new TreeMap<String,Float>();
	//To store top_selling_books in Descending order by values
	 Map <String,Integer> top_selling_books=new TreeMap <String, Integer>();
	//To store Book price
	 Map <String, Float> book_price=new TreeMap<String, Float>();
	//To store sales amount on each date
	 Map<String, Float>sales_on_date=new TreeMap<String, Float>();	
	//To store all the details of books
	 LinkedHashMap<String, LinkedHashMap<String,String>> BookDetails=new LinkedHashMap<String,LinkedHashMap<String,String>>();
	 String book_id[];
	
	public static void main(String[] args) throws IOException
	{	
		String book_path=new String();
		String sales_path=new String();
		int top_selling_books_count=0;
		int top_customers_count=0;
		String date=new String();
		BookSales obj=new BookSales();
		for(int i=0;i<args.length;i++)
		{
			if(args[i].contains("--books"))
			{
				book_path=args[0].substring(args[0].indexOf("--books=")+8);
				obj.readBookDetails(book_path);
			}
			if(args[i].contains("--sales"))
			{
				sales_path=args[1].substring(args[1].indexOf("--sales=")+8);
				obj.readSalesDetails(sales_path);
			}
			if(args[i].contains("--top_selling_books"))
			{
				top_selling_books_count=Integer.parseInt(args[i].substring(args[i].indexOf("=")+1));
				obj.top_selling_books=BookSales.sortByValues(obj.top_selling_books);
				obj.topSellingBooks(top_selling_books_count);
			}
			if(args[i].contains("--top_customers"))
			{
				top_customers_count=Integer.parseInt(args[i].substring(args[i].indexOf("=")+1));
				obj.top_Customers=BookSales.sortByValues(obj.top_Customers);
				obj.topCustomers(top_customers_count);
			}
			if(args[i].contains("--sales_on_date"))
			{
				date=args[i].substring(args[i].indexOf("=")+1);
				obj.salesOnDate(date);
			}if(args[i].contains("--book_details"))
			{
				obj.book_id=args[i].substring(args[i].indexOf("=")+1).split(",");
				for(int j=0;j<obj.book_id.length;j++)
				{
					obj.getBookDetails(obj.book_id[j]);
				}
			}
			}		
			}
	
	
	@SuppressWarnings("unchecked")
	//To fetch book details from book.list file
	void readBookDetails(String book_path) throws IOException
	{
		BufferedReader b=null;
		String book[]=new String[4];
		String seperator=",";
	
		try
		{
			b=new BufferedReader(new FileReader(book_path));
			String line;
			while((line=b.readLine())!=null)
			{
				
			book=line.split(seperator);
			@SuppressWarnings({ "rawtypes", "unused" })
			LinkedHashMap <String,String> temp=new <String,String> LinkedHashMap();
			temp.put("Title",book[1]);
			temp.put("Author",book[2]);
			temp.put("Price",book[3]);
			BookDetails.put(book[0], temp);
			top_selling_books.put(book[0], new Integer(0));
			
			book_price.put(book[0],new Float(book[3]));
			}			
			book_id=new String[book_price.size()];
		}catch(IOException e)
		{
			System.out.println("Books file not found");
			System.exit(0);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Details are missing for the book : "+book[0]+". Update it! ");
			System.exit(0);
		}
		finally
		{
			b.close();
		}
	}
	
	//To fetch sales details from sales.list file
	void readSalesDetails(String sales_path) throws IOException
	{
		BufferedReader b=null;
		String seperator=",";
		String sales[]=new String[500];
		String BookCount[]=new String[2];
		try
		{
			
			String seperator2=";";
			b=new BufferedReader(new FileReader(sales_path));
			String line;
			
			while((line=b.readLine())!=null)
			{
				
			sales=line.split(seperator);		
			for(int i=0;i<Integer.parseInt(sales[3]);i++)
			{
				//System.out.println(i);
			BookCount=sales[4+i].split(seperator2);
			if(top_selling_books.get(BookCount[0])==null)
			{
				top_selling_books.put(BookCount[0],new Integer(BookCount[1]));
			}	
			else
			{
				
				top_selling_books.put(BookCount[0],new Integer(Integer.valueOf(top_selling_books.get(BookCount[0]))+Integer.valueOf(BookCount[1])));
			}
			if(top_Customers.get(sales[1])==null)
			{
				top_Customers.put(sales[1],new Float(Float.valueOf(BookCount[1])*Float.valueOf(book_price.get(BookCount[0]))));
			
			}
			else
			{
				top_Customers.put(sales[1],new Float(top_Customers.get(sales[1])+Float.valueOf(BookCount[1])*Float.valueOf(book_price.get(BookCount[0]))));
			}
			if(sales_on_date.get(sales[0])==null)
			{
				sales_on_date.put(sales[0],new Float(Float.valueOf(BookCount[1])*Float.valueOf(book_price.get(BookCount[0]))));
			
			}
			else
			{
				sales_on_date.put(sales[0],new Float(sales_on_date.get(sales[0])+Float.valueOf(BookCount[1])*Float.valueOf(book_price.get(BookCount[0]))));
			}				
			}				
			}				
			
		}catch(IOException e)
		{
			System.out.println("Sales file not found");
			System.exit(0);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Invalid Sales History at "+sales[0]+" purchased by "+sales[1]);
			System.exit(0);
		}
		finally
		{
			b.close();
		}			
	}
	
	/**added additional functionality
	To get the specific Book details using Book_Id reference**/
	 void getBookDetails(String book_id)
	{
		 try {
		 System.out.println();
		 @SuppressWarnings({ "unused", "unchecked", "rawtypes" })
		LinkedHashMap <String,String> temp=new <String,String>LinkedHashMap();
			temp=BookDetails.get(book_id);
			@SuppressWarnings("rawtypes")
			Iterator itr=temp.entrySet().iterator();
		 System.out.println("Book_ID\t"+book_id);		
		while(itr.hasNext())
		{
			@SuppressWarnings("rawtypes")
			Map.Entry map=(Map.Entry) itr.next();	
			System.out.println(map.getKey()+"\t"+map.getValue());						
		}
		 }catch(NullPointerException e)
		 {
			 System.out.println("Book_ID "+book_id+" is an invalid ID");
			
		 }
	}
	
	
	//To print the top_selling_books
	 void topSellingBooks(int a)
	{System.out.print("top_selling_books\t");
	@SuppressWarnings("rawtypes")
	Iterator itr=top_selling_books.entrySet().iterator();
	
	try	
	{
		String temp=new String();
		for(int i=0;i<a;i++)
	{
		@SuppressWarnings("rawtypes")
		Map.Entry m=(Map.Entry) itr.next();
		System.out.print(m.getKey()+"\t");
		temp=m.getValue().toString();
	}
		
		while(itr.hasNext())
		{
			
		@SuppressWarnings("rawtypes")
		Map.Entry m=(Map.Entry) itr.next();
		if(temp.equals(m.getValue().toString()))
		{
			System.out.print(m.getKey()+"\t");
		}
		else
		{
			break;
		}
		}
	System.out.println();
	}catch(NoSuchElementException e)
	{
	System.out.println();	
	}
	
	}
	
	//To print the top_customers details
	 void topCustomers(int a)
	{
		 String temp=new String();
		System.out.print("top_customers"+"\t");
	@SuppressWarnings("rawtypes")
	Iterator itr=top_Customers.entrySet().iterator();
	
	try
	{for(int i=0;i<a;i++)
	{
		@SuppressWarnings("rawtypes")
		Map.Entry m=(Map.Entry) itr.next();
		System.out.print(m.getKey()+"\t");
		temp=m.getValue().toString();
	}
	while(itr.hasNext())
	{
		
	@SuppressWarnings("rawtypes")
	Map.Entry m=(Map.Entry) itr.next();
	if(temp.equals(m.getValue().toString()))
	{
		System.out.print(m.getKey()+"\t");
	}
	else
	{
		break;
	}
	}
	System.out.println();
	}catch(NoSuchElementException e)
	{
		System.out.println();
	}
	}
	
	
	//To print the sales amount on the given date
	void salesOnDate(String a)
	{
		System.out.print("sales_on_date"+"\t");		
		
	try
	{	
	
		if(sales_on_date.get(a)==null)
		{
			System.out.println(a+"\t"+"0");
		}
		else
		{
		System.out.println(a+"\t"+Math.round((float)sales_on_date.get(a)*100.0)/100.0);
		}
	
	}catch(NoSuchElementException e)
	{
		System.out.println();
	}
	}
	
	//Customized Comparable method to order TreeMap based on Values in descending order 
	public static <K,V extends Comparable<V>> Map<K,V> sortByValues(final Map<K,V> map)
	{
		Comparator<K> comparator = new Comparator<K>() {

			
			public int compare(K arg0, K arg1) {
				int compare=map.get(arg1).compareTo(map.get(arg0));
				if(compare==0)
				{
					return 1;
				}
				else
				{
					return compare;
				}
			}
			
		};
		Map<K,V> sortedMap =new TreeMap<K,V>(comparator);
		sortedMap.putAll(map);
		return sortedMap;
	}
}
