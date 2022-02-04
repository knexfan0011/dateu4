package com.tutego.dateu4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class Date4uWebController {
    //long totalProfiles;
    @Autowired
    ProfileRepository profiles;

    @Autowired
    UnicornRepository unicorns;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoService photoService;

    private final String loggedInUserCookieName = "loggedInUser";

    @RequestMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
        model.addAttribute("totalProfiles", profiles.count());
        boolean someoneIsLoggedIn = false;
        //Get cookies
        Cookie[] cookies = request.getCookies();
        //Iterate over them,
        System.out.println("ChEcKInngCooKieSOnInDexPAAge");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //see if any cookie with loggedInUser exists
                if (cookie.getName().equals(loggedInUserCookieName)) {
                    //If it's not null, set flag to true
                    someoneIsLoggedIn = (cookie.getValue() != null);
                    model.addAttribute("loggedInProfileId", getProfileIdOfLoggedInUser(cookies));
                }
            }
        } // else: no cookies, therefore noone is logged in, flag remains false

        //Check if this someone is already logged in
        model.addAttribute("someoneIsLoggedIn", someoneIsLoggedIn);
        return "index";
    }

    @RequestMapping("/profile") // IDEA: Remap to the currently logged in user's profile
    public String profilesPage() {
        return "profile";
    }

    @RequestMapping("/profile/{id}")
    public String profilePage(@PathVariable long id, Model model, HttpServletRequest request) throws IOException {
        System.out.println(id);
        //Profile profile = profiles.getById(id);
        var maybeProfile = profiles.findById(id);
        if (maybeProfile.isEmpty()) return "redirect:/";
        Profile profile = maybeProfile.get();

        // Image stuff test
        model.addAttribute("imgName", "unicorn001");

        // Profile of currently logged in user?
        Long currentlyLoggedInProfileId = getProfileIdOfLoggedInUser(request.getCookies());
        System.out.println("Currently logged in ID: "+currentlyLoggedInProfileId);
        if (currentlyLoggedInProfileId >= 0 && currentlyLoggedInProfileId == id) {
            //TODO: Enable editing of profile page if this profile user is currently logged in
            model.addAttribute("editable", true);
        } else { model.addAttribute("editable", false); }

        Photo profilePhoto = null; // Keep this updated with most recently uploaded image
        List<Photo> allPhotos = new ArrayList<Photo>();
        for (Photo photo : photoRepository.findAll()) {
            if (photo.profileFk.id.equals(id)) {
                System.out.println("Found pair: "+photo.profileFk.id+" == "+id);
                allPhotos.add(photo);
                profilePhoto = (photo.isProfilePhoto&&(profilePhoto == null || photo.created.compareTo(profilePhoto.created)>0)) ? photo : profilePhoto;
            }
        }
        System.out.println("Found "+allPhotos.size()+" photos for this profile");
        if (allPhotos.size() > 0) {
            model.addAttribute("profilePhotoName", profilePhoto.name);
            List<String> photoNames = new ArrayList<>();
            allPhotos.forEach(photo -> photoNames.add(photo.name));
            model.addAttribute("allPhotoNames", photoNames);
        }
        model.addAttribute("profile", new ProfileFormData(profile));// BAD PRACTICE: This was the FormData knows the Profile Bean, better to explicitly transfer the individual properties instead.
        return "individualProfile";
    }

    private Long getProfileIdOfLoggedInUser(Cookie[] cookies) {
        if (cookies == null || cookies.length < 1) return -1L;
        String nick = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(loggedInUserCookieName)){
                nick = cookie.getValue();
                break;
            }
        }
        if (nick.equals("")) return -1L;
        for (Profile profile : profiles.findAll()) {
            if (profile.nickname.equals(nick))
                return profile.id;
        }
        for (Unicorn unicorn : unicorns.findAll()) {
            if (unicorn.email.equals(nick))
                return unicorn.profile.id;
        }

        System.out.println("Reached end of getProfileIdOfLoggedInUser() unexpectedly, returning -1");
        return -1L;
    }

    @RequestMapping("/register")
    public String save(Model model){
        model.addAttribute("attr", "AttRiButEValUeTeSt");
        return "registration";
    }

    @PostMapping("/overwriteProfile")
    public String overwriteProfile(Model model, ProfileFormData profileForm){
        System.out.println("Overwriting existing profile/Unicorn");
        Profile profileWithChanges = profileForm.getProfile();
        profiles.save(profileWithChanges);
        //NOTE: Unicorn is not updated, changing email address and password should be handled seperately

        return "redirect:/";
    }

    @PostMapping( "/register" )
    public String saveProfile( @ModelAttribute ProfileFormData profileForm) {
        System.out.println("/\\//\\\\/\\".repeat(10)+"|"+ profileForm );
        Profile profile2bSaved = profileForm.getNewProfile();
        System.out.println("ACTUAL Profile: "+profile2bSaved.toString());
        Unicorn unicorn2bSaved = profileForm.getNewUnicorn(profile2bSaved);

        profiles.save(profile2bSaved);
        unicorns.save(unicorn2bSaved);

        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(Model model){
        //set model attributes if needed
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(Model model, @ModelAttribute LoginFormData loginForm, HttpServletRequest request, HttpServletResponse response ){

        String loginName = loginForm.getUsername();
        String loginPw = "{noop}"+loginForm.getPassword();
        Cookie[] cookies = request.getCookies();

        //Check if this someone is already logged in
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(loggedInUserCookieName)) {
                    System.out.println("Someone is already logged in, redirecting to front page");
                    return indexPage(model, request);
                }
            }
        } // else: no cookies, therefore noone is logged in
        System.out.println(
                "Login attempt detected with username="+
                loginName+
                ",  password="
                +loginForm.getPassword());


        // TODO Check if login credentials correct
        boolean authenticated = false;
        List<Unicorn> unicornEntries = unicorns.findAll();
        for (Unicorn unicornEntry:unicornEntries) { // Should be more efficient
            String nickname = unicornEntry.profile.nickname;
            if (unicornEntry.email.equals(loginName) || nickname.equals(loginName)){
                if (unicornEntry.password.equals(loginPw)){
                    System.out.println("Login auth. successful");
                    authenticated = true;
                    break;
                } else {
                    System.out.println("Password incorrect");
                    return "login";
                }
            }
        }
        if (!authenticated){
            System.out.println("No such user found");
            return "login";
        }


        if (cookies != null) {
            System.out.println("Existing cookies found: " + Arrays.stream(cookies).map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", ")));
            System.out.println("Overwriting cookie with username="+loginName+"<<<CARE: Username cookie shouldn't be present here>>>");
        } else {
            System.out.println("No Cookies Found, adding my own for username="+loginName);
        }
        Cookie cookie = new Cookie(loggedInUserCookieName, loginName);
        response.addCookie(cookie);


        return "redirect:/";//Redirect causes URL change, and therefore the triggering of the correct RequestMapping?
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response){
        System.out.println("Attempting to log out currently logged in user");
        Cookie[] cookies = request.getCookies();

        if (cookies == null){
            System.out.println("No cookies, therefore noone logged in currently");
            return "redirect:/";
        }

        //Check if this someone is already logged in
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals(loggedInUserCookieName)){
                System.out.println(cookie.getValue()+" is currently logged in, logging them out");

                Cookie cookieInstantExpiration = new Cookie(cookie.getName(), cookie.getValue());
                cookieInstantExpiration.setMaxAge(0);
                response.addCookie(cookieInstantExpiration);

                return "redirect:/";
            }
        }
        System.out.println("No logged in cookie found");
        return "redirect:/";
    }

    @RequestMapping("/discovery")
    public String discovery(Model model, HttpServletRequest request){
        List<Profile> profileList = new ArrayList<>();
        List<String> imageStrings = new ArrayList<>();
        Long loggedInProfileId = getProfileIdOfLoggedInUser(request.getCookies());

        for (Profile profile: profiles.findAll()) {
            if (profile.id == loggedInProfileId) continue; // Skip currently logged in user
            profileList.add(profile);
            imageStrings.add(photoService.getLatestProfilePictureName(profile));
        }

        model.addAttribute("profileList", profileList);
        model.addAttribute("imageNameList", imageStrings);

        return "discovery";
    }

    @RequestMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute("profiles", profiles.findAll());
        return "search";
    }


    /*@RequestMapping("/registration")
    public String registerPage(Model model){
        model.addAttribute("attr", new ProfileFormData());
        return "registration";
    }
    @PostMapping("/registration")
    public void registerPost(@ModelAttribute ProfileFormData form){
        System.out.println("\\     /");
        System.out.println(" \\   /");
        System.out.println("  \\ /");
        System.out.println("Received Registration form:");
        System.out.println(form.toString());
    }*/
}