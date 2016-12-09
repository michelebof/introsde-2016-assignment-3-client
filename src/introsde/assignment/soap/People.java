
package introsde.assignment.soap;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "People", targetNamespace = "http://soap.assignment.introsde/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface People {


    /**
     * 
     * @param personId
     * @param measureType
     * @return
     *     returns java.util.List<introsde.assignment.soap.Measure>
     */
    @WebMethod
    @WebResult(name = "measure", targetNamespace = "")
    @RequestWrapper(localName = "readPersonHistory", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadPersonHistory")
    @ResponseWrapper(localName = "readPersonHistoryResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadPersonHistoryResponse")
    @Action(input = "http://soap.assignment.introsde/People/readPersonHistoryRequest", output = "http://soap.assignment.introsde/People/readPersonHistoryResponse")
    public List<Measure> readPersonHistory(
        @WebParam(name = "personId", targetNamespace = "")
        Long personId,
        @WebParam(name = "measureType", targetNamespace = "")
        String measureType);

    /**
     * 
     * @param personId
     * @return
     *     returns introsde.assignment.soap.Person
     */
    @WebMethod
    @WebResult(name = "person", targetNamespace = "")
    @RequestWrapper(localName = "readPerson", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadPerson")
    @ResponseWrapper(localName = "readPersonResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadPersonResponse")
    @Action(input = "http://soap.assignment.introsde/People/readPersonRequest", output = "http://soap.assignment.introsde/People/readPersonResponse")
    public Person readPerson(
        @WebParam(name = "personId", targetNamespace = "")
        Long personId);

    /**
     * 
     * @param person
     */
    @WebMethod
    @RequestWrapper(localName = "createPerson", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.CreatePerson")
    @ResponseWrapper(localName = "createPersonResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.CreatePersonResponse")
    @Action(input = "http://soap.assignment.introsde/People/createPersonRequest", output = "http://soap.assignment.introsde/People/createPersonResponse")
    public void createPerson(
        @WebParam(name = "person", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Person> person);

    /**
     * 
     * @param person
     */
    @WebMethod
    @RequestWrapper(localName = "updatePerson", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.UpdatePerson")
    @ResponseWrapper(localName = "updatePersonResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.UpdatePersonResponse")
    @Action(input = "http://soap.assignment.introsde/People/updatePersonRequest", output = "http://soap.assignment.introsde/People/updatePersonResponse")
    public void updatePerson(
        @WebParam(name = "person", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Person> person);

    /**
     * 
     * @param personId
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(name = "personId", targetNamespace = "")
    @RequestWrapper(localName = "deletePerson", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.DeletePerson")
    @ResponseWrapper(localName = "deletePersonResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.DeletePersonResponse")
    @Action(input = "http://soap.assignment.introsde/People/deletePersonRequest", output = "http://soap.assignment.introsde/People/deletePersonResponse")
    public int deletePerson(
        @WebParam(name = "personId", targetNamespace = "")
        Long personId);

    /**
     * 
     * @return
     *     returns java.util.List<introsde.assignment.soap.Person>
     */
    @WebMethod
    @WebResult(name = "people", targetNamespace = "")
    @RequestWrapper(localName = "getPeopleList", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.GetPeopleList")
    @ResponseWrapper(localName = "getPeopleListResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.GetPeopleListResponse")
    @Action(input = "http://soap.assignment.introsde/People/getPeopleListRequest", output = "http://soap.assignment.introsde/People/getPeopleListResponse")
    public List<Person> getPeopleList();

    /**
     * 
     * @return
     *     returns java.util.List<introsde.assignment.soap.Measure>
     */
    @WebMethod
    @WebResult(name = "measure", targetNamespace = "")
    @RequestWrapper(localName = "readMeasureTypes", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadMeasureTypes")
    @ResponseWrapper(localName = "readMeasureTypesResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadMeasureTypesResponse")
    @Action(input = "http://soap.assignment.introsde/People/readMeasureTypesRequest", output = "http://soap.assignment.introsde/People/readMeasureTypesResponse")
    public List<Measure> readMeasureTypes();

    /**
     * 
     * @param measureId
     * @param personId
     * @param measureType
     * @return
     *     returns introsde.assignment.soap.Measure
     */
    @WebMethod
    @WebResult(name = "measure", targetNamespace = "")
    @RequestWrapper(localName = "readPersonMeasure", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadPersonMeasure")
    @ResponseWrapper(localName = "readPersonMeasureResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.ReadPersonMeasureResponse")
    @Action(input = "http://soap.assignment.introsde/People/readPersonMeasureRequest", output = "http://soap.assignment.introsde/People/readPersonMeasureResponse")
    public Measure readPersonMeasure(
        @WebParam(name = "personId", targetNamespace = "")
        Long personId,
        @WebParam(name = "measureType", targetNamespace = "")
        String measureType,
        @WebParam(name = "measureId", targetNamespace = "")
        Long measureId);

    /**
     * 
     * @param measure
     * @param personId
     */
    @WebMethod
    @RequestWrapper(localName = "savePersonMeasure", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.SavePersonMeasure")
    @ResponseWrapper(localName = "savePersonMeasureResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.SavePersonMeasureResponse")
    @Action(input = "http://soap.assignment.introsde/People/savePersonMeasureRequest", output = "http://soap.assignment.introsde/People/savePersonMeasureResponse")
    public void savePersonMeasure(
        @WebParam(name = "personId", targetNamespace = "")
        Long personId,
        @WebParam(name = "measure", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Measure> measure);

    /**
     * 
     * @param measure
     * @param personId
     */
    @WebMethod
    @RequestWrapper(localName = "updatePersonMeasure", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.UpdatePersonMeasure")
    @ResponseWrapper(localName = "updatePersonMeasureResponse", targetNamespace = "http://soap.assignment.introsde/", className = "introsde.assignment.soap.UpdatePersonMeasureResponse")
    @Action(input = "http://soap.assignment.introsde/People/updatePersonMeasureRequest", output = "http://soap.assignment.introsde/People/updatePersonMeasureResponse")
    public void updatePersonMeasure(
        @WebParam(name = "personId", targetNamespace = "")
        Long personId,
        @WebParam(name = "measure", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Measure> measure);

}
