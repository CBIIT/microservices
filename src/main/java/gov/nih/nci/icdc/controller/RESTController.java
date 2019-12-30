package gov.nih.nci.icdc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;

import gov.nih.nci.icdc.model.ConfigurationDAO;
import gov.nih.nci.icdc.service.Neo4JGraphQLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "ICDC REST APIs")
@Component
@RestController
public class RESTController {

	private static final Logger logger = LogManager.getLogger(RESTController.class);

	@Autowired
	private Neo4JGraphQLService neo4jService;

	@Autowired
	private ConfigurationDAO config;
	
	@CrossOrigin
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	@ResponseBody
	public String ping(HttpServletRequest request, HttpServletResponse response) {
		logger.info("hit end point:/ping");
		return "pong";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/v1/idl/update", method = RequestMethod.GET)
	@ResponseBody
	public String updateSchema(HttpServletRequest request, HttpServletResponse response) throws UnirestException {
		String graphQL = " query {  __type(name: \"case\") {\n" + 
				"    name\n" + 
				"    fields {\n" + 
				"      name\n" + 
				"      type {\n" + 
				"        name\n" + 
				"        kind\n" + 
				"      }\n" + 
				"    }\n" + 
				"  }}";
		neo4jService.query(graphQL);
		
		return null;
	}

	

	@ApiOperation(value = "Get list of programs")
	@CrossOrigin
	@RequestMapping(value = "/v1/rest/programs", method = RequestMethod.GET)
	@ResponseBody
	public String getPrograms(HttpServletRequest request, HttpServletResponse response) throws UnirestException {
		logger.info("hit end point:/v1/rest/programs");
		String graphQL = "query {"+"program {\n" + 
				"  name\n" + 
				"  program_description\n" + 
				"  external_program_url\n" + 
				"	}\n" + 
				"}";
		return neo4jService.query(graphQL);
	}

	@ApiOperation(value = "Get list of studies by program id")
	@CrossOrigin
	@RequestMapping(value = "/v1/rest/program/{id}/studies", method = RequestMethod.GET)
	@ResponseBody
	public String getProgramStudies(@ApiParam(value = "program_id", required = true) @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("hit end point:/v1/rest/program/{id}/studie   id: " + id);
		return "to be done";
	}

	@ApiOperation(value = "Get list of studies")
	@CrossOrigin
	@RequestMapping(value = "/v1/rest/studies", method = RequestMethod.GET)
	@ResponseBody
	public String getStudies(HttpServletRequest request, HttpServletResponse response) throws UnirestException {
		logger.info("hit end point:/v1/rest/studies ");
		String graphQL = "query {"+"study {\n" + 
				"  clinical_study_id\n" + 
				"  clinical_study_designation\n" + 
				"  clinical_study_name\n" + 
				"  clinical_study_description\n" + 
				"  clinical_study_type\n" + 
				"  date_of_iacuc_approval\n" + 
				"  dates_of_conduct\n" + 
				"  study_arms{\n" + 
				"    arm\n" + 
				"    ctep_treatment_assignment_code\n" + 
				"  }\n" + 
				"  study_sites{\n" + 
				"   site_short_name\n" + 
				"  veterinary_medical_center\n" + 
				"  registering_institution\n" + 
				"  }" + 
				"}}";
		return neo4jService.query(graphQL);
	}

	@ApiOperation(value = "Get Cases by study id")
	@CrossOrigin
	@RequestMapping(value = "/v1/rest/study/{id}/cases", method = RequestMethod.GET)
	@ResponseBody
	public String getStudyCases(@ApiParam(value = "study_id", required = true) @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("hit end point:/v1/rest/study/{id}/cases   id: " + id);
		return "to be done";
	}

	@ApiOperation(value = "Get list of cases")
	@CrossOrigin
	@RequestMapping(value = "/v1/rest/cases", method = RequestMethod.GET)
	@ResponseBody
	public String getCases(HttpServletRequest request, HttpServletResponse response) throws UnirestException {
		logger.info("hit end point:/v1/rest/cases ");
		String graphQL = "query "+"{  case{\n" + 
				"    patient_id\n" + 
				"    patient_first_name\n" + 
				"    crf_id\n" + 
				"    cohort{\n" + 
				"       cohort_description\n" + 
				"       cohort_dose\n" + 
				"       study_arm{\n" + 
				"         arm\n" + 
				"         ctep_treatment_assignment_code\n" + 
				"         study{\n" + 
				"              clinical_study_id\n" + 
				"              clinical_study_designation\n" + 
				"              clinical_study_name\n" + 
				"              clinical_study_description\n" + 
				"              clinical_study_type\n" + 
				"              date_of_iacuc_approval\n" + 
				"              dates_of_conduct\n" + 
				"         }\n" + 
				"         agents{\n" + 
				"             medication\n" + 
				"             document_number\n" + 
				"         }\n" + 
				"      }\n" + 
				"    }\n" + 
				"    enrollment{\n" + 
				"        date_of_registration\n" + 
				"        registering_institution\n" + 
				"        initials\n" + 
				"        date_of_informed_consent\n" + 
				"        site_short_name\n" + 
				"        veterinary_medical_center\n" + 
				"        enrollment_document_number\n" + 
				"        cohort_description\n" + 
				"        patient_subgroup\n" + 
				"    }\n" + 
				"    demographic{\n" + 
				"        breed\n" + 
				"        patient_age_at_enrollment\n" + 
				"        date_of_birth\n" + 
				"        sex\n" + 
				"        weight\n" + 
				"        neutered_indicator\n" + 
				"        crf_id\n" + 
				"    }\n" + 
				"    diagnoses{\n" + 
				"       disease_term\n" + 
				"        primary_disease_site\n" + 
				"        stage_of_disease\n" + 
				"        date_of_diagnosis\n" + 
				"        histology_cytopathology\n" + 
				"        date_of_histology_confirmation\n" + 
				"        histological_grade\n" + 
				"        pathology_report\n" + 
				"        treatment_data\n" + 
				"        follow_up_data\n" + 
				"        concurrent_disease\n" + 
				"        concurrent_disease_type\n" + 
				"        crf_id\n" + 
				"    }\n" + 
				"    off_study{\n" + 
				"       document_number\n" + 
				"        date_off_study\n" + 
				"        reason_off_study\n" + 
				"        date_of_disease_progression\n" + 
				"        date_off_treatment\n" + 
				"        best_resp_vet_tx_tp_secondary_response\n" + 
				"        date_last_medication_administration\n" + 
				"        best_resp_vet_tx_tp_best_response\n" + 
				"        date_of_best_response\n" + 
				"    }\n" + 
				"    off_treatment{\n" + 
				"        document_number\n" + 
				"        date_off_treatment\n" + 
				"        reason_off_treatment\n" + 
				"        date_of_disease_progression\n" + 
				"        best_resp_vet_tx_tp_secondary_response\n" + 
				"        date_last_medication_administration\n" + 
				"        best_resp_vet_tx_tp_best_response\n" + 
				"        date_of_best_response\n" + 
				"    }\n" + 
				"  }\n" + 
				"}";
		return neo4jService.query(graphQL);
	}

	@ApiOperation(value = "Get API version")
	@CrossOrigin
	@RequestMapping(value = "/api/version", method = RequestMethod.GET)
	public String getAPIVersion(HttpServletRequest request, HttpServletResponse response) {
		logger.info("hit end point:/api/version ");
		return config.getApiVersion();
	}
	
	
	@ApiOperation(value = "Get data model version")
	@CrossOrigin
	@RequestMapping(value = "/data_model/version", method = RequestMethod.GET)
	public String getDataModelVersion(HttpServletRequest request, HttpServletResponse response) {
		logger.info("hit end point:/data_model/version ");
		return config.getDataModelVersion();
	}
	
	

	@RequestMapping(value = "/authorize/accept", method = RequestMethod.GET)
	public void authorizeCallBack(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String redirect_url = "http://localhost/?passed";
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				if ("access_token".equals(cookies[i].getName())) {
					System.out.println(cookies[i].getValue());
					HttpSession session = request.getSession();
					session.setAttribute("token", cookies[i].getValue());
				}
			
				response.addCookie(cookies[i]);
			}
		} else {
			redirect_url = config.getErrorRedirectURL();
		}

		response.setHeader("Location", redirect_url);
		response.setStatus(302);
	}


	
	@RequestMapping(value = "/test_token", method = RequestMethod.GET)
	public String testToken(HttpServletRequest request, HttpServletResponse response) {
		
		return "Pass Token Test";
	}

}
