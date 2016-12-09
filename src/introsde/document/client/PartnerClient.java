package introsde.document.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.transform.TransformerException;
import javax.xml.ws.Holder;

import introsde.assignment.soap.PeopleService;
import introsde.assignment.soap.People;
import introsde.assignment.soap.Person;
import introsde.assignment.soap.Measure;

public class PartnerClient{
	
	private static String start;
	private static String info;
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
        long first = request1();
        printResult();
        Person person = request2(first); 
        printResult();
        request3(person);
        printResult();
        long delete = request4();
        printResult();
        request5(delete);
        printResult();
        long mid = request6(first, type);
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
    	PrintStream stream = print;
    	stream.println("Server WSDL url: https://introsde2016-assignment-3.herokuapp.com/soap/people?wsdl");
    }
    
    
    private static void initialize() throws FileNotFoundException{
    	FileOutputStream file = new FileOutputStream("partner-client.log");
        print = new PrintStream(file);
        service = new PeopleService();
        people = service.getPeopleImplPort();
        request = 1;
    }
    
    
	private static void printResult() throws TransformerException{
		System.out.println("Request #"+request+" was written in the log file");
		PrintStream stream = print;
		stream.println(start);
		stream.println("=> Info: "+info);
		stream.println("=> Result: "+ result);
		stream.println();
		stream.println(doc);
		stream.println("************************************");
		stream.println();
//		System.out.println(start);
//		System.out.println(info);
//		System.out.println("=> Result: "+ result);
//		System.out.println();
//		System.out.println(doc);
//		System.out.println("************************************");
//		System.out.println();
		request++;
	}
	
	
	private static String printPerson(Person p){
		String res= "Person with ID="+p.getIdPerson()+"\n"; 
		res += "--> Name: "+p.getFirstname()+"\n";
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
		res += "\t--> Measure Type: "+m.getMeasureType()+"\n";
		res += "\t--> Value: "+m.getMeasureValue()+"\n";
		res += "\t--> Data type : "+m.getMeasureValueType()+"\n";
		res += "\t--> Date of registration: "+m.getDateRegistered()+"\n";		
		return res;
	}
	
	
	
	//	List of request
	
	//	Request #1 - Return the first id
	private static long request1(){
		start = "Request #1: getPeopleList()";
		info = "return the list of all the people in the database";
		doc = "";
        List<Person> pList = people.getPeopleList();
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
	private static Person request2(long id){
		start = "Request #2: readPerson(int id)";
		info = "return the personal information and the current measures of the first Person in db (id="+id+")";
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
		info = "update the Personal information of the Person returned in request #2 with an extra F in the last name and return it";
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
	private static long request4(){
		start = "Request #4: createPerson(Person p)";
		info = "create a new Person with the personal information and current healtprofile and return it";
		Person p = new Person();
		p.setFirstname("Miky");
		p.setLastname("Test");
		p.setEmail("michele@test.it");
		p.setBirthdate("07/08/1993"); 
		Measure m = new Measure();
		m.setMeasureType("weight");
		m.setMeasureValue("176");
		m.setMeasureValueType("integer");
		m.setDateRegistered("01/12/2016");
		Person.CurrentHealth cp = new Person.CurrentHealth();
		cp.getMeasure().add(m);
		p.setCurrentHealth(cp);
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
	private static void request5(long id){
		start = "Request #5: deletePerson(int id)";
		info = "cancel the Person created in the request #4 with id="+id;
        int ris= people.deletePerson(id);
        if (ris==0)
        	result="OK,the person with id "+id+" was deleted ";
        else
        	result="ERROR, Didn't delete the person with  id = "+id;
	}
		
	//	Request #6
	private static long request6(long id, String type){
		start = "Request #6: readPersonHistory(Long id, String measureType)";
		info = "return the list of values (the history) of "+type+" for Person with id="+id;
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
		info = "return the list of all measures in the database";
		doc = "";
        List<Measure> list = people.readMeasureTypes();
        for(int i=0;i<list.size();i++){
        	doc += printMeasure(list.get(i));
        }
        if (!list.isEmpty())
        	result="OK, there are "+ list.size() +" measure in the database ";
        else
        	result="ERROR, the person hasn't measure of weight";
	}
	
	//	Request #8
	private static void request8(long id, String type, long mid){
		start = "Request #8: readPersonMeasure(Long id, String measureType, Long mid)";
		info = "return the measure with mid="+ mid +" and type="+type+" for Person with id="+id;
		doc = "";
        Measure m = people.readPersonMeasure(id, type, mid);
        doc = printMeasure(m);
        if (m!=null)
        	result="OK, Found Measure by mid ="+mid;
        else
        	result="ERROR, Didn't find any Measure with  mid = "+mid;
	}
	
	//	Request #9
	private static Measure request9(long id){
		start = "Request #9: savePersonMeasure(Long id, Measure m)";
		info = "save a new measure of Person identified with id="+ id +" and archive the old value in the history";
		doc = "";
		Measure m = new Measure();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	m.setDateRegistered(sdf.format(new Date()));
    	m.setMeasureType("height");
    	m.setMeasureValue("180");
    	m.setMeasureValueType("integer");
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
	private static void request10(long id, Measure m){
		String newValue = String.valueOf(Integer.parseInt(m.getMeasureValue())+5);
		start = "Request #10: updatePersonMeasure(Long id, Measure m)";
		info = "update the value of the measure created in request #9, the new value is "+newValue;
		doc = "";	
		m.setMeasureValue(newValue);
		Holder<Measure> measure = new Holder<Measure>(m);
        people.updatePersonMeasure(id, measure);
        m = measure.value;
        doc = printMeasure(m);
        if (newValue.equals(m.getMeasureValue()))
        	result="OK, the value is changed at "+newValue;
        else
        	result="ERROR, the value isn't changed ";
	}
	
	
}