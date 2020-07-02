package swjtu.zkd.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swjtu.zkd.miaosha.domain.MiaoshaOrder;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.domain.OrderInfo;
import swjtu.zkd.miaosha.redis.MiaoshaKey;
import swjtu.zkd.miaosha.redis.RedisService;
import swjtu.zkd.miaosha.util.MD5Util;
import swjtu.zkd.miaosha.util.UUIDUtil;
import swjtu.zkd.miaosha.vo.GoodsVO;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    private static char[] ops = new char[]{'+', '-', '*'};

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goods) {
        boolean success = goodsService.reduceStock(goods);
        OrderInfo orderInfo = null;
        if (success) {
            orderInfo = orderService.createOrder(user, goods);
        } else {
            setMiaoshaGoodsOver(goods.getId());
        }
        return orderInfo;
    }

    private void setMiaoshaGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.goodsOver, String.valueOf(goodsId), true);
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdAndGoodsId(userId, goodsId);
        if (miaoshaOrder != null) {
            return miaoshaOrder.getOrderId();
        } else {
            boolean isOver = getMiaoshaGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private boolean getMiaoshaGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.goodsOver, String.valueOf(goodsId));
    }

    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if (path == null || path.length() == 0) {
            return false;
        }
        String realPath = redisService.get(MiaoshaKey.miaoshaPath, "" + user.getId() + "_" + goodsId, String.class);
        return path.equals(realPath);
    }

    public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
        String str = MD5Util.MD5(UUIDUtil.uuid() + "654321");
        redisService.set(MiaoshaKey.miaoshaPath,  "" + user.getId() + "_" + goodsId, str);
        return str;
    }

    public BufferedImage createVerifyCode(MiaoshaUser user, long goodsId) {
        int width = 80;
        int height = 32;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        String verifyCode = generateVerifyCodeString(random);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.verifyCode, user.getId() + "_" + goodsId, rnd);
        return image;
    }

    private int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String generateVerifyCodeString(Random random) {
        int n1 = random.nextInt(10);
        int n2 = random.nextInt(10);
        int n3 = random.nextInt(10);
        char op1 = ops[random.nextInt(3)];
        char op2 = ops[random.nextInt(3)];
        return "" + n1 + op1 + n2 + op2 + n3;
    }

    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
        String key = user.getId() + "_" + goodsId;
        Integer realCode = redisService.get(MiaoshaKey.verifyCode, key, Integer.class);
        if (realCode == null || realCode != verifyCode) {
            return false;
        }
        redisService.del(MiaoshaKey.verifyCode, key);
        return true;
    }
}
