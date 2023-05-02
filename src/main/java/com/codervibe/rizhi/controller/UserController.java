package com.codervibe.rizhi.controller;

import com.codervibe.rizhi.model.Comment;
import com.codervibe.rizhi.model.Daily;
import com.codervibe.rizhi.model.Friend;
import com.codervibe.rizhi.model.User;
import com.codervibe.rizhi.service.CommentService;
import com.codervibe.rizhi.service.DailyService;
import com.codervibe.rizhi.service.FriendService;
import com.codervibe.rizhi.service.UserService;
import com.codervibe.rizhi.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {

    // 依赖注入
    @Autowired
    private UserService userService;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private CommentService commentService;
    /**
     * 用户登录
     */
    @RequestMapping(value = "/login.action", method = RequestMethod.POST)
    public String login(String usercode,String password, Model model,
                        HttpSession session) {

        // 通过账号和密码查询用户
        String p = MD5Util.encrypt(password);
        User user = userService.findUser(usercode, p);
        if(user != null){
            // 将用户对象添加到Session
            session.setAttribute("USER_SESSION", user);
            List<Daily> dailyList = dailyService.findDailyList(user.getUser_id());
            model.addAttribute("dailyCount", dailyList.size());
            List<Friend> friendList = friendService.findFriendList(user.getUser_id());
            for(int i=0;i<friendList.size();i++) {
                List<Daily> daily = dailyService.findFriendDaily(friendList.get(i).getFriend_id());
                dailyList.addAll(daily);
            }
            mysort(dailyList);
            /**设置**/
            for(int i=0;i<dailyList.size();i++){
                String picture_path = "img/"+dailyList.get(i).getDaily_picture();
                dailyList.get(i).setDaily_picture(picture_path);
            }
            for(int i=0;i<dailyList.size();i++) {
                List<Comment> comments = commentService.findComments(dailyList.get(i).getDaily_id());
                dailyList.get(i).setComments(comments);
            }
            model.addAttribute("dailyList",dailyList);
            // 将用户对象添加到Session
            //session.setAttribute("USER_SESSION", user);

            model.addAttribute("friendCount", friendList.size());
            String user_picture = "img/"+user.getDaily_picture();
            model.addAttribute("user_picture",user_picture);
            // 跳转到主页面
            return "home";
            //return "redirect:student/daily/list.action";   //有输出查询内容
            //return "redirect:manage/list.action";   //有输出查询内容
        }
        model.addAttribute("msg", "账号或密码错误，请重新输入！");
        // 返回到登录页面
        return "login";
    }



    /**
     * 模拟其他类中跳转到客户管理页面的方法
     */
    @RequestMapping(value = "/toStudent.action")
    public String toStudent() {
        return "student";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout.action")
    public String logout(HttpSession session) {
        // 清除Session
        session.invalidate();
        // 重定向到登录页面的跳转方法
        return "redirect:login.action";
    }
    /**
     * 向用户登陆页面跳转
     */
    @RequestMapping(value = "/login.action", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    public void mysort(List dailyList){
        Collections.sort(dailyList, new Comparator<Daily>(){
            public int compare(Daily d1, Daily d2) {
                if(d1.getDaily_id() < d2.getDaily_id()){
                    return 1;
                }
                if(d1.getDaily_id() == d2.getDaily_id()){
                    return 0;
                }
                return -1;
            }
        });
    }
}
