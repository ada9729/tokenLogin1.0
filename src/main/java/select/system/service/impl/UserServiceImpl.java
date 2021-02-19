package select.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import select.base.Constants;
import select.base.Result;
import select.constants.BaseEnums;
import select.constants.TransactionType;
import select.system.dao.UserMapper;
import select.system.dto.User;
import select.system.service.UserService;
import select.util.JedisUtil;
import select.util.PageBean;
import select.util.Results;
import select.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author yeyuting
 * @create 2021/1/25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper ;

    @Autowired
    TokenUtil tokenUtil ;

    @Autowired
    JedisUtil jedisUtil ;

    public User selectByName(String username) {
      return  userMapper.selectByName(username) ;
    }

    public User selectById(int id){
        return userMapper.selectById(id) ;
    }

    public List<User> selectAll(){
        return userMapper.selectAll() ;
    }

    public boolean insertOne(User user) {
        return userMapper.insertOne(user) ;
    }

    public boolean insertMany(List<User> userList) {
        return userMapper.insertMany(userList) ;
    }
    public boolean updateOne(User user){
        return userMapper.updateOne(user) ;
    }

    public boolean deleteById(int id){
        return userMapper.deleteById(id) ;
    }

    public List<User> SelectByStartIndexAndPageSize(int startIndex , int pageSize) {
        return userMapper.SelectByStartIndexAndPageSize(startIndex,pageSize) ;
    }

    public List<User> selectByMap(Map<String ,Object> map){
        return userMapper.selectByMap(map) ;
    }

    public List<User> SelectByPageBean(PageBean pageBean) {
        return userMapper.SelectByPageBean(pageBean) ;
    }

    public List<User> selectByLike(Map<String , Object> map){
        return userMapper.selectByLike(map) ;
    }

    public Result loginCheck(User user , HttpServletResponse response){
        User user1 = userMapper.selectByName(user.getUserName()) ;
        if(user1 == null ){
            return Results.failure("用户不存在") ;
        }else if(!user1.getPassword().equals(user.getPassword())){
            return Results.failure("密码输入错误！") ;
        }
        Jedis jedis = jedisUtil.getSource() ;
        String jedisKey = jedis.get(user1.getUserName()) ;
        if(jedisKey != null){
            jedisUtil.delString(user1.getUserName());
        }
        String token = tokenUtil.generateToken(user1) ;
        System.out.println("token:" + token);
        user1.setToken(token);
        jedisUtil.tokenToJedis(user1);
        return Results.successWithData(user1);
    }

    //查询金额
    public User selectByUserName(String username) {
        return userMapper.selectByUserName(username) ;
    }

    //转账
    public String transferAccount(Double accountMoney , String targetAccount , HttpServletRequest request){
        Jedis jedis = jedisUtil.getSource() ;
        String token = request.getHeader("token") ;
        String userName = jedis.get(token) ;
        User user = userMapper.selectByName(userName) ;
        double nowAccountMoney = user.getAccount() ;
        if(accountMoney > nowAccountMoney){
            return "余额不足" ;
        }
        User user1 = userMapper.selectByName(targetAccount) ;
        if (user1.equals(null)){
            return "对方账户不存在" ;
        }
        //转出账户余额更新
        boolean result = userMapper.updateAccountOut(accountMoney , userName) ;
        //转入账户余额更新
        boolean result1 = userMapper.updateAccountIn(accountMoney , user1.getUserName()) ;
        if ((result == false)||(result1 == false) ){
            return "转账操作失败" ;
        }
        //转账记录生成------------
        //String accountType = TransactionType.WITHDRAWMONEY ;
        //出账记录生成
        boolean insertReult = userMapper.accountOutInsert(userName ,user.getAccount() ,  accountMoney , targetAccount , TransactionType.WITHDRAWMONEY ) ;
        //入账记录生成
        //String accountType1 = TransactionType.SAVEMONEY ;
        boolean insertReult1 = userMapper.accountInInsert(user1.getUserName() , user1.getAccount() , accountMoney , user.getUserName() , TransactionType.SAVEMONEY ) ;

        if((insertReult == false) || (insertReult1 == false)){
            return "转账记录生成失败" ;
        }

        return "转账成功！" ;
    }

    //存钱
    public String saveMoney(Double accountMoney , HttpServletRequest request){
        Jedis jedis = jedisUtil.getSource() ;
        String token = request.getHeader("token") ;
        String userName = jedis.get(token) ;
        User user = userMapper.selectByName(userName) ;
        //存入余额更新
        boolean result = userMapper.updateAccountIn(accountMoney , userName) ;
        if(result = false){
            return "存入失败" ;
        }
        //存入记录生成
        boolean insertResult = userMapper.accountInInsert(userName ,user.getAccount() , accountMoney , userName , TransactionType.SAVEMONEY) ;
        if((insertResult == false)){
            return "入账记录生成失败" ;
        }
        return "成功存入" + accountMoney + "元！"  ;
    }

    //取钱
    public String withdrawMoney(Double accountMoney , HttpServletRequest request){
        Jedis jedis = jedisUtil.getSource() ;
        String token = request.getHeader("token") ;
        String userName = jedis.get(token) ;
        User user = userMapper.selectByName(userName) ;
        double nowAccountMoney = user.getAccount() ;
        if(accountMoney > nowAccountMoney){
            return "余额不足" ;
        }
        boolean result = userMapper.updateAccountOut(accountMoney , userName) ;
        if(result = false){
            return "取钱失败" ;
        }
        //出账记录生成
        boolean insertResult = userMapper.accountOutInsert(userName ,user.getAccount() , accountMoney , userName , TransactionType.WITHDRAWMONEY) ;
        if((insertResult == false)){
            return "出账记录生成失败" ;
        }
        return "成功取出" + accountMoney + "元！"  ;
    }


}
