package swjtu.zkd.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.redis.RedisService;
import swjtu.zkd.miaosha.service.GoodsService;
import swjtu.zkd.miaosha.service.MiaoshaUserService;
import swjtu.zkd.miaosha.vo.GoodsVO;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        List<GoodsVO> goodsList = goodsService.listGoodsVO();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(@PathVariable("goodsId") long goodsId, Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus;
        int remainSeconds;
        if (now < startAt) {    //秒杀未开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {   //秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {    //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
