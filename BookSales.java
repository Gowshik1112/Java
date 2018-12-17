import java.io.*;
import java.util.*;
import java.util.Map.Entry;
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
		
	
	public static void main(String[] args) throws IOException
	{	
		String book_path=new String();
		String sales_path=new String();
		int top_selling_books_count=0;
		int top_customers_count=0;
		String date=new String();
	
		for(int i=0;i<args.length;i++)
		{
			if(args[i].contains("--books"))
			{
				book_path=args[0].substring(args[0].indexOf("--books=")+8,args[0].indexOf("books.csv")+9);
			}
			if(args[i].contains("--sales"))
			{
				sales_path=args[1].substring(args[1].indexOf("--sales=")+8,args[1].indexOf("sales.csv")+9);
			}
			if(args[i].contains("--top_selling_books"))
			{
				top_selling_books_count=Integer.parseInt(args[i].substring(args[i].indexOf("=")+1));
			}
			if(args[i].contains("--top_customers"))
			{
				top_customers_count=Integer.parseInt(args[i].substring(args[i].indexOf("=")+1));
			}
			if(args[i].contains("--sales_on_date"))
			{
				date=args[i].substring(args[i].indexOf("=")+1);
			
			}
		}
		
		BookSales obj=new BookSales();
		
			obj.readBookDetails(book_path);
			obj.readSalesDetails(sales_path);		
			obj.top_Customers=BookSales.sortByValues(obj.top_Customers);
			obj.top_selling_books=BookSales.sortByValues(obj.top_selling_books);
			obj.topSellingBooks(top_selling_books_count);
			obj.topCustomers(top_customers_count);
			obj.salesOnDate(date);
			}
	
	//To fetch book details from book.csv file
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
			LinkedHashMap temp=new LinkedHashMap();
			temp.put("Title",book[1]);
			temp.put("Author",book[2]);
			temp.put("Price",book[3]);
			BookDetails.put(book[0], temp);
			top_selling_books.put(book[0], new Integer(0));
			
			book_price.put(book[0],new Float(book[3]));
			}			
		}catch(IOException e)
		{
			System.out.println("Books file not found");
		}
		finally
		{
			b.close();
		}
	}
	
	
	void readSalesDetails(String sales_path) throws IOException
	{
		BufferedReader b=null;
		String seperator=",";
		try
		{
			String sales[]=new String[10];
			String seperator2=";";
			b=new BufferedReader(new FileReader(sales_path));
			String line;
			String BookCount[]=new String[2];
			while((line=b.readLine())!=null)
			{
			sales=line.split(seperator);
		
			for(int i=0;i<Integer.parseInt(sales[3]);i++)
			{
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
			//System.out.println(book_price.get(BookCount[0]));
			}
			else
			{
				top_Customers.put(sales[1],new Float(top_Customers.get(sales[1])+Float.valueOf(BookCount[1])*Float.valueOf(book_price.get(BookCount[0]))));
			}
			if(sales_on_date.get(sales[0])==null)
			{
				sales_on_date.put(sales[0],new Float(Float.valueOf(BookCount[1])*Float.valueOf(book_price.get(BookCount[0]))));
			//System.out.println(book_price.get(BookCount[0]));
				//System.out.println(Float.valueOf(BookCount[1]));
				//*Float.valueOf(book_price.get(BookCount[0])
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
		}
		finally
		{
			b.close();
		}			
	}
	
	//To get the specific Book details using Book_Id reference
	 void getBookDetails(LinkedHashMap <String, LinkedHashMap<String,String>>m)
	{
		
		Iterator<Entry<String, LinkedHashMap<String, String>>> itr=m.entrySet().iterator();
		
		while(itr.hasNext())
		{
			Map.Entry map=(Map.Entry) itr.next();			
			LinkedHashMap <String,String> temp=new <String,String>LinkedHashMap();
			temp=(LinkedHashMap<String, String>) map.getValue();
			System.out.println("Book_Id"+"\t"+map.getKey());
			for(Map.Entry<String,String> entry: temp.entrySet())
			{	
				System.out.println(entry.getKey()+"\t"+entry.getValue());
			}			
		}
	}
	
	
	//To print the top_selling_books
	 void topSellingBooks(int a)
	{System.out.print("top_selling_books"+"\t");
	Iterator itr=top_selling_books.entrySet().iterator();
	
	try
	{for(int i=0;i<a;i++)
	{
		Map.Entry m=(Map.Entry) itr.next();
		System.out.print(m.getKey()+"\t");
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
		System.out.print("top_customers"+"\t");
	Iterator itr=top_Customers.entrySet().iterator();
	
	try
	{for(int i=0;i<a;i++)
	{
		Map.Entry m=(Map.Entry) itr.next();
		System.out.print(m.getKey()+"\t");
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
		/*Iterator itr=sales_on_date.entrySet().iterator();
		while(itr.hasNext())
		{
			Map.Entry m=(Map.Entry) itr.next();
			System.out.println(m.getKey()+"\t");
	System.out.println(m.getValue()+"\t");
		}*/
	try
	{
		
	
		if(sales_on_date.get(a)==null)
		{
			System.out.println(a+"\t"+"0");
		}
		else
		{
		System.out.println(a+"\t"+sales_on_date.get(a));
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
