package com.example.springbootdemo.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.Util.WeiBoShareUtil;
import com.example.springbootdemo.bean.UserModel;
import com.example.springbootdemo.service.WeiBoService;
import org.apache.commons.lang.math.LongRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class WeiboController {

    @Autowired
    private WeiBoService weiBoService;

    @GetMapping("/index")
    public String list(@PageableDefault(size = 50,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable,
                                              Model model) {

        List<HashMap<String, Object>> dataList;
        dataList = new ArrayList<>();

        //记录数值
        long number = 0;


        JSONObject jsonObject = WeiBoShareUtil.instance().getWeiBoInfo("s");

        JSONArray wbArray = jsonObject.getJSONArray("Wb");

            for (int i = 0; i <wbArray.size() ; i++) {
                JSONObject object = wbArray.getJSONObject(i);
                String numObject = (object.getString("id"));
                String nameObject = object.getString("name");
                String votesObject = object.getString("votes");

                if (i+1<wbArray.size()){
                    JSONObject object1 = wbArray.getJSONObject(i+1);
                    String votesObject1 = object1.getString("votes");
                    number = Str(votesObject) - Str(votesObject1);
                }

                UserModel userModel = new UserModel();
                userModel.setId(Integer.valueOf(numObject));
                userModel.setName(nameObject);
                userModel.setVotes(votesObject);
                userModel.setDiscrepancy(number);

                weiBoService.save(userModel);
            }
        Page<UserModel> page = weiBoService.findAll(pageable);
        model.addAttribute("page",page);
        return "index";
    }


    //把票数转化为int类型数字
    public long Str(String votesObject){
        String str1 = "";
        if (votesObject.indexOf(",")>-1){
            votesObject = votesObject.replace("票","");  //剔除“票”
            String[] str = votesObject.split(",");
            for (int j = 0; j < str.length; j++) {
                str1 = str1+""+str[j];
            }
        }
        return Long.valueOf(str1);
    }
}
