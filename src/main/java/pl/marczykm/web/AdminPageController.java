package pl.marczykm.web;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.marczykm.domain.Post;
import pl.marczykm.domain.PostFormWrapper;
import pl.marczykm.service.PostService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by marcin on 21.07.15.
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    PostService postService;

    @RequestMapping
    public String adminMainPage(){
        return "admin";
    }

    @RequestMapping("/create")
    public String adminCreatePage(){
        return "create";
    }

    @RequestMapping("/posts")
    public String adminManagePostsPage(){
        return "manage";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String adminSavePostPage(
            @ModelAttribute PostFormWrapper postFormWrapper,
            @RequestParam("background-image") MultipartFile file,
            Model model) {

        boolean uploadStatus = false;
        String name = DigestUtils.sha1Hex(new Date().toString()) + "." + extractExtension(file.getOriginalFilename());

        File fileU = new File("../webapps/ROOT/WEB-INF/classes/public/uploads/" + name);

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(fileU));
                stream.write(bytes);
                stream.close();

                uploadStatus = true;
            } catch (Exception e) {
                uploadStatus = false;
            }
        } else {
            uploadStatus = false;
        }


        if (uploadStatus) {
            Post post = new Post();
            post.setTitle(postFormWrapper.getTitle());
            post.setContent(postFormWrapper.getContent());
            post.setAuthor("");
            post.setActive(true);
            post.setPhoto(name);

            postService.savePost(post);
            model.addAttribute("messageTitle", "Success");
            model.addAttribute("messageContent", "Post created successfully.");
        }

        if (!uploadStatus){
            model.addAttribute("messageTitle", "Failed");
            model.addAttribute("messageContent", "Failed to create post.");
        }

        return "savePostStatus";
    }

    private String extractExtension(String filename) {
        String[] splitted = filename.split("\\.");
        return splitted[splitted.length-1];
    }


}
