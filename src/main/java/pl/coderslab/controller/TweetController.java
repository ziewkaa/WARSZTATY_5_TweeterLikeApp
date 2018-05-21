package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Tweet;
import pl.coderslab.service.TweetService;
import pl.coderslab.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    TweetService tweetService;

    @Autowired
    UserService userService;

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("tweet", new Tweet());
        return "newtweet" ;
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Tweet tweet, BindingResult bindingResult, HttpSession session){

        Long id = (Long) session.getAttribute("userId");

        if (bindingResult.hasErrors()){
            return "newtweet";
        }

        tweet.setUser(userService.findUserById(id));
        tweet.setCreated(LocalDate.now());
        tweetService.saveTweet(tweet);
        return "redirect:/user/home" ;
    }

    @GetMapping("/all/{id}")
    public String show(@PathVariable Long id, @ModelAttribute Tweet tweet){
        tweetService.findAllTweetsByUserID(id);
        return "tweets" ;
    }

}
