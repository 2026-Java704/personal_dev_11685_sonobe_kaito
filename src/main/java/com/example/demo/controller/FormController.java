//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.demo.entity.Form;
//
//@Controller
//@RequestMapping("/form")
//public class FormController {
//
//  @GetMapping
//  public String showForm(Model model) {
//    model.addAttribute("form", new Form());
//    return "form";
//  }
//
//  @PostMapping("/processForm")
//  public String processForm(@ModelAttribute Form form, Model model) {
//    // フォームの内容を処理
//    model.addAttribute("form", form);
//    return "result"; // 処理結果を表示するテンプレート
//  }
//}