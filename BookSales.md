# BookSales
    It is a console based Java Application which is used to find the book sales transaction summary using the common book resource file.

### Source file 
| Type of File| File Name  |
| ------ | ------ |
| Main class file | BookSales.java  |
| Books Details |  books.list |    
| Sales Details | sales.list |  

###  Input Details
Input| Required/Optional | Example| 
 |------ | ------ | -------|
|- -books=<books.list file path> | Required | - -books=C:/Users/xxx/Downloads.books.list
|- -sales=<sales.list file path> | Required | - -sales=C:/Users/xxx/Downloads.sales.list
|- -top_selling_books=<count>| Optional | - -top_selling_books=3
|- -top_customers=<count>| Optional | - -top_customers=4
|- -sales_on_date=<count>| Optional | - -sales_on_date=2018-04-23
|- -book_details=<book_id 1>,<book_id 2>...<book_id N>| Optional | - -book_details=1A23,3FS3,0FSD

### Output Details
#### Input 1:
    Input:  --top_selling_books=3
    Output: 1A23    3FS3    0FSD
    Description: It will show the top selling books based on number of quantity sold
    Note: If required count is 1, but top two selling books have same value, then it will print both.
#### Input 2:

    Input: --top_customers=2
    Output: xxxx@doe.ocom   yyyy@doe.com
    Description: It will show the top customers based on price amount which they have purchased
    Note: If required count is 1, but top two customers purchased in same value, then it will print both.
#### Input 3:

    Input: --sales_on_date=2018-02-17
    Output: 93.23
    Description: It will show the total sales price on the given date
#### Input 4:

    Input: --books_details=1A23,3FS3
    Output: Book_ID	1A23
            Title	A Brief history of time
            Author	Stephen Hawking
            Price	10
            
            Book_ID	3FS3
            Title	Hitchhiker's guide to the galaxy
            Author	Douglas Adams
            Price	20

    Description: It will show the total sales price on the given date
    Note: If book_id is not available in the book directory, it will show invalid book_id.

### Example:
#### Input
    BookSales --books=C:/Users/xxx/Downloads/books.list --sales=C:/Users/xxx/Downloads/sales.list --top_customers=2 --top_selling_books=3 --sales_on_date=2018-08-01 --book_details=1A,1c,1B

#### Output
    top_customers	aapshv@reads.com	cgeganpr@ehow.com	leoo@thetimes.co.uk	
    top_selling_books	1A	1308045A	AZ	
    sales_on_date	2018-08-01	1532.0

    Book_ID	1A
    Title	A Brief history of time
    Author	Stephen Hawking
    Price	10

    Book_ID 1c is an invalid ID

    Book_ID	1B
    Title	Hitchhiker's guide to the galaxy
    Author	Douglas Adams
    Price	20

### Steps to Execute 
* Navigate to the path where BookSales.java file is present and compile it  using **_javac_** command
> **javac BookSales.java** 
* Run the .class file including command line arguments as input using **_java_** command
> **java BookSales --books=C:/Users/xxx/Downloads/books.list --sales=C:/Users/xxx/Downloads/sales.list --top_customers=2 --top_selling_books=3 --sales_on_date=2018-08-01 --book_details=1A,1c,1B**
---------------------------  
