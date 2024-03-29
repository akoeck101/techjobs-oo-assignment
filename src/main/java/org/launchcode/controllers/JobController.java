package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {
        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
//        JobData job = new Job();

        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobFormParam, Errors errors, String value) {
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute(new JobForm());
            return "new-job";
        }

        Job newJob = new Job();
        newJob.setName(jobFormParam.getName());
        newJob.setEmployer(jobData.findEmployerById(jobFormParam.getEmployerId()));
        newJob.setLocation(jobData.findLocationById(jobFormParam.getLocationId()));
        newJob.setCoreCompetency(jobData.findcoreCompetencyById(jobFormParam.getCoreCompetencyId()));
        newJob.setPositionType(jobData.findPositionTypeById(jobFormParam.getPositionTypeId()));
        jobData.add(newJob);
        model.addAttribute("newjob", newJob);

// public Job(String aName, Employer aEmployer, Location aLocation,
//                PositionType aPositionType, CoreCompetency aSkill) {
        return "job-detail";

    }
}
