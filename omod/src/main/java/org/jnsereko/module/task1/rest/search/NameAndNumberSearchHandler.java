package org.jnsereko.module.task1.rest.search;

import org.apache.commons.lang3.StringUtils;
import org.jnsereko.module.task1.web.controller.Task1RestController;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.SearchConfig;
import org.openmrs.module.webservices.rest.web.resource.api.SearchHandler;
import org.openmrs.module.webservices.rest.web.resource.api.SearchQuery;
import org.openmrs.module.webservices.rest.web.resource.impl.EmptySearchResult;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NameAndNumberSearchHandler implements SearchHandler {
    private final SearchConfig searchConfig = new SearchConfig("getByNameOrMobile",  RestConstants.VERSION_1 + Task1RestController.IDGEN_NAMESPACE + "/patient",
            Arrays.asList("1.8.*", "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"),
            Arrays.asList(
                    new SearchQuery.Builder("Allows you to find patients with the same name or mobile number")
                            .withRequiredParameters("nameOrMobile").build()));

    @Override
    public PageableResult search(RequestContext context) throws ResponseException {
        List<Patient> results = null;
        String searchByNameOrMobile = context.getParameter("nameOrMobile");
        if (StringUtils.isNotBlank(searchByNameOrMobile)) {
            if(!StringUtils.isNumericSpace(searchByNameOrMobile)) {
                results = Context.getPatientService().getPatients(searchByNameOrMobile);

            }else if( validateNumber(searchByNameOrMobile)) {

                results = Context.getPatientService().getAllPatients()
                        .stream()
                        .filter(person -> hasSameMobile(person.getAttributes(), searchByNameOrMobile))
                        .collect(Collectors.toList());
            }
            Boolean resultsExist = results != null && !results.isEmpty();
            if (!resultsExist) {
                return new EmptySearchResult();
            }
            return new NeedsPaging<Patient>(results, context);
        }
        return new EmptySearchResult();
    }

    @Override
    public SearchConfig getSearchConfig() {
        return searchConfig;
    }
    public boolean hasSameMobile(Set<PersonAttribute> attributes, String searchByNameOrMobile){
        for (PersonAttribute personAttribute: attributes) {
            if(personAttribute.getValue().equalsIgnoreCase(searchByNameOrMobile)) {return true;}
        }
        return false;
    }
    private boolean validateNumber(String mobNumber)
    {
        //validates phone numbers having 10 digits (9998887776)
        if (mobNumber.matches("\\d{10}")) {return true;}
        //validates phone numbers having digits, -, . or spaces
        else if (mobNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {return true;}
        else if (mobNumber.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {return true;}
        //validates phone numbers having digits and extension (length 3 to 5)
        else if (mobNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {return true;}
        //validates phone numbers having digits and area code in braces
        else if (mobNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {return true;}
        else if (mobNumber.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {return true;}
        else if (mobNumber.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) {return true;}
        else {return false;}
    }
}
