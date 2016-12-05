package introsde.document.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Holder;

import org.w3c.dom.Document;

import introsde.document.ws.PeopleService;
import introsde.document.ws.People;
import introsde.document.ws.Person;
import introsde.document.ws.ReadPerson;
import introsde.document.ws.ReadPersonListResponse;
import introsde.document.ws.ReadPersonResponse;
import introsde.document.ws.UpdatePerson;
import introsde.document.ws.UpdatePersonResponse;
import introsde.document.ws.CreatePerson;
import introsde.document.ws.CreatePersonResponse;
import introsde.document.ws.Measure;
import introsde.document.ws.ParseException_Exception;

public class PeopleClient{
	
	private static String start;
	private static String result;
	private static PrintStream print;
	private static String doc;
	private static PeopleService service;
	private static People people;
	private static int request;
	
	
    public static void main(String[] args) throws Exception {
    	initialize();
    	getURI();
    	String type = "weight";
        int first = request1();
        printResult();
        Person person = request2(first); 
        printResult();
        request3(person);
        printResult();
        int delete = request4(first);
        printResult();
        request5(delete);
        printResult();
        int mid = request6(first, type);
        printResult();
        request7();
        printResult();
        request8(first, type, mid);
        printResult();
        Measure measure = request9(first);
        printResult();
        request10(first, measure);
        printResult();
        System.out.println("All the requests were executed and the results were written in the client.log!");
    }
    
    public static void getURI(){
    	System.out.println("Server WSDL url: https://introsde2016-assignment3.herokuapp.com/ws/people?wsdl");
    }
    
    
    private static void initialize() throws FileNotFoundException{
    	FileOutputStream file = new FileOutputStream("client.log");
        print = new PrintStream(file);
        service = new PeopleService();
        people = service.getPeopleImplPort();
        request = 1;
    }
    
    
	private static void printResult() throws TransformerException{
		System.out.println("Request #"+request+" was written in the log file");
		PrintStream stream = print;
		stream.println(start);
		stream.println("=> Result: "+ result);
		stream.println();
		stream.println(doc);
		stream.println("************************************");
		stream.println();
//		System.out.println(start);
//		System.out.println("=> Result: "+ result);
//		System.out.println();
//		System.out.println(doc);
//		System.out.println("************************************");
//		System.out.println();
		request++;
	}
	
	
	private static String printPerson(Person p){
		String res= "Person with ID="+p.getIdPerson()+"\n"; 
		res += "--> Name: "+p.getName()+"\n";
		res += "--> Lastname: "+p.getLastname()+"\n";
		res += "--> Email: "+p.getEmail()+"\n";
		res += "--> Birthdate: "+p.getBirthdate()+"\n";
		if (p.getCurrentHealth().getMeasure().size()!=0){
			List<Measure> m = p.getCurrentHealth().getMeasure();
			res += "--> Current measure: \n";
			for (int i = 0;i < m.size(); i++){
				res += "\t"+printMeasure(m.get(i))+"\n";
			}
		}
		
		
		return res;
	}
	
	
	private static String printMeasure(Measure m){
		String res = "Measure with ID="+m.getIdMeasure()+"\n";
		res += "\t--> Measure Type: "+m.getType()+"\n";
		res += "\t--> Value: "+m.getValue()+"\n";
		res += "\t--> Data type : "+m.getValueType()+"\n";
		res += "\t--> Date of registration: "+m.getDate()+"\n";		
		return res;
	}
	
	
	
	//	List of request
	
	//	Request #1 - Return the first id
	private static int request1(){
		start = "Request #1: readPersonList()";
		doc = "";
        List<Person> pList = people.readPersonList();
        if (!pList.isEmpty()){
        	result="OK, list has "+pList.size()+" elements";
        	for (int i=0;i<pList.size()-1;i++){
            	doc += printPerson(pList.get(i))+"------------------\n";
            }
        	doc += printPerson(pList.get(pList.size()-1));
        	return pList.get(0).getIdPerson();
        }	
        else
        	result="ERROR, list is EMPTY";
       return -1;
        
        
        
	}

	//	Request #2 - return the information of the Person with given id
	private static Person request2(int id){
		start = "Request #2: readPerson(int id)";
        Person p = people.readPerson(id);
        if (p!=null)
        	result="OK, Found Person by id ="+id;
        else
        	result="ERROR, Didn't find any Person with  id = "+id;
        doc = printPerson(p);
        return p;
	}
	
