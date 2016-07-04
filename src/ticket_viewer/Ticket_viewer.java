package ticket_viewer;

/*
 * Using external Gson Library for parsing Jason data and creating Ticket objects.
 * To get library visit this link https://github.com/google/gson 
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;


public class Ticket_viewer {

	ArrayList<Ticket> tickets;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		URL url;
		HttpURLConnection conn = null;
		Ticket_viewer view = new Ticket_viewer();
		
		try
		{
			url = new URL("https://sbhayani.zendesk.com/api/v2/tickets.json");
		    conn = (HttpURLConnection) url.openConnection();
			String credentials = "shehzad.bhayani@yahoo.com"+":"+"shehya007";
			
			//Encoding of Username and password in base64 so that it can be send in the headers of the request 
			String encoded = DatatypeConverter.printBase64Binary(credentials.getBytes());
	        conn.setRequestProperty("Authorization", "Basic "+encoded);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			int code = conn.getResponseCode();
			/*
			 * The handling of API not available is done here
			 */
			if(code == HttpURLConnection.HTTP_OK)
			{
				JsonReader reader = new JsonReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				view.createTickets(reader);
				String ch = "";
				while(! ch.toUpperCase().equals("Q")  )
				{
					ch = showMenu();
					switch (ch)
					{
					case "1":
						showAll(view);
						break;
					case "2":
						showOne(view);
						break;
					case "q":
						break;
					default:
						System.out.println("Please enter the corrent input.");
					
					}
				}
				System.out.println("          "+"Thank you for using Ticket Viewer Goodbye "+":-)");
		 		
			}
			else
			{
				if(code == HttpURLConnection.HTTP_NOT_FOUND)
				{
					System.out.println("The resource can not be found may get available in future");
				}
				if(code == HttpURLConnection.HTTP_UNAVAILABLE)
				{
					System.out.println("The server is down for a short period of time, please try later ");
				}
				if(code == HttpURLConnection.HTTP_UNAUTHORIZED)
				{
					System.out.println("Need Authorisation to access this resource");
				}
			}
		
		}
		catch(FileNotFoundException ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			conn.disconnect();
			
		}
		
	}
	/*
	 * Individual Tickets are shown by getting the required ticket id as an input from the user.Than searching it in 
	 * the array list of tickets.  
	 */
	private static void showOne(Ticket_viewer view) {
		Scanner getId = new Scanner(System.in);
		System.out.println("Enter Ticket number:" );
		String id = getId.next();
		Ticket single_ticket=null;
		for(Ticket ticket : view.tickets)
		{
			if(ticket.getId() == Integer.parseInt(id))
			{
				single_ticket = ticket;
			}
		}
		System.out.println(single_ticket);
		
	}
	
	/*
	 * Page through is done when more than 25 tickets are returned.
	 * Count variable is used to get mod of 25, when the result is 0 
	 * it means 25 tickets are showed its time to jump on the next page.
	 */
	private static void showAll(Ticket_viewer view) {
		Scanner scan = new Scanner (System.in);
		int left =view.tickets.size();
		int count = 1;
		for(int i =0 ; i< view.tickets.size(); i++)
		{
			System.out.println(view.tickets.get(i));
			if( count<view.tickets.size() && count % 25 == 0 )
			{
				System.out.println("Showing 25 of " + left+" press 1 to see more or 2 cancel ");
				left =left-count;
				String input = scan.next();
				if(input.equals("2")){
					break;
				}
				if(input.equals("1")){
					
				}
			}

			count ++;
		}
	}
	/*
	 * The first thing showed to user is the menu
	 * from where users selects an option
	 */
	private static String showMenu() {
		// TODO Auto-generated method stub
		Scanner getOption = new Scanner(System.in);
		System.out.println("WelCome to the Ticket Viewer");
		System.out.println();
		System.out.println();
		System.out.println("          "+"Select one of the following options then press enter");
		System.out.println("          "+"* To viw all tickets press 1");
		System.out.println("          "+"* To view a single ticket press 2");
		System.out.println("          "+"* To quit type q ");
		String selectedoption = getOption.next();
		
		return selectedoption;
		
	}
	/*
	 * An Array list of Tickets is created by parsing Jason received from API.
	 * An object of ticket is obtained from myGson.fromJson(Jason element, class);
	 * which takes an Jason Element and a class and maps it to Ticket object. 
	 */
	public  ArrayList<Ticket> createTickets(JsonReader reader)
	{
		Gson myGson = new Gson();
		JsonParser jsonParser = new JsonParser();
 		JsonObject userobject= jsonParser.parse(reader).getAsJsonObject();
		JsonArray arr = userobject.getAsJsonArray("tickets");
 		tickets = new ArrayList<Ticket>();
		
 		for(JsonElement element : arr)
 		{
 			Ticket aticket = myGson.fromJson(element, Ticket.class);
 	        tickets.add(aticket);
 		}
 		
		return tickets;
	}
	
}


