
/**
* Model class for storing the selected few attributes
* for  Zendesk Tickets.
*/
package ticket_viewer;

import java.util.Date;

public class Ticket {
	private int id;
	private Date created_at;
	private String subject;
	private String description;
	private String requester_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRequester_id() {
		return requester_id;
	}
	public void setRequester_id(String requester_id) {
		this.requester_id = requester_id;
	}
	public String toString(){
		//return this.id +"  "+this.subject
		

	    return "ID: "+this.id+"\n" +
	    "Subject: "+this.subject+"\n" +
	    "Description: "+ this.description + "\n"+
	    "Created at: "+ this.created_at +"\n" +
	    "Requested by: "+this.requester_id +"\n";
		
	    /*return "ID: "+this.id+"\t\t" +
	    "Subject: "+this.subject+"\t\t" +
	    "Description: "+ this.description + "\t\t"+
	    "Created at: "+ this.created_at +"\t\t" +
	    "Requested by: "+this.requester_id +"\t\t";*/
	  }
}
