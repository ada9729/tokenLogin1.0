package select.system.service;

import select.base.Result;
import select.system.dto.User;
import select.util.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author yeyuting
 * @create 2021/1/25
 */
public interface UserService {

    public User selectByName(String username) ;

    public User selectById(int id) ;

    public List<User> selectAll() ;

    public boolean insertOne(User user) ;

    public boolean insertMany(List<User> userList) ;

    public boolean updateOne(User user) ;

    public boolean deleteById(int id) ;

    public List<User> SelectByStartIndexAndPageSize(int startIndex , int pageSize) ;

    public List<User> selectByMap(Map<String, Object> map) ;

    public List<User> SelectByPageBean(PageBean pageBean) ;

    public List<User> selectByLike(Map<String , Object> map) ;

    public Result loginCheck (User user , HttpServletResponse response) ;

    //查询金额
    public User selectByUserName(String username) ;

    //转账
    public String  transferAccount(Double accountMoney , String targetAccount , HttpServletRequest request) ;

    //存钱
    public String saveMoney(Double accountMoney , HttpServletRequest request) ;

    //取钱
    public String withdrawMoney(Double accountMoney , HttpServletRequest request) ;


}
