package com.gdyzy.rind.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gdyzy.rind.common.BaseContext;
import com.gdyzy.rind.common.R;
import com.gdyzy.rind.entity.ShoppingCart;
import com.gdyzy.rind.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){

        log.info("购物车数据：{}", shoppingCart);

        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (dishId != null){
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else {
            //查询当前菜品或者套餐是否在购物车中
            //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
        if (cartServiceOne != null){
            //如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return R.success(cartServiceOne);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //SQL:delete from shopping_cart where user_id = ?
        shoppingCartService.clean();
        return R.success("清空购物车成功");
    }

    /*@PostMapping("/sub")
    @Transactional
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (dishId != null){
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            ShoppingCart cart1 = shoppingCartService.getOne(queryWrapper);
            cart1.setNumber(cart1.getNumber() - 1);
            shoppingCartService.updateById(cart1);
            return R.success(cart1);
        }

        Long setmealId = shoppingCart.getSetmealId();
        if (setmealId != null){
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
            ShoppingCart cart2 = shoppingCartService.getOne(queryWrapper);
            cart2.setNumber(cart2.getNumber() - 1);
            shoppingCartService.updateById(cart2);
            return R.success(cart2);
        }
        return R.error("操作异常");
    }*/

    @PostMapping("/sub")
    @Transactional
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (dishId != null){
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            ShoppingCart cart1 = shoppingCartService.getOne(queryWrapper);
            cart1.setNumber(cart1.getNumber() - 1);
            Integer latestNumber = cart1.getNumber();
            if (latestNumber > 0){
                shoppingCartService.updateById(cart1);
            }else if (latestNumber == 0){
                shoppingCartService.removeById(cart1.getUserId());
            }else if (latestNumber < 0){
                return R.error("操作异常");
            }
            return R.success(cart1);
        }

        Long setmealId = shoppingCart.getSetmealId();
        if (setmealId != null){
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId)
                    .eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            ShoppingCart cart2 = shoppingCartService.getOne(queryWrapper);
            cart2.setNumber(cart2.getNumber() - 1);
            Integer latestNumber = cart2.getNumber();
            if (latestNumber > 0){
                shoppingCartService.updateById(cart2);
            }else if (latestNumber == 0){
                shoppingCartService.removeById(cart2.getUserId());
            }else if (latestNumber < 0){
                return R.error("操作异常");
            }
            return R.success(cart2);
        }
        return R.error("操作异常");
    }
}
