package uk.ac.bbsrc.tgac.miso.webapp.controller.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eaglegenomics.simlims.core.User;

import uk.ac.bbsrc.tgac.miso.core.data.Submission;
import uk.ac.bbsrc.tgac.miso.core.data.type.SubmissionActionType;
import uk.ac.bbsrc.tgac.miso.core.util.EnaSubmissionPreparation;
import uk.ac.bbsrc.tgac.miso.service.SubmissionService;
import uk.ac.bbsrc.tgac.miso.service.security.AuthorizationManager;

@Controller
@RequestMapping("/rest/submissions")
public class SubmissionRestController extends RestController {
  @Autowired
  private SubmissionService submissionService;
  @Autowired
  private AuthorizationManager authorizationManager;

  @ResponseBody
  @GetMapping(path = "/{submissionId}/download")
  public byte[] download(@PathVariable("submissionId") Long submissionId, @QueryParam("action") String action,
      @QueryParam("centreName") String centreName, HttpServletResponse response) throws IOException {
    Submission submission = submissionService.get(submissionId);
    if (submission == null) {
      throw new RestException("Submission not found", Status.NOT_FOUND);
    }
    User user = authorizationManager.getCurrentUser();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(new MediaType("application", "zip"));
    response.setHeader("Content-Disposition",
        "attachment; filename="
            + String.format("SUBMISSON%d-%s.zip", submission.getId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
    return new EnaSubmissionPreparation(submission, user, centreName, SubmissionActionType.valueOf(action)).toBytes();
  }
}
