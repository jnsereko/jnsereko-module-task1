/* * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.jnsereko.module.task1.web.controller;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.SearchConfig;
import org.openmrs.module.webservices.rest.web.resource.api.SearchHandler;
import org.openmrs.module.webservices.rest.web.resource.api.SearchQuery;
import org.openmrs.module.webservices.rest.web.resource.impl.EmptySearchResult;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + Task1RestController.IDGEN_NAMESPACE)
public class Task1RestController extends MainResourceController { //implements SearchHandler

    public static final String IDGEN_NAMESPACE = "/task1";

    @Override
    public String getNamespace() {
        return RestConstants.VERSION_1 + IDGEN_NAMESPACE;
    }
    /**
    @RequestMapping("/patient")
    @ResponseBody
    public ResponseEntity< List<Patient> > search(@RequestParam(value = "nameOrMobile", required = false) String searchByNameOrMobile, RequestContext context) throws ResponseException {
        List<Patient> results = null;
        if (StringUtils.isNotBlank(searchByNameOrMobile)) {
            if(!StringUtils.isNumericSpace(searchByNameOrMobile)) {
                results = Context.getPatientService().getPatients(searchByNameOrMobile);

            }else if( validateNumber(searchByNameOrMobile)) {

                results = Context.getPatientService().getAllPatients()
                        .stream()
                        .filter(person -> hasSameMobile(person.getAttributes(), searchByNameOrMobile))
                        .collect(Collectors.toList());

                /**
                List<Patient> allPatients = Context.getPatientService().getAllPatients();
                for(Patient patiient: allPatients){
                    if(hasSameMobile(patiient.getPerson().getAttributes(), searchByNameOrMobile)) {
                        results.add(patiient);
                    }
                }
                Context.getPatientService();
            }
            Boolean resultsExist = results != null && !results.isEmpty();
            if (!resultsExist) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }**/
}