	//	Request #3
	private static void request3(Person p){
		start = "Request #3: updatePerson(Person p)";
		String newLastname = p.getLastname()+"F";
		p.setCurrentHealth(null);	// because the update method should modify only the personal information, not the health profile
		p.setLastname(newLastname); // modify the lastname with the current plus one F
		Holder<Person> holder=new Holder<Person>(p);
		people.updatePerson(holder);
		Person ris = holder.value;
        if (newLastname.equals(ris.getLastname()))
        	result="OK, The lastname is changed";
        else
        	result="ERROR, The lastname is NOT changed";
        doc = printPerson(ris);
	}
	
	//	Request #4 - return the id of the new Person 
	private static int request4(int id){
		start = "Request #4: createPerson(Person p)";
		Person p = people.readPerson(id);
		p.setIdPerson(0);
		p.setName("Miky");
		p.setLastname("Test");
		p.setEmail("michele@test.it");
		p.setBirthdate("07/08/1993"); 
		Measure m = new Measure();
		m.setType("weight");
		m.setValue("176");
		m.setValueType("integer");
		m.setDate("01/12/2016");
		List<Measure> cp = p.getCurrentHealth().getMeasure();
		cp.clear();
		cp.add(m);
		p.setCurrentHealth(p.getCurrentHealth());
		Holder<Person> holder=new Holder<Person>(p);
		people.createPerson(holder);
		p=holder.value;
        if (p!=null)
        	result="OK, Create person with id ="+p.getIdPerson();
        else
        	result="ERROR, Didn't create the Person";
        doc = printPerson(p);
        return p.getIdPerson();
	}
	
	//	Request #5 - remove the person created in request 4
	private static void request5(int id){
		start = "Request #5: deletePerson(int id)";
        int ris= people.deletePerson(id);
        if (ris==0)
        	result="OK,the person with id "+id+" was deleted ";
        else
        	result="ERROR, Didn't delete the person with  id = "+id;
	}
		
	//	Request #6
	private static int request6(int id, String type){
		start = "Request #6: readPersonHistory(Long id, String measureType)";
		doc = "";
        List<Measure> list = people.readPersonHistory(id, type);
        for(int i=0;i<list.size();i++){
        	doc += printMeasure(list.get(i));
        }
        if (!list.isEmpty()){
        	result="OK,the person has "+ list.size() +" weight ";
        	return list.get(0).getIdMeasure();
        }
        	
        else
        	result="ERROR, the person hasn't measure of weight";
        return 0;
        
	}
	
	//	Request #7
	private static void request7(){
		start = "Request #7: readMeasureTypes()";
		doc = "";
        List<Measure> list = people.readMeasureTypes();
        for(int i=0;i<list.size();i++){
        	doc += printMeasure(list.get(i));
        }
        if (!list.isEmpty())
        	result="OK,the person has "+ list.size() +" weight ";
        else
        	result="ERROR, the person hasn't measure of weight";
	}
	
	//	Request #8
	private static void request8(int id, String type, int mid){
		start = "Request #8: readPersonMeasure(Long id, String measureType, Long mid)";
		doc = "";
        Measure m = people.readPersonMeasure(id, type, mid);
        doc = printMeasure(m);
        if (m!=null)
        	result="OK, Found Measure by mid ="+mid;
        else
        	result="ERROR, Didn't find any Measure with  mid = "+mid;
	}
	
	//	Request #9
	private static Measure request9(int id) throws ParseException_Exception{
		start = "Request #9: savePersonMeasure(Long id, Measure m)";
		doc = "";
		Measure m = new Measure();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	m.setDate(sdf.format(new Date()));
    	m.setType("height");
    	m.setValue("180");
    	m.setValueType("integer");
		Holder<Measure> measure = new Holder<Measure>(m);
		people.savePersonMeasure(id, measure);
		m = measure.value;
        doc = printMeasure(m);
        if (m!=null){
        	result="OK, New Measure with mid ="+m.getIdMeasure();
        	return m;
        }       	
        else
        	result="ERROR, Didn't create any Measure ";
        return null;
	}
	
	//	Request #10
	private static void request10(int id, Measure m){
		start = "Request #10: updatePersonMeasure(Long id, Measure m)";
		doc = "";
		String newValue = String.valueOf(Integer.parseInt(m.getValue())+5);
		m.setValue(newValue);
		Holder<Measure> measure = new Holder<Measure>(m);
        people.updatePersonMeasure(id, measure);
        m = measure.value;
        doc = printMeasure(m);
        if (newValue.equals(m.getValue()))
        	result="OK, the value is changed at "+newValue;
        else
        	result="ERROR, the value isn't changed ";
	}
	
	
}