package com.example.springbootdemo.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.Util.WeiBoShareUtil;
import com.example.springbootdemo.bean.UserModel;
import com.example.springbootdemo.service.WeiBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class WeiboController {

    @Autowired
    private WeiBoService weiBoService;

    @GetMapping("/index")
    public List<HashMap<String, Object>> list(Model model) {

        List<HashMap<String, Object>> dataList;
        dataList = new ArrayList<>();

        JSONObject jsonObject = WeiBoShareUtil.instance().getWeiBoInfo("s");

        JSONArray wbArray = jsonObject.getJSONArray("Wb");
        for (int i = 0; i <wbArray.size() ; i++) {
            JSONObject object = wbArray.getJSONObject(i);
            String numObject = object.getString("num");
            String nameObject = object.getString("name");
            String votesObject = object.getString("votes");

            System.out.println("---"+numObject+"----");
            System.out.println("---"+nameObject+"----");
            System.out.println("---"+votesObject+"----");

            UserModel userModel = new UserModel();
            userModel.setNum(Integer.valueOf(numObject));
            userModel.setName(nameObject);
            userModel.setVotes(votesObject);
            weiBoService.save(userModel);

            HashMap<String , Object> map = new HashMap<>();
            map.put("num",numObject);
            map.put("name",nameObject);
            map.put("votes",votesObject);
            dataList.add(map);
        }

        System.out.println("json-----"+dataList);

        model.addAttribute("json",dataList);
        return dataList;
    }

}
