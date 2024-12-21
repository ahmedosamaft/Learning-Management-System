package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_VERSION + "/questions")
@RequiredArgsConstructor
@RolesAllowed({Roles.ADMIN, Roles.INSTRUCTOR})
public class QuestionController {
//    private final IQuestionService questionService;
//
//    @PostMapping
//    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto) {
//        return questionService.createQuestion(questionRequestDto);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateQuestion(@PathVariable String id,@Valid @RequestBody QuestionUpdateDto questionRequestDto) {
//        return questionService.updateQuestion(id, questionRequestDto);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getQuestion(@PathVariable String id) {
//        return questionService.getQuestion(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteQuestion(@PathVariable String id) {
//        return questionService.deleteQuestion(id);
//    }
}
