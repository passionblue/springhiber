package com.mkyong.form.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkyong.form.model.MyTask;
import com.mkyong.form.model.UserContext;
import com.mkyong.form.validator.TaskFormValidator;

@Controller
@Scope("session")
public class TaskController {
    
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserContext userContext;    
    
    
    @Autowired
    TaskFormValidator taskFormValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(taskFormValidator);
    }    
    
    /**
     * This should be called every operation and passed to the method. 
     * 
     * When the form has modelAttribute="taskForm", this is mandatory for the form to open successfully with @ModelAttribute
     * 
     */
    @ModelAttribute
    public MyTask createMyTask(Model model) {
        
        long timestamp = System.nanoTime();
        
        logger.debug(" createMyTask() My TASK " + timestamp);
        model.addAttribute("taskForm", new MyTask("X")); // this will be passed to showing and fill out the default values
        return new MyTask("XX " + timestamp);
    }    

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String getDefault(MyTask task) {

        return "redirect:/task/show/start";
    }

    @RequestMapping(value = "/task/show/{formId}", method = RequestMethod.GET)
    public String getPage(MyTask task, @PathVariable("formId") String formId) {

        logger.debug("getPage() with formID " + formId);
        logger.debug("MyTask() : {}", task); // this task is created from createMyTask. 
        return "/task/" + formId;
    }
    
    // delete user
    @RequestMapping(value = "/task/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("taskForm") @Validated MyTask taskForm,  
            BindingResult result,  Model model, final RedirectAttributes redirectAttributes ) {

        logger.debug("addTask() : formTask {}", taskForm);
//        logger.debug("addTask() : myTask   {}", task); // These two task are the same object from the FORM. 
        
        if (result.hasErrors()) {
            // common error redirect would lose the results
            return "/task/start";
        }
        
        if ( userContext == null ) {
            logger.debug("");
        }
        
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Task Added successfully!");
        
        return "redirect:/task/show/results";
    }    
    
    // delete user
    @RequestMapping(value = "/task/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("id") String id, MyTask task, final RedirectAttributes redirectAttributes) {

        logger.debug("deleteUser() : {}", id);
        logger.debug("MyTask() : {}", task);
        
        return "redirect:/task/show/results";
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

        logger.debug("handleEmptyData()");
        logger.error("Request: {}, error ", req.getRequestURL(), ex);

        ModelAndView model = new ModelAndView();
        model.setViewName("user/show");
        model.addObject("msg", "user not found");

        return model;

    }    
    
    
}
