package jdbc;

import java.io.FileNotFoundException; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class TelephoneBillingSystem {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	private String phoneno;
	public TelephoneBillingSystem() throws SQLException, FileNotFoundException, IOException
	{
		
		System.out.println("\t\t\tTelephone Billing System");
	    System.out.println("\t\t\t--------------------------");
	    con=MyConnection.getMyConnection();	
		LocalDate now=LocalDate.now();
		System.out.println("Date:"+now);
	}
	
	
	public void insertData(String phoneno,String name,String address,String adharno) throws SQLException
	{
		pstmt=con.prepareStatement("insert into customer values(?,?,?,?)");
		pstmt.setString(1, phoneno);
		pstmt.setString(2,name ); 
		pstmt.setString(3,address);
		pstmt.setString(4, adharno);
		pstmt.executeUpdate();
		System.out.println("record is inserted");
			 
	}
	
	   

	public void getData(String phoneno) throws SQLException
	{
		pstmt=con.prepareStatement("select*from customer where phoneno=?");
		 pstmt.setString(1,phoneno);
		rs=pstmt.executeQuery();
		
		while(rs.next())
		{
			System.out.print(rs.getString(1)+"\t");
			System.out.print(rs.getString(2)+"\t");
			System.out.print(rs.getString(3)+"\t");
			System.out.print(rs.getString(4));
			System.out.println();	
		}
		
	 }
	
	
	 public void updateData(String name,String phoneno) throws SQLException
    {
       pstmt=con.prepareStatement("update customer set name=? where phoneno=?");
       pstmt.setString(1,name);
       pstmt.setString(2, phoneno);
       pstmt.executeUpdate();
       System.out.println("record is updated");
      
    }
	
	 
     public void deleteData(String phoneno) throws SQLException
	   {
		  pstmt=con.prepareStatement("delete from customer where phoneno=?");
		    	
		   pstmt.setString(1,phoneno);
		    	
		   pstmt.executeUpdate();
		   	System.out.println("Deleted sucessfully");
		 }
     
     
	  public void generateBill(String phoneno,int nofcalls) throws SQLException
		{
		   	pstmt=con.prepareStatement("select*from customer where phoneno=?");
		    	
			pstmt.setString(1,phoneno);
						
			rs=pstmt.executeQuery();
				
		 while(rs.next())
		  {
			System.out.print(rs.getString(1)+"\t");
						
	   	  }	
			if(nofcalls<10)
	    	System.out.println("free of cost");
		    else if(nofcalls>=10 && nofcalls<=50)
		     System.out.println(nofcalls*2);
		    else if(nofcalls>50 && nofcalls<100)
		     System.out.println(nofcalls*3);
			  else
			    System.out.println(nofcalls*5);					
	 }
	  
	  
	public static void main(String[] args) throws FileNotFoundException, SQLException, IOException 
	{
	
		TelephoneBillingSystem t=new TelephoneBillingSystem();
		
		Scanner sc=new Scanner(System.in);
		char ch;
		
		do
		{
			System.out.println("\n\n\tMAIN MENU :-\n 1.Register the customer\n 2.search customer\n 3.update the customer\n 4.Generate bill\n 5.Delete customer\n 6.Help\n");
			
			System.out.println("Enter your choice from 1 to 6");
			int choice=sc.nextInt();
		 switch(choice) {
		 case 1:	System.out.println("Registration of customer");
		         System.out.println("Enter your phone number");  
		         String phoneno=sc.next();
		         System.out.println("Enter the name");
		         String name=sc.next();
		         System.out.println("Enter the  adderss");
		         String address=sc.next();
		         System.out.println("Enter the adharno  ");
		         String adharno=sc.next();
		        t.insertData(phoneno, name, address, adharno);
		 break;
		
		 
		 case 2:System.out.println("\tsearching for a customer");
		 System.out.println();
		 System.out.println("Enter the phoneno");
		 phoneno=sc.next();
		 t.getData(phoneno);
		 break;
		
		 case 3:System.out.println("Update the customer");
		 System.out.println("Enter the phoneno");
		 phoneno=sc.next();
		 System.out.println("Enter the name");
		 name=sc.next();
		 t.updateData(name,phoneno);
		 break;
		
		 case 4: System.out.println("Generate the Bill");
		 	System.out.println("Enter the phone number");
			phoneno=sc.next();
			System.out.println("Enter no of calls");
			int nofcalls=sc.nextInt();	
		    t.generateBill(phoneno,nofcalls);
		    System.out.println("do u want to pay the bill press 'y' ,if not press any another key ");
			
			 ch=sc.next().charAt(0);  
			 if(ch=='Y'|| ch=='y') 
			 {
			 System.out.println("Bill paid sucessfully\n Thanking You");
			 }
			 else
				 System.out.println("please make bill payment soon \n Thanking you");
		 break;
		 
		 case 5: System.out.println("Delete the customer");
		  System.out.println("enter the phone number");
		  phoneno=sc.next();
		 t.deleteData(phoneno);
		 break;
		
		 
		 case 6:System.out.println("Help");
		   System.out.println("nofcalls<10 is free of cost");
		   System.out.println("nofcalls>10 and nofcalls<50 per call 2 rupees");
		    System.out.println("nofcalls>=50 && nofcalls<=100 per call 3");
		    System.out.println("nofcalls>100 per call 5");	   	
	   	 break;
		
		default : System.out.println("Enter your correct choice");
	 }
		
		 System.out.println("\n do u want to continue (y/n)");
		 ch=sc.next().charAt(0);
	 	
	 }
		
		while(ch=='y' || ch=='Y');
		
		 
   }
	}

